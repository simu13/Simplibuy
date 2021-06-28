package com.example.simplibuy.onlineCart

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.simplibuy.R
import com.example.simplibuy.classes.Firebase
import com.example.simplibuy.model.User
import com.example.simplibuy.others.Constants
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.io.IOException


class SellerProfileFragment : Fragment() {

    companion object {
        private const val READ_PERMISSION = 1
        private const val PICK_REQUEST = 2
    }

    var mSelectedImageFileUri : Uri? = null

    // A global variable for user details.
    private lateinit var mUserDetails: User

    // A global variable for a user profile image URL
    private var mProfileImageURL: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile_seller, container, false)
        (activity as AppCompatActivity).supportActionBar?.show()
        Firebase().signInUser(this)

        root.iv_profile_user_image.setOnClickListener {

            if (ContextCompat.checkSelfPermission(activity as AppCompatActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Constants.showImageChooser(this)
            } else {
                /*Requests permissions to be granted to this application. These permissions
                 must be requested in your manifest, they should not be granted to your app,
                 and they should have protection level*/
                ActivityCompat.requestPermissions(
                    activity as AppCompatActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.PICK_IMAGE_REQUEST_CODE
                )
            }
        }
        root.btn_update.setOnClickListener {

            // Here if the image is not selected then update the other details of user.
            if (mSelectedImageFileUri != null) {

                uploadUserImage()
            } else {
                updateUserProfileData()

                // Call a function to update user details in the database.
                //updateUserProfileData()
            }
        }

        return root

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_PERMISSION) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.showImageChooser(this@SellerProfileFragment)
            } else {
                //Displaying another toast if permission is not granted

            }


        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
            && requestCode == Constants.PICK_IMAGE_REQUEST_CODE
            && data!!.data != null
        ) {
            // The uri of selection image from phone storage.
            mSelectedImageFileUri = data.data!!

            try {
                // Load the user image in the ImageView.
                Glide
                    .with(this@SellerProfileFragment)
                    .load(Uri.parse(mSelectedImageFileUri.toString())) // URI of the image
                    .centerCrop() // Scale type of the image.
                    .placeholder(R.drawable.profile) // A default place holder
                    .into(iv_profile_user_image) // the view in which the image will be loaded.
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun updateUserProfileData() {

        val userHashMap = HashMap<String, Any>()

        if (mProfileImageURL.isNotEmpty() && mProfileImageURL != mUserDetails.image) {
            userHashMap[Constants.IMAGE] = mProfileImageURL

        }

        if (et_name.text.toString() != mUserDetails.firstName) {
            userHashMap[Constants.NAME] = et_name.text.toString()
        }



        // Update the data in the database.
        Firebase().updateUserProfileData(this@SellerProfileFragment, userHashMap)
        Firebase().updateSellerProfile(this@SellerProfileFragment, userHashMap)
    }

    fun profileUpdateSuccess() {

        activity?.setResult(Activity.RESULT_OK)
        Toast.makeText(activity,"updated",Toast.LENGTH_SHORT).show()
    }
    private fun uploadUserImage() {
        if (mSelectedImageFileUri != null) {
            //getting the storage reference
            val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
                "USER_IMAGE" + System.currentTimeMillis() + "."
                        + activity?.let { Constants.getFileExtension(it, mSelectedImageFileUri) }
            )
            //adding the file to reference
            sRef.putFile(mSelectedImageFileUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    // The image upload is success
                    Log.e(
                        "Firebase Image URL",
                        taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                    )
                    // Get the downloadable url from the task snapshot
                    taskSnapshot.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { uri ->
                            Log.e("Downloadable Image URL", uri.toString())
                            // assign the image url to the variable.
                            mProfileImageURL = uri.toString()

                            // Call a function to update user details in the database.
                            updateUserProfileData()
                        }
                }
                .addOnFailureListener { exception ->

                }
        }
    }
    fun setUserDataInUI(user: User) {
        // Initialize the user details variable
        mUserDetails = user
        Glide
            .with(this@SellerProfileFragment)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.profile)
            .into(iv_profile_user_image)
        et_name.setText(user.firstName)
        et_email.setText(user.email)
        et_mobile.setText(user.role)
    }
}


