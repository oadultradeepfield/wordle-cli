package com.oadultradeepfield.wordlecli

import com.oadultradeepfield.wordlecli.logic.WordleGame
import com.oadultradeepfield.wordlecli.model.GameAction
import com.oadultradeepfield.wordlecli.model.GameConfig
import com.oadultradeepfield.wordlecli.model.GameState
import com.oadultradeepfield.wordlecli.model.Result
import com.oadultradeepfield.wordlecli.ui.Console

fun main() {
    val config = GameConfig.withDefaults().build()
    val game = WordleGame(config)
    val ui = Console(config)

    ui.showBanner()
    ui.showHelp()

    game.startGame()

    gameLoop@ while (true) {
        ui.showGuessHistory(game.history)

        game.onState<GameState.Won> { wonState ->
            ui.showGameEnd(wonState, game.history)
        }

        game.onState<GameState.Lost> { lostState ->
            ui.showGameEnd(lostState, game.history)
        }

        if (game.currentState != GameState.Playing) {
            print("\nPlay again? (yes/no): ")
            val input = readlnOrNull()?.lowercase() ?: "no"
            if (input in listOf("yes", "y")) {
                game.startGame()
                continue@gameLoop
            } else {
                break@gameLoop
            }
        }

        ui.showPrompt(game.remainingGuesses)

        val input = readlnOrNull() ?: continue

        ui.parseInput(input).let { action ->
            when (action) {
                is GameAction.Quit -> {
                    println("\n👋 Thanks for playing!")
                    return
                }

                is GameAction.Help -> ui.showHelp()
                is GameAction.NewGame -> game.startGame()
                is GameAction.Guess -> {
                    game.makeGuess(action.word).run {
                        when (this) {
                            is Result.Ok -> ui.showGuess(value)
                            is Result.Err -> ui.showError(message)
                        }
                    }
                }
            }
        }
    }

    println("\n👋 Thanks for playing WordleCLI!")
}