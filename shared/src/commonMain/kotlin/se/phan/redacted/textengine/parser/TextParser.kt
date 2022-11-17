package se.phan.redacted.textengine.parser

import se.phan.redacted.textengine.*

object TextParser {

    private data class ParsedResult(val parts: List<TextPart>, val currentWord: String) {
        constructor() : this(emptyList(), "")
        constructor(parts: List<TextPart>) : this(parts, "")
    }

    fun parse(str: String): Text {
        val result = str.foldIndexed(ParsedResult()) { index, result, char ->
            when {
                char.isSpecialCharacter() -> parseSpecialCharacter(result, char)
                else -> parseNormalCharacter(result, char, index, str)
            }
        }

        return Text(result.parts)
    }

    private fun parseSpecialCharacter(result: ParsedResult, char: Char): ParsedResult {
        return if (result.currentWord.isNotEmpty()) {
            parseSpecialCharacterWithPrecedingWord(result, char)
        } else {
            parseSpecialCharacterWithoutPrecedingWord(result, char)
        }
    }

    private fun parseSpecialCharacterWithPrecedingWord(result: ParsedResult, char: Char): ParsedResult {
        return ParsedResult(result.parts + Word(result.currentWord) + char.toTextPart())
    }

    private fun parseSpecialCharacterWithoutPrecedingWord(result: ParsedResult, char: Char): ParsedResult {
        return ParsedResult(result.parts + char.toTextPart())
    }

    private fun parseNormalCharacter(result: ParsedResult, char: Char, index: Int, str: String): ParsedResult {
        return if (index == str.length - 1) {
            parseLastNormalCharacter(result, char)
        } else {
            parseNonLastNormalCharacter(result, char)
        }
    }

    private fun parseLastNormalCharacter(result: ParsedResult, char: Char): ParsedResult {
        return ParsedResult(result.parts + Word(result.currentWord + char))
    }

    private fun parseNonLastNormalCharacter(result: ParsedResult, char: Char): ParsedResult {
        return ParsedResult(result.parts, result.currentWord + char)
    }

    private fun Char.isSpecialCharacter(): Boolean {
        return this in SpecialCharacters.ALL
    }

    private fun Char.toTextPart(): TextPart {
        return when (this) {
            in SpecialCharacters.PUNCTUATION -> Punctuation(this)
            in SpecialCharacters.SPACE -> Space
            in SpecialCharacters.NEWLINE -> Newline
            else -> throw IllegalArgumentException("Invalid character: $this")
        }
    }
}
