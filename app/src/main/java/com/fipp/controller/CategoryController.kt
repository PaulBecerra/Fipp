package com.fipp.controller

import android.app.Activity
import android.widget.Toast
import com.fipp.model.Category
import com.fipp.model.Expense
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class CategoryController(private var activity: Activity) {

    private var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private val collection = "expenses"

    init {
        this.auth = Firebase.auth
    }

    fun getCategoryById(id: String, callbackCategory: MyCallbackCategory) {
        var category: Category? = null
        db.collection(collection).document(id).get().addOnSuccessListener {
            category = Category(
                it.id,
                it.data?.get("catogoryName").toString(),
                it.data?.get("subCategory").toString()
            )
        }
        category?.let { callbackCategory.onCallback(it) }
//        return category
    }
}
interface MyCallbackCategory {
    fun onCallback(value: Category)
}