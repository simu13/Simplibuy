package com.example.simplibuy.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.simplibuy.R
import com.example.simplibuy.activties.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import kotlinx.android.synthetic.main.fragment_forgot_password.view.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ForgotPasswordFragment : Fragment() {

    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        val root = inflater.inflate(R.layout.fragment_forgot_password, container, false)

        root.submitButton.setOnClickListener {
            forgotPassword(it)
        }
        return root
    }


    private fun forgotPassword(view: View) {
        val email: String = forgotPassword.text.toString().trim {it <= ' '}
        if (email.isEmpty()){
            Toast.makeText(
                activity,
                "Please enter  Email Address",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(
                            activity,
                            "Email successfully sent to reset your password",
                            Toast.LENGTH_SHORT
                        ).show()
                        Navigation.findNavController(view).navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
                    }else{
                        Toast.makeText(activity,task.exception!!.message.toString(),Toast.LENGTH_LONG).show()
                    }
                }
        }

    }

}