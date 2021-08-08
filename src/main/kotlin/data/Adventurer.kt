package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Adventurer (
        val name: String,
        val adventurerClass: String,
        var id: Int=-1,
        @SerialName("maximumHP")
        var maximumHP_:Int? =null,
        @SerialName("healthPoints")
        var healthPoints_: Int? = null,
        var energyPoints: Int = 100,
        var experiencePoints: Int = 0,
        var coins: Int = 0,
        var level: Int = 1
)
