package com.oadultradeepfield.wordlecli.logic

import com.oadultradeepfield.wordlecli.model.*
import kotlin.properties.Delegates

/**
 * WordleGame is a class that implements the game logic for the Wordle game.
 * It keeps track of the game state, secret word, and observers.
 *
 * @param config The configuration settings for the game.
 */
class WordleGame(private val config: GameConfig) {
    private lateinit var secretWord: String
    private var state: GameState by Delegates.notNull()
    private val guesses = mutableListOf<GuessResult>()

    val currentState: GameState get() = state
    val attempts: Int get() = guesses.size
    val remainingGuesses: Int get() = config.maxAttempts - attempts
    val history: List<GuessResult> get() = guesses.toList()

    fun startGame() {
        secretWord = WordBank.randomWord()
        guesses.clear()
        state = GameState.Playing
    }

    fun makeGuess(guess: String): Result<GuessResult> {
        if (state != GameState.Playing) {
            return Result.Err("Game is not started or already finished.")
        }

        if (!WordBank.validateWord(guess)) {
            return Result.Err("The guess word '$guess' is not valid.")
        }

        return Result.Ok(evaluateGuess(guess)).also { result ->
            guesses.add(result.value)
            updateGameState(result.value)
        }
    }

    /**
     * Evaluates the guess word and returns the corresponding GuessResult.
     *
     * @param guess The guess word to be evaluated.
     * @return The GuessResult containing the guessed word and the letter results.
     */
    private fun evaluateGuess(guess: String): GuessResult {
        val secretChars = secretWord.toMutableList()
        val results = mutableListOf<LetterResult>()

        repeat(config.wordLength) {
            results.add(LetterResult.Wrong)
        }

        guess.forEachIndexed { index, char ->
            if (char == secretWord[index]) {
                results[index] = LetterResult.Correct
                secretChars[index] = '_'
            }
        }

        with(guess) {
            forEachIndexed { index, char ->
                if (results[index] != LetterResult.Correct) {
                    val secretIndex = secretChars.indexOf(char)
                    if (secretIndex != -1) {
                        results[index] = LetterResult.WrongPosition
                        secretChars[secretIndex] = '_'
                    }
                }
            }
        }

        return GuessResult(guess, results)
    }

    private fun updateGameState(result: GuessResult) {
        state = when {
            result.isWin -> GameState.Won(attempts)
            attempts >= config.maxAttempts -> GameState.Lost(secretWord)
            else -> GameState.Playing
        }
    }

    /**
     * INLINE + REIFIED: Generic handler for game results
     */
    inline fun <reified T : GameState> onState(action: (T) -> Unit) {
        (currentState as? T)?.let(action)
    }
}