package com.example.simplibuy.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.simplibuy.R
import com.example.simplibuy.activties.Final
import com.example.simplibuy.activties.ShoppingActivity
import com.example.simplibuy.activties.ZxingScanner
import com.example.simplibuy.adapter.MenuAdapter
import com.example.simplibuy.classes.Firebase
import com.example.simplibuy.classes.SuperMArket
import com.example.simplibuy.database.*
import com.example.simplibuy.model.User
import com.example.simplibuy.seller.authentication.AuthenticationActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.menu_list.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainFragment : Fragment()  {
    var totalWeight: String = "0"
    var totalPrice: String = "0"
    val images: ArrayList<String> = arrayListOf()
    lateinit var viewModel: ShoppingViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val database = activity?.let { ShoppingDatabase(it) }
        val repository =
            database?.let { ShoppingRepository(it) }
        val factory =
            repository?.let { ShoppingViewModelFactory(it) }

        viewModel =
            factory?.let { ViewModelProvider(this, it).get(ShoppingViewModel::class.java) }!!

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        root.additemButton.setOnClickListener {
            val intent = Intent(activity, ZxingScanner::class.java)
            startActivity(intent)
        }

        //images.add("https://firebasestorage.googleapis.com/v0/b/simplibuy-cfb2a.appspot.com/o/USER_IMAGE1624518111536.jpg?alt=media&token=02821538-84ac-49af-97d2-0e431e472a5c")// Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title
        com.google.firebase.ktx.Firebase.firestore.collection("offer").get().addOnSuccessListener {
            images.clear()
            for (doc in it) {

                val offer = doc.toObject(SuperMArket::class.java)

                if (offer.Image != null) {
                    images.add(offer.Image)
                }
            }
            Toast.makeText(activity, images.size.toString(), Toast.LENGTH_SHORT).show()
            val imageList = ArrayList<SlideModel>()

            when(images.size) {
                1 -> imageList.add(SlideModel(images[0]))
                2 -> {
                    imageList.add(SlideModel(images[0]))
                    imageList.add(SlideModel(images[1]))
                }

                3 -> {
                    imageList.add(SlideModel(images[0]))
                    imageList.add(SlideModel(images[1]))
                    imageList.add(SlideModel(images[2]))
                }
            }



            val imageSlider = root.findViewById<ImageSlider>(R.id.image_slider)
            imageSlider.setImageList(imageList)
            //Toast.makeText(activity,images[0],Toast.LENGTH_SHORT).show()

        }

        //imageList.add(SlideModel(images[1]))
        //imageList.add(SlideModel(images[2]))


        root.checkoutButton.setOnClickListener {
            val intent = Intent(activity, Final::class.java)
            startActivity(intent)
        }
        root.shoppingListButton.setOnClickListener {
            val intent = Intent(activity, ShoppingActivity::class.java)
            startActivity(intent)
        }

        root.mapsButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_mapsFragment)
        }
        (activity as AppCompatActivity).supportActionBar?.show()
        Firebase().signInUser(this)

        CoroutineScope(Dispatchers.IO).launch {
            //totalPrice = viewModel.getSubTotal().toString()
            //totalWeight = viewModel.getSubTotal2().toString()

        }
        root.tv_number_items.text = "Rs. $totalPrice"
        root.tv_bill_number.text = "$totalWeight Kg"

        root.image_slider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_mainFragment_to_superMarketFragment)
                }
            }
        })
        return root
    }

    fun signInSuccess(user: User) {
        tv_full_name.text = user.firstName
        tv_user_name.text = user.email
        Glide.with(this)
            .load(user.image)
            .placeholder(R.drawable.ic_person)
            .into(profile_image)
    }

}
