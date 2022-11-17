package se.phan.redacted.textengine

import ch.tutteli.atrium.api.fluent.en_GB.toEqual
import ch.tutteli.atrium.api.verbs.expect
import se.phan.redacted.textengine.parser.TextParser
import kotlin.test.Test

class TextTest {

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

        val expectedText = Text(listOf(
            Word("Paul", redacted = false),
            Space,
            Word("Atreides", redacted = true)
        ))

        expect(result.text).toEqual(expectedText)
        expect(result.matches).toEqual(1)
    }

    @Test
    fun `when guessing a word that is in the text twice - a new text with 2 matches should be returned`() {
        val str = "Paul Atreides and Leto Atreides"
        val text = TextParser.parse(str)

        val result = text.makeGuess(Guess("Atreides"))

        val expectedText = Text(listOf(
            Word("Paul", redacted = true),
            Space,
            Word("Atreides", redacted = false),
            Space,
            Word("and", redacted = true),
            Space,
            Word("Leto", redacted = true),
            Space,
            Word("Atreides", redacted = false)
        ))

        expect(result.text).toEqual(expectedText)
        expect(result.matches).toEqual(2)
    }

    @Test
    fun `when guessing a word in lowercase which is in uppercase in the text it should match`() {
        testDifferentCaps("ATREIDES", "atreides")
    }

    @Test
    fun `when guessing a word in uppercase which is in lowercase in the text it should match`() {
        testDifferentCaps("atreides", "ATREIDES")
    }

    @Test
    fun `when guessing a word in mixed case which is in uppercase in the text it should match`() {
        testDifferentCaps("ATREIDES", "AtreiDes")
    }

    @Test
    fun `when guessing a word in mixed case which is in lowercase in the text it should match`() {
        testDifferentCaps("atreides", "AtreiDes")
    }

    @Test
    fun `when guessing a word in lowercase which is in mixed case in the text it should match`() {
        testDifferentCaps("AtreiDes", "atreides")
    }

    @Test
    fun `when guessing a word in uppercase which is in mixed case in the text it should match`() {
        testDifferentCaps("AtreiDes", "ATREIDES")
    }

    @Test
    fun `when guessing a word in mixed case which is in different mixed case in the text it should match`() {
        testDifferentCaps("ATreidES", "AtreiDes")
    }

    @Test
    fun `when guessing a number it should match`() {
        testDifferentCaps("1965", "1965")
    }

    @Test
    fun `when guessing a mix of characters and numbers it should match`() {
        testDifferentCaps("20th", "20th")
    }

    @Test
    fun `when guessing a mix of characters in lowercase and numbers it should match`() {
        testDifferentCaps("20TH", "20th")
    }

    @Test
    fun `when guessing a mix of characters in uppercase and numbers it should match`() {
        testDifferentCaps("20th", "20TH")
    }

    private fun testDifferentCaps(wordInText: String, guess: String) {
        val str = "Paul $wordInText"
        val text = TextParser.parse(str)

        val result = text.makeGuess(Guess(guess))

        val expectedText = Text(listOf(
            Word("Paul", redacted = true),
            Space,
            Word(wordInText, redacted = false)
        ))

        expect(result.text).toEqual(expectedText)
        expect(result.matches).toEqual(1)
    }
}