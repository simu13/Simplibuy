package com.example.simplibuy.onlineCart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplibuy.R
import com.example.simplibuy.database.ShoppingDatabase
import com.example.simplibuy.database.ShoppingRepository2
import com.example.simplibuy.database.ShoppingViewModel2
import com.example.simplibuy.database.ShoppingViewModelFactory2
import com.example.simplibuy.visibility.OnlineFinal
import kotlinx.android.synthetic.main.fragment_online_cart.*
import kotlinx.android.synthetic.main.fragment_online_cart.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OnlineCartFragment : Fragment() {

    lateinit var viewModel: ShoppingViewModel2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = activity?.let { ShoppingDatabase(it) }
        val repository =
            database?.let { ShoppingRepository2(it) }
        val factory =
            repository?.let { ShoppingViewModelFactory2(it) }
        viewModel = factory?.let {
            ViewModelProvider(
                this,
                it
            ).get(ShoppingViewModel2::class.java)
        }!!
        val adapter = CartAdapter(this,viewModel, arrayListOf())
        rvMenu.layoutManager = LinearLayoutManager(activity)
        rvMenu.adapter = adapter
        activity?.let {
            viewModel.getAllMenuItems().observe(it, Observer {
                adapter.items = it
                adapter.notifyDataSetChanged()
            })
        }
        activity?.let {
           // viewModel.getTotalPrice().observe(it, Observer {


         //   })
        }
        viewModel.getTotalPrice().observeForever {
                tv_total_bill?.text = it?.toString()?:"0"
            }

        //GlobalScope.launch {
          //  tv_total_bill.text = viewModel.getTotalPrice().toString()
        //}
        //viewModel.score.observe(viewLifecycleOwner, Observer {
          //      tv_total_bill.text = it.toString()
        //}
       // )
        //updatePrice()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_online_cart, container, false)
        //root.tv_total_bill.text = viewModel.getTotalPrice().toString()
        /*root.apply {
        rvMenu.layoutManager = LinearLayoutManager(activity)
        rvMenu.adapter
            }*/
       // val adapter = CartAdapter(this,viewModel, arrayListOf())
        root.tv_view_cart.setOnClickListener {
            val intent = Intent(activity, OnlineFinal::class.java)
            startActivity(intent)
        }


        return root
    }
    }
