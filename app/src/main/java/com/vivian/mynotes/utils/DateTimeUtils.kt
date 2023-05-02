package com.vivian.mynotes.utils

import android.annotation.SuppressLint
import com.vivian.mynotes.utils.Constants.DATE_FORMAT
import java.text.SimpleDateFormat

object DateTimeUtils {

    /**
     * convert date value to milliseconds
     * */
    @SuppressLint("SimpleDateFormat")
    fun String.toTime(): Long? {
        val format = SimpleDateFormat(DATE_FORMAT)
        return format.parse(this)?.time
    }
}