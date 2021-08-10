package com.example.myfirebase.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.myfirebase.Constants
import com.example.myfirebase.models.User
import com.example.myfirebase.ui.LoginFragment
import com.example.myfirebase.ui.UserHomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class FireStore {

    private val db = Firebase.firestore

    private val usersCollectionRef = Firebase.firestore.collection(Constants.USERS)


    interface LoginListener {

        fun loginSuccess()
    }

    interface RegisterListener {

        fun registerSuccess()
    }

    interface UserInfoSuccess {

        fun userInfoComplete(name: String, email: String, phone: Int)
    }


    fun registry(listener: RegisterListener, context: Context, user: User) {

        //Setup
        // Create an instance and create a register a user with email and password.
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {
                // If the registration is successfully done
                if (it.isSuccessful) {
                    // Firebase registered user
                    val firebaseUser: FirebaseUser = it.result!!.user!!
                    val userComplete = User(
                        firebaseUser.uid,
                        user.name,
                        user.email,
                        user.phone,
                        Constants.COMPLETE_PROFILE,
                        user.password
                    )
                    registerUserDatabase(context, userComplete)
                    Toast.makeText(
                        context, "Authentication succeeded.",
                        Toast.LENGTH_SHORT
                    ).show()
                    listener.registerSuccess()
                } else {
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun registerUserDatabase(context: Context, user: User) {

        //The "user" is collection name. If the collection is already created then it will not create the same one again.
        usersCollectionRef

            //Document Id for users fields. Here the document it is the User ID.
            .document(user.id)

            //Here the userInfo are Field amd SetOption is set to merge. It is in case we want to merge to merge later on instead of replacing the fields.
            .set(user, SetOptions.merge())
            .addOnSuccessListener { documentReference ->
                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Couldn't register User", Toast.LENGTH_SHORT).show()
            }
    }

    fun login(listener: LoginListener, context: Context, email: String, password: String) {
        //Setup

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        context, "Authentication succeeded.",
                        Toast.LENGTH_SHORT
                    ).show()
                    listener.loginSuccess()

                } else {
                    Toast.makeText(
                        context, "Wrong E-mail or Password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    /**
     * A function to get the user id of current logged user.
     */

    fun getCurrentUserID(): String {
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }


    /**
     * A function to get the logged user details from from FireStore Database.
     */

    fun getCurrentUserDetails(listener: UserInfoSuccess) {

        // Here we pass the collection name from which we want the data.
        usersCollectionRef
            // The document id to get the Fields of user.
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    // Here we have received the document snapshot which is converted into the User Data model object

                    val name = it.getString("name") ?: return@addOnSuccessListener
                    val email = it.getString("email") ?: return@addOnSuccessListener
                    val phone = it.getLong("phone") ?: return@addOnSuccessListener
                    listener.userInfoComplete(name, email, phone.toInt())

                } else {

                    Log.d("Error", "No such document")
                }
            }


    }


    fun addToCollection(collection: String, user: User) {

        // Add a new document with a generated ID
        db.collection(collection)
            .add(user)
            .addOnSuccessListener {

            }
            .addOnFailureListener { e ->
                Log.w("log", "Error adding document", e)
            }
    }

    fun readCollectionAll(collection: String) {

        db.collection(collection)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }

    fun editCollection() {
        // Update one field, creating the document if it does not already exist.
        // val data = hashMapOf("capital" to true)

        db.collection(Constants.USERS)
        //.set("", SetOptions.merge())

    }


}