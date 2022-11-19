package se.phan.redacted.textengine

data class Game(val text: Text, val title: Text, val guesses: List<Guess>) {

    constructor(text: Text, title: Text) : this(text, title, emptyList())

    fun isCompleted(): Boolean {
        return title.areAllWordsUnredacted()
    }

    fun makeGuess(guess: Guess): Game {
        val titleText = makeGuessForTitle(guess)

        return if (titleText.areAllWordsUnredacted()) {
            Game(text.unredactAll(), titleText, guessesWithNewGuess(guess))
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
            is WordUnredacted -> Game(result.text, title, guessesWithNewGuess(guess))
            is WordAlreadyUnredacted -> this
            is WordNotInText -> Game(text, title, guessesWithNewGuess(guess))
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
