package com.goose.games.gomoku.algorithms

import com.goose.games.gomoku.common.bwInverted
import com.goose.games.gomoku.common.patternMap
import com.goose.games.gomoku.models.GameState
import com.goose.games.gomoku.models.Point
import com.goose.games.gomoku.models.PointContext
import java.util.*

class Evaluator(val gameState: GameState, val acAuto: ACAutomata) : GameState.Observer {
    val pointContext = gameState.pointContext
    var score = 0
        private set
    val beforeScores = Stack<Int>()
    val afterScores = Stack<Int>()

    init {
        gameState.addObserver(this)
    }

    fun getSearchLine(point: Point, dir: PointContext.Dir, isBlack: Boolean): Pair<String, Int> {
        val boundChar = if (isBlack) "w" else "b"
        val (points, pivot) = pointContext.scan(point, 5, dir)
        val coreString = points.map { gameState.get(it) }
                .joinToString(separator = "", prefix = boundChar, postfix = boundChar)
        val finalString = if (isBlack) coreString else coreString.bwInverted
        return Pair(finalString, pivot + 1)
    }

    fun getSingleScore(point: Point, isBlack: Boolean): Int {
        return PointContext.Dir.values()
                .map { getSearchLine(point, it, isBlack) }
                .flatMap { acAuto.find(it.first, it.second) }
                .sumBy { patternMap[it] ?: 0 }
    }

    fun getEffectiveScore(point: Point): Int {
        val blackScore = getSingleScore(point, true)
        val whiteScore = getSingleScore(point, false)
        return blackScore - whiteScore
    }

    override fun willSet(point: Point, value: Char) {
        val beforeScore = getEffectiveScore(point)
        score -= beforeScore
        beforeScores.push(beforeScore)
    }

    override fun didSet(point: Point, value: Char) {
        val afterScore = getEffectiveScore(point)
        score += afterScore
        afterScores.push(afterScore)
    }

    override fun didClear(point: Point) {
        score -= afterScores.pop()
        score += beforeScores.pop()
    }
}