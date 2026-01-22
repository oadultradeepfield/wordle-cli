package com.oadultradeepfield.wordlecli.model

/**
 * GameConfig represents the configuration settings for a game, including the word length, maximum attempts,
 * and word file path.
 *
 * @param wordLength The length of the words to be guessed.
 * @param maxAttempts The maximum number of attempts allowed for guessing the word.
 * @param wordFilePath The file path to the word list used for the game.
 */
data class GameConfig(val wordLength: Int, val maxAttempts: Int, val wordFilePath: String) {
    class Builder {
        private var wordLength: Int = 5
        private var maxAttempts: Int = 6

        // This s
        private var wordFilePath: String = "words.txt"

        fun wordLength(value: Int) = apply { wordLength = value }
        fun maxAttempts(value: Int) = apply { maxAttempts = value }
        fun wordFile(path: String) = apply { wordFilePath = path }

        fun build() = GameConfig(wordLength, maxAttempts, wordFilePath)
    }

    companion object {
        fun builder() = Builder()
    }
}