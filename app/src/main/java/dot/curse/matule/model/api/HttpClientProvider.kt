package dot.curse.matule.model.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.*

object HttpClientProvider {
    private const val SUPABASE_URL = "https://your-supabase-url.supabase.co"
    private const val SUPABASE_API_KEY = "your-supabase-api-key"

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        defaultRequest {
            url("$SUPABASE_URL/rest/v1")
            headers.append("apikey", SUPABASE_API_KEY)
            headers.append("Authorization", "Bearer $SUPABASE_API_KEY")
            headers.append("Content-Type", "application/json")
        }
    }
}