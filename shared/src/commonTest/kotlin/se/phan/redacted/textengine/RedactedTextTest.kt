package se.phan.redacted.textengine

import ch.tutteli.atrium.api.fluent.en_GB.toEqual
import ch.tutteli.atrium.api.verbs.expect
import se.phan.redacted.textengine.parser.TextParser
import kotlin.test.Test

class RedactedTextTest {

    @Test
    fun `when guessing a word that is not in text - the same text with 0 matches should be returned`() {
        val str = "Paul Atreides"
        val text = TextParser.parse(str)

        val result = text.makeGuess(Guess("Dune"))

        expect(result.text).toEqual(text)
        expect(result.matches).toEqual(0)
    }

    @Test
    fun `when guessing a word that is in the text once - a new text with 1 matches should be returned`() {
        val str = "Paul Atreides"
        val text = TextParser.parse(str)

        val result = text.makeGuess(Guess("Paul"))

        val expectedText = RedactedText(listOf(
            Word("Paul", redacted = false),
            Space,
            Word("Atreides", redacted = true)
        ))

        expect(result.text).toEqual(expectedText)
        expect(result.matches).toEqual(1)
    }

    @Test
    fun `when guessing a word that is in the text twice - a new text with 2 matches should be returned`() {
        val str = "Paul Atreides and Frank Atreides"
        val text = TextParser.parse(str)

        val result = text.makeGuess(Guess("Atreides"))

        val expectedText = RedactedText(listOf(
            Word("Paul", redacted = true),
            Space,
            Word("Atreides", redacted = false),
            Space,
            Word("and", redacted = true),
            Space,
            Word("Frank", redacted = true),
            Space,
            Word("Atreides", redacted = false)
        ))

        expect(result.text).toEqual(expectedText)
        expect(result.matches).toEqual(2)
    }
}