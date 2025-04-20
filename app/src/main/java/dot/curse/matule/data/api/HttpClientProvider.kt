package dot.curse.matule.data.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json

object HttpClientProvider {
    // Для работы нужно локально создать файл с переменными IGNORE_URL и IGNORE_KEY содержащих Supabase url и api key соответственно
    private const val BASE_URL = IGNORE_URL
    private const val API_KEY = IGNORE_KEY

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        defaultRequest {
            url(BASE_URL)
            headers.append("apikey", API_KEY)
            headers.append("Authorization", "Bearer $API_KEY")
            headers.append("Content-Type", "application/json")
        }

    }
}