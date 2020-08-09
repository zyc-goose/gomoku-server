package com.goose.games.gomoku

import com.goose.games.gomoku.algorithms.*
import com.goose.games.gomoku.common.patternMap
import com.goose.games.gomoku.models.GameState
import com.goose.games.gomoku.models.Point
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

	@GetMapping("/solve")
	fun solve(@RequestParam(value = "repr", defaultValue = "") repr: String): Minimax.Report {
		val gameState = GameState(repr)
		val monitor = Monitor(gameState)
		val proximity = Proximity(gameState, 2)
		val acAuto = ACAutomata(patternMap.keys)
		val evaluator = Evaluator(gameState, acAuto)
		val minimax = Minimax(gameState, proximity, evaluator, monitor)
		return minimax.run(3)
	}
}

fun main(args: Array<String>) {
	runApplication<GomokuApplication>(*args)
}
