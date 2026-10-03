package com.jn.domain

import kotlinx.serialization.Serializable

@Serializable
data class Rule(
    val id: String,
    val pattern: String,
    val priority: Int,
    val mode: MatchMode,
    val caseInsensitive: Boolean,
    val tag: Tag
)

@Serializable
enum class MatchMode { CONTAINS, EQUALS, REGEXP }