package com.goose.games.gomoku

import com.goose.games.gomoku.algorithms.*
import com.goose.games.gomoku.common.patternMap
import com.goose.games.gomoku.models.GameState
import com.goose.games.gomoku.models.Point
import com.goose.games.gomoku.optimization.ConcurrentSolver
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.system.measureTimeMillis

@SpringBootApplication
@RestController
class GomokuApplication {
	@GetMapping("/hello")
	fun hello(@RequestParam(value = "name", defaultValue = "Gomoku") name: String): String {
		return "Hello, ${name}!"
	}

	@GetMapping("/solve")
	fun solve(
			@RequestParam(value = "repr", defaultValue = "") repr: String,
			@RequestParam(value = "depth", defaultValue = "3") depth: Int
	): Minimax.Report {
		val solver = ConcurrentSolver(repr, depth)
		var report = Minimax.Report(0, Point(0, 0), 0)
		measureTimeMillis {
			report = solver.solve()
		}.also { println("time elapsed = $it ms") }
		return report
	}
}

fun main(args: Array<String>) {
	runApplication<GomokuApplication>(*args)
}
