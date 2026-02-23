package com.jn.wadget.computeclient

import com.jn.wadget.models.Category

class ComputeGatewayClientImpl: ComputeGatewayClient {
    override suspend fun getCategories(): List<Category> {
        TODO("Not yet implemented")
    }

    override suspend fun addCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun computeAllocation(): List<AllocationResult> {
        TODO("Not yet implemented")
    }
}