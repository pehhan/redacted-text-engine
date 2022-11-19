package se.phan.redacted.textengine

import ch.tutteli.atrium.api.fluent.en_GB.toBeEmpty
import ch.tutteli.atrium.api.fluent.en_GB.toEqual
import ch.tutteli.atrium.api.verbs.expect
import se.phan.redacted.textengine.parser.TextParser
import kotlin.test.Test

class GameTest {

    @Test
    fun `when creating a game the number of guesses should be zero and game in progress`() {
        val text = TextParser.parse("Paul Atreides")
        val game = Game(text, text)

        expect(game.guesses).toBeEmpty()
        expect(game.text).toEqual(text)
        expect(game.title).toEqual(text)
        expect(game.state).toEqual(GameState.InProgress)
    }

    @Test
    fun `when making a correct guess the word should be unredacted`() {
        val text = TextParser.parse("Paul Atreides")
        val originalGame = Game(text, text)
        val guess = Guess("Paul")

        val gameWithGuess = originalGame.makeGuess(guess)

        val expectedText = Text(
            listOf(
                Word("Paul", redacted = false),
                Space,
                Word("Atreides", redacted = true)
            )
        )

        expect(gameWithGuess.guesses).toEqual(listOf(guess))
        expect(gameWithGuess.text).toEqual(expectedText)
        expect(gameWithGuess.title).toEqual(expectedText)
        expect(gameWithGuess.state).toEqual(GameState.InProgress)
    }

    @Test
    fun `when making an incorrect guess the guess count should be increased`() {
        val text = TextParser.parse("Paul Atreides")
        val originalGame = Game(text, text)
        val guess = Guess("Dune")

        val gameWithGuess = originalGame.makeGuess(guess)

        val expectedText = Text(
            listOf(
                Word("Paul", redacted = true),
                Space,
                Word("Atreides", redacted = true)
            )
        )

        expect(gameWithGuess.guesses).toEqual(listOf(guess))
        expect(gameWithGuess.text).toEqual(expectedText)
        expect(gameWithGuess.title).toEqual(expectedText)
        expect(gameWithGuess.state).toEqual(GameState.InProgress)
    }

    @Test
    fun `when making the same incorrect guess more than once the guess count should not be increased`() {
        val text = TextParser.parse("Paul Atreides")
        val originalGame = Game(text, text)
        val guess = Guess("Dune")

        val gameWithGuesses = originalGame
            .makeGuess(guess)
            .makeGuess(guess)
            .makeGuess(guess)

        val expectedText = Text(
            listOf(
                Word("Paul", redacted = true),
                Space,
                Word("Atreides", redacted = true)
            )
        )

        expect(gameWithGuesses.guesses).toEqual(listOf(guess))
        expect(gameWithGuesses.text).toEqual(expectedText)
        expect(gameWithGuesses.title).toEqual(expectedText)
        expect(gameWithGuesses.state).toEqual(GameState.InProgress)
    }

    @Test
    fun `when making the same guess twice the guess count should only increase once`() {
        val text = TextParser.parse("Paul Atreides")
        val originalGame = Game(text, text)
        val guess = Guess("Paul")

        val gameWithGuess = originalGame
            .makeGuess(guess)
            .makeGuess(guess)

        val expectedText = Text(
            listOf(
                Word("Paul", redacted = false),
                Space,
                Word("Atreides", redacted = true)
            )
        )

        expect(gameWithGuess.guesses).toEqual(listOf(guess))
        expect(gameWithGuess.text).toEqual(expectedText)
        expect(gameWithGuess.title).toEqual(expectedText)
        expect(gameWithGuess.state).toEqual(GameState.InProgress)
    }

    @Test
    fun `when correctly guessing all words in title the game state should be completed`() {
        val text = TextParser.parse("Paul Atreides")
        val originalGame = Game(text, text)
        val guess1 = Guess("Paul")
        val guess2 = Guess("Atreides")

        val gameWithGuess = originalGame
            .makeGuess(guess1)
            .makeGuess(guess2)

        val expectedText = Text(
            listOf(
                Word("Paul", redacted = false),
                Space,
                Word("Atreides", redacted = false)
            )
        )

        expect(gameWithGuess.guesses).toEqual(listOf(guess1, guess2))
        expect(gameWithGuess.text).toEqual(expectedText)
        expect(gameWithGuess.title).toEqual(expectedText)
        expect(gameWithGuess.state).toEqual(GameState.Completed)
    }

