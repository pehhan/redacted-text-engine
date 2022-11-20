package se.phan.redacted.textengine

import ch.tutteli.atrium.api.fluent.en_GB.toEqual
import ch.tutteli.atrium.api.verbs.expect
import se.phan.redacted.textengine.difficulty.Difficulty
import se.phan.redacted.textengine.parser.TextParser
import kotlin.test.Test

class GameCreatorTest {

    @Test
    fun `game is created with text unredacted for normal difficulty`() {
        val title = TextParser.parse("Dune")
        val text = TextParser.parse("Arrakis is another name for Dune.")

        val game = GameCreator.createGame(title, text, Difficulty.Normal)

        val expectedTextAfterGameCreation = Text(
            listOf(
                Word("Arrakis", redacted = true),
                Space,
                Word("is", redacted = false),
                Space,
                Word("another", redacted = true),
                Space,
                Word("name", redacted = true),
                Space,
                Word("for", redacted = false),
                Space,
                Word("Dune", redacted = true),
                Punctuation('.')
            )
        )

        expect(game.text).toEqual(expectedTextAfterGameCreation)
    }

    @Test
    fun `game is created with no text unredacted for hard difficulty`() {
        val title = TextParser.parse("Dune")
        val text = TextParser.parse("Arrakis is another name for Dune.")

        val game = GameCreator.createGame(title, text, Difficulty.Hard)

        val expectedTextAfterGameCreation = Text(
            listOf(
                Word("Arrakis", redacted = true),
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

        expect(game.text).toEqual(expectedTextAfterGameCreation)
    }
}