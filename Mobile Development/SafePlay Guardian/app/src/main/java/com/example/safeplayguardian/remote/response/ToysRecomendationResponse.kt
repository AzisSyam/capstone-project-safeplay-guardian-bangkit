package com.example.safeplayguardian.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ToysRecomendationResponse(

   @field:SerializedName("listToy")
   val listToy: List<ListToyItem?>? = null,

   @field:SerializedName("error")
   val error: Boolean? = null,

   @field:SerializedName("message")
   val message: String? = null
)

@Entity
data class ListToyItem(

   @field:SerializedName("photoUrl")
   val photoUrl: String? = null,

   @field:SerializedName("name")
   val name: String? = null,

   @field:SerializedName("description")
   val description: String? = null,

   @PrimaryKey
   @field:SerializedName("id")
   val id: String? = null
)
