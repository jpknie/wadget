package com.jn.wadget.computeclient

import com.jn.domain.Tag

class AllocationResult

interface ComputeGatewayClient {
    suspend fun getTags(): List<Tag>

    suspend fun getTag(id: String): Tag?
    suspend fun addTag(tag: Tag)
    suspend fun updateTag(tag: Tag)

    suspend fun deleteTag(id: String)
    suspend fun computeAllocation(): List<AllocationResult>
}