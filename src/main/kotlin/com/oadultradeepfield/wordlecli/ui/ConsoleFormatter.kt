package com.oadultradeepfield.wordlecli.ui

/**
 * ConsoleFormatter provides utility functions for formatting console output.
 */
object ConsoleFormatter {
    private const val DEFAULT_WIDTH = 32
    private const val LINE_CHAR = "═"

    fun createBox(title: String, content: List<String>, width: Int = DEFAULT_WIDTH): String {
        val line = LINE_CHAR.repeat(width)

        val result = StringBuilder()
        result.appendLine(line)

        if (title.isNotEmpty()) {
            result.appendLine(title)
            result.appendLine(line)
        }

        content.forEach { result.appendLine(it) }
        result.append(line)

        return result.toString()
    }
}