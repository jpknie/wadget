package com.jn.wadget.state

import com.jn.domain.BudgetComputationResult

data class BudgetScreenState(
    val budgetComputationResult: BudgetComputationResult? = null,
    val isLoading: Boolean = false
)

