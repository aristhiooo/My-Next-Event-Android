package com.aristiyo.mynextevent.helpers

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object HelperFunction {

    fun dateFormatterSimple(dateMilis: Long): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = dateMilis
        val format = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return format.format(calendar.time)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateDay(dateString: String?): String? {
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val dateFormatterNew: DateTimeFormatter = DateTimeFormatter.ofPattern("dd")
        val ldateTime = LocalDate.parse(dateString, dateFormatter)
        return dateFormatterNew.format(ldateTime)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateMonth(dateString: String?): String {
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val dateFormatterNew: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM")
        val ldateTime = LocalDate.parse(dateString, dateFormatter)
        return dateFormatterNew.format(ldateTime)
    }
}