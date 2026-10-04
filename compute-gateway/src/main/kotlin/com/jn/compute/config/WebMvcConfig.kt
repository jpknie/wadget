package com.jn.compute.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {
    @Suppress("DEPRECATION")
    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        // Spring 6 changed the default; retain Spring Boot 2's trailing-slash aliases.
        configurer.setUseTrailingSlashMatch(true)
    }
}
