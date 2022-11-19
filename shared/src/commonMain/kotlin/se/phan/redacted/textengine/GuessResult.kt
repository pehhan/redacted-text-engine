package se.phan.redacted.textengine

sealed class GuessResult

data class WordUnredacted(val text: Text, val matches: Int) : GuessResult()

object WordAlreadyUnredacted: GuessResult()

object WordNotInText : GuessResult()