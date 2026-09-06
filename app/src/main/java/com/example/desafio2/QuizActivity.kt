package com.example.desafio2

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    private lateinit var tipo: String
    private lateinit var dificultad: String
    private lateinit var preguntas: List<Question>
    private lateinit var contenedorPreguntas: LinearLayout
    private val gruposRespuestas = mutableListOf<RadioGroup>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tipo = intent.getStringExtra(EXTRA_TIPO) ?: QuizRepository.TIPO_CULTURA
        dificultad = intent.getStringExtra(EXTRA_DIFICULTAD) ?: QuizRepository.DIFICULTAD_FACIL
        preguntas = QuizRepository.obtenerPreguntas(tipo, dificultad)

        findViewById<TextView>(R.id.txtTituloQuiz).text = QuizRepository.nombreVisible(tipo)
        findViewById<TextView>(R.id.txtSubtituloQuiz).text =
            "Dificultad: ${QuizRepository.dificultadVisible(dificultad)} - ${preguntas.size} preguntas"

        contenedorPreguntas = findViewById(R.id.contenedorPreguntas)
        mostrarPreguntas()

        findViewById<Button>(R.id.btnEnviarQuiz).setOnClickListener {
            enviarQuiz()
        }

        findViewById<Button>(R.id.btnReiniciarQuiz).setOnClickListener {
            reiniciarQuiz()
        }
    }

    private fun mostrarPreguntas() {
        contenedorPreguntas.removeAllViews()
        gruposRespuestas.clear()

        preguntas.forEachIndexed { indicePregunta, pregunta ->
            val txtPregunta = TextView(this).apply {
                text = "${indicePregunta + 1}. ${pregunta.text}"
                textSize = 18f
                setTextColor(getColor(R.color.black))
                setPadding(0, 24, 0, 8)
            }
            contenedorPreguntas.addView(txtPregunta)

            val grupo = RadioGroup(this).apply {
                orientation = RadioGroup.VERTICAL
            }

            pregunta.options.forEachIndexed { indiceOpcion, opcion ->
                val radio = RadioButton(this).apply {
                    id = View.generateViewId()
                    text = opcion
                    textSize = 16f
                    tag = indiceOpcion
                    setTextColor(getColor(R.color.black))
                }
                grupo.addView(radio)
            }

            contenedorPreguntas.addView(grupo)
            gruposRespuestas.add(grupo)
        }
    }

    private fun enviarQuiz() {
        val faltantes = mutableListOf<Int>()
        val respuestasSeleccionadas = mutableListOf<Int>()

        gruposRespuestas.forEachIndexed { indice, grupo ->
            val idSeleccionado = grupo.checkedRadioButtonId
            if (idSeleccionado == -1) {
                faltantes.add(indice + 1)
                respuestasSeleccionadas.add(-1)
            } else {
                val radioSeleccionado = findViewById<RadioButton>(idSeleccionado)
                respuestasSeleccionadas.add(radioSeleccionado.tag as Int)
            }
        }

        if (faltantes.isNotEmpty()) {
            Toast.makeText(
                this,
                "Faltan por responder las preguntas: ${faltantes.joinToString(", ")}",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val puntaje = respuestasSeleccionadas.countIndexed { indice, respuesta ->
            respuesta == preguntas[indice].correctIndex
        }

        startActivity(ResultActivity.intent(this, tipo, dificultad, respuestasSeleccionadas, puntaje))
    }

    private fun reiniciarQuiz() {
        gruposRespuestas.forEach { grupo ->
            grupo.clearCheck()
        }
        Toast.makeText(this, "Respuestas reiniciadas", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private inline fun <T> Iterable<T>.countIndexed(predicate: (Int, T) -> Boolean): Int {
        var total = 0
        forEachIndexed { index, item ->
            if (predicate(index, item)) total++
        }
        return total
    }

    companion object {
        const val EXTRA_TIPO = "extra_tipo"
        const val EXTRA_DIFICULTAD = "extra_dificultad"

        fun intent(context: android.content.Context, tipo: String, dificultad: String) =
            android.content.Intent(context, QuizActivity::class.java).apply {
                putExtra(EXTRA_TIPO, tipo)
                putExtra(EXTRA_DIFICULTAD, dificultad)
            }
    }
}
