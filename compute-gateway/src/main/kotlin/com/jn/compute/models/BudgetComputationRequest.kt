package com.jn.compute.models

data class BudgetComputationRequest(
    val income: Double,
    val fixedCosts: List<FixedCost>,
    val tagConfigs: List<TagConfig>,
)