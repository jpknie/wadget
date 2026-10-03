package com.jn.wadget.computeclient

import com.jn.wadget.models.Category

class AllocationResult

interface ComputeGatewayClient {
    suspend fun getCategories(): List<Category>

    suspend fun getCategory(id: Long): Category?
    suspend fun addCategory(category: Category)
    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(id: Long)
    suspend fun computeAllocation(): List<AllocationResult>
}