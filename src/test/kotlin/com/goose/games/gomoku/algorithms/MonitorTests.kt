package com.goose.games.gomoku.algorithms

import com.goose.games.gomoku.models.GameState
import com.goose.games.gomoku.models.Point
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MonitorTests {

    @Test
    fun checkResult() {
        val gameState = GameState("sbbbbbw")
        val monitor = Monitor(gameState)
        assertEquals(monitor.checkWinnerAt(Point(0, 5)), Monitor.Result.BLACK)
        assertEquals(monitor.checkWinnerAt(Point(0, 6)), Monitor.Result.ONGOING)
    }
}