package com.fipp.ui.expenses

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fipp.R
import com.fipp.databinding.FragmentExpensesCategoriesBinding
import com.fipp.model.Category

class ExpensesCategoriesFragment : Fragment() {
    private var categoryList: ArrayList<Category> = ArrayList()
    private var _binding: FragmentExpensesCategoriesBinding? = null
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
        _binding = FragmentExpensesCategoriesBinding.inflate(inflater, container, false)

        return binding.root
//        return inflater.inflate(R.layout.fragment_income_categories, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val boton: View = binding.buttonRegistrar

        val act = parentFragment?.parentFragment?.activity

        boton.setOnClickListener {
//            val intent = Intent (parentFragment?.activity, RegisterNewIncomeCategoryActivity::class.java)
//            parentFragment?.activity?.startActivity(intent)


            act?.startActivity(Intent(act, RegisterNewExpensesCategoryActivity::class.java))
        }

        getExpenseCategoriesByUser();

        val adapter = CategoryExpenseAdapter(categoryList, parentFragment?.parentFragment?.context)

        val recyclerView = binding.recyclerViewExpensesCategory

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

    private fun getExpenseCategoriesByUser(){
        val category1 = Category("","test 1", "subtest 1", R.drawable.fipp_app_iconos_22)
        val category2 = Category("", "test 2", "subtest 2", R.drawable.fipp_app_iconos_22)
        val category3 = Category("", "test 3", "subtest 3", R.drawable.fipp_app_iconos_22)
        val category4 = Category("", "test 4", "subtest 4", R.drawable.fipp_app_iconos_22)
        categoryList.addAll(listOf(category1, category2, category3, category4, category1, category2, category3, category4, category1, category2, category3, category4));
    }
}