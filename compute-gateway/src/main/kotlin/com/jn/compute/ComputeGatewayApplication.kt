package com.jn.compute

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@SpringBootApplication
class ComputeGatewayApplication

fun main(args: Array<String>) {
	runApplication<ComputeGatewayApplication>(*args)
}
