package dot.curse.matule.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckOtpResult(
    @SerialName("email") val email: String,
    @SerialName("token") val token: String,
    @SerialName("type") val type: String = "recovery",
)
