package com.example.simplibuy.fragment

import android.content.Context
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
import com.example.simplibuy.activties.BlankActivity
import com.example.simplibuy.activties.ClientActivity
import com.example.simplibuy.activties.ShoppingActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PatientLoginFragment : Fragment() {

    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        val root = inflater.inflate(R.layout.fragment_login, container, false)

        root.letsgoButton.setOnClickListener {
            loginUser(it)
        }
        root.createAccountButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        /*root.forgotPasswordBButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }*/

        return root
    }


    private fun loginUser(view: View) {
        val email = username1.text.toString()
        val password = loginPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {

                try {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // TODO (Step 2: Remove the toast message and call the FirestoreClass signInUser function to get the data of user from database. And also move the code of hiding Progress Dialog and Launching MainActivity to Success function.)
                            // Calling the FirestoreClass signInUser function to get the data of user from database.
                            //FirestoreClass().signInUser(this@LoginFragment,view)
                            // END
                        }





                    }.await()
                    withContext(Dispatchers.Main) {
                        val user = auth.currentUser
                        if (user!!.uid =="wBEh0HoQ9xUPIdYJkFxQzY1lttH2") {
                            view?.let {
                                //Navigation.findNavController(it).navigate(R.id.action_doctorLoginFragment_to_profileFragment)
                                if (user!!.isEmailVerified) {
                                    startActivity(Intent(activity, ClientActivity::class.java))
                                    //Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainFragment)
                                } else {
                                    Toast.makeText(
                                        activity,
                                        "Please Verify your Email Address",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        else{
                            auth.signOut()
                            Toast.makeText(activity,"Invalid Details",Toast.LENGTH_SHORT).show()
                        }


                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }


    }
}


