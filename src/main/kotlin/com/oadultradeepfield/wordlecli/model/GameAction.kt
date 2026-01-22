package com.oadultradeepfield.wordlecli.model

/**
 * GameAction is a sealed class that represents the possible actions a player can take in the game.
 */
sealed class GameAction {
    object Quit : GameAction()
    object Help : GameAction()
    object NewGame : GameAction()
    data class Guess(val word: String) : GameAction()
}
