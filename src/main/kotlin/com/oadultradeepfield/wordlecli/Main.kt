package com.oadultradeepfield.wordlecli

import com.oadultradeepfield.wordlecli.logic.CachingWordSource
import com.oadultradeepfield.wordlecli.logic.WordBank
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

    /**
     * Warning: The word bank singleton object must be configured before the game starts.
     */
    WordBank.configure(config, CachingWordSource(config.wordFilePath))

    val game = WordleGame(config)
    val ui = Console(config)

    ui.showBanner()
    ui.showHelp()

    game.startGame()

    gameLoop@ while (true) {
        ui.showGuessHistory(game.history)

        game.onState<GameState.Won> { wonState ->
            ui.showGameEnd(wonState, game.history)
            break@gameLoop
        }

        game.onState<GameState.Lost> { lostState ->
            ui.showGameEnd(lostState, game.history)
            break@gameLoop
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