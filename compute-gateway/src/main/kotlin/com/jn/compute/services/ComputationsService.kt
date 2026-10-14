package com.jn.compute.services

import com.jn.domain.BudgetComputationResult

interface ComputationsService {
    fun compute(): BudgetComputationResult;
}