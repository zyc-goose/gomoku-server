package com.goose.games.gomoku.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PointContextTests {
    val context = PointContext(15, 15)

    @Test
    fun scan_H() {
        val (points, pivot) = context.scan(Point(4, 3), 5, PointContext.Dir.H)
        assertEquals(points.first(), Point(4, 0))
        assertEquals(points.last(), Point(4, 8))
        assertEquals(points.size, 9)
        assertEquals(pivot, 3)
    }

    @Test
    fun scan_V() {
        val (points, pivot) = context.scan(Point(4, 3), 200, PointContext.Dir.V)
        assertEquals(points.first(), Point(0, 3))
        assertEquals(points.last(), Point(14, 3))
        assertEquals(points.size, 15)
        assertEquals(pivot, 4)
    }

    @Test
    fun scan_D1() {
        val (points, pivot) = context.scan(Point(4, 3), 5, PointContext.Dir.D1)
        assertEquals(points.first(), Point(1, 0))
        assertEquals(points.last(), Point(9, 8))
        assertEquals(points.size, 9)
        assertEquals(pivot, 3)
    }

    @Test
    fun scan_D2() {
        val (points, pivot) = context.scan(Point(4, 3), 15, PointContext.Dir.D2)
        assertEquals(points.first(), Point(7, 0))
        assertEquals(points.last(), Point(0, 7))
        assertEquals(points.size, 8)
        assertEquals(pivot, 3)
    }

    @Test
    fun square_normal() {
        val points = context.square(Point(7, 7), 2)
        assertEquals(points.size, 25)
    }

    @Test
    fun square_edge_topLeft() {
        val points = context.square(Point(0, 1), 2)
        assertEquals(points.size, 12)
    }

    @Test
    fun square_edge_bottomRight() {
        val points = context.square(Point(14, 13), 2)
        assertEquals(points.size, 12)
    }

    @Test
    fun all() {
        assertEquals(context.all().size, 225)
    }
}