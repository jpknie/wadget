package jn.compute.models

import jn.compute.models.Allocation

data class BudgetComputationResult(
    val allocations: List<Allocation>,
    val totalAllocated: Double,
    val unallocatedAmount: Double
)// THIS IS NOT TRUE, WE NEED TO REFINE THIS MODEL