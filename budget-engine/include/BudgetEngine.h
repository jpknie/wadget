/*
  @author Jani Nieminen, jpkniem@gmail.com
  License: Do what you want with this code :D
*/
#pragma once

#include <vector>
#include <string>

struct Expense {
    double amount;
    std::string tag;
};

struct CategoryConfig {
    std::string tag;

    double weight = 1.0;
    double softness = 50.0; // "b" variable controls how fast returns diminish

    /* Hard bound for allocation */
    double minAmount = 0.0;
    double maxAmount = 1e9;
};

struct AllocationResult {
    std::string tag;
    double amount = 0.0;
    double utility = 0.0;
};

class BudgetEngine {
public:
    explicit BudgetEngine(double income);

    void setIncome(double income);
    double income() const;

    void addExpense(const Expense& expense);
    const std::vector<Expense>& expenses() const;
    double totalExpenses() const;
    double remainingBudget() const;
    
    void setCategoryConfigs(const std::vector<CategoryConfig>& configs);

    /* U(x) = weight * log(1 + x/b) where b is softness */
    static double utility(double amount, double weight, double softness);

    /*
        Allocate remaining budget between categories and water-fillling,
        assuming diminishing returns utility per category.
        epsilon = precision of the lambda search
        maxIterations = safety for the binary search loop
    */
    std::vector<AllocationResult>
    allocateWaterFilling(double epsilon = 1e-4, int maxIterations = 100) const;

    /* Computes total utility of an allocation */
    double evaluateUtility(const std::vector<AllocationResult>& allocation) const;

    std::vector<AllocationResult>
    monteCarloBestAllocation(
        int scenarios,
        double weightNoiseStddev,
        double incomeNoiseStddev,
        unsigned int randomSeed = 0
    ) const;

private:
    double income_;
    std::vector<Expense> expenses_;
    std::vector<CategoryConfig> categoryConfigs_;

    /** Internal: water-filling on arbitrary configs + income */
    std::vector<AllocationResult>
    allocateWaterFillingInternal(
        double income,
        const std::vector<CategoryConfig>& configs,
        double epsilon,
        int maxIterations
    ) const;

    /** Internal helper: given lambda, compute allocations and sum */
    static double
    allocationForLambda(
        double lambda,
        const std::vector<CategoryConfig>& configs,
        std::vector<double>& outAmounts
    );
};
