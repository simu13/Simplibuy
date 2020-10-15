package com.example.simplibuy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_main, container, false)


        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar_main_fragment)
        }
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.fragment_title_main)
        return root
    }


}


