package se.phan.redacted.textengine.parser

import se.phan.redacted.textengine.*

object TextParser {

    fun parse(str: String): RedactedText {
        var currentWord = ""
        val items: MutableList<RedactedTextItem> = mutableListOf()

        for (char in str) {
            when {
                char.isSpecialCharacter() -> {
                    if (currentWord.isNotEmpty()) {
                        items += Word(currentWord, redacted = true)
                    }
                    items += char.textItemForSpecialCharacter()
                    currentWord = ""
                }
                else -> currentWord += char
            }
        }

        if (currentWord.isNotEmpty()) {
            items += Word(currentWord, redacted = true)
        }

        return RedactedText(items)
    }

    private fun Char.isSpecialCharacter(): Boolean {
        return this in SpecialCharacters.ALL
    }

    private fun Char.textItemForSpecialCharacter(): RedactedTextItem {
        return when (this) {
            in SpecialCharacters.PUNCTUATION -> Punctuation(this)
            in SpecialCharacters.SPACE -> Space
            in SpecialCharacters.NEWLINE -> Newline
            else -> throw IllegalArgumentException("Invalid character: $this")
        }
    }
}
