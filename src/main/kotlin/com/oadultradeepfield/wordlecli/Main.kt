package com.oadultradeepfield.wordlecli

import com.oadultradeepfield.wordlecli.logic.WordleGame
import com.oadultradeepfield.wordlecli.model.GameAction
import com.oadultradeepfield.wordlecli.model.GameConfig
import com.oadultradeepfield.wordlecli.model.GameState
import com.oadultradeepfield.wordlecli.model.Result
import com.oadultradeepfield.wordlecli.ui.Console

fun main() {
    val config = GameConfig.builder()
        .wordLength(5)
        .maxAttempts(6)
        .wordFile("words.txt")
        .build()

    val game = WordleGame(config)
    val ui = Console(config)

    ui.showBanner()
    ui.showHelp()

    game.startGame()

    gameLoop@ while (true) {
        ui.showGuessHistory(game.history)

        if (game.currentState is GameState.Won) {
            val wonState = game.currentState as GameState.Won
            ui.showGameEnd(wonState, game.history)
        }

        if (game.currentState is GameState.Lost) {
            val lostState = game.currentState as GameState.Lost
            ui.showGameEnd(lostState, game.history)
        }

        ui.showPrompt(game.remainingGuesses)

        val input = readlnOrNull() ?: continue

        when (val action = ui.parseInput(input)) {
            is GameAction.Quit -> {
                println("\n👋 Thanks for playing!")
                return
            }

            is GameAction.Help -> ui.showHelp()
            is GameAction.NewGame -> game.startGame()
            is GameAction.Guess -> {
                when (val result = game.makeGuess(action.word)) {
                    is Result.Ok -> ui.showGuess(result.value)
                    is Result.Err -> ui.showError(result.message)
                }
            }
        }
    }
}