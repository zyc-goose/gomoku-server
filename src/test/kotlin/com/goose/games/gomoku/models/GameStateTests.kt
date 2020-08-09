package com.goose.games.gomoku.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class GameStateTests {

    @Mock
    lateinit var observer: GameState.Observer

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun get() {
        val gameState = GameState("112sb14sw")
        assertEquals(gameState.get(Point(7,7)), 'b')
        assertEquals(gameState.get(Point(8,7)), 'w')
        assertEquals(gameState.get(Point(9,7)), 's')
    }

    @Test
    fun set() {
        val gameState = GameState("")
        gameState.addObserver(observer)
        gameState.set(Point(1, 1), 'b')
        assertEquals(gameState.get(Point(1, 1)), 'b')
        verify(observer).didSet(Point(1, 1), 'b')
    }

    @Test
    fun clear() {
        val gameState = GameState("16sb")
        gameState.addObserver(observer)
        gameState.clear(Point(1, 1))
        assertEquals(gameState.get(Point(1, 1)), 's')
        verify(observer).didClear(Point(1, 1))
    }
}