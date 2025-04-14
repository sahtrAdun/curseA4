package dot.curse.matule.data.repository

import dot.curse.matule.domain.model.user.UserDot
import dot.curse.matule.domain.model.user.UserPost
import dot.curse.matule.domain.repository.ApiRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val client: HttpClient
): ApiRepository {

    companion object {
        const val COLLECTION_USERS = "rest/v1/users"
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
            }.body<UserDot>().let { body ->
                Result.success(body)
            }
        } catch (e: Exception) {
            println("Exception getUserById($userId):\n${e.message}")
            Result.failure(e)
        }
    }

    /*override suspend fun addUser(user: UserPost): Result<UserDot> {
        return try {
            val response = client.post("/$COLLECTION_USERS") {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                setBody(user)
            }
            if (response.status == HttpStatusCode.OK) {
                val body = response.body<UserDot>()
                Result.success(body)
            } else {
                Result.failure(Exception("HTTP error: ${response.status}"))
            }
        } catch (e: Exception) {
            println("Exception addUser($user):\n${e.message}")
            Result.failure(e)
        }
    }*/

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

    override suspend fun updateUser(user: UserPost): Result<UserDot> {
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
            }.body<UserDot>().let { body ->
                Result.success(body)
            }
        } catch (e: Exception) {
            println("Exception checkUserExists($email, $password):\n${e.message}")
            Result.failure(e)
        }
    }

}