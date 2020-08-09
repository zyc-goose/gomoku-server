package com.goose.games.gomoku.models

import com.goose.games.gomoku.common.initSquareArray

class GameState(repr: String) {
    interface Observer {
        fun willSet(point: Point, value: Char)
        fun didSet(point: Point, value: Char)
        fun didClear(point: Point)
    }
    private val nrows = 15
    private val ncols = 15
    private val state = initSquareArray(nrows, ncols, 's')
    private val observers = mutableListOf<Observer>()

    val pointContext = PointContext(nrows, ncols)

    init {
        var number = 0
        var lastChar = 's'
        var point = Point(0, 0)
        for (ch in repr) {
            if (ch.isDigit()) {
                number = number * 10 + ch.toString().toInt()
            } else {
                if (!lastChar.isDigit()) {
                    number = 1
                }
                repeat(number) {
                    state[point.row][point.col] = ch
                    point = pointContext.rightNext(point)
                }
                number = 0
            }
            lastChar = ch
        }
    }

    fun addObserver(observer: Observer) {
        observers.add(observer)
    }

    fun get(point: Point): Char {
        return state[point.row][point.col]
    }

    fun set(point: Point, value: Char) {
        if (get(point) == 's' && value != 's') {
            observers.forEach { it.willSet(point, value) }
            state[point.row][point.col] = value
            observers.forEach { it.didSet(point, value) }
        }
    }

    fun clear(point: Point) {
        if (get(point) != 's') {
            state[point.row][point.col] = 's'
            observers.forEach { it.didClear(point) }
        }
    }
}
