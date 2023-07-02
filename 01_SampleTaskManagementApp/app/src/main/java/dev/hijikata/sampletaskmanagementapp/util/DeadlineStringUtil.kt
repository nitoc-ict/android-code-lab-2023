package dev.hijikata.sampletaskmanagementapp.util

import java.time.DayOfWeek
import java.time.ZonedDateTime

fun ZonedDateTime.toDeadLineString(): String {
    val now = ZonedDateTime.now()
    val day = when {
        now.dayOfMonth == this.dayOfMonth -> "今日"

        now.dayOfYear + 1 == this.dayOfYear -> "明日"

        now.dayOfYear + 6 >= this.dayOfYear -> when (this.dayOfWeek) {
            DayOfWeek.MONDAY -> "月曜日"
            DayOfWeek.TUESDAY -> "火曜日"
            DayOfWeek.WEDNESDAY -> "水曜日"
            DayOfWeek.THURSDAY -> "木曜日"
            DayOfWeek.FRIDAY -> "金曜日"
            DayOfWeek.SATURDAY -> "土曜日"
            DayOfWeek.SUNDAY -> "日曜日"
        }

        else -> "${this.monthValue}月${dayOfMonth}日"
    }
    val time = when {
        this.hour == 23 && this.minute == 59 -> {
            ""
        }

        else -> {
            " ${this.hour}:${this.minute}"
        }
    }

    return day + time
}