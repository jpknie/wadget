package com.jn.wadget.computeclient


import com.jn.wadget.models.Category
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

class ComputeGatewayClientImpl(
    private val baseUrl: String = "http://192.168.0.1:3333"
): ComputeGatewayClient {

    private val client = HttpClient(provideEngine())
    {
        install(ContentNegotiation) { json() }
    }
    override suspend fun getCategories(): List<Category> {
        TODO("Not yet implemented")
    }

    override suspend fun addCategory(category: Category) {
        println("Adding category: $category")
    }

    override suspend fun updateCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun computeAllocation(): List<AllocationResult> {
        TODO("Not yet implemented")
    }
}