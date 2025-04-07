package dot.curse.matule.model.api

import dot.curse.matule.model.entities.user.UserDto
import dot.curse.matule.model.entities.user.UserPost
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post

class ApiClient(private val client: HttpClient) {
    suspend fun getUsers(): Result<List<UserDto>> {
        return try {
            val users = client.get("rest/v1/users").body<List<UserDto>>()
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserById(userId: Int): Result<UserDto> {
        return try {
            val user = client.get("rest/v1/users?id=eq.$userId").body<UserDto>()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addUser(user: UserPost): Result<UserDto> {
        return try {
            val user = client.post("rest/v1/users").body<UserDto>()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUser(user: UserPost): Result<UserDto> {
        return try {
            val user = client.post("rest/v1/users").body<UserDto>()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}