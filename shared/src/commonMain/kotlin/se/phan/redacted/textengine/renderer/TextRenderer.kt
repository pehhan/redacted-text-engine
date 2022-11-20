package se.phan.redacted.textengine.renderer

import se.phan.redacted.textengine.text.Text

interface TextRenderer {
    fun render(text: Text): String
}