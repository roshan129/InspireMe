package com.roshanadke.inspireme.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ShareCompat
import java.io.File
import java.io.FileOutputStream
import java.util.Random


fun saveBitmapAsImage(bitmap: Bitmap): Boolean {

    val uniqueString = generateUniqueRandomString(7)
    val filename = "quote_$uniqueString.jpg" // Change this to the desired filename

    return try {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            filename
        )
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun generateUniqueRandomString(length: Int): String {
    val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    val random = Random()

    val timestamp = System.currentTimeMillis()
    val randomString = (1..length)
        .map { random.nextInt(charPool.size) }
        .map(charPool::get)
        .joinToString("")

    return "$timestamp$randomString"
}

fun shareBitmap(context: Context, bitmap: Bitmap, title: String? = null) {
    val bitmapDrawable = BitmapDrawable(context.resources, bitmap)
    val bitmapPath = MediaStore.Images.Media.insertImage(
        context.contentResolver,
        bitmapDrawable.bitmap,
        "Quote",
        null
    )
    val bitmapUri = Uri.parse(bitmapPath)

    val shareIntent = ShareCompat.IntentBuilder.from(context as Activity)
        .setType("image/jpeg")
        .setStream(bitmapUri)
        /*.setText(title) // You can include a title or message with the shared image*/
        .createChooserIntent()
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    context.startActivity(shareIntent)
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}