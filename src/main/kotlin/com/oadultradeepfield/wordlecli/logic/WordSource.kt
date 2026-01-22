package com.oadultradeepfield.wordlecli.logic

import com.oadultradeepfield.wordlecli.model.Result

interface WordSource {
    fun loadWords(): Result<List<String>>
}

/**
 * ReliableWordSource is an interface for word sources that guarantee success.
 */
interface ReliableWordSource {
    fun getWords(): List<String>
}

internal class FileWordSource(private val filePath: String) : WordSource {
    override fun loadWords(): Result<List<String>> {
        return javaClass.getResourceAsStream(filePath)?.bufferedReader()?.readLines()
            ?.let { Result.Ok(it) }
            ?: Result.Err("Could not load $filePath")
    }
}

internal class DefaultWordSource : ReliableWordSource {
    override fun getWords(): List<String> =
        listOf(
            "aback",
            "abaft",
            "abase",
            "abate",
            "abbey",
        )
}

/**
 * CachingWordSource implements a three-step mechanism and guarantees success:
 * 1. Load from cache
 * 2. If fails, load from file
 * 3. If fails, load default
 *
 * @param filePath The path to the file containing the words.
 */
class CachingWordSource(private val filePath: String) : ReliableWordSource {
    private var cachedWords: List<String>? = null

    override fun getWords(): List<String> {
        cachedWords?.let { return it }

        val words = when (val result = FileWordSource(filePath).loadWords()) {
            is Result.Ok -> {
                println("Loaded ${result.value.size} words from file")
                result.value
            }

            is Result.Err -> {
                println("Failed to load words: ${result.message}")
                println("Using default word list")
                DefaultWordSource().getWords()
            }
        }

        cachedWords = words
        return words
    }
}
