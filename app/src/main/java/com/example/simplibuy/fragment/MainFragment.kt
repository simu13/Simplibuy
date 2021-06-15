package com.example.simplibuy.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.simplibuy.R
import com.example.simplibuy.activties.Final
import com.example.simplibuy.activties.ShoppingActivity
import com.example.simplibuy.activties.ZxingScanner
import com.example.simplibuy.classes.Firebase
import com.example.simplibuy.database.ShoppingDatabase
import com.example.simplibuy.database.ShoppingRepository
import com.example.simplibuy.database.ShoppingViewModel
import com.example.simplibuy.database.ShoppingViewModelFactory
import com.example.simplibuy.model.User
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainFragment : Fragment() {
    var totalWeight:String="0"
    var totalPrice:String="0"
    lateinit var viewModel : ShoppingViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val database = activity?.let { ShoppingDatabase(it) }
        val repository =
            database?.let { ShoppingRepository(it) }
        val factory =
            repository?.let { ShoppingViewModelFactory(it) }

        viewModel = factory?.let { ViewModelProvider(this, it).get(ShoppingViewModel::class.java) }!!

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        root.additemButton.setOnClickListener {
            val intent = Intent(activity, ZxingScanner::class.java)
            startActivity(intent)
        }
        val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(SlideModel(R.drawable.one))
        imageList.add(SlideModel(R.drawable.two))
        imageList.add(SlideModel(R.drawable.three))

        val imageSlider = root.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)

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

        CoroutineScope(Dispatchers.IO).launch{
            totalPrice = viewModel.getSubTotal().toString()
            totalWeight = viewModel.getSubTotal2().toString()

        }
        root.tv_number_items.text = "Rs. $totalPrice"
        root.tv_bill_number.text =  "$totalWeight Kg"

        root.image_slider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                view?.let { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_superMarketFragment) }
            }
        })
        return root
    }

    fun signInSuccess(user: User){
        tv_full_name.text = user.firstName
        tv_user_name.text = user.email
        Glide.with(this)
            .load(user.image)
            .placeholder(R.drawable.ic_person)
            .into(profile_image)

    }
}


