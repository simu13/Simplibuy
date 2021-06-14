package com.example.simplibuy.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplibuy.R
import com.example.simplibuy.adapter.MenuAdapter
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_info.view.*

class InfoFragment : Fragment() {
    private lateinit var adapter:MenuAdapter
    val args:InfoFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = args.article.menu
        adapter = MenuAdapter(this, data)
        menu.adapter = adapter
        menu.layoutManager =LinearLayoutManager(activity)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val article = args.article
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_info, container, false)
        root.tvStoreName.text = article.Name
            //article.menu[1]

        return root
    }

}