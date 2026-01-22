package com.oadultradeepfield.wordlecli.ui

/**
 * ConsoleFormatter provides utility functions for formatting console output.
 */
object ConsoleFormatter {
    fun createBox(title: String, content: List<String>, width: Int = 40): String {
        val topBorder = "╔${"═".repeat(width - 2)}╗"
        val bottomBorder = "╚${"═".repeat(width - 2)}╝"
        val titleLine = "║ ${title.padEnd(width - 4)} ║"
        val separator = "╠${"═".repeat(width - 2)}╣"

        val contentLines = content.map { line ->
            "║ ${line.padEnd(width - 4)} ║"
        }

        return (listOf(topBorder, titleLine, separator) + contentLines + listOf(bottomBorder))
            .joinToString("\n")
    }
}
