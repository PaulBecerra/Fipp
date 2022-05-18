package com.fipp.ui.income

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fipp.R
import com.fipp.model.Category
import com.fipp.model.Subcategory

class RegisterNewIncomeSubcategoryActivity : AppCompatActivity() {

    private var categoryList: ArrayList<Subcategory> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new_income_subcategory)

        getIncomeSubCategoriesByUser();

        val adapter = SubcategoryIncomeAdapter(categoryList, this)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewSubcategoryIncome)

        val grid = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = grid
        recyclerView.adapter = adapter

        val btn_close: ImageButton = findViewById(R.id.btn_new_income_subcategory_close)

        btn_close.setOnClickListener{
            finish()
        }
    }

    private fun getIncomeSubCategoriesByUser(){
        val category1 = Subcategory("Bills", R.drawable.ic_bills_24, "bills.png")
        val category2 = Subcategory("Car", R.drawable.ic_car_24, "car.jpg")
        //val category3 = Subcategory("Clothes", R.drawable.)
        val category4 = Subcategory("Communication", R.drawable.ic_communication_24, "communication.png")
        //val category6 = Subcategory("Entertainment", R.drawable.)
        val category7 = Subcategory("Food", R.drawable.ic_food_24, "eat.png")
        val category8 = Subcategory("Gifts", R.drawable.ic_gifts_24, "gifts.png")
        val category9 = Subcategory("Health", R.drawable.ic_health_24, "health.png")
        val category10 = Subcategory("House", R.drawable.ic_baseline_home_24,"house.png")
        val category11 = Subcategory("Pets", R.drawable.ic_pets_24,"pets.png")
        val category12 = Subcategory("Sports", R.drawable.ic_sports_24,"sports.png")
        val category13 = Subcategory("Taxi", R.drawable.ic_taxi_24,"taxi.png")
        //val category14 = Subcategory("Toiletry", R.drawable.fipp_app_iconos_85)
        val category15 = Subcategory("Transport", R.drawable.ic_transport_24,"transport.png")
        categoryList.addAll(listOf(category1, category2, category4, category7, category8, category9, category10, category11, category12, category13, category15));
    }
}