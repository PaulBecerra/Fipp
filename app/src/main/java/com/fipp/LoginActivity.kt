package com.fipp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvCreateAccount: TextView = findViewById(R.id.textViewCreateAccount)

        tvCreateAccount.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }

        val btnLogin: Button = findViewById(R.id.button)

        // Redirect to IncomeStatisticsActivity
        btnLogin.setOnClickListener {
            val intent = Intent(this, IncomeStatisticsActivity::class.java)
            startActivity(intent)
        }
        // Redirect to Chart
//        btnLogin.setOnClickListener {
//            val intent = Intent(this, ChartActivity::class.java)
//            startActivity(intent)
//        }
        // Redirect to main activity
//        btnLogin.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
    }
}