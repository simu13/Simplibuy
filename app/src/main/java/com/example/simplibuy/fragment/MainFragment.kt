package com.example.simplibuy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.simplibuy.R
import com.example.simplibuy.classes.Firebase
import com.example.simplibuy.model.User
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        root.additemButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_scanFragment)
        }
       (activity as AppCompatActivity).supportActionBar?.show()
        Firebase().signInUser(this)

        return root
    }

    fun signInSuccess(user: User){
        tv_full_name.text = user.firstName
        tv_user_name.text = user.email
        Glide.with(this).load(user.image).into(profile_image)
    }
}


