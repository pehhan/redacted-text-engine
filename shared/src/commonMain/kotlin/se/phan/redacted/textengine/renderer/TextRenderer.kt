package se.phan.redacted.textengine.renderer

import se.phan.redacted.textengine.Text

interface TextRenderer {
    fun render(text: Text): String
}