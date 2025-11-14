/*
  @author Jani Nieminen, jpkniem@gmail.com
  License: Do what you want with this code :D
*/

#include "BudgetEngine.h"
#include <cmath>
#include <algorithm>
#include <random>


BudgetEngine::BudgetEngine(double income) : income_(income) {}

void BudgetEngine::setIncome(double income) {
    income_ = income;
}

double BudgetEngine::income() const {
    return income_;
}

void BudgetEngine::addExpense(const Expense& expense) {
    expenses_.push_back(expense);
}

double BudgetEngine::totalExpenses() const {
    double total = 0.0;
    for (const auto& expense : expenses_) {
        total += expense.amount;
    }
    return total;
}

double BudgetEngine::remainingBudget() const {
    return income_ - totalExpenses();
}

const std::vector<Expense>& BudgetEngine::expenses() const {
    return expenses_;
}

void BudgetEngine::setCategoryConfigs(const std::vector<CategoryConfig>& configs) {
    categoryConfigs_ = configs;
}

double BudgetEngine::utility(double amount, double weight, double softness) {
    if(softness <= 0.0) softness = 1e-6; // Prevent division by zero
    if(amount < 0.0) amount = 0.0; // Utility is non-decreasing
    return weight * std::log1p(1.0 + amount / softness);
}


std::vector<AllocationResult>
BudgetEngine::allocateWaterFilling(double epsilon, int maxIterations) const
{
    return allocateWaterFillingInternal(income_, categoryConfigs_, epsilon, maxIterations);
}


