package com.goose.games.gomoku.algorithms

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ACAutomataTests {

    val acAuto = ACAutomata(listOf("sbbbs", "sbsbs", "sbbbbw"))

    @Test
    fun findWithPivot() {
        assertEquals(acAuto.find("sbbbsbsbsbbbbw", 0), listOf("sbbbs"))
        assertEquals(acAuto.find("sbbbsbsbsbbbbw", 4), listOf("sbbbs", "sbsbs"))
        assertEquals(acAuto.find("sbbbsbsbsbbbbw", 8), listOf("sbsbs", "sbbbbw"))
        assertEquals(acAuto.find("sbbbsbsbsbbbbw", 13), listOf("sbbbbw"))
    }

}