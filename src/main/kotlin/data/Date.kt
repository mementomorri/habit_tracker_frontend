package data

import kotlinx.serialization.Serializable

@Serializable
class Date(
    val year: Int,
    val month: String,
    val monthValue: Int,
    val dayOfMonth: Int,
    val era: String,
    val dayOfYear: Int,
    val dayOfWeek: String,
    val leapYear: Boolean
)