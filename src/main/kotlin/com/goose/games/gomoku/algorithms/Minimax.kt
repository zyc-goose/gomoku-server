package com.goose.games.gomoku.algorithms

import com.goose.games.gomoku.models.GameState
import com.goose.games.gomoku.models.Point

class Minimax(
        val gameState: GameState,
        val proximity: Proximity,
        val evaluator: Evaluator,
        val monitor: Monitor
) {
    data class Report(
            val score: Int,
            val point: Point,
            val nodeCount: Int
    )
    private val invalidPoint = Point(-1, -1)
    private val inf = 10000000

    fun run(depth: Int): Report {
        if (monitor.emptyBoard) {
            return Report(0, Point(7, 7), 0)
        }
        return search(depth, -inf, inf)
    }

    fun search(remainingDepth: Int, alpha: Int, beta: Int): Report {
        var alpha = alpha
        var beta = beta
        val result = monitor.result
        if (result != Monitor.Result.ONGOING) {
            return when (result) {
                Monitor.Result.BLACK -> Report(inf, invalidPoint, 1)
                Monitor.Result.WHITE -> Report(-inf, invalidPoint, 1)
                else -> Report(0, invalidPoint, 1) // should not happen
            }
        } else if (remainingDepth == 0) {
            return Report(evaluator.score, invalidPoint, 1)
        }
        val validPoints = proximity.getValidPoints()
        val isMaximizer = monitor.blackNext
        val charValue = if (isMaximizer) 'b' else 'w'
        val candidates = mutableListOf<Report>()
        for (point in validPoints) {
            gameState.set(point, charValue)
            val searchResult = search(remainingDepth - 1, alpha, beta)
            if (isMaximizer) {
                alpha = maxOf(alpha, searchResult.score)
            } else {
                beta = minOf(beta, searchResult.score)
            }
            candidates.add(Report(searchResult.score, point, searchResult.nodeCount))
            gameState.clear(point)
            // alpha-beta pruning
            if (alpha >= beta) { break }
        }
        var selectedScore = 0
        if (isMaximizer) {
            selectedScore = candidates.map { it.score }.max() ?: 0
        } else {
            selectedScore = candidates.map { it.score }.min() ?: 0
        }
        val report = candidates.filter { it.score == selectedScore }.random()
        val nodeCount = candidates.sumBy { it.nodeCount } + 1
        return Report(report.score, report.point, nodeCount)
    }
}