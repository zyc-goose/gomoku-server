package com.goose.games.gomoku

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class GomokuApplication {
	@GetMapping("/hello")
	fun hello(@RequestParam(value = "name", defaultValue = "Gomoku") name: String): String {
		return "Hello, ${name}!"
	}
}

fun main(args: Array<String>) {
	runApplication<GomokuApplication>(*args)
}
