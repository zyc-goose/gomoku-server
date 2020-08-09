package com.goose.games.gomoku.algorithms

import com.goose.games.gomoku.models.GameState
import com.goose.games.gomoku.models.Point
import com.goose.games.gomoku.models.PointContext

class Monitor(val gameState: GameState) : GameState.Observer {
    val pointContext = gameState.pointContext
    enum class Result {
        BLACK, WHITE, ONGOING
    }
    var result = Result.ONGOING
        private set
    var blackNext = true
        private set
    var emptyBoard = false
        private set
    init {
        gameState.addObserver(this)
        val allChars = pointContext.all().map { gameState.get(it) }
        blackNext = (allChars.count { it == 'b' } == allChars.count { it == 'w' })
        emptyBoard = (allChars.all { it == 's' })
    }

    fun checkWinnerAt(point: Point): Result {
        val searchStrings = PointContext.Dir.values()
                .map { pointContext.scan(point, 4, it).first }
                .map { points -> points.map { gameState.get(it) }.joinToString(separator = "") }
        if (searchStrings.any { it.contains("bbbbb") }) {
            return Result.BLACK
        } else if (searchStrings.any { it.contains("wwwww") }) {
            return Result.WHITE
        } else {
            return Result.ONGOING
        }
    }

    override fun willSet(point: Point, value: Char) {}

    override fun didSet(point: Point, value: Char) {
        blackNext = !blackNext
        result = checkWinnerAt(point)
    }

    override fun didClear(point: Point) {
        blackNext = !blackNext
        result = Result.ONGOING
    }
}