package se.phan.redacted.textengine

data class Game(val title: Text, val text: Text, val guesses: List<Guess>) {

    constructor(title: Text, text: Text) : this(title, text, emptyList())

    fun isCompleted(): Boolean {
        return title.areAllWordsUnredacted()
    }

    fun makeGuess(guess: Guess): Game {
        val titleText = makeGuessForTitle(guess)

        return if (titleText.areAllWordsUnredacted()) {
            Game(titleText, text.unredactAll(), guessesWithNewGuess(guess))
        } else {
            makeGuessForText(guess, titleText)
        }
    }

    private fun makeGuessForTitle(guess: Guess): Text {
        return when (val result = title.makeGuess(guess)) {
            is WordUnredacted -> result.text
            is WordAlreadyUnredacted, is WordNotInText -> title
        }
    }

    private fun makeGuessForText(guess: Guess, title: Text): Game {
        return when (val result = text.makeGuess(guess)) {
            is WordUnredacted -> Game(title, result.text, guessesWithNewGuess(guess))
            is WordAlreadyUnredacted -> this
            is WordNotInText -> Game(title, text, guessesWithNewGuess(guess))
        }
    }

    private fun guessesWithNewGuess(guess: Guess): List<Guess> {
        return if (alreadyGuessed(guess)) {
            guesses
        } else {
            guesses + guess
        }
    }

    private fun alreadyGuessed(guess: Guess): Boolean {
        return guess in guesses
    }
}
