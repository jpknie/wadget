package com.jn.wadget.repository

import com.jn.wadget.computeclient.ComputeGatewayClient
import com.jn.wadget.models.dto.Category

class CategoryRepository(
    private val computeGatewayClient: ComputeGatewayClient
) {

    private val categories = mutableListOf(
        Category(name = "Food", isMandatory = false, capCents = 0, mandatoryCents = 0, weight = 20f),
        Category(name = "Fuel", isMandatory = false, capCents = 0, mandatoryCents = 0, weight = 10f),
        Category(name = "Hygiene", isMandatory = false, capCents = 0, mandatoryCents = 0, weight = 100f)
    )

    fun getAll(): List<Category> = categories.toList()

    suspend fun add(category: Category) {
        //categories.add(category)
        computeGatewayClient.addCategory(category)
    }

    fun update(category: Category) {
        val index = categories.indexOfFirst { it.id == category.id }
        if (index >= 0) categories[index] = category
    }
}