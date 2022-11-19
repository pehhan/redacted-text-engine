package se.phan.redacted.textengine

data class Text(val parts: List<TextPart>) {

    fun makeGuess(guess: Guess): GuessResult {
        return when (numberOfMatches(guess)) {
            0 -> WordNotInText
            else -> makeGuessForWordInText(guess)
        }
    }

    // TODO: Tests
    fun areAllWordsUnredacted(): Boolean {
        return parts
            .filterIsInstance<Word>()
            .all { !it.redacted }
    }

    // TODO: Tests
    fun unredactAll(): Text {
        val parts = parts.map { part ->
            if (part is Word) {
                part.copy(redacted = false)
            } else {
                part
            }
        }

        return Text(parts)
    }

    private fun makeGuessForWordInText(guess: Guess): GuessResult {
        return if (isGuessRedacted(guess)) {
            WordUnredacted(unredactText(guess), numberOfMatches(guess))
        } else {
            WordAlreadyUnredacted
        }
    }

    private fun unredactText(guess: Guess): Text {
        val parts = parts.map { part ->
            if (part is Word && part.matches(guess)) {
                part.copy(redacted = false)
            } else {
                part
            }
        }

        return Text(parts)
    }

    private fun numberOfMatches(guess: Guess): Int {
        return parts
            .filterIsInstance<Word>()
            .count { it.matches(guess) }
    }

    private fun isGuessRedacted(guess: Guess): Boolean {
        return parts
            .filterIsInstance<Word>()
            .filter { it.matches(guess) }
            .all { it.redacted }
    }
}
