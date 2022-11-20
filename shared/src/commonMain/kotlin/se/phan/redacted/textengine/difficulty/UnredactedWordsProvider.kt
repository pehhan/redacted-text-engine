package se.phan.redacted.textengine.difficulty

import se.phan.redacted.textengine.Word

interface UnredactedWordsProvider {
    fun getWords(): List<Word>
}