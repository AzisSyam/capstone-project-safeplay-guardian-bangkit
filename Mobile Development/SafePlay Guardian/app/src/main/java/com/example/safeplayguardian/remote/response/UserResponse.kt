package com.example.safeplayguardian.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserResponse(
   val name: String? = null,
   val email: String? = null,
   val photoUrl: String? = null
): Parcelable
