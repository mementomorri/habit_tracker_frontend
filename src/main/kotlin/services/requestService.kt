
package services

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.coroutines.MainScope

val scope = MainScope()

val Request = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}