package com.oadultradeepfield.wordlecli.logic

import com.oadultradeepfield.wordlecli.model.GameConfig

object WordBank {
    private lateinit var source: ReliableWordSource
    private lateinit var config: GameConfig

    private val validWords: List<String> by lazy {
        source.getWords().filter { it.length == config.wordLength }
    }

    fun configure(config: GameConfig, source: ReliableWordSource) {
        this.config = config
        this.source = source
    }

    fun randomWord(): String = validWords.random()

    /**
     * Validates whether a given word is a valid word in the word bank.
     *
     * @param word The word to validate.
     * @return True if the word is valid, false otherwise.
     */
    fun validateWord(word: String): Boolean {
        return word.length == config.wordLength && validWords.contains(word)
    }
}