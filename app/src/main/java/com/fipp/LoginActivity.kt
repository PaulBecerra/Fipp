package com.fipp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.fipp.controller.UserController
import com.fipp.databinding.ActivityLoginBinding
import com.fipp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : GoogleActivityResult() {

    private lateinit var userController: UserController
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    // key for login with google
    private val GOOGLE_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth
        val tvCreateAccount = binding.textViewCreateAccount

        tvCreateAccount.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }

        var password: TextView=findViewById(R.id.forgetPassword)

        // cambio a la actividad de olvidaste tu contraseña
        password.setOnClickListener{
            startActivity(Intent(this,Activity_password::class.java))
        }


        val btnLogin: Button = binding.btnEmail
        userController = UserController(this)

        btnLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().replace(" ", "")
            val password = binding.editTextPass.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(baseContext, "email or password is wrong.", Toast.LENGTH_SHORT).show()
            } else {
                userController.login(User(email, password, ""))
            }
        }

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }

        // google authenticator
        val btnGoogle: Button = binding.btnGoogle

        btnGoogle.setOnClickListener{
            userController.signInWithGoogle()
        }
    }

    private fun reload(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}