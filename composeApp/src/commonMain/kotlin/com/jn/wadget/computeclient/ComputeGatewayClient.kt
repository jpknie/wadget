package com.jn.wadget.computeclient

import com.jn.wadget.models.dto.Category

class AllocationResult

interface ComputeGatewayClient {
    suspend fun getCategories(): List<Category>
    suspend fun addCategory(category: Category)
    suspend fun updateCategory(category: Category)
    suspend fun computeAllocation(): List<AllocationResult>
}