package com.jn.compute.models.entity

import com.jn.domain.MatchMode
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table


@Entity
@Table(name = "rules")
data class Rule(
    @Id val id: String,
    val pattern: String,
    val priority: Int,
    @Enumerated(EnumType.STRING)
    val mode: MatchMode,
    val caseInsensitive: Boolean,

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "tag_id", nullable = false)
    val tag: Tag
)
