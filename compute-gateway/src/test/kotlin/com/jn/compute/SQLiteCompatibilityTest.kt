package com.jn.compute

import com.fasterxml.jackson.databind.ObjectMapper
import com.jn.domain.MatchMode
import com.jn.domain.Rule
import com.jn.domain.Tag
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.boot.WebApplicationType
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.nio.file.Path
import java.sql.DriverManager

class SQLiteCompatibilityTest {
    @TempDir
    lateinit var directory: Path

    @ParameterizedTest(name = "SQLite REST and persistence compatibility, legacy schema = {0}")
    @ValueSource(booleans = [false, true])
    fun preservesRestAndPersistenceBehavior(legacySchema: Boolean) {
        val url = "jdbc:sqlite:${directory.resolve("gateway.sqlite")}"
        val tag = Tag("tag-1", "Food", 0.123456789012345, Long.MAX_VALUE, true, 0.987654321098765, 500)
        val legacyTag = tag.copy(id = "legacy-tag", capCents = null, mandatoryCents = null)
        if (legacySchema) {
            createLegacySchema(url, legacyTag)
        }

        SpringApplicationBuilder(ComputeGatewayApplication::class.java)
            .web(WebApplicationType.SERVLET)
            .run(
                "--server.port=0",
                "--spring.datasource.url=$url",
                "--spring.jpa.properties.hibernate.hbm2ddl.halt_on_error=true"
            ).use { context ->
                val mvc = MockMvcBuilders.webAppContextSetup(context as WebApplicationContext).build()
                val mapper = context.getBean(ObjectMapper::class.java)

                mvc.perform(get("/api/v1/plan/health"))
                    .andExpect(status().isOk)
                    .andExpect(content().string("OK"))
                mvc.perform(get("/api/v1/plan/health/"))
                    .andExpect(status().isOk)
                    .andExpect(content().string("OK"))

                if (legacySchema) {
                    mvc.perform(get("/api/v1/tags/legacy-tag"))
                        .andExpect(status().isOk)
                        .andExpect(content().json(mapper.writeValueAsString(legacyTag)))
                    val legacyRule = Rule("legacy-rule", "food", 2, MatchMode.CONTAINS, true, legacyTag)
                    mvc.perform(get("/api/v1/rules/legacy-rule"))
                        .andExpect(status().isOk)
                        .andExpect(content().json(mapper.writeValueAsString(legacyRule)))
                }

                mvc.perform(post("/api/v1/tags")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(tag)))
                    .andExpect(status().isOk)
                    .andExpect(content().json(mapper.writeValueAsString(tag)))

                mvc.perform(get("/api/v1/tags/${tag.id}"))
                    .andExpect(status().isOk)
                    .andExpect(content().json(mapper.writeValueAsString(tag)))
                mvc.perform(get("/api/v1/tags/${tag.id}/"))
                    .andExpect(status().isOk)
                    .andExpect(content().json(mapper.writeValueAsString(tag)))
                val listed = mvc.perform(get("/api/v1/tags"))
                    .andExpect(status().isOk).andReturn().response.contentAsString
                assertTrue(mapper.readTree(listed).any { it["id"].asText() == tag.id })
                mvc.perform(get("/api/v1/tags/")).andExpect(status().isOk)

                DriverManager.getConnection(url).use { connection ->
                    connection.prepareStatement("select cap_cents from tags where id = ?").use { query ->
                        query.setString(1, tag.id)
                        query.executeQuery().use { rows ->
                            assertTrue(rows.next())
                            assertEquals(Long.MAX_VALUE, rows.getLong("cap_cents"))
                        }
                    }
                }

                // Assigned IDs must still merge existing rows, rather than insert duplicates.
                val updated = tag.copy(name = "Updated", mandatory = false, capCents = null, mandatoryCents = null)
                mvc.perform(put("/api/v1/tags")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(updated)))
                    .andExpect(status().isOk)
                    .andExpect(content().json(mapper.writeValueAsString(updated)))

