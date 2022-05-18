package com.fipp.ui.income

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fipp.R
import com.fipp.interfaces.ItemClickListener
import com.fipp.model.Category
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class CategoryIncomeAdapter(private var categoryList: List<Category>, var activity: Activity?) :
    RecyclerView.Adapter<CategoryIncomeAdapter.ViewHolder>() {
    private var selectedPos = -1
    private val storage = Firebase.storage

        class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
            val image: ImageView
            val categoryName: TextView
            val subCategoryName: TextView
            lateinit var itemClickListener: ItemClickListener

            init {
                image = itemView.findViewById(R.id.imageViewCategoryIncome)
                categoryName = itemView.findViewById(R.id.textViewCategoryIncome)
                subCategoryName = itemView.findViewById(R.id.textViewSubCategoryIncome)
                itemView.setOnClickListener(this)
            }

            fun setOnClickListener(itemClickListener: ItemClickListener){
                this.itemClickListener = itemClickListener
            }

            override fun onClick(p0: View) {
                itemClickListener.onClick(p0, adapterPosition)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_categories_income, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryName.text = category.categoryName
        holder.subCategoryName.text = category.subCategory

        val url = "https://fipp-31ec2.appspot.com.storage.googleapis.com/img%2F${category.image}"
        Glide.with(activity!! /* context */)
            .load(url)
            .into(holder.image)

        holder.setOnClickListener (
            object : ItemClickListener{
                override fun onClick(view: View, position: Int) {
                    selectedPos = position
                    notifyDataSetChanged()
                }
            }
        )

        if (selectedPos == position){
            holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}