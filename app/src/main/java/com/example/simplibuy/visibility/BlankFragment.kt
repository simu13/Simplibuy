package com.example.simplibuy.visibility

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplibuy.R
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_blank.Rv
import kotlinx.android.synthetic.main.fragment_blank.view.*
import kotlinx.android.synthetic.main.list.*

class BlankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_blank, container, false)
        val list:ArrayList<String> = arrayListOf()
        list.add("Yeah")
        list.add("Yo")
        val adapter = BlankAdapter(this@BlankFragment, list)
        root.Rv.adapter = adapter
        root.apply {

            //Rv.adapter = adapter
            Rv.layoutManager = LinearLayoutManager(activity)
            adapter.setOnItemClickListener {
                item1.visibility = View.GONE
            }
        }
        return root
    }
}