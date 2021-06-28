package com.example.simplibuy.authentication

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.simplibuy.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_start.view.*

class StartFragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_start, container, false)
        (activity as AppCompatActivity).supportActionBar?.hide()
        FirebaseAuth.getInstance().signOut()

        // if user is already registered
        root.signInButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_startFragment_to_loginFragment)
        }
        root.sellerLogin.setOnClickListener {
            //Navigation.findNavController(it).navigate(R.id.action_startFragment2_to_patientLoginFragment)
        }
        // for new user
        root.signUpButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_startFragment_to_addShippingRuleFragment)
        }

        val topAnimation = AnimationUtils.loadAnimation(activity, R.anim.left_animation)
        val bottomAnimation = AnimationUtils.loadAnimation(activity, R.anim.right_animation)

        root.cv_start.startAnimation(topAnimation)
        root.name_app.startAnimation(topAnimation)
        root.tv_tagline.startAnimation(topAnimation)

        root.signInButton.startAnimation(topAnimation)
        root.signUpButton.startAnimation(bottomAnimation)

        return root
    }



}