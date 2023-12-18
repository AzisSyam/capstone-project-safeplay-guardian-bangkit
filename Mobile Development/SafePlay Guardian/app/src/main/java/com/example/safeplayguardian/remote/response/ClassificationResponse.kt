package com.example.safeplayguardian.remote.response

import com.google.gson.annotations.SerializedName

data class ClassificationResponse(

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Result(

	@field:SerializedName("toy_name")
	val toyName: String? = null,

	@field:SerializedName("description")
	val description: String? = null
)
