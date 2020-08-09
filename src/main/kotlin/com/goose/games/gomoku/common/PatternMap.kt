package com.goose.games.gomoku.common

// 以黑棋为基准 (b)
val patternMap = mapOf(
        // 活2
        "sbbs" to 20,
        // 空3
        "sbsbs" to 10,
        // 半活3
        "sbbbw" to 50,
        "wbbbs" to 50,
        // 活3
        "sbbbs" to 1300,
        // 半空4
        "sbsbbw" to 500,
        "wbsbbs" to 500,
        "sbbsbw" to 500,
        "wbbsbs" to 500,
        // 空4
        "sbsbbs" to 1200,
        "sbbsbs" to 1200,
        // 半活4
        "sbbbbw" to 2000,
        "wbbbbs" to 2000,
        // 活4 (gg)
        "sbbbbs" to 100000,
        // 空5
        "bsbbb" to 2400,
        "bbsbb" to 2200,
        "bbbsb" to 2400,
        // 5连珠
        "bbbbb" to 1000000
)

val String.bwInverted: String
    get() = this.map { when (it) {
        'b' -> 'w'
        'w' -> 'b'
        else -> it
    } }.joinToString(separator = "")

fun main() {
    println(patternMap.keys)
    print("wbbsbswb".bwInverted)
}