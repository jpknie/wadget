package com.jn.compute.services

import com.jn.domain.Tag

interface TagService {
    fun getTagById(tagId: String): Tag
    fun saveTag(tag: Tag): Tag
    fun deleteTag(tagId: String)
    fun getAllTags(): List<Tag>
}