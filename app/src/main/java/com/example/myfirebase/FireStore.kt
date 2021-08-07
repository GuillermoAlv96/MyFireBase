package com.example.myfirebase

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

open class FireStore {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore


    fun checkLogIn() {
        val currentUser = auth.currentUser
        if (currentUser != null) {

        }
    }

    fun registry(context: Context, user: User) {

        //Setup
        // Create an instance and create a register a user with email and password.
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {
                // If the registration is successfully done
                if (it.isSuccessful) {
                    // Firebase registered user
                    val firebaseUser: FirebaseUser = it.result!!.user!!

                    Toast.makeText(
                        context, "Authentication succeeded.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    open fun registerUserDatabase(context: Context, user: User) {

        //The "user" is collection name. If the collection is already created then it will not create the same one again.
        db.collection("users")

            //Document Id for users fields. Here the document it is the User ID.
            .document(user.id)

            //Here the userInfo are Field amd SetOption is set to merge. It is in case we want to merge to merge later on instead of replacing the fields.
            .set(user, SetOptions.merge())
            .addOnSuccessListener { documentReference ->
                Log.d("Error", "success")
            }
            .addOnFailureListener { e ->
                Log.d("Error", "error")
            }
    }

    fun login(context: Context, email: String, password: String) {
        //Setup
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Firebase registered user
                    val firebaseUser: FirebaseUser = it.result!!.user!!
                    val id: String = firebaseUser.toString()
                    Toast.makeText(
                        context, "Authentication succeeded.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    /* fun createCollection(collection: String){

         // Add a new document with a generated ID
         db.collection("$collection")
             .add(user)
             .addOnSuccessListener { documentReference ->
                 Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
             }
             .addOnFailureListener { e ->
                 Log.w(TAG, "Error adding document", e)
             }
     }*/

    fun addToCollection(collection: String, user: User) {

        // Add a new document with a generated ID
        db.collection("$collection")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("log", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("log", "Error adding document", e)
            }
    }

    fun readCollectionAll(collection: String) {

        db.collection("$collection")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun editCollection() {
        // Update one field, creating the document if it does not already exist.
        // val data = hashMapOf("capital" to true)

        db.collection("users")
        //.set("", SetOptions.merge())

    }


}