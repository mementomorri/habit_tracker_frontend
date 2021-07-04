package data

import kotlinx.serialization.Serializable

@Serializable
open class Ability(
    val name: String,
    val characterClass: String,
    val description: String,
    val energyRequired: Int,
    val levelRequired: Int,
    val id: Int = -1
){
    override fun toString(): String {
        return "name=${name}, characterClass=${characterClass}, energyRequired=${energyRequired}, levelRequired=${levelRequired}, id=${id}"
    }
}