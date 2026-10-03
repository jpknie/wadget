package com.jn.domain

import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val id: String,
    val name: String,
    val weight: Double,
    val capCents: Long?,
    val mandatory: Boolean,
    val mandatoryCents: Long?
)