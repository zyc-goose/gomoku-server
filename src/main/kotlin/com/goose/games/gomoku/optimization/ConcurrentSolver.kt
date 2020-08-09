package com.goose.games.gomoku.optimization

import com.goose.games.gomoku.algorithms.*
import com.goose.games.gomoku.common.patternMap
import com.goose.games.gomoku.models.GameState
import com.goose.games.gomoku.models.Point
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class ConcurrentSolver(val repr: String, val depth: Int) {
    val rootGameState = GameState(repr)
    val rootProximity = Proximity(rootGameState, 2)
    val rootMonitor = Monitor(rootGameState)

    class Task(val point: Point, val repr: String, val depth: Int): Callable<Minimax.Report> {
        companion object {
            val acAuto = ACAutomata(patternMap.keys)
        }

        override fun call(): Minimax.Report {
            val gameState = GameState(repr)
            val proximity = Proximity(gameState, 2)
            val monitor = Monitor(gameState)
            val evaluator = Evaluator(gameState, acAuto)
            val minimax = Minimax(gameState, proximity, evaluator, monitor)
            gameState.set(point, if (monitor.blackNext) 'b' else 'w')
            return minimax.run(depth)
        }
    }

    fun createTasks(): List<Task> {
        return rootProximity.getValidPoints()
                .map { Task(it, repr, depth - 1) }
    }

    fun solve(): Minimax.Report {
        val executorService = Executors.newFixedThreadPool(12)
        val futures = executorService.invokeAll(createTasks())
        val reports = futures.map { it.get() }
        var selectedScore = 0
        if (rootMonitor.blackNext) {
            selectedScore = reports.map { it.score }.max() ?: 0
        } else {
            selectedScore = reports.map { it.score }.min() ?: 0
        }
        val nodeCount = reports.sumBy { it.nodeCount }
        val report = reports.filter { it.score == selectedScore }.random()
        return Minimax.Report(report.score, report.point, nodeCount)
    }
}