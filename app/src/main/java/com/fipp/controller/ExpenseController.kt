package com.fipp.controller

import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.fipp.model.Expense
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.concurrent.TimeUnit

class ExpenseController(private var activity: Activity) {
    // key for login with google
    private var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private val collection = "expenses"
    private lateinit var categoryController: CategoryController

    init {
        this.auth = Firebase.auth
        this.categoryController = CategoryController(activity)
    }



    fun createExpense(expense: Expense) {
        val user = auth.currentUser
        val userId = user?.uid
        // Create a new document

        db.collection(collection).add(
            hashMapOf("amount" to expense.amount,
                "createdAt" to expense.createdAt,
                "user" to userId,
                "category" to expense.category!!.uid
            )
        )
            .addOnSuccessListener {
                Toast.makeText(activity, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(activity, "Hubo un error registrando el gasto", Toast.LENGTH_SHORT).show()
            }
    }

}


