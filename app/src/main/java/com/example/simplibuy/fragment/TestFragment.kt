package com.example.simplibuy.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplibuy.R
import com.example.simplibuy.adapter.ShoppingItemAdapter
import com.example.simplibuy.adapter.SuperMarketAdapter
import com.example.simplibuy.classes.SuperMArket
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_test.view.*
import java.util.*
import kotlin.collections.ArrayList

class TestFragment : Fragment() {
    lateinit var adapter: SuperMarketAdapter
    private val mFireStore = FirebaseFirestore.getInstance()
    var lists:ArrayList<SuperMArket> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_test, container, false)


        mFireStore.collection("SuperMarket").get().addOnSuccessListener {
            for(documents in it)
            {
                val list = documents.toObject(SuperMArket::class.java)
                lists.add(list)
            }

            adapter = SuperMarketAdapter(this, lists)
            root.apply {
                SuperMarketList.adapter = adapter
                SuperMarketList.layoutManager = LinearLayoutManager(activity)
                adapter.notifyDataSetChanged()
            }
        }
        adapter.setOnClickListener(object :)
        return root
    }
}