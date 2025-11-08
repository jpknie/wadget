package com.jn.compute.controllers

import com.jn.compute.controllers.constants.ControllerConstants

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(ControllerConstants.API_V1_PLANNER)
class PlanController {

  @GetMapping("/health")
  fun health() = "OK"

}