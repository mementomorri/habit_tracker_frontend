package data

import kotlinx.serialization.Serializable

@Serializable
open class Item(
        open var quantity: Int,
        val name: String,
        val description: String,
        val price: Int,
        open val id: Int= -1
)