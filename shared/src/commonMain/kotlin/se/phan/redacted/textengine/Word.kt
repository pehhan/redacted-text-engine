package se.phan.redacted.textengine

data class Word(val value: String, val redacted: Boolean) : RedactedTextItem() {

    constructor(value: String) : this(value, redacted = true)
}