package com.jn.wadget.repository

import com.jn.wadget.computeclient.ComputeGatewayClient
import com.jn.domain.Tag
import com.jn.wadget.models.generateId

class TagRepository(
    private val computeGatewayClient: ComputeGatewayClient
) {

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