package com.goose.games.gomoku.models

class PointContext(val nrows: Int, val ncols: Int) {
    enum class Dir {
        H, V, D1, D2
    }

    fun fromLinear(pos: Int): Point {
        return Point(pos / ncols, pos % ncols)
    }

    fun rightNext(point: Point): Point {
        var row = point.row
        var col = point.col + 1
        if (col == ncols) {
            col = 0
            ++row
        }
        return Point(row, col)
    }

    fun scan(point: Point, halfSpan: Int, dir: Dir): Pair<List<Point>, Int> {
        val topSpace = point.row
        val bottomSpace = nrows - point.row - 1
        val leftSpace = point.col
        val rightSpace = ncols - point.col - 1

        val beginShift = when (dir) {
            Dir.H -> minOf(leftSpace, halfSpan)
            Dir.V -> minOf(topSpace, halfSpan)
            Dir.D1 -> minOf(topSpace, leftSpace, halfSpan)
            Dir.D2 -> minOf(bottomSpace, leftSpace, halfSpan)
        }
        val endShift = when (dir) {
            Dir.H -> minOf(rightSpace, halfSpan)
            Dir.V -> minOf(bottomSpace, halfSpan)
            Dir.D1 -> minOf(bottomSpace, rightSpace, halfSpan)
            Dir.D2 -> minOf(topSpace, rightSpace, halfSpan)
        }
        val beginPoint = when (dir) {
            Dir.H -> point + Point(0, -beginShift)
            Dir.V -> point + Point(-beginShift, 0)
            Dir.D1 -> point + Point(-beginShift, -beginShift)
            Dir.D2 -> point + Point(beginShift, -beginShift)
        }
        val endPoint = when (dir) {
            Dir.H -> point + Point(0, endShift)
            Dir.V -> point + Point(endShift, 0)
            Dir.D1 -> point + Point(endShift, endShift)
            Dir.D2 -> point + Point(-endShift, endShift)
        }
        val delta = when (dir) {
            Dir.H -> Point(0, 1)
            Dir.V -> Point(1, 0)
            Dir.D1 -> Point(1, 1)
            Dir.D2 -> Point(-1, 1)
        }
        val points = mutableListOf<Point>()
        var curPoint = beginPoint
        var pivot = -1
        var index = 0
        while (curPoint != endPoint) {
            points.add(curPoint)
            if (curPoint == point) { pivot = index }
            curPoint += delta
            ++index
        }
        points.add(curPoint)
        if (curPoint == point) { pivot = index }
        return Pair(points, pivot)
    }

    fun square(point: Point, halfSpan: Int): List<Point> {
        val top = maxOf(0, point.row - halfSpan)
        val bottom = minOf(nrows - 1, point.row + halfSpan)
        val left = maxOf(0, point.col - halfSpan)
        val right = minOf(ncols - 1, point.col + halfSpan)
        return (top..bottom).map { row -> (left..right).map { col -> Point(row, col) } }.flatten()
    }

    fun all(): List<Point> {
        return (0 until nrows).map { row -> (0 until ncols).map { col -> Point(row, col) } }.flatten()
    }
}