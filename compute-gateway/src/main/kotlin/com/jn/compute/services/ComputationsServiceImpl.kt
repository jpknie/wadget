package com.jn.compute.services

import com.jn.compute.models.BudgetComputationRequest
import com.jn.compute.models.FixedCost
import com.jn.compute.models.TagConfig
import com.jn.compute.repositories.TagRepository
import com.jn.domain.BudgetComputationResult
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class ComputationsServiceImpl(
    @Value($$"${budget-engine.base-url}")
    baseUrl: String,
    restClientBuilder: RestClient.Builder,
    private val tagRepository: TagRepository,
) : ComputationsService {

    private val restClient = restClientBuilder
        .baseUrl(baseUrl)
        .build()


    /*
    BudgetComputationRequest(
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

     */
    override fun compute(): BudgetComputationResult {
        val tags = tagRepository.findAll()
        val bcrRequest = BudgetComputationRequest(
            income = 3500.0,
            fixedCosts =
                tags.filter { it.mandatory }
                    .map {
                        FixedCost(
                            tag = it.name,
                            amount = it.mandatoryCents?.toDouble()?.div(100.0) ?: 0.0
                        )
                    },
            tagConfigs =
                tags.filter { !it.mandatory } // the other tags
                    .map {
                        TagConfig(
                            tag = it.name,
                            weight = it.weight,
                            softness = it.softness,
                            minAmount = 0.0,
                            maxAmount = it.capCents?.toDouble()?.div(100.0) ?: 0.0
                        )
                    }
        )
        val result = restClient
            .post()
            .uri("/compute")
            .contentType(MediaType.APPLICATION_JSON)
            .body(bcrRequest)
            .retrieve()
            .body(BudgetComputationResult::class.java)!!
        return result;
    }

}