package com.jn.compute.models.entity


import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "tags")
class Tag(
    @Id val id: String,
    val name: String,
    val weight: Double,
    val softness: Double,
    val capCents: Long?,
    val mandatory: Boolean,
    val mandatoryCents: Long?
)
