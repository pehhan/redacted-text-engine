package se.phan.redacted.textengine.difficulty.normal

import se.phan.redacted.textengine.Word
import se.phan.redacted.textengine.difficulty.UnredactedWordsProvider

class NormalDifficultyUnredactedWordsProvider : UnredactedWordsProvider {

    override fun getWords(): List<Word> {
        return listOf(
            "a",
            "an",
            "and",
            "as",
            "at",
            "but",
            "by",
            "for",
            "from",
            "if",
            "in",
            "is",
            "it",
            "of",
            "on",
            "or",
            "out",
            "the",
            "to",
            "with",
            "up"
        ).map { Word(it) }
    }
}