                for (mode in MatchMode.values()) {
                    val rule = Rule("rule-${mode.name}", "food", 2, mode, true, updated)
                    mvc.perform(post("/api/v1/rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(rule)))
                        .andExpect(status().isOk)
                        .andExpect(content().json(mapper.writeValueAsString(rule)))
                    mvc.perform(get("/api/v1/rules/${rule.id}"))
                        .andExpect(status().isOk)
                        .andExpect(content().json(mapper.writeValueAsString(rule)))
                    mvc.perform(get("/api/v1/rules/${rule.id}/"))
                        .andExpect(status().isOk)
                        .andExpect(content().json(mapper.writeValueAsString(rule)))

                    val updatedRule = rule.copy(pattern = "updated", priority = 3, caseInsensitive = false)
                    mvc.perform(post("/api/v1/rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedRule)))
                        .andExpect(status().isOk)
                        .andExpect(content().json(mapper.writeValueAsString(updatedRule)))

                    DriverManager.getConnection(url).use { connection ->
                        connection.prepareStatement("select mode, tag_id from rules where id = ?").use { query ->
                            query.setString(1, rule.id)
                            query.executeQuery().use { rows ->
                                assertTrue(rows.next())
                                assertEquals(mode.name, rows.getString("mode"))
                                assertEquals(tag.id, rows.getString("tag_id"))
                            }
                        }
                    }
                    mvc.perform(delete("/api/v1/rules/${rule.id}")).andExpect(status().isOk)
                    mvc.perform(get("/api/v1/rules/${rule.id}")).andExpect(status().isNotFound)
                }

                DriverManager.getConnection(url).use { connection ->
                    connection.prepareStatement("select weight, softness, cap_cents from tags where id = ?").use { query ->
                        query.setString(1, tag.id)
                        query.executeQuery().use { rows ->
                            assertTrue(rows.next())
                            assertEquals(tag.weight, rows.getDouble("weight"))
                            assertEquals(tag.softness, rows.getDouble("softness"))
                            assertEquals(null, rows.getObject("cap_cents"))
                        }
                    }
                }
                mvc.perform(delete("/api/v1/tags/${tag.id}")).andExpect(status().isOk)
                mvc.perform(get("/api/v1/tags/${tag.id}")).andExpect(status().isNotFound)
            }
    }

    private fun createLegacySchema(url: String, tag: Tag) {
        DriverManager.getConnection(url).use { connection ->
            connection.createStatement().use { statement ->
                // Hibernate 5/gwenn declarations: doubles use double, enums use varchar without a check.
                statement.executeUpdate("""
                    create table tags (
                        id varchar(255) not null primary key, name varchar(255),
                        weight double not null, softness double not null, cap_cents bigint,
                        mandatory boolean not null, mandatory_cents bigint
                    )
                """.trimIndent())
                statement.executeUpdate("""
                    create table rules (
                        id varchar(255) not null primary key, pattern varchar(255),
                        priority integer not null, mode varchar(255), case_insensitive boolean not null,
                        tag_id varchar(255) not null
                    )
                """.trimIndent())
            }
            connection.prepareStatement(
                "insert into tags (id, name, weight, softness, mandatory) values (?, ?, ?, ?, ?)"
            ).use { insert ->
                insert.setString(1, tag.id)
                insert.setString(2, tag.name)
                insert.setDouble(3, tag.weight)
                insert.setDouble(4, tag.softness)
                insert.setBoolean(5, tag.mandatory)
                insert.executeUpdate()
            }
            connection.createStatement().use { statement ->
                statement.executeUpdate("""
                    insert into rules (id, pattern, priority, mode, case_insensitive, tag_id)
                    values ('legacy-rule', 'food', 2, 'CONTAINS', 1, 'legacy-tag')
                """.trimIndent())
            }
        }
    }
}
