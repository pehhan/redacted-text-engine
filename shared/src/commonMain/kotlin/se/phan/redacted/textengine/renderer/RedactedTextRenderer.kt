package se.phan.redacted.textengine.renderer

import se.phan.redacted.textengine.RedactedText

interface RedactedTextRenderer {
    fun render(text: RedactedText): String
}