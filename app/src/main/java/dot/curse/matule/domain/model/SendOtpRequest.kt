package dot.curse.matule.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendOtpRequest(
    @SerialName("email") val email: String,
    @SerialName("type") val type: String = "recovery",
)
