package com.jn.compute.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry

@Configuration
class WebConfig : WebMvcConfigurer {

    @Suppress("DEPRECATION")
    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        // Spring 6 changed the default; retain Spring Boot 2's trailing-slash aliases.
        configurer.setUseTrailingSlashMatch(true)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:8082",
                                        "http://localhost:8080")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*")
    }
}