package com.jn.compute.services

import com.jn.compute.exceptions.NotFoundException
import com.jn.compute.models.entity.Rule
import com.jn.compute.repositories.RuleRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Suppress("unused")
@Service
class RuleServiceImpl(private val ruleRepository: RuleRepository): RuleService {

    @Transactional
    override fun getRuleById(ruleId: String): Rule = ruleRepository
        .findById(ruleId)
        .orElseThrow {
            NotFoundException("Could not find rule with rule ID: $ruleId")
        }

    @Transactional
    override fun saveRule(rule: Rule): Rule = ruleRepository.save(rule)

    @Transactional
    override fun deleteRule(ruleId: String) = ruleRepository.deleteById(ruleId)
}