    @Test
    fun `when guessing a word that is only present in the title - game is updated correctly`() {
        val title = TextParser.parse("Dune Messiah")
        val text = TextParser.parse("Paul Atreides")
        val guess1 = Guess("Dune")

        val originalGame = Game(text, title)

        val gameAfterFirstGuess = originalGame.makeGuess(guess1)

        val expectedTitleAfterFirstGuess = Text(
            listOf(
                Word("Dune", redacted = false),
                Space,
                Word("Messiah", redacted = true)
            )
        )

        expect(gameAfterFirstGuess.guesses).toEqual(listOf(guess1))
        expect(gameAfterFirstGuess.text).toEqual(text)
        expect(gameAfterFirstGuess.title).toEqual(expectedTitleAfterFirstGuess)
        expect(gameAfterFirstGuess.state).toEqual(GameState.InProgress)

        val guess2 = Guess("Messiah")
        val gameAfterSecondGuess = gameAfterFirstGuess.makeGuess(guess2)

        val expectedTitleAfterSecondGuess = Text(
            listOf(
                Word("Dune", redacted = false),
                Space,
                Word("Messiah", redacted = false)
            )
        )

        val expectedTextAfterSecondGuess = Text(
            listOf(
                Word("Paul", redacted = false),
                Space,
                Word("Atreides", redacted = false)
            )
        )

        expect(gameAfterSecondGuess.guesses).toEqual(listOf(guess1, guess2))
        expect(gameAfterSecondGuess.text).toEqual(expectedTextAfterSecondGuess)
        expect(gameAfterSecondGuess.title).toEqual(expectedTitleAfterSecondGuess)
        expect(gameAfterSecondGuess.state).toEqual(GameState.Completed)
    }

    @Test
    fun `when guessing a word that is a present in both the title and the text - game is updated correctly`() {
        val title = TextParser.parse("Dune")
        val text = TextParser.parse("Arrakis is another name for Dune.")
        val guess1 = Guess("Arrakis")

        val originalGame = Game(text, title)

        val gameAfterFirstGuess = originalGame.makeGuess(guess1)

        val expectedTextAfterFirstGuess = Text(
            listOf(
                Word("Arrakis", redacted = false),
                Space,
                Word("is", redacted = true),
                Space,
                Word("another", redacted = true),
                Space,
                Word("name", redacted = true),
                Space,
                Word("for", redacted = true),
                Space,
                Word("Dune", redacted = true),
                Punctuation('.')
            )
        )

        expect(gameAfterFirstGuess.guesses).toEqual(listOf(guess1))
        expect(gameAfterFirstGuess.text).toEqual(expectedTextAfterFirstGuess)
        expect(gameAfterFirstGuess.title).toEqual(title)
        expect(gameAfterFirstGuess.state).toEqual(GameState.InProgress)

        val guess2 = Guess("Dune")
        val gameAfterSecondGuess = gameAfterFirstGuess.makeGuess(guess2)

        val expectedTitleAfterSecondGuess = Text(
            listOf(
                Word("Dune", redacted = false)
            )
        )

        val expectedTextAfterSecondGuess = Text(
            listOf(
                Word("Arrakis", redacted = false),
                Space,
                Word("is", redacted = false),
                Space,
                Word("another", redacted = false),
                Space,
                Word("name", redacted = false),
                Space,
                Word("for", redacted = false),
                Space,
                Word("Dune", redacted = false),
                Punctuation('.')
            )
        )

        expect(gameAfterSecondGuess.guesses).toEqual(listOf(guess1, guess2))
        expect(gameAfterSecondGuess.text).toEqual(expectedTextAfterSecondGuess)
        expect(gameAfterSecondGuess.title).toEqual(expectedTitleAfterSecondGuess)
        expect(gameAfterSecondGuess.state).toEqual(GameState.Completed)
    }
}