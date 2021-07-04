package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Character (
        val name: String,
        val characterClass: String,
        var id: Int=-1,
        @SerialName("maximumHP")
        var maximumHP_:Int? =null,
        @SerialName("healthPoints")
        var healthPoints_: Int? = null,
        var energyPoints: Int = 100,
        var experiencePoints: Int = 0,
        var coins: Int = 0,
        var level: Int = 1,
        var habits: List<Task>,
        var dailies: List<Task>,
        var toDos: List<Task>,
        var quests: List<Task>,
        var inventory: List<Item>
)
