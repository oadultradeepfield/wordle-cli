package model

/**
 * GameState represents the current state of the game.
 */
sealed class GameState {
    data object Playing : GameState()

    /**
     * Represents the game state when the player has won the game.
     *
     * @param attempts The number of attempts made by the player to guess the secret word.
     */
    data class Won(val attempts: Int) : GameState()

    /**
     * Represents the game state when the player has lost the game.
     *
     * @param secretWord The secret word that the player was trying to guess.
     */
    data class Loss(val secretWord: String) : GameState()
}