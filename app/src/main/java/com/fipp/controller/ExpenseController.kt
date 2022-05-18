package com.fipp.controller

import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.fipp.model.Expense
import com.fipp.model.Income
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.YearMonth

class ExpenseController(private var activity: Activity) {
    // key for login with google
    private var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private val collection = "expenses"

    init {
        this.auth = Firebase.auth
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthExpenses(month: Int, year: Int): ArrayList<Expense> {
        val expenses = ArrayList<Expense>()
        val user = auth.currentUser
        val userId = user?.uid
        // Create instnace of localDateTime with the month and year
        val yearMonthObject = YearMonth.of(year, month)
        val daysInMonth = yearMonthObject.lengthOfMonth(); 
        
        
        val startDate = LocalDateTime.of(year, month, 1, 0, 0)
        val endDate = LocalDateTime.of(year, month, daysInMonth, 0, 0)
        db.collection("expenses").whereEqualTo("user", userId)
            .whereGreaterThanOrEqualTo("createdAt", startDate)
            .whereLessThanOrEqualTo("createdAt", endDate)
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    val expense = document.toObject(Expense::class.java)
                    expenses.add(expense)
                    println(document.id + " => " + document.data)
                }
            }
            .addOnFailureListener { exception ->
                // Create a Toast with a message
                Toast.makeText(activity, "Hubo un error obteniendo los gastos ", Toast.LENGTH_LONG).show()
                println("Error getting documents: " + exception)
            }
        return expenses
    }

    fun createExpense(expense: Expense) {
        val user = auth.currentUser
        val userId = user?.uid
        // Create a new document

        db.collection(collection).add(
            hashMapOf("amount" to expense.amount,
                "createdAt" to expense.createdAt,
                "user" to userId,
                "category" to expense.category.uid
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
