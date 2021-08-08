package com.example.simplibuy.classes

import android.util.Log
import androidx.fragment.app.Fragment
import com.example.simplibuy.authentication.LoginFragment
import com.example.simplibuy.fragment.MainFragment
import com.example.simplibuy.fragment.ProfileFragment
import com.example.simplibuy.authentication.RegisterFragment
import com.example.simplibuy.seller.SellerMainFragment
import com.example.simplibuy.model.User
import com.example.simplibuy.onlineCart.SellerProfileFragment
import com.example.simplibuy.others.Constants
import com.example.simplibuy.seller.OfferFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

/**
 * A custom class where we will add the operation performed for the firestore database.
 */
class Firebase {

    // Create a instance of Firebase Firestore
    private val mFireStore = FirebaseFirestore.getInstance()

    /**
     * A function to make an entry of the registered user in the firestore database.
     */


    fun registerUser(activity: Fragment, userInfo: User) {

        mFireStore.collection(Constants.USERS)
            // Document ID for users fields. Here the document it is the User ID.
            .document(getCurrentUserID())
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                //activity.userRegisteredSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error writing document",
                    e
                )
            }
    }

    fun registerSuperMarket(activity: Fragment, userInfo: SuperMArket) {

        mFireStore.collection("SuperMarket")
            // Document ID for users fields. Here the document it is the User ID.
            .document(getCurrentUserID())
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                //activity.userRegisteredSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error writing document",
                    e
                )
            }
    }



    // TODO (Step 1: Create a function to SignIn using firebase and get the user details from Firestore Database.)
    // START
    /**
     * A function to SignIn using firebase and get the user details from Firestore Database.
     */
    fun signInUser(activity: Fragment) {

        // Here we pass the collection name from which we wants the data.
        mFireStore.collection(Constants.USERS)
            // The document id to get the Fields of user.
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.e(
                    activity.javaClass.simpleName, document.toString()
                )

                // TODO (STEP 3: Pass the result to base activity.)
                // START
                // Here we have received the document snapshot which is converted into the User Data model object.
                val loggedInUser = document.toObject(User::class.java)!!

                // Here call a function of base activity for transferring the result to it.
                when (activity) {
                    is  MainFragment -> {
                        activity.signInSuccess(loggedInUser)
                    }
                    is ProfileFragment -> {
                        activity.setUserDataInUI(loggedInUser)
                    }
                    is SellerProfileFragment -> {
                        activity.setUserDataInUI(loggedInUser)
                    }
                    is SellerMainFragment ->{
                        activity.setUserDataInUI(loggedInUser)
                    }
                    is OfferFragment->{
                        activity.setUserDataInUI(loggedInUser)
                    }
                    is LoginFragment ->{
                        activity.setIntent(loggedInUser)
                    }
                    else -> {
                    }
                }

            }
            .addOnFailureListener { e ->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting loggedIn user details",
                    e
                )
            }
    }
    // END

    /**
     * A function for getting the user id of current logged user.
     */
    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId

    }
    fun updateUserProfileData(activity: Fragment, userHashMap: HashMap<String, Any>) {
        mFireStore.collection(Constants.USERS) // Collection Name
            .document(getCurrentUserID()) // Document ID
            .update(userHashMap) // A hashmap of fields which are to be updated.
            .addOnSuccessListener {
                // Profile data is updated successfully.
                Log.e(activity.javaClass.simpleName, "Profile Data updated successfully!")

                //Toast.makeText(activity, "Profile updated successfully!", Toast.LENGTH_SHORT).show()

                // Notify the success result.
                when(activity){
                    is SellerProfileFragment ->
                    {activity.profileUpdateSuccess()}
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
    fun updateSellerProfile(activity: Fragment, userHashMap: HashMap<String, Any>) {
        mFireStore.collection("SuperMarket") // Collection Name
            .document(getCurrentUserID()) // Document ID
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


    /*fun signIn(activity: SettingFragment) {

        // Here we pass the collection name from which we wants the data.
        mFireStore.collection(Constants.USERS)
            // The document id to get the Fields of user.
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                // TODO (STEP 3: Pass the result to base activity.)
                // START
                // Here we have received the document snapshot which is converted into the User Data model object.
                val loggedInUser = document.toObject(User::class.java)!!

                // Here call a function of base activity for transferring the result to it.


                activity.setUserDataInUI(loggedInUser)

                // END
            }

    }*/

    /*fun updateUserProfileData(activity: SettingFragment, userHashMap: HashMap<String, Any>) {
        mFireStore.collection(Constants.USERS) // Collection Name
            .document(getCurrentUserID()) // Document ID
            .update(userHashMap) // A hashmap of fields which are to be updated.
            .addOnSuccessListener {
                // Profile data is updated successfully.
                Log.e(activity.javaClass.simpleName, "Profile Data updated successfully!")

//                Toast.makeText(activity, "Profile updated successfully!", Toast.LENGTH_SHORT).show()

                // Notify the success result.
//                activity.profileUpdateSuccess()
            }
            .addOnFailureListener { e ->
//                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while creating a board.",
                    e
                )
            }
    }*/
    /*fun firestore(data: Tokens){
        mFireStore.collection("Tokens")
            // Document ID for users fields. Here the document it is the User ID.
            .document(getCurrentUserID())
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(data, SetOptions.merge())
    }*/
}