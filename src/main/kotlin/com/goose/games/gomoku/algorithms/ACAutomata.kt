package com.goose.games.gomoku.algorithms

import java.util.*

class ACAutomata(words: Collection<String>) {
    class Node {
        var fail: Node = this
        var word: String? = null
        val next = mutableMapOf<Char, Node?>(
                'b' to null,
                'w' to null,
                's' to null
        )
    }
    private val root = Node()
    private val alphabet = listOf('b', 'w', 's')

    init {
        insertAll(words)
        build()
    }

    private fun insert(word: String) {
        var curNode = root
        for (ch in word) {
            val nextNode = curNode.next[ch] ?: Node()
            curNode.next[ch] = nextNode
            curNode = nextNode
        }
        curNode.word = word
    }

    private fun insertAll(words: Collection<String>) {
        words.forEach { insert(it) }
    }

    private fun build() {
        val queue: Queue<Node> = ArrayDeque()
        queue.add(root)
        while (queue.isNotEmpty()) {
            val curNode = queue.remove()
            alphabet.forEach {
                val nextNode = curNode.next[it]
                val failNextNode = when (curNode === root) {
                    true -> root
                    else -> curNode.fail.next[it] ?: root
                }
                if (nextNode == null) {
                    curNode.next[it] = failNextNode
                } else {
                    nextNode.fail = failNextNode
                    queue.add(nextNode)
                }
            }
        }
    }

    fun find(text: String, pivot: Int): List<String> {
        val occurrences = mutableListOf<String>()
        var curNode = root
        text.forEachIndexed { index, ch ->
            curNode = curNode.next[ch] ?: root
            var tmpNode = curNode
            while (tmpNode !== root) {
                val word = tmpNode.word
                if (word != null && pivot in (index - word.length + 1)..index) {
                    occurrences.add(word)
                }
                tmpNode = tmpNode.fail
            }
        }
        return occurrences
    }
}
