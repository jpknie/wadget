package com.jn.wadget.models.dto

import kotlin.random.Random


data class Category(
    val id: Long = generateId(),
    val name: String,
    val weight: Float,
    val capCents: Long?,
    val isMandatory: Boolean,
    val mandatoryCents: Long?
)

fun generateId(): Long = Random.nextLong()