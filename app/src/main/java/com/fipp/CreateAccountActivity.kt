package com.fipp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.fipp.controller.UserController
import com.fipp.databinding.ActivityCreateAccountBinding
import com.fipp.model.User

class CreateAccountActivity : GoogleActivityResult() {

    private lateinit var userController: UserController
    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvLogin: TextView = binding.textViewLogin

        tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        userController = UserController(this)

        val btnEmail = binding.btnCreateAccountWithEmail
        btnEmail.setOnClickListener{
            val email = binding.editTextEmail.text.toString().replace(" ", "")
            val pass = binding.editTextPassword.text.toString()
            val passConfirm = binding.editTextConfirmPassword.text.toString()
            val name = binding.editTextName.text.toString()

            val user = User(email, pass, name)

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(baseContext, "Email is not valid", Toast.LENGTH_SHORT).show()
            } else if (pass.isEmpty() || user.isValidPassword()){
                Toast.makeText(baseContext, "Password is not valid", Toast.LENGTH_SHORT).show()
            } else if (pass != passConfirm){
                Toast.makeText(baseContext, "Passwords are not equals",  Toast.LENGTH_SHORT).show()
            } else {
                userController.createAccount(user)
            }
        }



        // google authenticator
        val btnGoogle: Button = binding.btnCreateAccountWithGoogle

        btnGoogle.setOnClickListener{
            userController.signInWithGoogle()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}