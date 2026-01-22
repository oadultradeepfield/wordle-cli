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
        private var wordLength: Int = DEFAULT_WORD_LENGTH
        private var maxAttempts: Int = DEFAULT_MAX_ATTEMPTS
        private var wordFilePath: String = DEFAULT_WORD_FILE_PATH

        fun wordLength(value: Int) = apply { wordLength = value }
        fun maxAttempts(value: Int) = apply { maxAttempts = value }
        fun wordFile(path: String) = apply { wordFilePath = path }

        fun build() = GameConfig(wordLength, maxAttempts, wordFilePath)
    }

    companion object {
        const val DEFAULT_WORD_LENGTH = 5
        const val DEFAULT_MAX_ATTEMPTS = 6
        const val DEFAULT_WORD_FILE_PATH = "words.txt"

        fun builder() = Builder()
        fun withDefaults() = Builder()
    }
}