package com.example.simplibuy

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.android.synthetic.main.fragment_splash.view.*
import java.util.*

class SplashFragment : Fragment() {
    lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        val root = inflater.inflate(R.layout.fragment_splash, container, false)



        val topAnimation = AnimationUtils.loadAnimation(activity, R.anim.top_animation)

        root.iv_note.startAnimation(topAnimation)

        Handler().postDelayed({


            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_splashFragment_to_startFragment)
            }


        }, 2000)


//        requireActivity().finish()

        return root
    }


}