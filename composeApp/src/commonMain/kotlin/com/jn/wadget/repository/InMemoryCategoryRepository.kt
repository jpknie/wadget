package com.jn.wadget.repository

import com.jn.wadget.models.Category
import com.jn.wadget.models.CategoryType

class InMemoryCategoryRepository {

    private val categories = mutableListOf<Category>(
        Category(name = "Food", type = CategoryType.EXPENSE, isMandatory = true, weight = 20f),
        Category(name = "Fuel", type = CategoryType.EXPENSE, weight = 10f),
        Category(name = "Salary", type = CategoryType.INCOME, weight = 100f)
    )

    fun getAll(): List<Category> = categories.toList()

    fun add(category: Category) {
        categories.add(category)
    }

    fun update(category: Category) {
        val index = categories.indexOfFirst { it.id == category.id }
        if (index >= 0) categories[index] = category
    }
}