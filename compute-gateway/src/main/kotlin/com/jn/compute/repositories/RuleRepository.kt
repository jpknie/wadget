package com.jn.compute.repositories

import com.jn.compute.models.entity.Rule
import org.springframework.data.repository.CrudRepository


interface RuleRepository: CrudRepository<Rule, String>