package se.phan.redacted.textengine

import ch.tutteli.atrium.api.fluent.en_GB.toEqual
import ch.tutteli.atrium.api.verbs.expect
import se.phan.redacted.textengine.parser.TextParser
import kotlin.test.Test

class GameTest {

    @Test
    fun `when creating a game the number of guesses should be zero and game in progress`() {
        val text = TextParser.parse("Paul Atreides")
        val game = Game(text, text)

        expect(game.numberOfGuesses).toEqual(0)
        expect(game.text).toEqual(text)
        expect(game.title).toEqual(text)
        expect(game.state).toEqual(GameState.InProgress)
    }

    @Test
    fun `when making a correct guess the word should be unredacted`() {
        val text = TextParser.parse("Paul Atreides")
        val originalGame = Game(text, text)

        val gameWithGuess = originalGame.makeGuess(Guess("Paul"))

        val expectedText = Text(listOf(
            Word("Paul", redacted = false),
            Space,
            Word("Atreides", redacted = true)
        ))

        expect(gameWithGuess.numberOfGuesses).toEqual(1)
        expect(gameWithGuess.text).toEqual(expectedText)
        expect(gameWithGuess.title).toEqual(expectedText)
        expect(gameWithGuess.state).toEqual(GameState.InProgress)
    }

    @Test
    fun `when making an incorrect guess the guess count should be increased`() {
        val text = TextParser.parse("Paul Atreides")
        val originalGame = Game(text, text)

        val gameWithGuess = originalGame.makeGuess(Guess("Dune"))

        val expectedText = Text(listOf(
            Word("Paul", redacted = true),
            Space,
            Word("Atreides", redacted = true)
        ))

        expect(gameWithGuess.numberOfGuesses).toEqual(1)
        expect(gameWithGuess.text).toEqual(expectedText)
        expect(gameWithGuess.title).toEqual(expectedText)
        expect(gameWithGuess.state).toEqual(GameState.InProgress)
    }

    @Test
    fun `when making the same guess twice the guess count should only increase once`() {
        val text = TextParser.parse("Paul Atreides")
        val originalGame = Game(text, text)

        val gameWithGuess = originalGame
            .makeGuess(Guess("Paul"))
            .makeGuess(Guess("Paul"))

        val expectedText = Text(listOf(
            Word("Paul", redacted = false),
            Space,
            Word("Atreides", redacted = true)
        ))

        expect(gameWithGuess.numberOfGuesses).toEqual(1)
        expect(gameWithGuess.text).toEqual(expectedText)
        expect(gameWithGuess.title).toEqual(expectedText)
        expect(gameWithGuess.state).toEqual(GameState.InProgress)
    }

    @Test
    fun `when correctly guessing all words in title the game state should be completed`() {
        val text = TextParser.parse("Paul Atreides")
        val originalGame = Game(text, text)

        val gameWithGuess = originalGame
            .makeGuess(Guess("Paul"))
            .makeGuess(Guess("Atreides"))

        val expectedText = Text(listOf(
            Word("Paul", redacted = false),
            Space,
            Word("Atreides", redacted = false)
        ))

        expect(gameWithGuess.numberOfGuesses).toEqual(2)
        expect(gameWithGuess.text).toEqual(expectedText)
        expect(gameWithGuess.title).toEqual(expectedText)
        expect(gameWithGuess.state).toEqual(GameState.Completed)
    }

    // TODO: Test for when title and text is different (one when the text contains the words in the title, and one when it does not
}