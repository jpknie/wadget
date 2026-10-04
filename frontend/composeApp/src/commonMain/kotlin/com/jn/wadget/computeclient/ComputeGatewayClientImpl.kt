package com.jn.wadget.computeclient


import com.jn.domain.Tag
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*


class ComputeGatewayClientImpl(
    private val tagBaseUrl: String = "http://localhost:8080" + ApiConstants.TAGS
): ComputeGatewayClient {

    private val client = HttpClient(provideEngine())
    {
        install(ContentNegotiation) { json() }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

    override suspend fun getTags(): List<Tag> {
        return client.get(tagBaseUrl).body()
    }

    override suspend fun getTag(id: String): Tag? {
        return client.get("$tagBaseUrl/$id").body()
    }

    override suspend fun addTag(tag: Tag) {
        client.post(tagBaseUrl) {
            setBody(tag)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun updateTag(tag: Tag) {
        client.put(tagBaseUrl) {
            setBody(tag)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun deleteTag(id: String) {
        client.delete("$tagBaseUrl/$id")
    }

    override suspend fun computeAllocation(): List<AllocationResult> {
        TODO("Not yet implemented")
    }
}
