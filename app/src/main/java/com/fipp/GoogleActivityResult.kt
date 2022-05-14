package com.fipp

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

public open class GoogleActivityResult: AppCompatActivity() {
    // key for login with google
    private val GOOGLE_SIGN_IN = 100
    var authGoogle: FirebaseAuth = Firebase.auth
    private val db = FirebaseFirestore.getInstance()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try{
                val account = task.getResult(ApiException::class.java)

                if (account != null){
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                    authGoogle.signInWithCredential(credential).addOnCompleteListener{ it ->
                        if (it.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success")
                            val user = authGoogle.currentUser
                            if (user != null) {
                                db.collection("users").document(user?.uid.toString()).set(
                                    hashMapOf("email" to user?.email,
                                        "name" to user?.displayName)
                                )
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", it.exception)
                            Toast.makeText(baseContext, it.exception?.message,
                                Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }catch (e: ApiException){
                Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }

        }
    }
}