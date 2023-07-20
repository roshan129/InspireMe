package com.roshanadke.inspireme.common

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.util.Random


fun saveBitmapAsImage(bitmap: Bitmap): Boolean {

    val uniqueString = generateUniqueRandomString(7)
    val filename = "quote_$uniqueString.jpg" // Change this to the desired filename

    return try {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
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