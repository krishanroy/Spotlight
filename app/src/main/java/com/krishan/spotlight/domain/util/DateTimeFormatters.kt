package com.krishan.spotlight.domain.util

import java.time.Duration
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

// Need to refactor so that we can pass in String resources from xml file and then show times without hardcoding that

fun formatRelativeTimeLocalized(isoString: String, locale: Locale = Locale.getDefault()): String {
    val dateTime = OffsetDateTime.parse(isoString)
    val now = OffsetDateTime.now(ZoneOffset.UTC)
    val duration = Duration.between(dateTime, now)

    return when {
        duration.toMinutes() < 1 -> "Just now"
        duration.toMinutes() < 60 -> {
            val mins = duration.toMinutes()
            "$mins minute${if (mins != 1L) "s" else ""} ago"
        }
        duration.toHours() < 24 -> {
            val hrs = duration.toHours()
            "$hrs hour${if (hrs != 1L) "s" else ""} ago"
        }
        duration.toDays() == 1L -> "Yesterday"
        duration.toDays() < 7 -> {
            val days = duration.toDays()
            "$days day${if (days != 1L) "s" else ""} ago"
        }
        else -> dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy", locale))
    }
}