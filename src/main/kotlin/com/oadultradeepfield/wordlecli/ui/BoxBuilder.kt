package com.oadultradeepfield.wordlecli.ui

import com.oadultradeepfield.wordlecli.model.GuessResult

/**
 * BoxBuilder is a class that helps in creating a box layout for the Wordle game.
 */
class BoxBuilder(private val title: String) {
    private val lines = mutableListOf<String>()

    fun line(content: String) {
        lines.add(content)
    }

    fun emptyLine() {
        lines.add("")
    }

    fun guess(result: GuessResult) {
        lines.add(result.displayWord())
        lines.add(result.displayEmojis())
    }

    fun build(): String {
        val maxWidth = maxOf(
            lines.maxOfOrNull { it.length } ?: 0,
            title.length,
            20
        )
        return ConsoleFormatter.createBox(title, lines, maxWidth)
    }
}