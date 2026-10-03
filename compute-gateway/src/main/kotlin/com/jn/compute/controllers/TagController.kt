package com.jn.compute.controllers

import com.jn.compute.controllers.constants.ControllerConstants
import com.jn.compute.models.entity.Tag
import com.jn.compute.services.TagService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Suppress("unused")
@RestController
@RequestMapping(ControllerConstants.API_V1_TAGS)
class TagController(private val tagService: TagService)
{
    @PostMapping
    fun createTag(@RequestBody tag: Tag): ResponseEntity<Tag> {
        val savedTag: Tag = tagService.saveTag(tag);
        return ResponseEntity.ok().body(savedTag)
    }

    @DeleteMapping("/{tagId}")
    fun deleteTag(@PathVariable tagId: String) = tagService.deleteTag(tagId)

    @GetMapping("/{tagId}")
    fun getTagById(@PathVariable tagId: String): Tag = tagService.getTagById(tagId)

}