package com.example.fitness

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private var isLoginMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val tabLogin = findViewById<TextView>(R.id.tabLogin)
        val tabSignUp = findViewById<TextView>(R.id.tabSignUp)
        val tilName = findViewById<TextInputLayout>(R.id.tilName)
        val tilConfirmPassword = findViewById<TextInputLayout>(R.id.tilConfirmPassword)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)
        val btnAction = findViewById<MaterialButton>(R.id.btnAction)
        val tvToggleHint = findViewById<TextView>(R.id.tvToggleHint)
        val tvToggleAction = findViewById<TextView>(R.id.tvToggleAction)
        val tvSubtitle = findViewById<TextView>(R.id.tvSubtitle)
        val btnGoogle = findViewById<MaterialButton>(R.id.btnGoogle)

        // Tab switching
        fun switchToLogin() {
            isLoginMode = true
            tabLogin.setBackgroundResource(R.drawable.bg_button_gradient)
            tabLogin.setTextColor(getColor(R.color.white))
            tabSignUp.setBackgroundColor(getColor(R.color.transparent))
            tabSignUp.setTextColor(getColor(R.color.text_secondary_light))

            tilName.visibility = View.GONE
            tilConfirmPassword.visibility = View.GONE
            tvForgotPassword.visibility = View.VISIBLE
            btnAction.text = getString(R.string.login)
            tvToggleHint.text = getString(R.string.dont_have_account)
            tvToggleAction.text = getString(R.string.sign_up)
            tvSubtitle.text = getString(R.string.login_subtitle)
        }

        fun switchToSignUp() {
            isLoginMode = false
            tabSignUp.setBackgroundResource(R.drawable.bg_button_gradient)
            tabSignUp.setTextColor(getColor(R.color.white))
            tabLogin.setBackgroundColor(getColor(R.color.transparent))
            tabLogin.setTextColor(getColor(R.color.text_secondary_light))

            tilName.visibility = View.VISIBLE
            tilConfirmPassword.visibility = View.VISIBLE
            tvForgotPassword.visibility = View.GONE
            btnAction.text = getString(R.string.sign_up)
            tvToggleHint.text = getString(R.string.already_have_account)
            tvToggleAction.text = getString(R.string.login)
            tvSubtitle.text = getString(R.string.signup_subtitle)
        }

        tabLogin.setOnClickListener { switchToLogin() }
        tabSignUp.setOnClickListener { switchToSignUp() }
        tvToggleAction.setOnClickListener {
            if (isLoginMode) switchToSignUp() else switchToLogin()
        }

        // Login/Register action
        btnAction.setOnClickListener {
            // Navigate to main screen
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // Google sign in
        btnGoogle.setOnClickListener {
            Toast.makeText(this, "Google Sign-In", Toast.LENGTH_SHORT).show()
        }

        // Forgot password
        tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Reset password link sent!", Toast.LENGTH_SHORT).show()
        }

        // Animate login card entrance
        val loginCard = findViewById<View>(R.id.loginCard)
        loginCard.alpha = 0f
        loginCard.translationY = 60f
        loginCard.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(600)
            .setStartDelay(200)
            .start()
    }
}
