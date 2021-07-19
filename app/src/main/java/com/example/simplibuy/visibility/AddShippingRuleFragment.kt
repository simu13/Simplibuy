package com.example.simplibuy.visibility

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.simplibuy.R
import com.example.simplibuy.classes.Firebase
import com.example.simplibuy.classes.SuperMArket
import com.example.simplibuy.databinding.FragmentAddShippingRuleBinding
import com.example.simplibuy.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_add_shipping_rule.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.ConfirmTextField
import kotlinx.android.synthetic.main.fragment_register.FirstNameTextField
import kotlinx.android.synthetic.main.fragment_register.PasswordTextField
import kotlinx.android.synthetic.main.fragment_register.UsernameTextField
import kotlinx.android.synthetic.main.fragment_register.et_date
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class AddShippingRuleFragment : Fragment(),View.OnClickListener {
    private val cal = Calendar.getInstance()
    private lateinit var binding: FragmentAddShippingRuleBinding
    private lateinit var auth: FirebaseAuth
    var firstName:String = ""
    var role = "CUSTOMER"
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    val shippingType: List<String> = listOf(
        "CUSTOMER",
        "BUSINESS"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddShippingRuleBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        dateSetListener = DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
            cal.set(Calendar.YEAR, i)
            cal.set(Calendar.MONTH, i2)
            cal.set(Calendar.DAY_OF_MONTH, i3)

            updateDateInView()
        }



        binding.signUpButtonForBusiness.setOnClickListener {view ->
            firstName =
                binding.etBusinessName.text.toString().trim { it <= ' ' }
            registerUser(view)
        }
        binding.signUpButtonForCustomer.setOnClickListener {view ->
            firstName =
                binding.FirstNameTextField.text.toString().trim { it <= ' ' }
            registerUser(view)
        }

        /*binding.createAccountButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_loginFragment)
            //root.etDate.setOnClickListener(this)
        }*/
        binding.etDate.setOnClickListener(this)
        initUI()
        return binding.root
    }



    private fun initUI() {

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, shippingType
        )

        binding.spinShippingRuleType.adapter = adapter
        binding.spinShippingRuleType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when {
                        shippingType[position] === "CUSTOMER" -> {
                            binding.apply {
                                Toast.makeText(activity, "clicked", Toast.LENGTH_SHORT).show()
                                groupConditionFlatShipping.visibility = View.VISIBLE
                                groupFlatShipping.visibility = View.GONE
                                role = "CUSTOMER"
                            }
                        }
                        shippingType[position] === "BUSINESS" -> {
                            binding.apply {
                                groupConditionFlatShipping.visibility = View.GONE
                                groupFlatShipping.visibility = View.VISIBLE
                                role = "BUSINESS"
                            }

                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

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
    private fun updateDateInView() {
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        et_date.setText(sdf.format(cal.time).toString())
    }
    private fun registerUser(view: View) {

        val email = UsernameTextField.text.toString()
        //val firstName: String =
        val date: String = et_date.text.toString().trim { it <= ' ' }
        val password = PasswordTextField.text.toString()


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

        if (PasswordTextField.text.toString().length < 6) {
            PasswordTextField.error = "Password is too short"
            PasswordTextField.requestFocus()
            Toast.makeText(
                activity,
                "The password must be of minimum length 6 characters",
                Toast.LENGTH_LONG
            ).show()
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




                                val businessName = etBusinessName.text.toString()
                                val address = etAddress.text.toString()
                                var user = User(firebaseUser.uid, firstName, registeredEmail,role)
                                if (user.role=="BUSINESS"){
                                    val users = User(firebaseUser.uid, businessName, registeredEmail,role)
                                    val superMArket = SuperMArket(users.firstName,users.image,address)
                                    Firebase().registerSuperMarket(this@AddShippingRuleFragment,superMArket)

                                }

                                // call the registerUser function of FirestoreClass to make an entry in the database.
                                user = User(firebaseUser.uid, firstName, registeredEmail,role)
                                Firebase().registerUser(this@AddShippingRuleFragment, user)
                                val verifyEmailToast = Toast.makeText(
                                    activity,
                                    "Please verify your email address",
                                    Toast.LENGTH_LONG
                                )
                                verifyEmailToast.setGravity(Gravity.CENTER, 0, 0)
                                verifyEmailToast.show()
                            } else {
                                Toast.makeText(
                                    activity,
                                    "An error occurred, please try again later",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }).await()

                    withContext(Dispatchers.Main)
                    {
                        val user = auth.currentUser
                        user!!.sendEmailVerification()
                        Navigation.findNavController(view)
                            .navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        //Toast.makeText(this@RegisterFragment, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
}
