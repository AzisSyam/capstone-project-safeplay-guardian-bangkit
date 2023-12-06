package com.example.safeplayguardian.utils

import android.util.Log
import com.example.safeplayguardian.remote.response.UserResponse
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseManager {
   companion object {
      private const val TAG = "FirebaseManager"

      fun getUserData(userId: String, onSuccess: (UserResponse) -> Unit, onFailure: (Exception) -> Unit) {
         try {
            val db = Firebase.firestore
            val docRef = db.collection("users").document(userId)
            docRef.get()
               .addOnSuccessListener { document ->
                  if (document.exists()) {
                     val user = document.toObject(UserResponse::class.java)
                     if (user != null) {
                        onSuccess.invoke(user)
                     }
                  } else {
                     Log.d(TAG, "No such document")
                  }
               }
               .addOnFailureListener { exception ->
                  onFailure.invoke(exception)
                  Log.d(TAG, "get failed with ", exception)
               }
         } catch (e: Exception) {
            onFailure.invoke(e)
            Log.d(TAG, "Exception: ${e.message}")
         }
      }
   }
}