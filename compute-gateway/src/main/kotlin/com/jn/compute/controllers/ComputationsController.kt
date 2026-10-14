package com.jn.compute.controllers

import com.jn.compute.controllers.constants.ControllerConstants
import com.jn.compute.services.ComputationsService
import com.jn.compute.services.ComputationsServiceImpl
import com.jn.domain.BudgetComputationResult

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(ControllerConstants.API_V1_COMPUTATIONS)
class ComputationsController(
    private val computationsService: ComputationsService
) {

    @GetMapping("/health")
    fun health() = "OK"

    @GetMapping("/compute")
    fun compute() = computationsService.compute()

}