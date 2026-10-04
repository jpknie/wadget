/*
  @author Jani Nieminen, jpkniem@gmail.com
  License: Do what you want with this code :D
*/

#include "BudgetEngine.h"
#include "Server.h"
#include <iostream>

int main() {

  Server server;
  server.start();

/*
  BudgetEngine engine(2000.0);
  engine.addExpense({400.0, "rent"});
  engine.addExpense({150.0, "loan"});
  engine.addExpense({80.0, "phone"});

  std::vector<CategoryConfig> configs = {
    {"food",      2.0,  50.0, 150.0, 600.0},
    {"transport", 1.0,  40.0,  50.0, 300.0},
    {"fun",       0.8,  80.0,   0.0, 500.0},
    {"travel",    0.5, 120.0,   0.0, 600.0}
  };

  engine.setCategoryConfigs(configs);

  // ------------------------------
  // 1) Deterministic water-filling
  // ------------------------------
  auto alloc = engine.allocateWaterFilling();

  std::cout << "=== Deterministic water-filling ===\n";
  for (const auto& r : alloc) {
    std::cout << "Category: " << r.tag
              << ", Amount: " << r.amount
              << ", Utility: " << r.utility << '\n';
  }
  std::cout << "Total utility: "
            << engine.evaluateUtility(alloc) << "\n\n";

  // -------------------------------------------
  // 2) Monte Carlo: noisy weights / noisy income
  // -------------------------------------------
  int scenarios = 500;          // how many random worlds to try
  double weightNoiseStddev = 0.2; // 20% stddev on weights
  double incomeNoiseStddev = 0.05; // 5% stddev on income
  unsigned int seed = 42;       // fixed seed -> reproducible

  auto best = engine.monteCarloBestAllocation(
      scenarios,
      weightNoiseStddev,
      incomeNoiseStddev,
      seed
  );

  std::cout << "=== Monte Carlo best allocation ===\n";
  for (const auto& r : best) {
    std::cout << "Category: " << r.tag
              << ", Amount: " << r.amount
              << ", Utility: " << r.utility << '\n';
  }
  std::cout << "Total utility (deterministic eval): "
            << engine.evaluateUtility(best) << '\n';

  return 0; */
}
