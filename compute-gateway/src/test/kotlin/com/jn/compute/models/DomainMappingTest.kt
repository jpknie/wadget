package com.jn.compute.models

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.jn.compute.models.entity.toDomain
import com.jn.compute.models.entity.toEntity
import com.jn.domain.MatchMode
import com.jn.domain.Rule
import com.jn.domain.Tag
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DomainMappingTest {
    private val mapper = jacksonObjectMapper()
    private val tag = Tag("tag-1", "Food", 0.123456789012345, null, true, 500)

    @Test
    fun tagPersistenceMappingIsLosslessAndPreservesJson() {
        val entity = tag.toEntity()
        assertEquals(tag, entity.toDomain())
        assertEquals(mapper.readTree(mapper.writeValueAsString(entity)), mapper.readTree(mapper.writeValueAsString(tag)))
        assertEquals(tag, mapper.readValue<Tag>(mapper.writeValueAsString(entity)))
    }

    @Test
    fun rulePersistenceMappingPreservesRelationshipAndJson() {
        for (mode in MatchMode.values()) {
            val rule = Rule("rule-1", "food", 2, mode, true, tag)
            val entity = rule.toEntity()
            assertEquals(rule, entity.toDomain())
            assertEquals(mapper.readTree(mapper.writeValueAsString(entity)), mapper.readTree(mapper.writeValueAsString(rule)))
            assertEquals(rule, mapper.readValue<Rule>(mapper.writeValueAsString(entity)))
        }
    }
}