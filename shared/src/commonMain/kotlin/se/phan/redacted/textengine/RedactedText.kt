package se.phan.redacted.textengine

data class RedactedText(val items: List<RedactedTextItem>) {

    // TODO: Handle the case when the guessed word was already unredacted
    fun makeGuess(guess: Guess): GuessResult {
        return GuessResult(unredactText(guess.value), numberOfMatches(guess))
    }

    private fun unredactText(str: String): RedactedText {
        val items = items.map { item ->
            if (item is Word && item.value == str) {
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
            .count { it.value == guess.value }
    }
}
