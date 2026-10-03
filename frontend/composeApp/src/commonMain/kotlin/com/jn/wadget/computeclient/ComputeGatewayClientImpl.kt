package com.jn.wadget.computeclient


import com.jn.wadget.models.Category
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
    private val baseUrl: String = "http://localhost:8080" + ApiConstants.CATEGORIES
): ComputeGatewayClient {

    private val client = HttpClient(provideEngine())
    {
        install(ContentNegotiation) { json() }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

    override suspend fun getCategories(): List<Category> {
        return client.get(baseUrl).body()
    }

    override suspend fun getCategory(id: Long): Category? {
        return client.get("$baseUrl/{id}").body()
    }

    override suspend fun addCategory(category: Category) {
        client.post(baseUrl) {
            setBody(category)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun updateCategory(category: Category) {
        client.put(baseUrl) {
            setBody(category)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun deleteCategory(id: Long) {
        client.delete("$baseUrl/{id}")
    }

    override suspend fun computeAllocation(): List<AllocationResult> {
        TODO("Not yet implemented")
    }
}
