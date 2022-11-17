package se.phan.redacted.textengine

data class Text(val parts: List<TextPart>) {

    // TODO: Handle the case when the guessed word was already unredacted
    // Make GuessResult a sealed class:
    // data class WordAlreadyUnredacted
    // data class WordUnredacted
    // data class WordNotInText
    // ?
    fun makeGuess(guess: Guess): GuessResult {
        return GuessResult(unredactText(guess), numberOfMatches(guess))
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
}
