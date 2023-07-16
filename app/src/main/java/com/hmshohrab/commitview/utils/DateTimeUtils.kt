package com.hmshohrab.commitview.utils

import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object TimeUtils {
    fun formatDateFromString(
        timestamp: String,
        inputFormat: String,
        outputFormat: String
    ): String {
        val inputDateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
        val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
        try {
            val date: Date = inputDateFormat.parse(timestamp)
          //  var time= outputDateFormat.format(date)
           return getTimeAgo(date.time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }
    fun getTimeAgo(timestamp: Long): String {
        val currentTime = System.currentTimeMillis()
        val timeDifference = currentTime - timestamp
        return DateUtils.getRelativeTimeSpanString(
            timestamp,
            currentTime,
            DateUtils.MINUTE_IN_MILLIS
        ).toString()
    }
}