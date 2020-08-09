package com.goose.games.gomoku.algorithms

import com.goose.games.gomoku.models.GameState
import com.goose.games.gomoku.models.Point
import java.util.*

class Proximity(val gameState: GameState, val halfSpan: Int): GameState.Observer {
    val validPoints = mutableSetOf<Point>()
    val bufferPoints = Stack<Point>()
    val bufferCounts = Stack<Int>()

    val pointContext = gameState.pointContext

    init {
        gameState.addObserver(this)
        pointContext.all()
                .filter { gameState.get(it) != 's' }
                .forEach { setValidAround(it, false) }
    }

    fun setValidAround(point: Point, tracked: Boolean) {
        if (gameState.get(point) == 's') { return }
        validPoints.remove(point)
        val newValidPoints = pointContext.square(point, halfSpan)
                .filter { gameState.get(it) == 's' && !validPoints.contains(it) }
        validPoints.addAll(newValidPoints)
        if (tracked) {
            newValidPoints.forEach { bufferPoints.push(it) }
            bufferCounts.push(newValidPoints.size)
        }
    }

    fun clearValidAround(point: Point) {
        if (gameState.get(point) != 's') { return }
        validPoints.add(point)
        val bufferCount = bufferCounts.pop()
        repeat(bufferCount) { validPoints.remove(bufferPoints.pop()) }
    }

    override fun willSet(point: Point, value: Char) {}

    override fun didSet(point: Point, value: Char) {
        setValidAround(point, true)
    }

    override fun didClear(point: Point) {
        clearValidAround(point)
    }
}