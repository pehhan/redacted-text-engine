package se.phan.redacted.textengine.parser

import se.phan.redacted.textengine.*

object TextParser {

    fun parse(str: String): Text {
        var currentWord = ""
        val items: MutableList<TextPart> = mutableListOf()

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

        return Text(items)
    }

    private fun Char.isSpecialCharacter(): Boolean {
        return this in SpecialCharacters.ALL
    }

    private fun Char.textItemForSpecialCharacter(): TextPart {
        return when (this) {
            in SpecialCharacters.PUNCTUATION -> Punctuation(this)
            in SpecialCharacters.SPACE -> Space
            in SpecialCharacters.NEWLINE -> Newline
            else -> throw IllegalArgumentException("Invalid character: $this")
        }
    }
}
