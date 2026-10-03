package com.jn.wadget.computeclient


import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import com.jn.domain.Tag

class ComputeGatewayClientImpl(
    private val baseUrl: String = "http://192.168.0.1:3333"
) : ComputeGatewayClient {

    private val client = HttpClient(Darwin) {
        install(ContentNegotiation) { json() }
    }

    override suspend fun getTags(): List<Tag> {
        TODO("Not yet implemented")
    }

    override suspend fun addTag(tag: Tag) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTag(tag: Tag) {
        TODO("Not yet implemented")
    }

    override suspend fun computeAllocation(): List<AllocationResult> {
        TODO("Not yet implemented")
    }
}