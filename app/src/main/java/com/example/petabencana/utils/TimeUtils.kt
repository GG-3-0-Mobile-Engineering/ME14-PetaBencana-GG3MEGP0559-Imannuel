package com.example.petabencana.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object TimeUtils {

    fun getTimeAgo(dateTimeString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ROOT)
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = inputFormat.parse(dateTimeString)
        if (date == null) {
            Log.e("getTimeAgo", "Invalid date-time format: $dateTimeString")
            return ""
        }

        val now = Date()
        val diffInMillis = now.time - date.time
        val diffInSeconds = diffInMillis / 1000

        return when {
            diffInSeconds < 60 -> "$diffInSeconds seconds ago"
            diffInSeconds < 3600 -> "${diffInSeconds / 60} minutes ago"
            diffInSeconds < 86400 -> "${diffInSeconds / 3600} hours ago"
            else -> "${diffInSeconds / 86400} days ago"
        }
    }

}