package com.jn.wadget.models

import kotlin.random.Random

enum class CategoryType { INCOME, EXPENSE }

data class Category(
    val id: Long = generateId(),
    val name: String,
    val type: CategoryType,
    val isMandatory: Boolean = false,
    val weight: Float = 0f
)

fun generateId(): Long = Random.nextLong()