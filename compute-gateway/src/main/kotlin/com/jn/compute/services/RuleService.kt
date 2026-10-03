package com.jn.compute.services

import com.jn.domain.Rule

interface RuleService {
    fun getRuleById(ruleId: String): Rule
    fun saveRule(rule: Rule): Rule
    fun deleteRule(ruleId: String)
}