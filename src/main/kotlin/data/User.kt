package data

import kotlinx.serialization.Serializable

@Serializable
open class User (
        open val name: String,
        val password: String,
        val email: String? = null,
        open var id: Int= -1

)