package com.fipp.ui.income

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fipp.R
import com.fipp.RegisterNewIncomeCategoryActivity
import com.fipp.databinding.FragmentIncomeCategoriesBinding


/**
 * A simple [Fragment] subclass.
 * Use the [IncomeCategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IncomeCategoriesFragment : Fragment() {
    private var _binding: FragmentIncomeCategoriesBinding? = null

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
//        return inflater.inflate(R.layout.fragment_income_categories, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val boton: View = binding.buttonIncomes

        boton.setOnClickListener {
//            val intent = Intent (parentFragment?.activity, RegisterNewIncomeCategoryActivity::class.java)
//            parentFragment?.activity?.startActivity(intent)

            val act = parentFragment?.parentFragment?.activity
            act?.startActivity(Intent(act, RegisterNewIncomeCategoryActivity::class.java))
        }
    }
}