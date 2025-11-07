package com.jn.compute.models.entity


import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tags")
class Tag(
    @Id val id: String,
    val name: String,
    val weight: Double,
    val capCents: Long?,
    val mandatory: Boolean,
    val mandatoryCents: Long?
)