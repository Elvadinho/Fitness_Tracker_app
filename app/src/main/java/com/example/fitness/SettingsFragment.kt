package com.example.fitness

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.materialswitch.MaterialSwitch

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchDarkMode = view.findViewById<MaterialSwitch>(R.id.switchDarkMode)
        val switchNotifications = view.findViewById<MaterialSwitch>(R.id.switchNotifications)
        val btnLogout = view.findViewById<MaterialButton>(R.id.btnLogout)

        val prefs = requireContext().getSharedPreferences("FitnessUserPrefs", Context.MODE_PRIVATE)
        val userName = prefs.getString("user_name", "User")
        val userEmail = prefs.getString("user_email", "user@example.com")

        // In fragment_settings.xml, we have TextViews with text set to @string/user_name and @string/user_email. 
        // We will find them by navigating the view hierarchy since they don't have IDs. (I will add IDs in the layout next step)
        val tvUserName = view.findViewById<android.widget.TextView>(R.id.tvSettingsUserName)
        val tvUserEmail = view.findViewById<android.widget.TextView>(R.id.tvSettingsUserEmail)
        
        tvUserName?.text = userName
        tvUserEmail?.text = userEmail

        // Dark mode toggle
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            val mode = if (isChecked) {
                androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
            } else {
                androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
            }
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(mode)
        }

        // Notifications toggle
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "enabled" else "disabled"
            Toast.makeText(requireContext(), "Notifications $status", Toast.LENGTH_SHORT).show()
        }

        val btnDeleteAccount = view.findViewById<MaterialButton>(R.id.btnDeleteAccount)

        // Logout
        btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.logout_confirm))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    // Clear login state from SharedPreferences
                    val prefs = requireContext().getSharedPreferences("FitnessUserPrefs", Context.MODE_PRIVATE)
                    prefs.edit().putBoolean("is_logged_in", false).apply()

                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                .setNegativeButton(getString(R.string.no), null)
                .show()
        }

        // Delete Account
        btnDeleteAccount.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to permanently delete your account and all data? This action cannot be undone.")
                .setPositiveButton("Delete") { _, _ ->
                    // Clear ALL data from SharedPreferences
                    val prefs = requireContext().getSharedPreferences("FitnessUserPrefs", Context.MODE_PRIVATE)
                    prefs.edit().clear().apply()

                    Toast.makeText(requireContext(), "Account deleted successfully", Toast.LENGTH_SHORT).show()
                    
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}
