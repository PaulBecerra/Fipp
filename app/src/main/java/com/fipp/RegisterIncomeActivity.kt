package com.fipp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class RegisterIncomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_income)

        val btn_close: ImageButton = findViewById(R.id.btn_new_income_close)
        val btn: Button = findViewById(R.id.btn_new_income)

        btn.setOnClickListener{
            finish()
        }

        btn_close.setOnClickListener{
            finish()
        }
    }
}