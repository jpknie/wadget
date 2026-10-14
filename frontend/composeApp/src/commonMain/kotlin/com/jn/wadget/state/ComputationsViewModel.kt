package com.jn.wadget.state

import com.jn.domain.BudgetComputationResult
import com.jn.wadget.computeclient.ComputeGatewayClient

class ComputationsViewModel(
    private val computeGatewayClient: ComputeGatewayClient,

) {

    suspend fun onCalculatePlan(): BudgetComputationResult = computeGatewayClient.computeAllocation()

}