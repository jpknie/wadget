package com.jn.compute.controllers

import com.jn.compute.controllers.constants.ControllerConstants
import com.jn.compute.models.entity.Rule
import com.jn.compute.services.RuleService
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
@RequestMapping(ControllerConstants.API_V1_RULES)
class RuleController(private val ruleService: RuleService)
{
    @PostMapping
    fun createRule(@RequestBody rule: Rule): ResponseEntity<Rule> {
        val savedRule: Rule = ruleService.saveRule(rule);
        return ResponseEntity.ok().body(savedRule)
    }

    @GetMapping("/{ruleId}")
    fun getRuleById(@PathVariable ruleId: String): Rule = ruleService.getRuleById(ruleId)

    @DeleteMapping("/{ruleId}")
    fun deleteRule(@PathVariable ruleId: String) = ruleService.deleteRule(ruleId)

}