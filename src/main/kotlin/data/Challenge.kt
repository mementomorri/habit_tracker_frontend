package data

import kotlinx.serialization.Serializable

@Serializable
open class Challenge (
    val name: String,
    val description: String
)