package com.example.safeplayguardian.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.Uri
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.example.safeplayguardian.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val MAXIMAL_SIZE = 1000000 //1 MB
private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())


fun uriToFile(imageUri: Uri, context: Context): File {
   val myFile = createCustomTempFile(context)
   val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
   val outputStream = FileOutputStream(myFile)
   val buffer = ByteArray(1024)
   var length: Int
   while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
   outputStream.close()
   inputStream.close()
   return myFile
}

fun createCustomTempFile(context: Context): File {
   val filesDir = context.externalCacheDir
   return File.createTempFile(timeStamp, ".jpg", filesDir)
}

fun File.reduceFileImage(): File {
   val file = this
   val bitmap = BitmapFactory.decodeFile(file.path).getRotatedBitmap(file)
   var compressQuality = 100
   var streamLength: Int
   do {
      val bmpStream = ByteArrayOutputStream()
      bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
      val bmpPicByteArray = bmpStream.toByteArray()
      streamLength = bmpPicByteArray.size
      compressQuality -= 5
   } while (streamLength > MAXIMAL_SIZE)
   bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
   return file
}

fun Bitmap.getRotatedBitmap(file: File): Bitmap? {
   val orientation = ExifInterface(file).getAttributeInt(
      ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
   )
   return when (orientation) {
      ExifInterface.ORIENTATION_ROTATE_90 -> TransformationUtils.rotateImage(this, 90)
      ExifInterface.ORIENTATION_ROTATE_180 -> TransformationUtils.rotateImage(this, 180)
      ExifInterface.ORIENTATION_ROTATE_270 -> TransformationUtils.rotateImage(this, 270)
      ExifInterface.ORIENTATION_NORMAL -> this
      else -> this
   }
}


fun isNetworkAvailable(context: Context): Boolean {
   val connectivityManager =
      context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
   val networkInfo = connectivityManager.activeNetworkInfo
   return networkInfo != null && networkInfo.isConnected
}

object DialogHelper {
   fun showLoading(isLoading: Boolean, progressBar: ProgressBar) {
      progressBar.visibility = if (isLoading == true) View.VISIBLE else View.GONE
   }

   fun showAlert(
      message: String,
      context: Context,
      onOkClick: (() -> Unit)? = null,
   ) {

      AlertDialog.Builder(context).apply {
         setMessage(message)
         setPositiveButton(context.getString(R.string.yes)) { _, _ ->
            onOkClick?.invoke()
         }

         setNegativeButton(context.getString(R.string.no)) { _, _ ->
//
         }
         create()
         show()
      }
   }

   fun showAlertWithoutNegativeButton(
      message: String,
      context: Context,
      onOkClick: (() -> Unit)? = null,
   ) {
      AlertDialog.Builder(context).apply {
         setMessage(message)
         setPositiveButton(context.getString(R.string.ok)) { _, _ ->
            onOkClick?.invoke()
         }
         create()
         show()
      }
   }
}



