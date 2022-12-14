package se.phan.redacted.textengine.renderer

import se.phan.redacted.textengine.text.*

class TrueWordLengthPunctuationVisibleRenderer : TextRenderer {

    override fun render(text: Text): String {
        return text.parts.fold("") { result, part ->
            when (part) {
                is Word -> result + renderWord(part)
                is Punctuation -> result + renderPunctuation(part)
                is Space -> result + renderSpace()
                is Newline -> result + renderNewline()
            }
        }
    }

    private fun renderWord(word: Word): String {
        return if (word.redacted) {
            REDACTED_SIGN.repeat(word.value.length)
        } else {
            word.value
        }
    }

    private fun renderPunctuation(punctuation: Punctuation): Char {
        return punctuation.sign
    }

    private fun renderSpace(): String {
        return SPACE_SIGN
    }

    private fun renderNewline(): String {
        return NEWLINE_SIGN
    }

    companion object {
        private const val SPACE_SIGN = " "
        private const val NEWLINE_SIGN = "\n"
        private const val REDACTED_SIGN = "█"
    }
}