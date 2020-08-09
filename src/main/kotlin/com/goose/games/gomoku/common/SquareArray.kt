package com.goose.games.gomoku.common

typealias SquareArray<T> = List<MutableList<T>>

fun <T> initSquareArray(nrows: Int, ncols: Int, value: T): SquareArray<T> {
    return List(nrows) { MutableList(ncols) { value } }
}

fun <T> SquareArray<T>.setAll(value: T) {
    repeat(this.nrows) { row ->
        repeat(this.ncols) { col ->
            this[row][col] = value
        }
    }
}

val <T> SquareArray<T>.nrows: Int
    get() = this.size

val <T> SquareArray<T>.ncols: Int
    get() = this[0].size