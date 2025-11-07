package com.jn.compute.repositories

import com.jn.compute.models.entity.Tag
import org.springframework.data.repository.CrudRepository


interface TagRepository: CrudRepository<Tag, String>