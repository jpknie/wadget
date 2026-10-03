package com.jn.compute.models.entity

import com.jn.domain.Rule as DomainRule
import com.jn.domain.Tag as DomainTag

fun Tag.toDomain(): DomainTag = DomainTag(id, name, weight, capCents, mandatory, mandatoryCents)

fun DomainTag.toEntity(): Tag = Tag(id, name, weight, capCents, mandatory, mandatoryCents)

fun Rule.toDomain(): DomainRule = DomainRule(id, pattern, priority, mode, caseInsensitive, tag.toDomain())

fun DomainRule.toEntity(): Rule = Rule(id, pattern, priority, mode, caseInsensitive, tag.toEntity())