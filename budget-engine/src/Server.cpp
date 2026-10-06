#include "Server.h"
#include "BudgetEngine.h"
#include <memory>
#include "json/json.hpp"
#include <type_traits>


nlohmann::json Server::createAllocationsResponse(const std::vector<AllocationResult>& allocations) {
    nlohmann::json allocationsJson = nlohmann::json::array();
    for (const auto& allocation : allocations) {
        allocationsJson.push_back({
            {"tag", allocation.tag},
            {"amount", allocation.amount},
            {"utility", allocation.utility}
        });
    }
    return allocationsJson;
}

Server::Server() {

    svr.Get("/hi", [](const httplib::Request &, httplib::Response &res) {
        res.set_content("Hello World!", "text/plain");
    });

    svr.Post("/compute", [this](const httplib::Request &req, httplib::Response &res) {
        auto json = nlohmann::json::parse(req.body);

        double income = json["income"];

        BudgetEngine budgetEngine(income);

        auto fixedCostsJson = json["fixedCosts"];
        for (const auto& fixedCostJson : fixedCostsJson) {
            std::string tag = fixedCostJson["tag"];
            double amount = fixedCostJson["amount"];
            budgetEngine.addExpense(Expense{amount, tag});
        }

        std::vector<CategoryConfig> tagConfigs;

        for (const auto& tagConfigJson : json["tagConfigs"]) {
            std::string tag = tagConfigJson["tag"];
            double weight = tagConfigJson["weight"];
            double softness = tagConfigJson["softness"];
            double minAmount = tagConfigJson["minAmount"];
            double maxAmount = tagConfigJson["maxAmount"];

            tagConfigs.push_back(
                CategoryConfig{tag, weight, softness, minAmount, maxAmount}
            );
        }

        budgetEngine.setCategoryConfigs(tagConfigs);
        auto allocations = budgetEngine.allocateWaterFilling();
        auto allocationsOutput = createAllocationsResponse(allocations);
        double totalAllocated = 0.0;

        for (const auto& allocation : allocations) {
            totalAllocated += allocation.amount;
        }

        double unallocatedAmount = budgetEngine.remainingBudget() - totalAllocated;

        nlohmann::json output = {
            {"allocations", allocationsOutput},
            {"totalAllocated", totalAllocated},
            {"unallocatedAmount", unallocatedAmount}
        };

        // Here you can add logic to process the request body and perform computations
        res.set_content(output.dump(), "application/json");
    });
}

void Server::start() {
    svr.listen("0.0.0.0", 8080);
}