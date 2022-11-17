package se.phan.redacted.textengine.parser

import se.phan.redacted.textengine.*

object TextParser {

    fun parse(str: String): Text {
        var currentWord = ""
        val parts: MutableList<TextPart> = mutableListOf()

        for (char in str) {
            when {
                char.isSpecialCharacter() -> {
                    if (currentWord.isNotEmpty()) {
                        parts += Word(currentWord)
                    }
                    parts += char.textPartForSpecialCharacter()
                    currentWord = ""
                }
                else -> currentWord += char
            }
        }

        if (currentWord.isNotEmpty()) {
            parts += Word(currentWord)
        }

        return Text(parts)
    }

    private fun Char.isSpecialCharacter(): Boolean {
        return this in SpecialCharacters.ALL
    }

    private fun Char.textPartForSpecialCharacter(): TextPart {
        return when (this) {
            in SpecialCharacters.PUNCTUATION -> Punctuation(this)
            in SpecialCharacters.SPACE -> Space
            in SpecialCharacters.NEWLINE -> Newline
            else -> throw IllegalArgumentException("Invalid character: $this")
        }
    }
}
