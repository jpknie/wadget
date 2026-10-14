package com.jn.wadget.state

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jn.domain.Allocation
import com.jn.domain.BudgetComputationResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


class BudgetScreenViewModel : ViewModel() {
    private var _state = mutableStateOf(BudgetScreenState(
        // Mock computation result
        budgetComputationResult = BudgetComputationResult(
            allocations = listOf(
                Allocation("Food", 500.0),
                Allocation("Beer", 200.0),
                Allocation("Medicine", 100.0)
            ),
            totalAllocated = 800.0,
            unallocatedAmount = 0.0
        ),
        isLoading = false
    ))
    val state: State<BudgetScreenState> get() = _state
    val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun setLoading(loading: Boolean) {
        _state.value = _state.value.copy(isLoading = loading)
    }

    fun setResult(result: BudgetComputationResult) {
        _state.value = _state.value.copy(budgetComputationResult = result)
    }

    fun getResult() = _state.value.budgetComputationResult

    fun getLoading() = _state.value.isLoading
}

