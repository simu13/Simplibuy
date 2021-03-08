package com.example.simplibuy.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.simplibuy.R
import com.example.simplibuy.classes.Firebase
import com.example.simplibuy.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class RegisterFragment : Fragment(),View.OnClickListener {

    private val cal = Calendar.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        dateSetListener = DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
            cal.set(Calendar.YEAR, i)
            cal.set(Calendar.MONTH, i2)
            cal.set(Calendar.DAY_OF_MONTH, i3)

            updateDateInView()
        }
        val root = inflater.inflate(R.layout.fragment_register, container, false)

        root.signUpButton.setOnClickListener {view ->
            registerUser(view)
        }
        root.createAccountButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_loginFragment)
            //root.etDate.setOnClickListener(this)
        }
        root.et_date.setOnClickListener(this)
        return root
    }
    private fun registerUser(view: View) {

        val email = UsernameTextField.text.toString()
        val firstName: String = FirstNameTextField.text.toString().trim { it <= ' ' }
        val date: String = et_date.text.toString().trim { it <= ' ' }
        val password = PasswordTextField.text.toString()

        if (FirstNameTextField.text.toString().isEmpty()) {
            FirstNameTextField.error = "Please enter name"
            FirstNameTextField.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(UsernameTextField.text.toString()).matches()) {
            UsernameTextField.error = "Please enter valid email"
            UsernameTextField.requestFocus()
            return
        }


        if (PasswordTextField.text.toString().isEmpty()) {
            PasswordTextField.error = "Please enter password"
            PasswordTextField.requestFocus()
            return
        }

        if (ConfirmTextField.text.toString() != PasswordTextField.text.toString()) {
            ConfirmTextField.error = "Password and Confirm password should be same"
            ConfirmTextField.requestFocus()
            return
        }

        if (email.isNotEmpty() && password.isNotEmpty())

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                        OnCompleteListener<AuthResult> { task ->

                            // If the registration is successfully done
                            if (task.isSuccessful) {

                                // Firebase registered user
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                // Registered Email
                                val registeredEmail = firebaseUser.email!!



                                val user = User(firebaseUser.uid,firstName,registeredEmail)

                                // call the registerUser function of FirestoreClass to make an entry in the database.
                                Firebase().registerUser(this@RegisterFragment, user)
                            } else {

                            }
                        }).await()

                    withContext(Dispatchers.Main)
                    {
                        val user = auth.currentUser
                        user!!.sendEmailVerification()
                        Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        //Toast.makeText(this@RegisterFragment, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.et_date -> {
                activity?.let {
                    DatePickerDialog(
                        it,
                        dateSetListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }
        }
    }
    private fun updateDateInView(){
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat,Locale.getDefault())
        et_date.setText(sdf.format(cal.time).toString())
    }
}