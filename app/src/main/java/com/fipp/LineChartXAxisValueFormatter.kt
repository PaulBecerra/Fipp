package com.fipp

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


class LineChartXAxisValueFormatter : IndexAxisValueFormatter() {
    override fun getFormattedValue(value: Float): String {

        // Convert float value to date string
        // Convert from seconds back to milliseconds to format time  to show to the user
        val emissionsMilliSince1970Time = value.toLong() * 1000

        // Show time in local version
        val timeMilliseconds = Date(emissionsMilliSince1970Time)
        val formattedDate = SimpleDateFormat("dd", Locale.getDefault())
//        val dateTimeFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.getDefault())
        return formattedDate.format(timeMilliseconds)
    }
}