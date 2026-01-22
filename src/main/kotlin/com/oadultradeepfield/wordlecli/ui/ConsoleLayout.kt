package com.oadultradeepfield.wordlecli.ui

/**
 * ConsoleLayout is a builder class for creating formatted console output.
 */
class ConsoleLayout {
    private val elements = mutableListOf<String>()

    fun box(title: String, block: BoxBuilder.() -> Unit) {
        val builder = BoxBuilder(title)
        builder.block()
        elements.add(builder.build())
    }

    fun text(content: String) {
        elements.add(content)
    }

    fun render(): String {
        val result = elements.joinToString("\n\n")
        elements.clear()
        return result
    }
}