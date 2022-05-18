package com.fipp.controller

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.fipp.MainActivity
import com.fipp.R
import com.fipp.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class UserController {
    // key for login with google
    private val GOOGLE_SIGN_IN = 100
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private var activity: Activity

    constructor(activity: Activity){
        this.activity = activity
        this.auth = Firebase.auth
    }

    fun login(user: User){
        auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithEmail:success")
                    val user = auth.currentUser
                    if (user != null) reload(user)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(activity, task.exception?.message,
                        Toast.LENGTH_SHORT).show()

                }
            }
    }

    fun createAccount(user: User){
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val currentUser = auth.currentUser
                    db.collection("users").document(currentUser?.uid.toString()).set(
                        hashMapOf("email" to currentUser?.email,
                                    "name" to user.name)
                    )
                    if (currentUser != null) reload(currentUser)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(activity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signInWithGoogle(){
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(activity, googleConf)
        googleClient.signOut()
        activity.startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
    }

    private fun reload(user: FirebaseUser){
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
    }

    fun updateUser(name: String, email:String) {
        val user1 = auth.currentUser
        val userId = user1?.uid

        db.collection("users").document(userId!!).set(
            hashMapOf("name" to name, "email" to email)
        )
            .addOnSuccessListener {
                Toast.makeText(activity, "¡Edición exitosa!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(activity, "Hubo un error editando el nombre de usuario", Toast.LENGTH_SHORT).show()
            }
    }


}