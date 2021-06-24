package com.example.simplibuy.seller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.simplibuy.R
import com.example.simplibuy.adapter.MenuAdapter
import com.example.simplibuy.adapter.SellerMenuAdapter
import com.example.simplibuy.database.MenuCart
import com.example.simplibuy.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_seller_main.*
import kotlinx.android.synthetic.main.fragment_seller_main.view.*
import kotlinx.android.synthetic.main.fragment_test.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.StringBuilder

class SellerMainFragment : Fragment() {

    private lateinit var mUserDetails: User
    private lateinit var adapter: SellerMenuAdapter
    val menuCart:ArrayList<MenuCart> = arrayListOf()
    var firstName:String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_seller_main, container, false)
        adapter = SellerMenuAdapter(this, arrayListOf())
        com.example.simplibuy.classes.Firebase().signInUser(this)
        root.apply {
            sellerMenu.adapter = adapter
            sellerMenu.layoutManager = LinearLayoutManager(activity)
            adapter.setOnItemClickListener {
                Toast.makeText(activity, "clicked", Toast.LENGTH_SHORT).show()
                deletePerson(it)
            }

        }
       /*root.apply {
           menu.adapter = adapter
           menu.layoutManager = LinearLayoutManager(activity)
           adapter.setOnItemClickListener { MenuCart ->

               //item1.visibility =View.GONE
               //Toast.makeText(activity, it.amount, Toast.LENGTH_SHORT).show()
           }
       }*/

root.button4.setOnClickListener {
    val name = editTextTextPersonName.text
    val price = editTextTextPersonName2.text
    val quantity = editTextTextPersonName3.text
    val menuCart = MenuCart(name.toString(),price.toString().toInt(),quantity.toString().toInt())
    savePerson(menuCart)
}

        return root

    }


    private fun deletePerson(menuCart: MenuCart) = CoroutineScope(Dispatchers.IO).launch {
        val personQuery = Firebase.firestore.collection(firstName)
            .whereEqualTo("name", menuCart.name)
            .whereEqualTo("amount", menuCart.amount)

            .get()
            .await()
        if(personQuery.documents.isNotEmpty()) {
            for(document in personQuery) {
                try {
                    Firebase.firestore.collection(firstName).document(document.id).delete().await()
                    /*personCollectionRef.document(document.id).update(mapOf(
                        "firstName" to FieldValue.delete()
                    )).await()*/
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, "No persons matched the query.", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun setUserDataInUI(user: User) {
        // Initialize the user details variable
        mUserDetails = user
        firstName = user.firstName
        retrievePersons()
    }
    private fun retrievePersons() {
            Firebase.firestore.collection(firstName).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                firebaseFirestoreException?.let {
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                querySnapshot?.let {
                    val sb = StringBuilder()
                    for (document in it) {
                        val person = document.toObject<MenuCart>()
                        sb.append("$person\n")
                    }

                    //textView.text = sb.toString()
                }
                val sb = StringBuilder()
                if (querySnapshot != null) {
                    menuCart.clear()
                    for (document in querySnapshot.documents) {
                        val person = document.toObject<MenuCart>()

                        menuCart.add(person!!)

                    }
                    adapter.setData(menuCart)

                }
            }

    }
    private fun savePerson(person: MenuCart) = CoroutineScope(Dispatchers.IO).launch {
        try {
            Firebase.firestore.collection(firstName).add(person).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, "Successfully saved data.", Toast.LENGTH_LONG).show()
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}