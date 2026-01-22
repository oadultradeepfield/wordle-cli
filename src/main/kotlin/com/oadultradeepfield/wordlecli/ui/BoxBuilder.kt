package com.oadultradeepfield.wordlecli.ui

/**
 * BoxBuilder is a class that helps in creating a box layout for the Wordle game.
 * It provides methods to add lines of text to the box and build the final box string.
 *
 * @param title The title of the box.
 */
class BoxBuilder(private val title: String) {
    private val lines = mutableListOf<String>()

    fun line(content: String) {
        lines.add(content)
    }

    fun build(): String {
        val maxWidth = (lines.maxOfOrNull { it.length } ?: 0).coerceAtLeast(title.length) + 4
        return ConsoleFormatter.createBox(title, lines, maxWidth)
    }
}