double
BudgetEngine::evaluateUtility(const std::vector<AllocationResult>& allocation) const {
    /* Remarks: MAP TAGS -> CONFIG ... totalU is total UTILITY!*/
    double totalU = 0.0;
    for (const auto& alloc: allocation) {
        auto it = std::ranges::find_if(
            categoryConfigs_.begin(), categoryConfigs_.end(),
            [&](const CategoryConfig& cfg) {
                return cfg.tag == alloc.tag;
            });
        
            if (it != categoryConfigs_.end()) {
                totalU += utility(alloc.amount, it->weight, it->softness);
            }
    }
    return totalU;
}
    std::vector<AllocationResult>
    BudgetEngine::monteCarloBestAllocation(
        int scenarios,
        double weightNoiseStddev, 
        double incomeNoiseStddev,
        unsigned int randomSeed
    ) const {
        std::mt19937 rng(randomSeed ? randomSeed : std::random_device{}());
        std::normal_distribution<double> weightNoise(0.0, weightNoiseStddev);
        std::normal_distribution<double> incomeNoise(0.0, incomeNoiseStddev);


        double bestUtility = -std::numeric_limits<double>::infinity();
        std::vector<AllocationResult> bestAllocation;

        for (int s = 0; s < scenarios; ++s) {
            double incomeFactor = 1.0 + incomeNoise(rng);
            double scenarioIncome = income_ * incomeFactor;

            std::vector<CategoryConfig> scenarioConfigs = categoryConfigs_;
            for(auto& cfg : scenarioConfigs) {
                double factor = 1.0 + weightNoise(rng);
                if (factor < 0.0) factor = 0.0;
                cfg.weight *= factor;
            }

            auto allocation = allocateWaterFillingInternal(
                scenarioIncome,
                scenarioConfigs,
                1e-4,
                100
            );
            
            double totalU = 0.0;
            for(size_t i = 0; i < allocation.size(); ++i) {
                totalU += utility(
                    allocation[i].amount,
                    scenarioConfigs[i].weight,
                    scenarioConfigs[i].softness
                );
            }

            if (totalU > bestUtility) {
                bestUtility = totalU;
                bestAllocation = allocation;
            }
        }

        return bestAllocation;
    }

    /* Internal helpers */

    double BudgetEngine::allocationForLambda(
        double lambda,
        const std::vector<CategoryConfig>& configs,
        std::vector<double>& outAmounts
    ) {
        double sum = 0.0;
        outAmounts.resize(configs.size());

        for (size_t i = 0; i < configs.size(); ++i) {
            const auto& cfg  = configs[i];


            double b = cfg.softness;
            double w = cfg.weight;
            if (b <= 0.0) b = 1e-6;

            double x;

            if (lambda <= 0.0 || w <= 0.0) {
                x = cfg.minAmount;
            } else {
                x = (w / lambda) - b;
                if (x < cfg.minAmount) x = cfg.minAmount;
                if (x > cfg.maxAmount) x = cfg.maxAmount;
            }

            if (x < 0.0) x = 0.0;

            outAmounts[i] = x;
            sum += x;
        }
        return sum;
    }
 
    std::vector<AllocationResult>
    BudgetEngine::allocateWaterFillingInternal(
        double income,
        const std::vector<CategoryConfig>& configs,
        double epsilon,
        int maxIterations
    ) const {
        std::vector<AllocationResult> result;

        if (configs.empty()) {
            return result;
        }

        /** Remaining budget after fixed expenses is "this" */
        double fixedExpenses = totalExpenses();
        double B = income - fixedExpenses;
        if (B <= 0.0) {
            result.reserve(configs.size());
            for (const auto& cfg: configs) {
                AllocationResult r;
                r.tag = cfg.tag;
                r.amount = std::max(0.0, cfg.minAmount);
                r.utility = utility(r.amount, cfg.weight, cfg.softness);
                result.push_back(r);
            }
            return result;
        }

        /* If min sum alread exceeds B, just scale mins or clip?
           Here we just honor mins and let sum > B (user can detect this) */
        
        double minSum = 0.0;

        for (const auto& cfg: configs) {
            minSum += cfg.minAmount;
        }

        //Boundaries for lambda
        //lambda hi: very high marginal utility -> allocations close to min
        //lambda lo: very low marginal utility -> allocations close to max
        double lambda_lo = 1e-9;
        double lambda_hi = 0.0;

        for (const auto& cfg: configs) {
            double b = cfg.softness > 0.0 ? cfg.softness : 1e-6;
            double w = cfg.weight;

            double d_min = w / (b + cfg.minAmount);
            double d_max = w / (b + cfg.maxAmount);

            if(d_min > lambda_hi) lambda_hi = d_min;
            if(lambda_lo == 0.0 || d_max < lambda_lo) lambda_lo = d_max;
        }

        if(lambda_lo <= 0.0) {
            lambda_lo = 1e-9;
        }
        if(lambda_hi < lambda_lo) {
            lambda_hi =  lambda_lo * 10.0;
        }

        std::vector<double> amounts(configs.size());

        /* If even all max allocations are BELOW B, just give maxes*/
        double maxSum = 0.0;
        for(const auto& cfg: configs) {
            maxSum += cfg.maxAmount;
        }
        if(maxSum <= B) {
            result.reserve(configs.size());
            for (const auto& cfg: configs) {
                AllocationResult r;
                r.tag = cfg.tag;
                r.amount = cfg.maxAmount;
                r.utility = utility(r.amount, cfg.weight, cfg.softness);
                result.push_back(r);
            }
            return result;
        }

        /* Binary search on lambda to make sum(x_i(lambda)) close to B */
        for(int iter = 0; iter < maxIterations; ++iter) {
            double lambda_mid = 0.5 * (lambda_lo + lambda_hi);
            double sum = allocationForLambda(lambda_mid, configs, amounts);

            if (std::fabs(sum - B) <= epsilon) {
                break; // Found satisfactory allocation
            }

            if (sum > B) {
                // Allocated too much, increase lambda
                lambda_lo = lambda_mid;
            } else {
                // Allocated too little, decrease lambda
                lambda_hi = lambda_mid;
            }
        }

        result.reserve(configs.size());
        for (size_t i = 0; i < configs.size(); ++i) {
            const auto& cfg = configs[i];
            AllocationResult r;
            r.tag = cfg.tag;
            r.amount = amounts[i];
            r.utility = utility(r.amount, cfg.weight, cfg.softness);
            result.push_back(r);
        }
        return result;
    }