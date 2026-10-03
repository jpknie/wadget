package com.jn.domain

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class DomainSerializationTest {
    @Test
    fun tagUsesCanonicalFieldNamesAndTypes() {
        val json = """{"id":"tag-1","name":"Food","weight":0.123456789012345,"capCents":null,"mandatory":true,"mandatoryCents":500}"""
        val tag = Tag("tag-1", "Food", 0.123456789012345, null, true, 500)

        assertEquals(tag, Json.decodeFromString<Tag>(json))
        assertEquals(Json.parseToJsonElement(json), Json.parseToJsonElement(Json.encodeToString(tag)))
        assertEquals(tag.copy(mandatory = false), tag.copy().copy(mandatory = false))
    }

    @Test
    fun rulePreservesMatchModesAndNestedTag() {
        for (mode in MatchMode.values()) {
            val rule = Rule("rule-1", "food", 2, mode, true, Tag("tag-1", "Food", 20.0, 0, false, null))
            assertEquals(rule, Json.decodeFromString<Rule>(Json.encodeToString(rule)))
        }
    }
}