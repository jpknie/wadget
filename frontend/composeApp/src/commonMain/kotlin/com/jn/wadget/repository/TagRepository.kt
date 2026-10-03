package com.jn.wadget.repository

import com.jn.wadget.computeclient.ComputeGatewayClient
import com.jn.domain.Tag
import com.jn.wadget.models.generateId

class TagRepository(
    private val computeGatewayClient: ComputeGatewayClient
) {

    private val tags = mutableListOf(
        Tag(id = generateId(), name = "Food", mandatory = false, capCents = 0, mandatoryCents = 0, weight = 20.0),
        Tag(id = generateId(), name = "Fuel", mandatory = false, capCents = 0, mandatoryCents = 0, weight = 10.0),
        Tag(id = generateId(), name = "Hygiene", mandatory = false, capCents = 0, mandatoryCents = 0, weight = 100.0)
    )

    suspend fun getAll(): List<Tag> = computeGatewayClient.getTags()

    suspend fun add(tag: Tag) {
        computeGatewayClient.addTag(tag)
    }

    suspend fun update(tag: Tag) {
        computeGatewayClient.updateTag(tag)
    }

    suspend fun delete(id: String) {
        computeGatewayClient.deleteTag(id)
    }
}