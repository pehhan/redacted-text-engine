package se.phan.redacted.textengine.text

import se.phan.redacted.textengine.Guess

data class Word(val value: String, val redacted: Boolean) : TextPart() {

    constructor(value: String) : this(value, redacted = true)

    fun matches(guess: Guess): Boolean {
        return value.uppercase() == guess.value.uppercase()
    }
}