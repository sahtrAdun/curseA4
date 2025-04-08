package dot.curse.matule.data.api

import dot.curse.matule.data.model.users.UserDot
import dot.curse.matule.data.model.users.UserPost
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val client: HttpClient
) {
    companion object {
        const val COLLECTION_USERS = "rest/v1/users"
        const val COLLECTION_SHOES = "rest/v1/shoes"
    }

    suspend fun getAllUsers(): Result<List<UserDot>> {
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

    suspend fun getUserById(userId: Int): Result<UserDot> {
        return try {
            client.get("/$COLLECTION_USERS") {
                parameter("id", "eq.$userId")
            }.body<UserDot>().let { body ->
                Result.success(body)
            }
        } catch (e: Exception) {
            println("Exception getUserById($userId):\n${e.message}")
            Result.failure(e)
        }
    }

    suspend fun addUser(user: UserPost): Result<UserDot> {
        return try {
            client.post("/$COLLECTION_USERS") {
                setBody(user)
            }.body<UserDot>().let { body ->
                Result.success(body)
            }
        } catch (e: Exception) {
            println("Exception addUser($user):\n${e.message}")
            Result.failure(e)
        }
    }

    suspend fun updateUser(user: UserPost): Result<UserDot> {
        return try {
            client.put("/$COLLECTION_USERS") {
                setBody(user)
            }.body<UserDot>().let { body ->
                Result.success(body)
            }
        } catch (e: Exception) {
            println("Exception updateUser($user):\n${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getUserByEmail(email: String): Result<UserDot> {
        return try {
            client.get("/$COLLECTION_USERS") {
                parameter("email", "eq.$email")
            }.body<UserDot>().let { body ->
                Result.success(body)
            }
        } catch (e: Exception) {
            println("Exception getUserByEmail($email):\n${e.message}")
            Result.failure(e)
        }
    }

    suspend fun checkUserExists(email: String, password: String): Result<Boolean> {
        return try {
            client.get("/$COLLECTION_USERS") {
                parameter("email", "eq.$email")
                parameter("password", "eq.$password")
            }.body<List<UserDot>>().let { body ->
                Result.success(body.isNotEmpty())
            }
        } catch (e: Exception) {
            println("Exception checkUserExists($email, $password):\n${e.message}")
            Result.failure(e)
        }
    }

}