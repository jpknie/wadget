package com.jn.compute.services

import com.jn.compute.exceptions.NotFoundException
import com.jn.compute.models.entity.toDomain
import com.jn.compute.models.entity.toEntity
import com.jn.compute.repositories.TagRepository
import com.jn.domain.Tag
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional

@Service
@Suppress("unused")
class TagServiceImpl(private val tagRepository: TagRepository) : TagService {
    @Transactional
    override fun getTagById(tagId: String): Tag = tagRepository
        .findById(tagId)
        .orElseThrow {
            NotFoundException("Could not find tag with tag ID: $tagId")
        }
        .toDomain()

    @Transactional
    override fun saveTag(tag: Tag): Tag = tagRepository.save(tag.toEntity()).toDomain()

    @Transactional
    override fun deleteTag(tagId: String) = tagRepository.deleteById(tagId)

    @Transactional
    override fun getAllTags(): List<Tag> = tagRepository.findAll().map { it.toDomain() }

}
