package com.github.simonhauck.example.commontest.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["com.github.simonhauck.example"])
class CommonTestConfiguration
