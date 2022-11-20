package se.phan.redacted.textengine.difficulty

import se.phan.redacted.textengine.difficulty.hard.HardDifficultyUnredactedWordsProvider
import se.phan.redacted.textengine.difficulty.normal.NormalDifficultyUnredactedWordsProvider

enum class Difficulty(val unredactedWordsProvider: UnredactedWordsProvider) {
    Normal(NormalDifficultyUnredactedWordsProvider()),
    Hard(HardDifficultyUnredactedWordsProvider())
}