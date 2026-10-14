package com.jn.domain

import com.jn.domain.Allocation
import kotlinx.serialization.Serializable

@Serializable
data class BudgetComputationResult (
    val allocations: List<Allocation>,
    val totalAllocated: Double,
    val unallocatedAmount: Double
)