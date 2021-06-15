package com.example.simplibuy.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplibuy.R
import com.example.simplibuy.adapter.MenuAdapter
import com.example.simplibuy.database.*
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_info.view.*
import kotlinx.android.synthetic.main.menu_list.*

class InfoFragment : Fragment() {
    private lateinit var adapter:MenuAdapter
    val args:InfoFragmentArgs by navArgs()
    lateinit var viewModel: ShoppingViewModel2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setUpRecyclerView()

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
        val data = args.article.menu
        adapter = MenuAdapter(this@InfoFragment,viewModel, data)
        menu.adapter = adapter
        menu.layoutManager =LinearLayoutManager(activity)
        adapter.setOnItemClickListener {
            viewModel.upsertFood(item = MenuCart(it))

            //item1.visibility =View.GONE
            Toast.makeText(activity,it,Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val article = args.article
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_info, container, false)
root.apply {
    //val data = args.article.menu
    //adapter = MenuAdapter(this@InfoFragment, data)
    //menu.adapter = adapter
    /*adapter.setOnItemClickListener {
        selected.visibility = View.GONE
        minus.visibility = View.VISIBLE
        tvAmount.visibility = View.VISIBLE
        add.visibility = View.VISIBLE
        //item1.visibility =View.GONE
        Toast.makeText(activity,it,Toast.LENGTH_SHORT).show()
    }*/
    //menu.layoutManager =LinearLayoutManager(activity)
}
        root.tv_view_cart.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_infoFragment_to_onlineCartFragment)
        }
        root.tvStoreName.text = article.Name
            //article.menu[1]

        return root
    }
    fun setUpRecyclerView(){

    }

}