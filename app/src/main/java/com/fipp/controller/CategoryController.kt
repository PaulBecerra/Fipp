package com.fipp.controller

import android.app.Activity
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

    fun getCategoryById(id: String): Category {
        var category = Category()
        db.collection(collection).document(id).get().addOnSuccessListener {
            category = it.toObject(Category::class.java)!!
        }
//        return Category(result.get("name").toString(), result.get("id").toString())
        return category
    }
}