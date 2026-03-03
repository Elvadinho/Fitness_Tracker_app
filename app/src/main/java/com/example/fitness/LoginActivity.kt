package com.example.fitness

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private var isLoginMode = true
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("FitnessUserPrefs", Context.MODE_PRIVATE)

        // If user is already logged in, skip to main
        if (sharedPreferences.getBoolean("is_logged_in", false)) {
            navigateToMain()
            return
        }

        val tabLogin = findViewById<TextView>(R.id.tabLogin)
        val tabSignUp = findViewById<TextView>(R.id.tabSignUp)
        val tilName = findViewById<TextInputLayout>(R.id.tilName)
        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)
        val tilConfirmPassword = findViewById<TextInputLayout>(R.id.tilConfirmPassword)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)
        val btnAction = findViewById<MaterialButton>(R.id.btnAction)
        val tvToggleHint = findViewById<TextView>(R.id.tvToggleHint)
        val tvToggleAction = findViewById<TextView>(R.id.tvToggleAction)
        val tvSubtitle = findViewById<TextView>(R.id.tvSubtitle)

        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<TextInputEditText>(R.id.etConfirmPassword)

        // Tab switching
        fun clearErrors() {
            tilName.error = null
            tilEmail.error = null
            tilPassword.error = null
            tilConfirmPassword.error = null
        }

        fun getInactiveColor(): Int {
            val typedValue = android.util.TypedValue()
            theme.resolveAttribute(com.google.android.material.R.attr.colorOnSurfaceVariant, typedValue, true)
            return typedValue.data
        }

        fun switchToLogin() {
            isLoginMode = true
            clearErrors()
            tabLogin.setBackgroundResource(R.drawable.bg_button_gradient)
            tabLogin.setTextColor(getColor(R.color.white))
            tabSignUp.setBackgroundColor(getColor(R.color.transparent))
            tabSignUp.setTextColor(getInactiveColor())

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
            clearErrors()
            tabSignUp.setBackgroundResource(R.drawable.bg_button_gradient)
            tabSignUp.setTextColor(getColor(R.color.white))
            tabLogin.setBackgroundColor(getColor(R.color.transparent))
            tabLogin.setTextColor(getInactiveColor())

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

        // Initialize state based on whether an account exists
        val hasAccount = sharedPreferences.getString("user_email", null) != null
        if (hasAccount) {
            switchToLogin()
        } else {
            switchToSignUp()
        }

        // Login/Register action
        btnAction.setOnClickListener {
            clearErrors()

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (isLoginMode) {
                // --- LOGIN ---
                if (email.isEmpty()) {
                    tilEmail.error = "Email is required"
                    return@setOnClickListener
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    tilEmail.error = "Enter a valid email"
                    return@setOnClickListener
                }
                if (password.isEmpty()) {
                    tilPassword.error = "Password is required"
                    return@setOnClickListener
                }

                // Check if user exists in SharedPreferences
                val savedEmail = sharedPreferences.getString("user_email", null)
                val savedPassword = sharedPreferences.getString("user_password", null)

                if (savedEmail == null || savedPassword == null) {
                    Toast.makeText(
                        this,
                        "No account found. Please sign up first!",
                        Toast.LENGTH_LONG
                    ).show()
                    switchToSignUp()
                    return@setOnClickListener
                }

                if (email != savedEmail) {
                    tilEmail.error = "Email not found"
                    return@setOnClickListener
                }

                if (password != savedPassword) {
                    tilPassword.error = "Incorrect password"
                    return@setOnClickListener
                }

                // Login successful
                sharedPreferences.edit().putBoolean("is_logged_in", true).apply()
                Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show()
                navigateToMain()

            } else {
                // --- SIGN UP ---
                val name = etName.text.toString().trim()
                val confirmPassword = etConfirmPassword.text.toString().trim()

                if (name.isEmpty()) {
                    tilName.error = "Name is required"
                    return@setOnClickListener
                }
                if (name.length < 2) {
                    tilName.error = "Name must be at least 2 characters"
                    return@setOnClickListener
                }
                if (email.isEmpty()) {
                    tilEmail.error = "Email is required"
                    return@setOnClickListener
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    tilEmail.error = "Enter a valid email"
                    return@setOnClickListener
                }
                if (password.isEmpty()) {
                    tilPassword.error = "Password is required"
                    return@setOnClickListener
                }
                if (password.length < 6) {
                    tilPassword.error = "Password must be at least 6 characters"
                    return@setOnClickListener
                }
                if (confirmPassword.isEmpty()) {
                    tilConfirmPassword.error = "Please confirm your password"
                    return@setOnClickListener
                }
                if (password != confirmPassword) {
                    tilConfirmPassword.error = "Passwords do not match"
                    return@setOnClickListener
                }

                // Check if account already exists
                val existingEmail = sharedPreferences.getString("user_email", null)
                if (existingEmail != null && existingEmail == email) {
                    tilEmail.error = "An account with this email already exists"
                    return@setOnClickListener
                }

                // Save user data to SharedPreferences
                sharedPreferences.edit().apply {
                    putString("user_name", name)
                    putString("user_email", email)
                    putString("user_password", password)
                    putBoolean("is_logged_in", true)
                    apply()
                }

                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                navigateToMain()
            }
        }

        // Forgot password
        tvForgotPassword.setOnClickListener {
            val savedEmail = sharedPreferences.getString("user_email", null)
            if (savedEmail != null) {
                Toast.makeText(
                    this,
                    "Password reset hint: Your registered email is $savedEmail",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "No account found. Please sign up first!",
                    Toast.LENGTH_LONG
                ).show()
            }
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

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
