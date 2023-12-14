package com.example.safeplayguardian.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog

fun isNetworkAvailable(context: Context): Boolean {
   val connectivityManager =
      context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
   val networkInfo = connectivityManager.activeNetworkInfo
   return networkInfo != null && networkInfo.isConnected
}

fun showLoading(isLoading: Boolean) {

}

object DialogHelper {
   fun showLoading(isLoading: Boolean, progressBar: ProgressBar) {
      progressBar.visibility = if (isLoading == true) View.VISIBLE else View.GONE
   }

   fun showAlert(
      message: String,
      context: Context,
      onOkClick: (() -> Unit)? = null
   ) {
//      val title = if (isSuccess) "Success" else "Error"
//      val icon = if (isSuccess) android.R.drawable.ic_dialog_info else android.R.drawable.ic_dialog_alert

//      MaterialAlertDialogBuilder(context)
////         .setTitle(title)
//         .setMessage(message)
////         .setIcon(icon)
//         .setPositiveButton("Ok") { _, _ ->
//            // Handle button click if needed
//            onOkClick?.invoke()
//         }
//         .setNegativeButton("Tidak"){_,_->
//
//         }
//         .create()
//         .show()

      AlertDialog.Builder(context).apply {
//         val title = getString(R.string.alert_title)
//         setTitle(title)
         setMessage(message)
         setPositiveButton("OK") { _, _ ->
            onOkClick?.invoke()
         }

         setNegativeButton("Tidak") { _, _ ->
//
         }
         create()
         show()
      }
   }
}

