package com.goose.games.gomoku.models

data class Point(val row: Int, val col: Int) {
    operator fun plus(rhs: Point) = Point(row + rhs.row, col + rhs.col)
}