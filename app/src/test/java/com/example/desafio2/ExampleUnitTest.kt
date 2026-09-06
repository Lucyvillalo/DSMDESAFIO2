package com.example.desafio2

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun everyQuizHasFiveQuestionsForEachDifficulty() {
        val types = listOf(QuizRepository.TIPO_CULTURA, QuizRepository.TIPO_CIENCIA, QuizRepository.TIPO_DEPORTES, QuizRepository.TIPO_HISTORIA)
        val difficulties = listOf(QuizRepository.DIFICULTAD_FACIL, QuizRepository.DIFICULTAD_DIFICIL)
        types.forEach { type -> difficulties.forEach { difficulty -> assertEquals(5, QuizRepository.obtenerPreguntas(type, difficulty).size) } }
    }

    @Test
    fun everyQuestionHasValidOptionsAndAnswer() {
        val types = listOf(QuizRepository.TIPO_CULTURA, QuizRepository.TIPO_CIENCIA, QuizRepository.TIPO_DEPORTES, QuizRepository.TIPO_HISTORIA)
        types.forEach { type -> listOf(QuizRepository.DIFICULTAD_FACIL, QuizRepository.DIFICULTAD_DIFICIL).forEach { difficulty ->
            QuizRepository.obtenerPreguntas(type, difficulty).forEach { question ->
                assertTrue(question.options.size >= 3)
                assertTrue(question.correctIndex in question.options.indices)
            }
        } }
    }
}
