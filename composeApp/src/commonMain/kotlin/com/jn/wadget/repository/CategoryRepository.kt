package com.jn.wadget.repository

import com.jn.wadget.computeclient.ComputeGatewayClient
import com.jn.wadget.models.Category

class CategoryRepository(
    private val computeGatewayClient: ComputeGatewayClient
) {

    private val categories = mutableListOf(
        Category(name = "Food", isMandatory = false, capCents = 0, mandatoryCents = 0, weight = 20f),
        Category(name = "Fuel", isMandatory = false, capCents = 0, mandatoryCents = 0, weight = 10f),
        Category(name = "Hygiene", isMandatory = false, capCents = 0, mandatoryCents = 0, weight = 100f)
    )

    suspend fun getAll(): List<Category> = computeGatewayClient.getCategories()

    suspend fun add(category: Category) {
        computeGatewayClient.addCategory(category)
    }

    suspend fun update(category: Category) {
        computeGatewayClient.updateCategory(category)
    }
}