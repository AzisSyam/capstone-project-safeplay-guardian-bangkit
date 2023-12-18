package com.example.safeplayguardian.data.pref

data class LoginResult(
   val userId: String? = null,
   val userEmail: String? = null,
   val success: Boolean = false,
   val error: String? = null
)
