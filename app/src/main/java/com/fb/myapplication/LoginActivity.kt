package com.fb.myapplication

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.fb.myapplication.data.DatabaseHelper

class LoginActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize database helper
        dbHelper = DatabaseHelper(this)

        // Hide the action bar
        supportActionBar?.hide()

        // Start background animation
        val animDrawable = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.rootLayout).background as AnimationDrawable
        animDrawable.setEnterFadeDuration(2000)
        animDrawable.setExitFadeDuration(4000)
        animDrawable.start()

        val usernameLayout = findViewById<TextInputLayout>(R.id.usernameLayout)
        val passwordLayout = findViewById<TextInputLayout>(R.id.passwordLayout)
        val usernameInput = findViewById<TextInputEditText>(R.id.usernameInput)
        val passwordInput = findViewById<TextInputEditText>(R.id.passwordInput)
        val loginButton = findViewById<MaterialButton>(R.id.loginButton)
        val forgotPasswordButton = findViewById<MaterialButton>(R.id.forgotPasswordButton)

        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            // Clear previous errors
            usernameLayout.error = null
            passwordLayout.error = null

            when {
                username.isEmpty() -> {
                    usernameLayout.error = "Username is required"
                }
                password.isEmpty() -> {
                    passwordLayout.error = "Password is required"
                }
                else -> {
                    val userData = dbHelper.validateUser(username, password)
                    if (userData != null) {
                        // Show welcome toast with user's name and rank
                        Toast.makeText(
                            this,
                            "Welcome, ${userData.rank} ${userData.fullName}",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Start MainActivity
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        forgotPasswordButton.setOnClickListener {
            showForgotPasswordDialog()
        }
    }

    private fun showForgotPasswordDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Forgot Password")
            .setMessage("""
                Available test accounts:
                
                1. Admin:
                   Username: admin
                   Password: admin123
                
                2. Captain:
                   Username: pcpt_john
                   Password: pass123
                
                3. Major:
                   Username: pmaj_sarah
                   Password: pass456
                
                4. Lieutenant Colonel:
                   Username: pltcol_mike
                   Password: pass789
            """.trimIndent())
            .setPositiveButton("OK", null)
            .show()
    }
} 