package com.jn.compute.services

import com.jn.compute.models.entity.Rule
import com.jn.compute.models.entity.Tag

interface TagService {
    fun getTagById(tagId: String): Tag
    fun saveTag(tag: Tag): Tag
    fun deleteTag(tagId: String)
}