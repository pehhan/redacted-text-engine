package se.phan.redacted.textengine

data class Game(val text: Text, val title: Text, val state: GameState, val numberOfGuesses: Int) {

    constructor(text: Text, title: Text) : this(text, title, GameState.InProgress, 0)

    fun makeGuess(guess: Guess): Game {
        val titleText = makeGuessForTitle(guess)

        return if (titleText.areAllWordsUnredacted()) {
            Game(text.unredactAll(), titleText, GameState.Completed, numberOfGuesses + 1)
        } else {
            makeGuessForText(guess, titleText)
        }
    }

    private fun makeGuessForTitle(guess: Guess): Text {
        return when (val titleResult = title.makeGuess(guess)) {
            is WordUnredacted -> titleResult.text
            is WordAlreadyUnredacted, is WordNotInText -> title
        }
    }

    private fun makeGuessForText(guess: Guess, title: Text): Game {
        return when (val guessResult = text.makeGuess(guess)) {
            is WordUnredacted -> Game(guessResult.text, title, GameState.InProgress, numberOfGuesses + 1)
            is WordAlreadyUnredacted -> this
            is WordNotInText -> Game(text, title, state, numberOfGuesses + 1)
        }
    }
}
