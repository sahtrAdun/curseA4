package dot.curse.matule.data.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json

object HttpClientProvider {
    private const val BASE_URL = ""
    private const val API_KEY = ""

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        defaultRequest {
            url(BASE_URL)
            headers.append("api_key", API_KEY)
            headers.append(HttpHeaders.Authorization, "Bearer $API_KEY")
            headers.append(HttpHeaders.ContentType, "application/json")
        }

    }
}