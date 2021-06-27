package com.example.simplibuy.launch

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.simplibuy.R
import com.example.simplibuy.activties.ClientActivity
import com.example.simplibuy.activties.MainActivity
import com.example.simplibuy.activties.Preferences.BUSINESS
import com.example.simplibuy.activties.Preferences.userRole
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_splash.view.*

class SplashFragment : Fragment() {
    lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        val root = inflater.inflate(R.layout.fragment_splash, container, false)


        // Animation Code
        val topAnimation = AnimationUtils.loadAnimation(activity, R.anim.top_animation)
        root.iv_note.startAnimation(topAnimation)

if (auth.currentUser?.uid!=null) {
    Handler().postDelayed({
        if (auth.currentUser!!.isEmailVerified) {
            val userRole = context?.userRole()
            if (userRole == BUSINESS) {
                startActivity(Intent(activity, ClientActivity::class.java))
            } else {
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
        else{
            auth.signOut()
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_splashFragment2_to_authentication_navigation)
            }
        }

    }, 2000)
}
        else
{
    Handler().postDelayed({

        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_splashFragment2_to_authentication_navigation)
        }



    }, 2000)
}


//        requireActivity().finish()

        return root
    }


}