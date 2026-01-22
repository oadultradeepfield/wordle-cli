package com.oadultradeepfield.wordlecli.ui

/**
 * ConsoleLayout is a class that helps in creating a console layout for the Wordle game.
 * It provides methods to add boxes and text components to the layout.
 */
class ConsoleLayout {
    private val components = mutableListOf<String>()

    fun box(title: String, builder: BoxBuilder.() -> Unit) {
        val boxBuilder = BoxBuilder(title)
        boxBuilder.builder()
        components.add(boxBuilder.build())
    }

    fun text(content: String) {
        components.add(content)
    }

    fun render(): String = components.joinToString("\n\n")
}
