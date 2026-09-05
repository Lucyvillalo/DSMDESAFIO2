package com.example.desafio2

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewRegister: TextView
    private lateinit var progressLogin: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        buttonLogin = findViewById(R.id.btnLogin)
        textViewRegister = findViewById(R.id.textViewRegister)
        progressLogin = findViewById(R.id.progressLogin)

        buttonLogin.setOnClickListener {
            val email = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString()

            if (validar(email, password)) {
                login(email, password)
            }
        }

        textViewRegister.setOnClickListener {
            goToRegister()
        }
    }

    private fun validar(email: String, password: String): Boolean {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Ingresa un correo electrónico válido", Toast.LENGTH_LONG).show()
            return false
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Ingresa tu contraseña", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun login(email: String, password: String) {
        progressLogin.visibility = android.view.View.VISIBLE
        buttonLogin.isEnabled = false

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressLogin.visibility = android.view.View.GONE
                buttonLogin.isEnabled = true

                if (task.isSuccessful) {
                    val intent = Intent(this, WelcomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            .addOnFailureListener { exception ->
                progressLogin.visibility = android.view.View.GONE
                buttonLogin.isEnabled = true
                Toast.makeText(
                    applicationContext,
                    exception.localizedMessage ?: "Credenciales incorrectas o fallo en la autenticación",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun goToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }
}