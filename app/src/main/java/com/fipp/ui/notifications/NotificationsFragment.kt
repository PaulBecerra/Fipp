package com.fipp.ui.notifications

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fipp.LoginActivity
import com.fipp.R
import com.fipp.databinding.FragmentNotificationsBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val notificationsViewModel =
//            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnLogout = binding.btnLogout

        btnLogout.setOnClickListener{
            signOut()
        }
    }

    private fun signOut(){
        val prefs = activity?.getSharedPreferences(getString(R.string.shared_prefences), Context.MODE_PRIVATE)?.edit()
        prefs?.clear()
        prefs?.apply()

        Firebase.auth.signOut()

        val act = parentFragment?.activity
        act?.startActivity(Intent(act, LoginActivity::class.java))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}