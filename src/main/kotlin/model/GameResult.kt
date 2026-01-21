package model

/**
 * GameResult represents the result of a game, including the guessed word and the corresponding letter results.
 *
 * @param word The guessed word.
 * @param letters The list of letter results corresponding to each letter in the guessed word.
 */
data class GameResult(val word: String, val letters: List<LetterResult>) {
    val isWin: Boolean = letters.all { it is LetterResult.Correct }

    // By using 'run' function, we can avoid the use of 'let' function and make the code more concise.
    fun display(): String = run {
        val colored = word.mapIndexed { index, ch ->
            "$ch ${letters[index].emoji}"
        }
        colored.joinToString(" ")
    }
}