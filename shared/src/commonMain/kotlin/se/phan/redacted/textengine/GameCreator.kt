package se.phan.redacted.textengine

import se.phan.redacted.textengine.difficulty.Difficulty
import se.phan.redacted.textengine.text.Text

object GameCreator {

    fun createGame(title: Text, text: Text, difficulty: Difficulty): Game {
        val unredactedWords = difficulty.unredactedWordsProvider.getWords()
        return Game(title.unredactWords(unredactedWords), text.unredactWords(unredactedWords))
    }
}