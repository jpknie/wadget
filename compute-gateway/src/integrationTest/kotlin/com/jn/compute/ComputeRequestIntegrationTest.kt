package com.jn.compute

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestClient
import org.springframework.http.MediaType


import com.jn.compute.models.BudgetComputationRequest
import com.jn.compute.models.FixedCost
import com.jn.compute.models.TagConfig


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

    @Test
    fun `send budget computation request to budget engine`() {
        val restClient = RestClient.create()
        val request = BudgetComputationRequest(
            income = 3500.0,
            fixedCosts = listOf(
                FixedCost(
                    tag = "rent",
                    amount = 1000.0
                )
            ),
            tagConfigs = listOf(
                TagConfig(
                    tag = "groceries",
                    weight = 1.0,
                    softness = 100.0,
                    minAmount = 0.0,
                    maxAmount = 2000.0
                ),
                TagConfig(
                    tag = "fun",
                    weight = 1.0,
                    softness = 100.0,
                    minAmount = 0.0,
                    maxAmount = 2000.0
                )
            )
        )

        val result = restClient
            .post()
            .uri("http://localhost:8081/compute")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(String::class.java)

        assertEquals("""{"income":3500.0}""", result)
    }
}
