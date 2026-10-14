package com.jn.domain

import kotlinx.serialization.Serializable

@Serializable
data class Allocation(
    val tag: String,
    val amount: Double
)