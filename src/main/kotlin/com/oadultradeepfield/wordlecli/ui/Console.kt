package com.oadultradeepfield.wordlecli.ui

import com.oadultradeepfield.wordlecli.model.*

/**
 * Console is a class that handles the user interface interactions for the Wordle game.
 * It provides methods to display banners, help texts, prompts, and guess results.
 *
 * @param config The configuration settings for the game.
 */
class Console(private val config: GameConfig) {
    private val layout = ConsoleLayout()

    private val helpText: String by lazy {
        layout.apply {
            box("HOW TO PLAY WORDLE") {
                line("Guess the word in ${config.maxAttempts} tries.")
                line("")
                line("${LetterResult.Correct.emoji} = Correct letter & position")
                line("${LetterResult.WrongPosition.emoji} = Correct letter, wrong spot")
                line("${LetterResult.Wrong.emoji} = Letter not in word")
                line("")
                line("Commands: quit, help, stats, new")
            }
        }.render()
    }

    private val banner: String by lazy {
        layout.apply {
            text(
                """
                U  ___ u   ____     ____     _     U _____ u 
 __        __    \/"_ \/U |  _"\ u |  _"\   |"|    \| ___"|/ 
 \"\      /"/    | | | | \| |_) |//| | | |U | | u   |  _|"   
 /\ \ /\ / /\.-,_| |_| |  |  _ <  U| |_| |\\| |/__  | |___   
U  \ V  V /  U\_)-\___/   |_| \_\  |____/ u |_____| |_____|  
.-,_\ /\ /_,-.     \\     //   \\_  |||_    //  \\  <<   >>  
 \_)-'  '-(_/     (__)   (__)  (__)(__)_)  (_")("_)(__) (__) 
            """.trimIndent()
            )
        }.render()
    }

    fun showBanner() = println("\n$banner\n")

    fun showHelp() = println(helpText)

    fun showPrompt(attemptsRemaining: Int) {
        print("\n[$attemptsRemaining tries left] Enter guess: ")
    }

    fun parseInput(input: String): GameAction {
        return input.trim().lowercase().let { normalized ->
            when (normalized) {
                "quit", "q", "exit" -> GameAction.Quit
                "help", "h", "?" -> GameAction.Help
                "new", "n", "restart" -> GameAction.NewGame
                else -> GameAction.Guess(normalized)
            }
        }
    }

    fun showGuess(result: GuessResult) {
        val guessDisplay = layout.apply {
            box("") {
                line(result.letters.joinToString("") { it.emoji })
            }
        }.render()

        println("\n${result.display()}")
        println(guessDisplay)
    }

    fun showGuessHistory(history: List<GuessResult>) {
        if (history.isEmpty()) return

        val historyBox = layout.apply {
            box("GUESS HISTORY") {
                history.forEach { guess ->
                    line(guess.letters.joinToString("") { it.emoji })
                }
            }
        }.render()

        println(historyBox)
    }

    fun showGameEnd(state: GameState, history: List<GuessResult>) {
        val endMessage = when (state) {
            is GameState.Won -> {
                "🎉 BRILLIANT! You got it in ${state.attempts} ${if (state.attempts == 1) "try" else "tries"}!"
            }

            is GameState.Lost -> {
                "😔 Game Over! The word was: ${state.secretWord.uppercase()}"
            }

            /* shouldn't happen */
            is GameState.Playing -> ""
        }

        val gameEndLayout = layout.apply {
            if (endMessage.isNotEmpty()) {
                text(endMessage)
            }
            box("YOUR GAME") {
                history.forEach { guess ->
                    line(guess.letters.joinToString("") { it.emoji })
                }
            }
        }.render()

        println(gameEndLayout)
    }

    fun showError(message: String) {
        val errorBox = layout.apply {
            box("ERROR") {
                line(message)
            }
        }.render()

        println(errorBox)
    }
}