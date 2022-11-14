package se.phan.redacted.textengine

data class Word(val value: String, val redacted: Boolean) : RedactedTextItem() {

    companion object {

        fun from(value: String): Word {
            return Word(value, redacted = true)
        }
    }
}