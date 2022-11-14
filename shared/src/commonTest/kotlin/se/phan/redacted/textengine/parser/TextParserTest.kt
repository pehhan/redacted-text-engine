package se.phan.redacted.textengine.parser

import ch.tutteli.atrium.api.fluent.en_GB.toBeEmpty
import ch.tutteli.atrium.api.fluent.en_GB.toContainExactly
import ch.tutteli.atrium.api.verbs.expect
import se.phan.redacted.textengine.Newline
import se.phan.redacted.textengine.Punctuation
import se.phan.redacted.textengine.Space
import se.phan.redacted.textengine.Word
import kotlin.test.Test

class TextParserTest {

    @Test
    fun `parse empty string`() {
        val text = TextParser.parse("")

        expect(text.items).toBeEmpty()
    }

    @Test
    fun `parse one word`() {
        val str = "Dune"
        val text = TextParser.parse(str)

        expect(text.items).toContainExactly(Word.from(str))
    }

    @Test
    fun `parse two words`() {
        val str = "Paul Atreides"
        val text = TextParser.parse(str)

        expect(text.items).toContainExactly(
            Word.from("Paul"),
            Space,
            Word.from("Atreides")
        )
    }

    @Test
    fun `parse punctuation`() {
        for (punctuation in SpecialCharacters.PUNCTUATION) {
            testPunctuationMark(punctuation)
        }
    }

    @Test
    fun `parse space after punctuation`() {
        val str = "Paul, Atreides"
        val text = TextParser.parse(str)

        expect(text.items).toContainExactly(
            Word.from("Paul"),
            Punctuation(','),
            Space,
            Word.from("Atreides")
        )
    }

    @Test
    fun `parse newline`() {
        val str = """
            Paul
            
            Atreides
        """.trimIndent()

        val text = TextParser.parse(str)

        expect(text.items).toContainExactly(
            Word.from("Paul"),
            Newline,
            Newline,
            Word.from("Atreides"),
        )
    }

    private fun testPunctuationMark(punctuationMark: Char) {
        val str = "Paul Atreides$punctuationMark"
        val text = TextParser.parse(str)

        expect(text.items).toContainExactly(
            Word.from("Paul"),
            Space,
            Word.from("Atreides"),
            Punctuation(punctuationMark),
        )
    }
}