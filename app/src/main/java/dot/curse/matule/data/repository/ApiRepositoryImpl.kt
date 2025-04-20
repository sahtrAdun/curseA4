package dot.curse.matule.data.repository

import android.content.Context
import android.net.Uri
import dot.curse.matule.data.api.IGNORE_URL
import dot.curse.matule.domain.model.CheckOtpResult
import dot.curse.matule.domain.model.SendOtpRequest
import dot.curse.matule.domain.model.notification.Notification
import dot.curse.matule.domain.model.notification.NotificationDot
import dot.curse.matule.domain.model.notification.toNotificationPost
import dot.curse.matule.domain.model.order.Order
import dot.curse.matule.domain.model.order.OrderDot
import dot.curse.matule.domain.model.order.toOrderPost
import dot.curse.matule.domain.model.shoe.ShoeDot
import dot.curse.matule.domain.model.user.User
import dot.curse.matule.domain.model.user.UserDot
import dot.curse.matule.domain.model.user.UserPost
import dot.curse.matule.domain.model.user.toUserPost
import dot.curse.matule.domain.repository.ApiRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import java.io.File
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val client: HttpClient
): ApiRepository {

    companion object {
        const val COLLECTION_USERS = "rest/v1/users"
        const val COLLECTION_USERS_FAV = "rest/v1/users_fav"
        const val COLLECTION_USERS_CART = "rest/v1/users_cart"
        const val COLLECTION_SHOES = "rest/v1/shoes"
        const val COLLECTION_NOTIFICATIONS = "rest/v1/notifications"
        const val COLLECTION_ORDERS = "rest/v1/orders"
        const val STORAGE_AVATARS = "storage/v1/object/avatars"
    }

    override suspend fun getAllUsers(): Result<List<UserDot>> {
        return try {
            client.get("/$COLLECTION_USERS") {
                parameter("select", "*")
            }.body<List<UserDot>>().let { body ->
                Result.success(body)
            }
        } catch (e: Exception) {
            println("Exception getAllUsers():\n${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun getUserById(userId: Int): Result<UserDot> {
        return try {
            client.get("/$COLLECTION_USERS") {
                parameter("id", "eq.$userId")
            }.body<List<UserDot>>().let { body ->
                Result.success(body.first())
            }
        } catch (e: Exception) {
            println("Exception getUserById($userId):\n${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun addUser(user: UserPost): Result<Boolean> {
        return try {
            val response = client.post("/$COLLECTION_USERS") {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                setBody(user)
            }
            if (response.status == HttpStatusCode.Created) {
                Result.success(true)
            } else {
                Result.failure(Exception("HTTP error: ${response.status}"))
            }
        } catch (e: Exception) {
            println("Exception addUser($user):\n${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun updateUser(user: User): Result<Boolean> {
        return try {
            val response = client.patch("/$COLLECTION_USERS") {
                parameter("id", "eq.${user.id}")
                setBody(user.toUserPost())
            }
            if (response.status == HttpStatusCode.NoContent) {
                Result.success(response.bodyAsText().contains("error", true) == false)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            println("Exception updateUser($user):\n${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun updateUserByEmail(user: UserPost): Result<Boolean> {
        return try {
            val response = client.patch("/$COLLECTION_USERS") {
                parameter("email", "eq.${user.email}")
                setBody(user)
            }
            if (response.status == HttpStatusCode.NoContent) {
                Result.success(true)
            } else {
                Result.failure(Exception("HTTP error: ${response.status}"))
            }
        } catch (e: Exception) {
            println("Exception updateUserByEmail($user):\n${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun getUserByEmail(email: String): Result<UserDot> {
        return try {
            val response = client.get("/$COLLECTION_USERS") {
                parameter("email", "eq.$email")
            }
            if (response.status == HttpStatusCode.OK) {
                val body = response.body<List<UserDot>>()
                if (body.isNotEmpty()) {
                    Result.success(body.first())
                } else {
                    Result.failure(NoSuchElementException("User with email $email not found"))
                }
            } else {
                Result.failure(Exception("HTTP error: ${response.status}"))
            }
        } catch (e: Exception) {
            println("Exception getUserByEmail($email):\n${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun checkUserExists(email: String, password: String): Result<UserDot> {
        return try {
            client.get("/$COLLECTION_USERS") {
                parameter("email", "eq.$email")
                parameter("password", "eq.$password")
            }.body<List<UserDot>>().let { body ->
                Result.success(body.first())
            }
        } catch (e: Exception) {
            println("Exception checkUserExists($email, $password):\n${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun sendOtp(email: String): Result<Boolean> {
        return try {
            val response = client.post("/auth/v1/otp") {
                contentType(ContentType.Application.Json)
                setBody(SendOtpRequest(
                    email = email,
                    type = "email"
                ))
            }
            Result.success(response.status == HttpStatusCode.OK)
        } catch (e: Exception) {
            println("Error sending OTP: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun checkOtp(email: String, otp: String): Result<Boolean> {
        return try {
            val response = client.post("/auth/v1/verify") {
                setBody(CheckOtpResult(
                    email = email,
                    token = otp,
                    type = "email"
                ))
            }
            if (response.status == HttpStatusCode.OK) {
                val responseBody = response.bodyAsText()
                return if (responseBody.contains("403") || responseBody.contains("error", ignoreCase = true)) {
                    Result.success(false)
                } else {
                    Result.success(true)
                }
            } else {
                println("Error: ${response.status}")
                return Result.success(false)
            }
        } catch (e: Exception) {
            println("Error sending OTP: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun uploadAvatar(context: Context, user: User, uri: Uri): Result<Boolean> {
        return try {
            val name = "avatar_${user.id}_${System.currentTimeMillis()}.jpg"

            val inputStream = context.contentResolver.openInputStream(uri)
            val tempFile = File.createTempFile("temp", null, context.cacheDir)
            inputStream?.copyTo(tempFile.outputStream())
            inputStream?.close()

            val response = client.post("/$STORAGE_AVATARS/$name") {
                contentType(ContentType.Application.OctetStream)
                setBody(tempFile.readBytes())
            }

            if (response.status.value in 200..299) {
                val update = updateUser(user.copy(
                    avatar = "$IGNORE_URL/$STORAGE_AVATARS/$name"
                ))
                Result.success(update.getOrElse { false } == true)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            println("Exception uploadAvatar(user = $user, fileUri = $uri): ${e.message} --- ${e.cause}")
            Result.failure(e)
        }
    }

    override suspend fun getShoeById(id: Int): Result<ShoeDot> {
        return try {
            val response = client.get("/$COLLECTION_SHOES") {
                parameter("id", "eq.$id")
            }
            if (response.status == HttpStatusCode.OK) {
                val body = response.body<List<ShoeDot>>()
                if (body.isNotEmpty()) {
                    Result.success(body.first())
                } else {
                    Result.failure(NoSuchElementException("Error"))
                }
            } else {
                Result.failure(Exception("HTTP error: ${response.status}"))
            }
        } catch (e: Exception) {
            println("Exception getShoeById($id):\n${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun getUserFavorites(userId: Int): Result<List<ShoeDot>> {
        return try {
            val response = client.post("/rest/v1/rpc/get_user_favorites") {
                setBody(mapOf("user_id" to userId))
            }
            println("Response getUserFavorites($userId) --- ${response.bodyAsText()}")
            if (response.status == HttpStatusCode.OK) {
                Result.success(response.body<List<ShoeDot>>())
            } else {
                Result.success(emptyList<ShoeDot>())
            }
        } catch (e: Exception) {
            println("Exception getUserFavorites($userId) --- ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun addShoeToUserFavorites(userId: Int, shoeId: Int): Result<Boolean> {
        return try {
            val response = client.post("/$COLLECTION_USERS_FAV") {
                setBody(mapOf(
                    "user_id" to userId,
                    "shoe_id" to shoeId
                ))
            }
            println("Response addShoeToUserFavorites($userId, $shoeId) --- ${response.bodyAsText()}")
            if (response.status == HttpStatusCode.Created) {
                Result.success(
                    response.bodyAsText().contains("error", ignoreCase = true) == false
                )
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            println("Exception addShoeToUserFavorites($userId, $shoeId) --- ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun deleteShoeFromUserFavorites(userId: Int, shoeId: Int): Result<Boolean> {
        return try {
            val response = client.delete("/$COLLECTION_USERS_FAV") {
                parameter("user_id", "eq.$userId")
                parameter("shoe_id", "eq.$shoeId")
            }
            println("Response deleteShoeFromUserFavorites($userId, $shoeId) --- ${response.bodyAsText()}")
            if (response.status == HttpStatusCode.NoContent || response.status == HttpStatusCode.OK) {
                Result.success(
                    response.bodyAsText().contains("error", ignoreCase = true) == false
                )
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            println("Exception deleteShoeToUserFavorites($userId, $shoeId) --- ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun getUserCart(userId: Int): Result<List<ShoeDot>> {
        return try {
            val response = client.post("/rest/v1/rpc/get_user_cart") {
                setBody(mapOf("user_id" to userId))
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(response.body<List<ShoeDot>>())
            } else {
                Result.success(emptyList<ShoeDot>())
            }
        } catch (e: Exception) {
            println("Exception getUserCart($userId) --- ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun addShoeToUserCart(userId: Int, shoeId: Int): Result<Boolean> {
        return try {
            val response = client.post("/$COLLECTION_USERS_CART") {
                setBody(mapOf(
                    "user_id" to userId,
                    "shoe_id" to shoeId
                ))
            }
            if (response.status == HttpStatusCode.Created) {
                Result.success(
                    response.bodyAsText().contains("error", ignoreCase = true) == false
                )
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            println("Exception addShoeToUserCart($userId, $shoeId) --- ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun deleteShoeFromUserCart(userId: Int, shoeId: Int): Result<Boolean> {
        return try {
            val response = client.delete("/$COLLECTION_USERS_CART") {
                parameter("user_id", "eq.$userId")
                parameter("shoe_id", "eq.$shoeId")
            }
            if (response.status == HttpStatusCode.NoContent || response.status == HttpStatusCode.OK) {
                Result.success(
                    response.bodyAsText().contains("error", ignoreCase = true) == false
                )
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            println("Exception deleteShoeToUserCart($userId, $shoeId) --- ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun clearUserCart(userId: Int): Result<Boolean> {
        return try {
            val response = client.delete("/$COLLECTION_USERS_CART") {
                parameter("user_id", "eq.$userId")
            }
            if (response.status == HttpStatusCode.NoContent || response.status == HttpStatusCode.OK) {
                Result.success(
                    response.bodyAsText().contains("error", ignoreCase = true) == false
                )
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            println("Exception clearUserCart($userId) --- ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun getAllShoe(): Result<List<ShoeDot>> {
        return try {
            val response = client.get("/$COLLECTION_SHOES") {
                parameter("select", "*")
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(response.body<List<ShoeDot>>())
            } else {
                Result.success(emptyList<ShoeDot>())
            }
        } catch (e: Exception) {
            println("Exception getAllShoe() --- ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun getShoesByTag(tag: String): Result<List<ShoeDot>> {
        return try {
            val response = client.get("/$COLLECTION_SHOES") {
                parameter("tag", "eq.$tag")
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(response.body<List<ShoeDot>>())
            } else {
                Result.success(emptyList<ShoeDot>())
            }
        } catch (e: Exception) {
            println("Exception getShoesByTag($tag) --- ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun getShoesByCategory(category: String): Result<List<ShoeDot>> {
        return try {
            val response = client.get("/$COLLECTION_SHOES") {
                parameter("category", "eq.$category")
            }
            println("Response getShoesByCategory($category) --- ${response.bodyAsText()}")
            if (response.status == HttpStatusCode.OK) {
                Result.success(response.body<List<ShoeDot>>())
            } else {
                Result.success(emptyList<ShoeDot>())
            }
        } catch (e: Exception) {
            println("Exception getShoesByCategory($category) --- ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun getNotificationByUserId(id: Int): Result<List<NotificationDot>> {
        return try {
            val response = client.get("/$COLLECTION_NOTIFICATIONS") {
                parameter("user_id", "eq.$id")
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(response.body<List<NotificationDot>>())
            } else {
                Result.success(emptyList<NotificationDot>())
            }
        } catch (e: Exception) {
            println("Exception getNotificationByUserId($id) --- ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun sendNotificationToUser(not: Notification): Result<Boolean> {
        return try {
            val response = client.post("/$COLLECTION_NOTIFICATIONS") {
                setBody(not.toNotificationPost())
            }
            if (response.status == HttpStatusCode.Created) {
                Result.success(response.bodyAsText().contains("error", true) == false)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            println("Exception sendNotificationToUser($not) --- ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun getAllUserOrders(id: Int): Result<List<OrderDot>> {
        return try {
            val response = client.get("/$COLLECTION_ORDERS") {
                parameter("user_id", "eq.$id")
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(response.body<List<OrderDot>>())
            } else {
                Result.success(emptyList<OrderDot>())
            }
        } catch (e: Exception) {
            println("Exception getAllUserOrders($id) --- ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun deleteOrder(id: Int): Result<Boolean> {
        return try {
            val response = client.delete("/$COLLECTION_ORDERS") {
                parameter("id", "eq.$id")
            }
            if (response.status == HttpStatusCode.NoContent || response.status == HttpStatusCode.OK) {
                Result.success(true)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            println("Exception deleteOrder($id) --- ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun newOrder(order: Order): Result<Boolean> {
        return try {
            val response = client.post("/$COLLECTION_ORDERS") {
                setBody(order.toOrderPost())
            }
            if (response.status == HttpStatusCode.Created) {
                val notification = sendNotificationToUser(Notification(
                    userId = order.userId,
                    label = "New order",
                    description = "Thank you for using our service!"
                ))
                Result.success(notification.getOrElse { false } == true)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            println("Exception newOrder($order) --- ${e.message}")
            return Result.failure(e)
        }
    }

}