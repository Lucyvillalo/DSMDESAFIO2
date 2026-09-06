package com.example.desafio2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val tipo = intent.getStringExtra(EXTRA_TIPO) ?: QuizRepository.TIPO_CULTURA
        val dificultad = intent.getStringExtra(EXTRA_DIFICULTAD) ?: QuizRepository.DIFICULTAD_FACIL
        val answers = intent.getIntegerArrayListExtra(EXTRA_ANSWERS)?.toList().orEmpty()
        val questions = QuizRepository.obtenerPreguntas(tipo, dificultad)
        val score = intent.getIntExtra(EXTRA_SCORE, 0)

        findViewById<TextView>(R.id.txtResultTitle).text = QuizRepository.nombreVisible(tipo)
        findViewById<TextView>(R.id.txtScore).text = getString(R.string.score_format, score, questions.size)
        findViewById<TextView>(R.id.txtFeedback).text = feedback(score, dificultad)
        val review = findViewById<LinearLayout>(R.id.reviewContainer)
        questions.forEachIndexed { index, question ->
            val selected = answers.getOrNull(index)?.let { question.options[it] } ?: "Sin respuesta"
            val row = TextView(this).apply {
                text = getString(R.string.review_format, index + 1, question.text, selected, question.options[question.correctIndex])
                setPadding(20, 20, 20, 20)
                setTextColor(getColor(if (answers.getOrNull(index) == question.correctIndex) R.color.correct_green else R.color.incorrect_red))
                textSize = 15f
            }
            review.addView(row)
        }
        findViewById<MaterialButton>(R.id.btnTryAgain).setOnClickListener {
            startActivity(QuizActivity.intent(this, tipo, dificultad))
            finish()
        }
        findViewById<MaterialButton>(R.id.btnChooseOther).setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }
    }

    private fun feedback(score: Int, difficulty: String): String = when {
        score <= 1 -> "Mejor me dedico a otra cosa."
        score <= 3 -> "Más o menos OK."
        score == 4 -> "Me merezco un churro."
        difficulty == QuizRepository.DIFICULTAD_DIFICIL -> "Como pegarle a un bolo."
        else -> "¡Excelente resultado!"
    }

    companion object {
        private const val EXTRA_TIPO = "result_tipo"
        private const val EXTRA_DIFICULTAD = "result_dificultad"
        private const val EXTRA_ANSWERS = "result_answers"
        private const val EXTRA_SCORE = "result_score"
        fun intent(context: Context, tipo: String, dificultad: String, answers: List<Int>, score: Int) = Intent(context, ResultActivity::class.java).apply {
            putExtra(EXTRA_TIPO, tipo)
            putExtra(EXTRA_DIFICULTAD, dificultad)
            putIntegerArrayListExtra(EXTRA_ANSWERS, ArrayList(answers))
            putExtra(EXTRA_SCORE, score)
        }
    }
}
