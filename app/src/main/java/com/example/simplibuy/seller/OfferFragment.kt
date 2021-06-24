package com.example.simplibuy.seller

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.simplibuy.R
import com.example.simplibuy.classes.SuperMArket
import com.example.simplibuy.fragment.ProfileFragment
import com.example.simplibuy.model.User
import com.example.simplibuy.others.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_offer.*
import kotlinx.android.synthetic.main.fragment_offer.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.IOException

class OfferFragment : Fragment() {
    private var mProfileImageURL: String = ""
    var firstName = ""
    var mSelectedImageFileUri : Uri? = null
    private lateinit var mUserDetails: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_offer, container, false)
        root.ivOffer.setOnClickListener {
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
        com.example.simplibuy.classes.Firebase().signInUser(this)

        root.update.setOnClickListener{
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
    private fun updateUserProfileData() {

        val userHashMap = HashMap<String, Any>()

        if (mProfileImageURL.isNotEmpty() && mProfileImageURL != mUserDetails.image) {
            userHashMap[Constants.IMAGE] = mProfileImageURL

        }
updateOffer(this,userHashMap)




        // Update the data in the database.
    }
    fun profileUpdateSuccess() {

        activity?.setResult(Activity.RESULT_OK)
        Toast.makeText(activity,"updated", Toast.LENGTH_SHORT).show()
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
                    .with(this)
                    .load(Uri.parse(mSelectedImageFileUri.toString())) // URI of the image
                    .centerCrop() // Scale type of the image.
                    .placeholder(R.drawable.profile) // A default place holder
                    .into(ivOffer) // the view in which the image will be loaded.
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun setUserDataInUI(user: User) {
        // Initialize the user details variable
        mUserDetails = user
        firstName = user.firstName
        getOffer()
    }
    fun getOffer(){
        val db = Firebase.firestore
        val docRef =db.collection("offer").document(firstName)
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val offer = document.toObject(SuperMArket::class.java)

                Glide
                    .with(this)
                    .load(offer!!.Image)
                    //.centerCrop()
                    //.placeholder(R.drawable.loading_spinner)
                    .into(ivOffer)
            }
        }
    }
    fun updateOffer(activity: Fragment, userHashMap: HashMap<String, Any>) {
        Firebase.firestore.collection("offer") // Collection Name
            .document(firstName) // Document ID
            .update(userHashMap) // A hashmap of fields which are to be updated.
            .addOnSuccessListener {
                // Profile data is updated successfully.
                Log.e(activity.javaClass.simpleName, "Profile Data updated successfully!")

                //Toast.makeText(activity, "Profile updated successfully!", Toast.LENGTH_SHORT).show()

                // Notify the success result.
                when(activity){
                    is ProfileFragment ->
                    {activity.profileUpdateSuccess()}
                    is OfferFragment ->
                    {activity.profileUpdateSuccess()}
                }


            }
            .addOnFailureListener { e ->

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while creating a board.",
                    e
                )
            }
    }
    }
