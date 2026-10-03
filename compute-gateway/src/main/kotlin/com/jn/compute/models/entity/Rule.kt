package com.jn.compute.models.entity

import com.jn.domain.MatchMode
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table


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
