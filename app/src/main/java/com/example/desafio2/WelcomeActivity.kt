package com.example.desafio2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {

    private var tipoSeleccionado: String? = null

    private lateinit var txtSeleccionTipo: TextView
    private lateinit var rgDificultad: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        txtSeleccionTipo = findViewById(R.id.txtSeleccionTipo)
        rgDificultad = findViewById(R.id.rgDificultad)

        val btnCultura: Button = findViewById(R.id.btnCultura)
        val btnCiencia: Button = findViewById(R.id.btnCiencia)
        val btnDeportes: Button = findViewById(R.id.btnDeportes)
        val btnHistoria: Button = findViewById(R.id.btnHistoria)
        val btnComenzar: Button = findViewById(R.id.btnComenzar)

        btnCultura.setOnClickListener { seleccionarTipo(QuizRepository.TIPO_CULTURA) }
        btnCiencia.setOnClickListener { seleccionarTipo(QuizRepository.TIPO_CIENCIA) }
        btnDeportes.setOnClickListener { seleccionarTipo(QuizRepository.TIPO_DEPORTES) }
        btnHistoria.setOnClickListener { seleccionarTipo(QuizRepository.TIPO_HISTORIA) }

        btnComenzar.setOnClickListener {
            val tipo = tipoSeleccionado
            if (tipo == null) {
                Toast.makeText(this, "Selecciona un tipo de quiz para continuar", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val dificultad = if (rgDificultad.checkedRadioButtonId == R.id.rbDificil) {
                QuizRepository.DIFICULTAD_DIFICIL
            } else {
                QuizRepository.DIFICULTAD_FACIL
            }
            irAQuiz(tipo, dificultad)
        }
    }

    private fun seleccionarTipo(tipo: String) {
        tipoSeleccionado = tipo
        txtSeleccionTipo.text = "Tipo seleccionado: ${QuizRepository.nombreVisible(tipo)}"
    }

    private fun irAQuiz(tipo: String, dificultad: String) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra(QuizActivity.EXTRA_TIPO, tipo)
        intent.putExtra(QuizActivity.EXTRA_DIFICULTAD, dificultad)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_sign_out) {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}