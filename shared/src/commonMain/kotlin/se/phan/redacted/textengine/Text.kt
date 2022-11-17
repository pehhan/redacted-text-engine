package se.phan.redacted.textengine

data class Text(val items: List<TextPart>) {

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
        val items = items.map { item ->
            if (item is Word && item.matches(guess)) {
                item.copy(redacted = false)
            } else {
                item
            }
        }

        return Text(items)
    }

    private fun numberOfMatches(guess: Guess): Int {
        return items
            .filterIsInstance<Word>()
            .count { it.matches(guess) }
    }
}
