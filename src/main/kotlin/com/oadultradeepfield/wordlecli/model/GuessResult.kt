package com.oadultradeepfield.wordlecli.model

/**
 * GuessResult represents the result of a game, including the guessed word and the corresponding letter results.
 *
 * @param word The guessed word.
 * @param letters The list of letter results corresponding to each letter in the guessed word.
 */
data class GuessResult(val word: String, val letters: List<LetterResult>) {
    val isWin: Boolean = letters.all { it is LetterResult.Correct }

    fun displayWord(): String = word.uppercase().toList().joinToString(" ")

    fun displayEmojis(): String = letters.joinToString(" ") { it.emoji }

    fun displayLines(): Pair<String, String> = displayWord() to displayEmojis()
}