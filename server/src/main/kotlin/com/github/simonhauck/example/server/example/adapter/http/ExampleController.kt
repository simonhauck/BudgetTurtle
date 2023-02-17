package com.github.simonhauck.example.server.example.adapter.http

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/example")
class ExampleController {

    @GetMapping("asfasf")
    fun getHelloWorld(): HelloWorldDto {
        return HelloWorldDto("hello world 2")
    }
}

data class HelloWorldDto(val message: String)
