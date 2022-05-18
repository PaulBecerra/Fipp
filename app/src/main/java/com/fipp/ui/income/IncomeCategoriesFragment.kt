package com.fipp.ui.income

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fipp.R
import com.fipp.databinding.FragmentIncomeCategoriesBinding
import com.fipp.model.Category
import com.fipp.model.CategoryType
import com.fipp.model.Expense
import com.fipp.ui.expenses.MyCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class IncomeCategoriesFragment : Fragment() {

    private var categoryList: ArrayList<Category> = ArrayList()
    private var _binding: FragmentIncomeCategoriesBinding? = null
    private val db = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = Firebase.auth
    private var currentPosition = -1

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIncomeCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val boton: View = binding.buttonIncomes

        val act = parentFragment?.parentFragment?.activity

        boton.setOnClickListener {
//            val intent = Intent (parentFragment?.activity, RegisterNewIncomeCategoryActivity::class.java)
//            parentFragment?.activity?.startActivity(intent)

            act?.startActivity(Intent(act, RegisterNewIncomeCategoryActivity::class.java))
        }

        this.getIncomeCategories( object: MyCallbackIncomeCategories {
            override fun onCallback(value: List<Category>) {
                categoryList = value as java.util.ArrayList<Category>

                configRecyclerview()
            }
        })
    }

    private fun configRecyclerview(){
        val act = parentFragment?.parentFragment?.activity
        val adapter = CategoryIncomeAdapter(categoryList, parentFragment?.parentFragment?.activity)

        val recyclerView = binding.recyclerViewIncomeCategory

        val grid = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = grid
        recyclerView.adapter = adapter

        val mGestureDetector = GestureDetector(act, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        })

        recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onRequestDisallowInterceptTouchEvent(b: Boolean) {}
            override fun onInterceptTouchEvent(
                recyclerView: RecyclerView,
                motionEvent: MotionEvent
            ): Boolean {
                try {
                    val child: View? = recyclerView.findChildViewUnder(motionEvent.x, motionEvent.y)
                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                        val pos = recyclerView.getChildAdapterPosition(child)

                        currentPosition = pos

                        Toast.makeText(act, "the item selected is $currentPosition", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return false
            }

            override fun onTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent) {}
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getIncomeCategories(myCallback : MyCallbackIncomeCategories) {
        val user = auth.currentUser
        val userId = user?.uid

        db.collection("categories").whereEqualTo("user", userId)
            .whereEqualTo("categoryType", CategoryType.INCOMES.toString()).get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    val categoryList : ArrayList<Category> = ArrayList<Category>()
                    for (document in task.result!!){
                        val uuid = document.data["uuid"].toString()
                        val name = document.data["categoryName"].toString()
                        val subcategory = document.data["subCategory"].toString()
                        val image = document.data["image"].toString()
                        val category = Category(uuid, name, subcategory, image, CategoryType.INCOMES)
                        categoryList.add(category)
                    }
                    myCallback.onCallback(categoryList)
                }
            }
    }
}

interface MyCallbackIncomeCategories {
    fun onCallback(value: List<Category>)
}