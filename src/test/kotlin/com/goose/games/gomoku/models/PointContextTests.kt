package com.goose.games.gomoku.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PointContextTests {
    val context = PointContext(15, 15)

    @Test
    fun scan_H() {
        val points = context.scan(Point(4, 3), 5, PointContext.Dir.H)
        assertEquals(points.first(), Point(4, 0))
        assertEquals(points.last(), Point(4, 8))
        assertEquals(points.size, 9)
    }

    @Test
    fun scan_V() {
        val points = context.scan(Point(4, 3), 200, PointContext.Dir.V)
        assertEquals(points.first(), Point(0, 3))
        assertEquals(points.last(), Point(14, 3))
        assertEquals(points.size, 15)
    }

    @Test
    fun scan_D1() {
        val points = context.scan(Point(4, 3), 5, PointContext.Dir.D1)
        assertEquals(points.first(), Point(1, 0))
        assertEquals(points.last(), Point(9, 8))
        assertEquals(points.size, 9)
    }

    @Test
    fun scan_D2() {
        val points = context.scan(Point(4, 3), 15, PointContext.Dir.D2)
        assertEquals(points.first(), Point(7, 0))
        assertEquals(points.last(), Point(0, 7))
        assertEquals(points.size, 8)
    }
}