package data

import kotlinx.serialization.Serializable

@Serializable
class Objective(
        val id: Int,
        var name: String,
        var description: String,
        val type: String,
        val adventurerId: Int,
        var difficulty: String = "MEDIUM",
        var deadline: String?= null,
        val startDate: String?= null,
        var completionCount: Int?= 0,
){
        override fun toString(): String {
                return Objective.serializer().toString()
        }
}