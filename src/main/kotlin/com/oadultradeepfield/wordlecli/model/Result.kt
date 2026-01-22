package com.oadultradeepfield.wordlecli.model

/**
 * Result is a sealed class that represents either success (Ok) or failure (Err) with associated data.
 * Note: The 'out' keyword is used to make the type covariant.
 *
 * @param T The type of the successful value.
 */
sealed class Result<out T> {
    data class Ok<T>(val value: T) : Result<T>()
    data class Err(val message: String) : Result<Nothing>()
}