package com.jn.compute.services

import com.jn.compute.exceptions.NotFoundException
import com.jn.compute.models.entity.Tag
import com.jn.compute.repositories.TagRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Suppress("unused")
class TagServiceImpl(private val tagRepository: TagRepository) : TagService {
    @Transactional
    override fun getTagById(tagId: String): Tag = tagRepository
        .findById(tagId)
        .orElseThrow {
            NotFoundException("Could not find tag with tag ID: $tagId")
        }

    @Transactional
    override fun saveTag(tag: Tag): Tag = tagRepository.save(tag)

    @Transactional
    override fun deleteTag(tagId: String) = tagRepository.deleteById(tagId)

}