package com.jn.compute.services

import com.jn.compute.exceptions.NotFoundException
import com.jn.compute.models.entity.toDomain
import com.jn.compute.models.entity.toEntity
import com.jn.compute.repositories.RuleRepository
import com.jn.domain.Rule
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional

@Suppress("unused")
@Service
class RuleServiceImpl(private val ruleRepository: RuleRepository): RuleService {

    @Transactional
    override fun getRuleById(ruleId: String): Rule = ruleRepository
        .findById(ruleId)
        .orElseThrow {
            NotFoundException("Could not find rule with rule ID: $ruleId")
        }
        .toDomain()

    @Transactional
    override fun saveRule(rule: Rule): Rule = ruleRepository.save(rule.toEntity()).toDomain()

    @Transactional
    override fun deleteRule(ruleId: String) = ruleRepository.deleteById(ruleId)
}
