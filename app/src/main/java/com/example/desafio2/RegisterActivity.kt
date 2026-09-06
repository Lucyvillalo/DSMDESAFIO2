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

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var txtPasswordConfirm: EditText
    private lateinit var buttonRegister: Button
    private lateinit var textViewLogin: TextView
    private lateinit var progressRegister: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        txtPasswordConfirm = findViewById(R.id.txtPasswordConfirm)
        buttonRegister = findViewById(R.id.btnRegister)
        textViewLogin = findViewById(R.id.textViewLogin)
        progressRegister = findViewById(R.id.progressRegister)

        buttonRegister.setOnClickListener {
            val email = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString()
            val passwordConfirm = txtPasswordConfirm.text.toString()

            if (validar(email, password, passwordConfirm)) {
                register(email, password)
            }
        }

        textViewLogin.setOnClickListener {
            goToLogin()
        }

        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                goToWelcome()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        auth.addAuthStateListener(authStateListener)
    }

    override fun onPause() {
        super.onPause()
        auth.removeAuthStateListener(authStateListener)
    }

    private fun validar(email: String, password: String, passwordConfirm: String): Boolean {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Ingresa un correo electrónico válido", Toast.LENGTH_LONG).show()
            return false
        }
        if (password.length < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_LONG).show()
            return false
        }
        if (password != passwordConfirm) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun register(email: String, password: String) {
        progressRegister.visibility = android.view.View.VISIBLE
        buttonRegister.isEnabled = false

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressRegister.visibility = android.view.View.GONE
                buttonRegister.isEnabled = true

                if (task.isSuccessful) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    goToWelcome()
                }
            }
            .addOnFailureListener { exception ->
                progressRegister.visibility = android.view.View.GONE
                buttonRegister.isEnabled = true
                Toast.makeText(
                    applicationContext,
                    exception.localizedMessage ?: "Ocurrió un error al registrar el usuario",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun goToWelcome() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
