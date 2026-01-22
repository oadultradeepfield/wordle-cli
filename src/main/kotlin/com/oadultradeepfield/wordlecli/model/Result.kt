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

    /**
     * Non-Inline function: During runtime, there will be an anonymous class created for the lambda parameter.
     * Inline function: The compiler replaces the function call with actual function body. There will be less runtime
     * and memory overhead.
     *
     * Further Note: Inline function is usually used with 'reified' which is a keyword use with generic types to make it
     * survives the type-erasure and accessible at runtime.
     */
    inline fun <R> map(transform: (T) -> R): Result<R> = when (this) {
        is Ok -> Ok(transform(value))
        is Err -> Err(message)
    }
}