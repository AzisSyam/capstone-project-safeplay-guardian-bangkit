package com.example.safeplayguardian.data.repository

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.safeplayguardian.R
import com.example.safeplayguardian.data.pref.LoginResult
import com.example.safeplayguardian.data.pref.UserPreferences
import com.example.safeplayguardian.remote.response.UserResponse
import com.example.safeplayguardian.ui.signup.SignUpActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import java.io.ByteArrayOutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserRepository(
   private val userPreferences: UserPreferences,
   private val firebaseAuth: FirebaseAuth,
   private val context: android.content.Context
) {
   private val db = Firebase.firestore
   fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
      return firebaseAuth.signInWithEmailAndPassword(email, password)
   }

   suspend fun saveSession(user: LoginResult) {
      userPreferences.saveSession(user)
   }

   fun getSession(): Flow<LoginResult> {
      return userPreferences.getUserSession()
   }

   fun getUserData(userId: String): MutableLiveData<UserResponse?> {

      val resultLiveData = MutableLiveData<UserResponse?>()

      try {
         val docRef = db.collection("users").document(userId)
         docRef.get()
            .addOnSuccessListener { document ->
               if (document.exists()) {
                  val user = document.toObject(UserResponse::class.java)
                  user?.let {
                     resultLiveData.value = it
                  }
               } else {
                  // Handle case when document does not exist
                  Log.d("UserRepository", "No such document")
               }
            }
            .addOnFailureListener { exception ->
               // Handle failure
               Log.d("UserRepository", "get failed with ", exception)
               resultLiveData.value = null

            }
      } catch (e: Exception) {
         // Handle exception
         Log.d("UserRepository", "Exception: ${e.message}")
         resultLiveData.value = null
      }

      return resultLiveData
   }

   fun performSignup(
      imageUrl: String,
      photoName: String,
      name: String,
      email: String,
      password: String
   ) {
      try {
         val user = firebaseAuth.createUserWithEmailAndPassword(email, password)

         user.addOnCompleteListener {
            if (it.isSuccessful) {
               val firebaseUser = it.result?.user
               if (firebaseUser != null) {
                  val db = Firebase.firestore
                  val user = hashMapOf(
                     "email" to email,
                     "name" to name,
                     "photoUrl" to imageUrl,
                     "fotoName" to photoName
                  )

                  db.collection("users").document(firebaseUser.uid)
                     .set(user)
                     .addOnSuccessListener {
                        Log.d(
                           SignUpActivity.TAG,
                           "DocumentSnapshot successfully written!"
                        )
                     }
                     .addOnFailureListener { e ->
                        Log.d(
                           SignUpActivity.TAG,
                           "Error writing document",
                           e
                        )
                     }

               } else {
                  Log.d(SignUpActivity.TAG, "Firebase user is null")
               }

            } else {
               Log.d("Tombol regis ditekan", "onCreate: ${it.exception}")

            }
         }
      } catch (e: Exception) {
         Log.d("Tombol regis ditekan", "onCreate: ${e.message}")
      }
   }

   suspend fun uploadImageToFirebaseStorage(currentImageUri: Uri?): Pair<String, String> {
      return suspendCoroutine { continuation ->
         if (currentImageUri != null) {
            // Pengguna menyertakan file foto
            val storageReference = FirebaseStorage.getInstance().reference
            val imageRef =
               storageReference.child("profile_images/${System.currentTimeMillis()}.jpg")

            imageRef.putFile(currentImageUri)
               .addOnSuccessListener {
                  // Gambar berhasil diunggah, dapatkan URL gambar
                  imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                     val photoName = imageRef.name
//                     onSuccess.invoke(imageUrl.toString(), photoName)
                     continuation.resume(Pair(imageUrl.toString(), photoName))
                  }
               }
               .addOnFailureListener { exception ->
                  Log.d("upload gambar regis", "$exception")
               }
         } else {
            // Pengguna tidak menyertakan file foto, gunakan gambar dari Drawable
            val storageReference = FirebaseStorage.getInstance().reference
            val drawable = ContextCompat.getDrawable(context, R.drawable.user)
            val bitmap = (drawable as BitmapDrawable).bitmap

            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val imageRef =
               storageReference.child("profile_images/${System.currentTimeMillis()}.jpg")

            imageRef.putBytes(data)
               .addOnSuccessListener {
                  // Gambar dari Drawable berhasil diunggah, dapatkan URL gambar
                  imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                     val photoName = imageRef.name
                     continuation.resume(Pair(imageUrl.toString(), photoName))
                  }
               }
               .addOnFailureListener { exception ->
                  // Gagal mengunggah gambar
                  Log.d("upload gambar regis", "$exception")
               }
         }

      }
   }

   suspend fun logout() {
      userPreferences.logout()
   }
}