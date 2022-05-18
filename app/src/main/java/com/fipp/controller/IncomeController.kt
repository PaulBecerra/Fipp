package com.fipp.controller

import android.app.Activity
import android.graphics.Typeface
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.fipp.model.Category
import com.fipp.model.Expense
import com.fipp.model.Income
import com.fipp.ui.expenses.MyCallbackIncome
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.YearMonth

class IncomeController(private var activity: Activity) {
    // key for login with google
    private var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private val collection = "income"
    private lateinit var categoryController: CategoryController

    init {
        this.auth = Firebase.auth
        this.categoryController = CategoryController(activity)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthIncome(month: Int, year: Int, myCallback : MyCallbackIncome) {
        val miau: java.util.ArrayList<String> = java.util.ArrayList()
        val user = auth.currentUser
        val userId = user?.uid
        // Create instance of localDateTime with the month and year
        val yearMonthObject = YearMonth.of(year, month)
        val daysInMonth = yearMonthObject.lengthOfMonth();



        val startDate = LocalDateTime.of(year, month, 1, 0, 0)
        val endDate = LocalDateTime.of(year, month, daysInMonth, 0, 0)
        val algo =
            db.collection("income").whereEqualTo("user", userId)
                .whereGreaterThanOrEqualTo("createdAt", startDate)
                .whereLessThanOrEqualTo("createdAt", endDate).get()

        algo.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val simon : java.util.ArrayList<Income> = java.util.ArrayList<Income>()
                for (document in task.result!!) {
                    val createdAt = document.data["createdAt"] as Map<*, *>
                    val date = createdAt["dayOfMonth"] as Long
                    val month = createdAt["monthValue"] as Long
                    val year = createdAt["year"] as Long
                    var category: Category? = null
                    categoryController.getCategoryById(document.data["category"].toString(), object:
                        MyCallbackCategory {
                        override fun onCallback(value: Category) {
                            category = value

                        }
                    })
                    val income = Income(
                        document.data["amount"].toString(),
                        LocalDateTime.of(year.toInt(), month.toInt(), date.toInt(), 0, 0),
                        category,
                    )
                    simon.add(income)

                }
                myCallback.onCallback(simon)
            }
        }
//        return income
    }

    fun createIncome(income: Income) {
        val user = auth.currentUser
        val userId = user?.uid
        // Create a new document

        db.collection(collection).add(
            hashMapOf("amount" to income.amount,
                "createdAt" to income.createdAt,
                "user" to userId,
                "category" to (income.category?.uid ?: "")
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