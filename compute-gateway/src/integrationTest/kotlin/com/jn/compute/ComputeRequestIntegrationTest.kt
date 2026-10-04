package com.jn.compute

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.web.client.RestClient

class ComputeRequestIntegrationTest {

    @Test
    fun `send computation request to budget engine`() { 
        val restClient = RestClient.create()

        val result = restClient
            .get()
            .uri("http://localhost:8081/hi")
            .retrieve()
            .body(String::class.java)
            
        assertEquals("Hello World!", result)
    }
}
