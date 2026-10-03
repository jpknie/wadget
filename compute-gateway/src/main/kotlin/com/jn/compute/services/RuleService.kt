package com.jn.compute.services

import com.jn.compute.models.entity.Rule
import com.jn.compute.models.entity.Tag

interface RuleService {
    fun getRuleById(ruleId: String): Rule
    fun saveRule(rule: Rule): Rule
    fun deleteRule(ruleId: String)
}