package se.phan.redacted.textengine

data class RedactedText(val items: List<RedactedTextItem>) {

    // TODO: Handle the case when the guessed word was already unredacted
    // Make GuessResult a sealed class:
    // data class WordAlreadyUnredacted
    // data class WordUnredacted
    // data class WordNotInText
    // ?
    fun makeGuess(guess: Guess): GuessResult {
        return GuessResult(unredactText(guess), numberOfMatches(guess))
    }

    private fun unredactText(guess: Guess): RedactedText {
        val items = items.map { item ->
            if (item is Word && item.matches(guess)) {
                item.copy(redacted = false)
            } else {
                item
            }
        }

        return RedactedText(items)
    }

    private fun numberOfMatches(guess: Guess): Int {
        return items
            .filterIsInstance<Word>()
            .count { it.matches(guess) }
    }
}
