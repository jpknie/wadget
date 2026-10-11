package com.jn.domain

import com.jn.domain.Allocation

data class BudgetComputationResult (
    val allocations: List<Allocation>,
    val totalAllocated: Double,
    val unallocatedAmount: Double
)