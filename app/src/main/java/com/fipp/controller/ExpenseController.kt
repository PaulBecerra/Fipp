package com.fipp.controller

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.YearMonth

class ExpenseController {
    // key for login with google
    private val GOOGLE_SIGN_IN = 100
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private var activity: Activity

    constructor(activity: Activity){
        this.activity = activity
        this.auth = Firebase.auth
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthExpenses(month: Int, year: Int){
        val user = auth.currentUser
        val userId = user?.uid
        // Create instnace of localDateTime with the month and year
        val yearMonthObject = YearMonth.of(year, month)
        val daysInMonth = yearMonthObject.lengthOfMonth(); 
        
        
        val startDate = LocalDateTime.of(year, month, 1, 0, 0)
        val endDate = LocalDateTime.of(year, month, daysInMonth, 0, 0)
        db.collection("expenses").whereEqualTo("user", userId)
            .whereGreaterThanOrEqualTo("createdAt", startDate)
            .whereLessThanOrEqualTo("createAr", endDate)
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    println(document.id + " => " + document.data)
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: " + exception)
            }
    }

}
