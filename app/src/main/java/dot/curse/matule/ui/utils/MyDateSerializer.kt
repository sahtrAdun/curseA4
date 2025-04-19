package dot.curse.matule.ui.utils

import android.content.Context
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import dot.curse.matule.R

object MyDateSerializer {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME

    fun serializeDateToString(date: ZonedDateTime): String {
        return date.format(formatter)
    }
    fun deSerializeDateToString(date: String): ZonedDateTime {
        return ZonedDateTime.parse(date, formatter)
    }
    fun String.getMinute(): String {
        val zonedDateTime = deSerializeDateToString(this)
        return zonedDateTime.minute.toString()
    }
    fun String.getHour(): String {
        val zonedDateTime = deSerializeDateToString(this)
        return zonedDateTime.hour.toString()
    }
    fun String.getDay(): String {
        val zonedDateTime = deSerializeDateToString(this)
        return zonedDateTime.dayOfMonth.toString()
    }
    fun String.getMonth(): String {
        val zonedDateTime = deSerializeDateToString(this)
        return zonedDateTime.monthValue.toString()
    }
    fun String.getYear(): String {
        val zonedDateTime = deSerializeDateToString(this)
        return zonedDateTime.year.toString()
    }
    fun String.getFullDate(context: Context): String {
        return "${getDay()}.${getMonth()}.${getYear()} ${context.getString(R.string.date_in)} ${getMinute()}:${getHour()}"
    }
    fun String.getRelativeTimeString(context: Context): String {
        val parsedDate = deSerializeDateToString(this)
        val now = ZonedDateTime.now(parsedDate.zone)
        val duration = Duration.between(parsedDate, now)

        return when {
            duration.toSeconds() < 60 -> "${duration.toSeconds()} ${context.getString(R.string.date_seconds)}"
            duration.toMinutes() < 60 -> "${duration.toMinutes()} ${context.getString(R.string.date_minutes)}"
            duration.toHours() < 12 -> "${duration.toHours()} ${context.getString(R.string.date_hours)}"
            duration.toHours() < 24 -> "${context.getString(R.string.date_today)} ${parsedDate.format(DateTimeFormatter.ofPattern("HH:mm"))}"
            duration.toDays() < 2 -> "${context.getString(R.string.date_yesterday)} ${parsedDate.format(DateTimeFormatter.ofPattern("HH:mm"))}"
            duration.toDays() < 365 -> "${parsedDate.dayOfMonth}.${parsedDate.monthValue} ${context.getString(R.string.date_in)} ${parsedDate.format(DateTimeFormatter.ofPattern("HH:mm"))}"
            else -> getFullDate(context)
        }
    }
}