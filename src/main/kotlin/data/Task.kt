package data

import kotlinx.serialization.Serializable

@Serializable
open class Task (
        open var name: String,
        open var description: String,
        open var difficulty: String = "MEDIUM",
        val type: String,
        open val characterId: Int,
        open var deadline: Date?,
        open val startDate: Date?,
        open var completionCount: Int?= 0,
)