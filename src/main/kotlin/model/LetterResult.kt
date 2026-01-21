package model

/**
 * LetterResult represents the result of a single letter in a word guess.
 *
 * @param emoji The emoji representation of the letter result.
 */
sealed class LetterResult(val emoji: String) {
    object Correct : LetterResult("\uD83D\uDFE9")
    object WrongPosition : LetterResult("\uD83D\uDFE8")
    object Wrong : LetterResult("\uD83D\uDFE5")
}