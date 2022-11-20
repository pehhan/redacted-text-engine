package se.phan.redacted.textengine.difficulty.hard

import se.phan.redacted.textengine.text.Word
import se.phan.redacted.textengine.difficulty.UnredactedWordsProvider

class HardDifficultyUnredactedWordsProvider : UnredactedWordsProvider {

    override fun getWords(): List<Word> {
        return emptyList()
    }
}
