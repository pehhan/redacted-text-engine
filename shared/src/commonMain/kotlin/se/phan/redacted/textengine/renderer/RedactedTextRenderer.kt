package se.phan.redacted.textengine.renderer

import se.phan.redacted.textengine.Text

interface RedactedTextRenderer {
    fun render(text: Text): String
}