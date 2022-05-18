package com.fipp.ui.expenses

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
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fipp.R
import com.fipp.interfaces.ItemClickListener
import com.fipp.model.Category
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class CategoryExpenseAdapter(private var categoryList: List<Category>, var activity: Activity?) :
    RecyclerView.Adapter<CategoryExpenseAdapter.ViewHolder>() {
    private var selectedPos = -1

        class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
            val image: ImageView
            val categoryName: TextView
            val subCategoryName: TextView
            lateinit var itemClickListener: ItemClickListener

            init {
                image = itemView.findViewById(R.id.imageViewCategoryExpense)
                categoryName = itemView.findViewById(R.id.textViewCategoryExpense)
                subCategoryName = itemView.findViewById(R.id.textViewSubCategoryExpense)
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_categories_expense, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryName.text = category.categoryName
        holder.subCategoryName.text = category.subCategory

        val storageReference = Firebase.storage.reference

        val pathReference = storageReference.child("img/${category.image}")

        val url = "https://fipp-31ec2.appspot.com.storage.googleapis.com/img%2F${category.image}"
        Glide.with(activity!! /* context */)
            .load(url)
            .into(holder.image)

        //holder.itemView.setSelected(selectedPos == position);
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
        //holder.categoryName.setTextColor(Color.parseColor("#F9F9FA"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        //holder.categoryName.setTextColor(Color.parseColor("#fff"))
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}