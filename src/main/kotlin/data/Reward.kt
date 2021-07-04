package data

import kotlinx.serialization.Serializable

@Serializable
class Reward(
    val experiencePoints: Int,
    val coins: Int,
    val items: List<Item>?
)