package com.fipp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Activity_password : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        auth = Firebase.auth

        // elementos del layout
        var email: EditText=findViewById(R.id.editTextEmail)
        var btn_Restablecer: Button= findViewById(R.id.btnRestablecer)

        btn_Restablecer.setOnClickListener{
            var correo: String = email.text.toString().trim()


            //validar campos vacios
            if(correo.isNullOrEmpty()){
                Toast.makeText(this,"por favor de llenar el correo",Toast.LENGTH_SHORT).show()

            }else{
                // restablecer contraseña

                auth.sendPasswordResetEmail(correo)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful){
                            Toast.makeText(this,"Se ha enviado un correo",Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this,"ha ocurrido un error",Toast.LENGTH_SHORT).show()
                        }

                    }

            }
        }

    }
}