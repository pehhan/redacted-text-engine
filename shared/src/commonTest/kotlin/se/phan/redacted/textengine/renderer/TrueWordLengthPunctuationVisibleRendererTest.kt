package se.phan.redacted.textengine.renderer

import ch.tutteli.atrium.api.fluent.en_GB.toEqual
import ch.tutteli.atrium.api.verbs.expect
import se.phan.redacted.textengine.Guess
import se.phan.redacted.textengine.parser.TextParser
import kotlin.test.Test

class TrueWordLengthPunctuationVisibleRendererTest {

    @Test
    fun `renderer renders short redacted text`() {
        val str = "Paul Atreides and Leto Atreides!"

        val expectedTextBeforeGuess = "████ ████████ ███ ████ ████████!"
        val expectedTextAfterGuess = "████ Atreides ███ ████ Atreides!"

        val text = TextParser.parse(str)

        val renderer = TrueWordLengthPunctuationVisibleRenderer()
        val renderedTextBefore = renderer.render(text)

        expect(renderedTextBefore).toEqual(expectedTextBeforeGuess)

        val result = text.makeGuess(Guess("Atreides"))
        val renderedTextAfter = renderer.render(result.text)

        expect(renderedTextAfter).toEqual(expectedTextAfterGuess)
    }

    @Test
    fun `renderer renders long redacted text`() {
        val str = """
            Dune is a 1965 epic science fiction novel by American author Frank Herbert, originally published as two separate serials in Analog magazine. It tied with Roger Zelazny's This Immortal for the Hugo Award in 1966 and it won the inaugural Nebula Award for Best Novel. It is the first installment of the Dune saga. In 2003, it was described as the world's best-selling science fiction novel.

            Dune is set in the distant future amidst a feudal interstellar society in which various noble houses control planetary fiefs. It tells the story of young Paul Atreides, whose family accepts the stewardship of the planet Arrakis. While the planet is an inhospitable and sparsely populated desert wasteland, it is the only source of melange, or "spice", a drug that extends life and enhances mental abilities. Melange is also necessary for space navigation, which requires a kind of multidimensional awareness and foresight that only the drug provides. As melange can only be produced on Arrakis, control of the planet is a coveted and dangerous undertaking. The story explores the multilayered interactions of politics, religion, ecology, technology, and human emotion, as the factions of the empire confront each other in a struggle for the control of Arrakis and its spice.

            Herbert wrote five sequels: Dune Messiah, Children of Dune, God Emperor of Dune, Heretics of Dune, and Chapterhouse: Dune. Following Herbert's death in 1986, his son Brian Herbert and author Kevin J. Anderson continued the series in over a dozen additional novels since 1999.

            Adaptations of the novel to cinema have been notoriously difficult and complicated. In the 1970s, cult filmmaker Alejandro Jodorowsky attempted to make a film based on the novel. After three years of development, the project was canceled due to a constantly growing budget. In 1984, a film adaptation directed by David Lynch was released to a mostly negative response from critics and failed at the box office. The book was also adapted into the 2000 Sci-Fi Channel miniseries Frank Herbert's Dune and its 2003 sequel Frank Herbert's Children of Dune (which combines the events of Dune Messiah and Children of Dune). A second film adaptation directed by Denis Villeneuve was released on October 21, 2021, to generally positive reviews from critics and grossed ${'$'}401 million worldwide. It also went on to win six Academy Awards. Villeneuve's film was essentially the first half of the original novel, and a sequel, which will cover the remaining story, has begun production and is set for release in 2023. The series has been used as the basis for several board, role-playing, and video games.

            Since 2009, the names of planets from the Dune novels have been adopted for the real-life nomenclature of plains and other features on Saturn's moon Titan.
        """.trimIndent()

        val expectedTextBeforeGuess = """
            ████ ██ █ ████ ████ ███████ ███████ █████ ██ ████████ ██████ █████ ███████, ██████████ █████████ ██ ███ ████████ ███████ ██ ██████ ████████. ██ ████ ████ █████ ███████'█ ████ ████████ ███ ███ ████ █████ ██ ████ ███ ██ ███ ███ █████████ ██████ █████ ███ ████ █████. ██ ██ ███ █████ ███████████ ██ ███ ████ ████. ██ ████, ██ ███ █████████ ██ ███ █████'█ ████-███████ ███████ ███████ █████.

            ████ ██ ███ ██ ███ ███████ ██████ ██████ █ ██████ ████████████ ███████ ██ █████ ███████ █████ ██████ ███████ █████████ █████. ██ █████ ███ █████ ██ █████ ████ ████████, █████ ██████ ███████ ███ ███████████ ██ ███ ██████ ███████. █████ ███ ██████ ██ ██ ████████████ ███ ████████ █████████ ██████ █████████, ██ ██ ███ ████ ██████ ██ ███████, ██ "█████", █ ████ ████ ███████ ████ ███ ████████ ██████ █████████. ███████ ██ ████ █████████ ███ █████ ██████████, █████ ████████ █ ████ ██ ████████████████ █████████ ███ █████████ ████ ████ ███ ████ ████████. ██ ███████ ███ ████ ██ ████████ ██ ███████, ███████ ██ ███ ██████ ██ █ ███████ ███ █████████ ███████████. ███ █████ ████████ ███ ████████████ ████████████ ██ ████████, ████████, ███████, ██████████, ███ █████ ███████, ██ ███ ████████ ██ ███ ██████ ████████ ████ █████ ██ █ ████████ ███ ███ ███████ ██ ███████ ███ ███ █████.

            ███████ █████ ████ ███████: ████ ███████, ████████ ██ ████, ███ ███████ ██ ████, ████████ ██ ████, ███ ████████████: ████. █████████ ███████'█ █████ ██ ████, ███ ███ █████ ███████ ███ ██████ █████ █. ████████ █████████ ███ ██████ ██ ████ █ █████ ██████████ ██████ █████ ████.

            ███████████ ██ ███ █████ ██ ██████ ████ ████ ███████████ █████████ ███ ███████████. ██ ███ █████, ████ █████████ █████████ ██████████ █████████ ██ ████ █ ████ █████ ██ ███ █████. █████ █████ █████ ██ ███████████, ███ ███████ ███ ████████ ███ ██ █ ██████████ ███████ ██████. ██ ████, █ ████ ██████████ ████████ ██ █████ █████ ███ ████████ ██ █ ██████ ████████ ████████ ████ ███████ ███ ██████ ██ ███ ███ ██████. ███ ████ ███ ████ ███████ ████ ███ ████ ███-██ ███████ ██████████ █████ ███████'█ ████ ███ ███ ████ ██████ █████ ███████'█ ████████ ██ ████ (█████ ████████ ███ ██████ ██ ████ ███████ ███ ████████ ██ ████). █ ██████ ████ ██████████ ████████ ██ █████ ██████████ ███ ████████ ██ ███████ ██, ████, ██ █████████ ████████ ███████ ████ ███████ ███ ███████ ${'$'}███ ███████ █████████. ██ ████ ████ ██ ██ ███ ███ ███████ ██████. ██████████'█ ████ ███ ███████████ ███ █████ ████ ██ ███ ████████ █████, ███ █ ██████, █████ ████ █████ ███ █████████ █████, ███ █████ ██████████ ███ ██ ███ ███ ███████ ██ ████. ███ ██████ ███ ████ ████ ██ ███ █████ ███ ███████ █████, ████-███████, ███ █████ █████.

            █████ ████, ███ █████ ██ ███████ ████ ███ ████ ██████ ████ ████ ███████ ███ ███ ████-████ ████████████ ██ ██████ ███ █████ ████████ ██ ██████'█ ████ █████.
        """.trimIndent()

        val expectedTextAfterGuess = """
            Dune ██ █ ████ ████ ███████ ███████ █████ ██ ████████ ██████ █████ ███████, ██████████ █████████ ██ ███ ████████ ███████ ██ ██████ ████████. ██ ████ ████ █████ ███████'█ ████ ████████ ███ ███ ████ █████ ██ ████ ███ ██ ███ ███ █████████ ██████ █████ ███ ████ █████. ██ ██ ███ █████ ███████████ ██ ███ Dune ████. ██ ████, ██ ███ █████████ ██ ███ █████'█ ████-███████ ███████ ███████ █████.

            Dune ██ ███ ██ ███ ███████ ██████ ██████ █ ██████ ████████████ ███████ ██ █████ ███████ █████ ██████ ███████ █████████ █████. ██ █████ ███ █████ ██ █████ ████ ████████, █████ ██████ ███████ ███ ███████████ ██ ███ ██████ ███████. █████ ███ ██████ ██ ██ ████████████ ███ ████████ █████████ ██████ █████████, ██ ██ ███ ████ ██████ ██ ███████, ██ "█████", █ ████ ████ ███████ ████ ███ ████████ ██████ █████████. ███████ ██ ████ █████████ ███ █████ ██████████, █████ ████████ █ ████ ██ ████████████████ █████████ ███ █████████ ████ ████ ███ ████ ████████. ██ ███████ ███ ████ ██ ████████ ██ ███████, ███████ ██ ███ ██████ ██ █ ███████ ███ █████████ ███████████. ███ █████ ████████ ███ ████████████ ████████████ ██ ████████, ████████, ███████, ██████████, ███ █████ ███████, ██ ███ ████████ ██ ███ ██████ ████████ ████ █████ ██ █ ████████ ███ ███ ███████ ██ ███████ ███ ███ █████.

            ███████ █████ ████ ███████: Dune ███████, ████████ ██ Dune, ███ ███████ ██ Dune, ████████ ██ Dune, ███ ████████████: Dune. █████████ ███████'█ █████ ██ ████, ███ ███ █████ ███████ ███ ██████ █████ █. ████████ █████████ ███ ██████ ██ ████ █ █████ ██████████ ██████ █████ ████.

            ███████████ ██ ███ █████ ██ ██████ ████ ████ ███████████ █████████ ███ ███████████. ██ ███ █████, ████ █████████ █████████ ██████████ █████████ ██ ████ █ ████ █████ ██ ███ █████. █████ █████ █████ ██ ███████████, ███ ███████ ███ ████████ ███ ██ █ ██████████ ███████ ██████. ██ ████, █ ████ ██████████ ████████ ██ █████ █████ ███ ████████ ██ █ ██████ ████████ ████████ ████ ███████ ███ ██████ ██ ███ ███ ██████. ███ ████ ███ ████ ███████ ████ ███ ████ ███-██ ███████ ██████████ █████ ███████'█ Dune ███ ███ ████ ██████ █████ ███████'█ ████████ ██ Dune (█████ ████████ ███ ██████ ██ Dune ███████ ███ ████████ ██ Dune). █ ██████ ████ ██████████ ████████ ██ █████ ██████████ ███ ████████ ██ ███████ ██, ████, ██ █████████ ████████ ███████ ████ ███████ ███ ███████ ${'$'}███ ███████ █████████. ██ ████ ████ ██ ██ ███ ███ ███████ ██████. ██████████'█ ████ ███ ███████████ ███ █████ ████ ██ ███ ████████ █████, ███ █ ██████, █████ ████ █████ ███ █████████ █████, ███ █████ ██████████ ███ ██ ███ ███ ███████ ██ ████. ███ ██████ ███ ████ ████ ██ ███ █████ ███ ███████ █████, ████-███████, ███ █████ █████.

            █████ ████, ███ █████ ██ ███████ ████ ███ Dune ██████ ████ ████ ███████ ███ ███ ████-████ ████████████ ██ ██████ ███ █████ ████████ ██ ██████'█ ████ █████.
        """.trimIndent()

        val text = TextParser.parse(str)

        val renderer = TrueWordLengthPunctuationVisibleRenderer()
        val renderedTextBefore = renderer.render(text)

        expect(renderedTextBefore).toEqual(expectedTextBeforeGuess)

        val result = text.makeGuess(Guess("Dune"))
        val renderedTextAfter = renderer.render(result.text)

        expect(renderedTextAfter).toEqual(expectedTextAfterGuess)
    }
}