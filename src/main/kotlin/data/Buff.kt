package data

import kotlinx.serialization.Serializable

@Serializable
open class Buff(
        val name:String,
        val duration: Date,
        val character_id: Int
)