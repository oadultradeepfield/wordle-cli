package com.oadultradeepfield.wordlecli.ui

import com.oadultradeepfield.wordlecli.model.*

/**
 * Console handles the user interface interactions for the Wordle game.
 */
class Console(private val config: GameConfig) {
    private val banner = """
                        U  ___ u   ____     ____     _     U _____ u 
         __        __    \/"_ \/U |  _"\ u |  _"\   |"|    \| ___"|/ 
         \"\      /"/    | | | | \| |_) |//| | | |U | | u   |  _|"   
         /\ \ /\ / /\.-,_| |_| |  |  _ <  U| |_| |\\| |/__  | |___   
        U  \ V  V /  U\_)-\___/   |_| \_\  |____/ u |_____| |_____|  
        .-,_\ /\ /_,-.     \\     //   \\_  |||_    //  \\  <<   >>  
         \_)-'  '-(_/     (__)   (__)  (__)(__)_)  (_")("_)(__) (__) 
    """.trimIndent()

    private val helpText: String by lazy {
        ConsoleLayout().apply {
            box("HOW TO PLAY WORDLE") {
                line("Guess the word in ${config.maxAttempts} tries.")
                emptyLine()
                line("${LetterResult.Correct.emoji} = Correct letter & position")
                line("${LetterResult.WrongPosition.emoji} = Correct letter, wrong spot")
                line("${LetterResult.Wrong.emoji} = Letter not in word")
                emptyLine()
                line("Commands: quit, help, stats, new")
            }
        }.render()
    }

    fun showBanner() {
        println()
        println(banner)
        println()
    }

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
        println()
        val box = ConsoleLayout().apply {
            box("") {
                guess(result)
            }
        }.render()
        println(box)
    }

    fun showGuessHistory(history: List<GuessResult>) {
        if (history.isEmpty()) return

        val box = ConsoleLayout().apply {
            box("GUESS HISTORY") {
                history.forEachIndexed { index, guess ->
                    if (index > 0) emptyLine()
                    guess(guess)
                }
            }
        }.render()
        println(box)
    }

    fun showGameEnd(state: GameState, history: List<GuessResult>) {
        println()

        when (state) {
            is GameState.Won -> {
                val tries = if (state.attempts == 1) "try" else "tries"
                println("🎉 BRILLIANT! You got it in ${state.attempts} $tries!")
            }

            is GameState.Lost -> {
                println("😔 Game Over! The word was: ${state.secretWord.uppercase()}")
            }

            is GameState.Playing -> return
        }

        println()

        val box = ConsoleLayout().apply {
            box("YOUR GAME") {
                history.forEachIndexed { index, guess ->
                    if (index > 0) emptyLine()
                    guess(guess)
                }
            }
        }.render()
        println(box)
    }

    fun showError(message: String) {
        println()
        println("⚠ ERROR: $message")
    }
}