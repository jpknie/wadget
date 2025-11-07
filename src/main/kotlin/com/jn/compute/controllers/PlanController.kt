package com.jn.compute.controllers

import com.jn.compute.controllers.constants.ControllerConstants

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PlanController {

  @GetMapping(ControllerConstants.API_V1_PLANNER + "/health")
  fun health() = "OK"

}