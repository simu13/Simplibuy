package com.example.simplibuy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplibuy.R
import com.example.simplibuy.adapter.SuperMarketAdapter
import com.example.simplibuy.classes.SuperMArket
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_test.view.*

class TestFragment : Fragment() {
    lateinit var adapter: SuperMarketAdapter
    private val mFireStore = FirebaseFirestore.getInstance()
    var lists:ArrayList<SuperMArket> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //adapter = SuperMarketAdapter(this, listOf())
        /*adapter.setOnClickListener(object :SuperMarketAdapter.OnclickListener{
            override fun onClick(position: Int, model: SuperMArket) {
                //view?.let { Navigation.findNavController(it).navigate(R.id.action_testFragment_to_infoFragment) }
                Toast.makeText(activity,"Clicked",Toast.LENGTH_SHORT).show()
            }

        })*/

        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_test, container, false)

        adapter = SuperMarketAdapter(this, listOf())






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
                adapter.setOnItemClickListener {
                    val bundle = Bundle().apply {
                        putSerializable("article",it)
                    }
                    findNavController().navigate(
                        R.id.action_testFragment_to_infoFragment,
                    bundle)
                }
                adapter.notifyDataSetChanged()
            }
        }

        return root
    }
}