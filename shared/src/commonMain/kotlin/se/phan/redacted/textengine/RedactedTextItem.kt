package se.phan.redacted.textengine

sealed class RedactedTextItem

data class Word(val str: String) : RedactedTextItem()
data class Punctuation(val sign: Char) : RedactedTextItem()
object Space : RedactedTextItem()
object Newline : RedactedTextItem()
