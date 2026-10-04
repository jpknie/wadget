package com.jn.compute.models.entity

import com.jn.domain.Rule as DomainRule
import com.jn.domain.Tag as DomainTag

fun Tag.toDomain(): DomainTag = DomainTag(
    id = id, 
    name = name, 
    weight = weight, 
    softness = softness, 
    capCents = capCents, 
    mandatory = mandatory, 
    mandatoryCents = mandatoryCents
)

fun DomainTag.toEntity(): Tag = Tag(
    id = id, 
    name = name, 
    weight = weight, 
    softness = softness, 
    capCents = capCents, 
    mandatory = mandatory, 
    mandatoryCents = mandatoryCents
)

fun Rule.toDomain(): DomainRule = DomainRule(id, pattern, priority, mode, caseInsensitive, tag.toDomain())

fun DomainRule.toEntity(): Rule = Rule(id, pattern, priority, mode, caseInsensitive, tag.toEntity())