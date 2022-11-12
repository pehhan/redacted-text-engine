package se.phan.redacted.textengine.parser

import se.phan.redacted.textengine.*

object TextParser {

    private val punctuationChars = listOf(
        '.',
        ',',
        '!',
        '?'
    )

    private val spaceChars = listOf(' ')
    private val newlineChars = listOf('\n')

    fun parse(str: String): RedactedText {
        var currentWord = ""
        val items: MutableList<RedactedTextItem> = mutableListOf()

        for (char in str) {
            when {
                char.isSpecialCharacter() -> {
                    if (currentWord.isNotEmpty()) {
                        items += Word(currentWord)
                    }
                    items += char.textItemForSpecialCharacter()
                    currentWord = ""
                }
                else -> currentWord += char
            }
        }

        if (currentWord.isNotEmpty()) {
            items += Word(currentWord)
        }

        return RedactedText(items)
    }

    private fun Char.isSpecialCharacter(): Boolean {
        return this in punctuationChars || this in spaceChars || this in newlineChars
    }

    private fun Char.textItemForSpecialCharacter(): RedactedTextItem {
        return when (this) {
            in punctuationChars -> Punctuation(this)
            in spaceChars -> Space
            in newlineChars -> Newline
            else -> throw IllegalArgumentException("Invalid character: $this")
        }
    }
}
