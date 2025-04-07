package dot.curse.matule.model.repository

import dot.curse.matule.model.api.ApiClient
import dot.curse.matule.model.entities.user.User
import dot.curse.matule.model.entities.user.toUser
import dot.curse.matule.model.storage.SharedPrefsManager
import kotlin.collections.map

class UserRepository(
    private val apiClient: ApiClient,
    private val sharedPrefsManager: SharedPrefsManager
) {
    suspend fun getUsers(): Result<List<User>> {
        return try {
            val userDto = apiClient.getUsers().getOrThrow()
            Result.success(userDto.map { it.toUser() })
        } catch (e: Exception) {
            println("getUsers(): Error fetching users: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getUserById(userId: Int): Result<User> {
        return try {
            val userDto = apiClient.getUsers().getOrThrow()
            val user = userDto.map { it.toUser() }.first()
            Result.success(user)
        } catch (e: Exception) {
            println("getUserById($userId): Error fetching user: ${e.message}")
            Result.failure(e)
        }
    }

    fun saveUserId(userId: Int) {
        sharedPrefsManager.saveUserId(userId)
    }

    fun getUserId(): Int {
        return sharedPrefsManager.getUserId()
    }

    fun getAppTheme(): Boolean {
        return sharedPrefsManager.getDarkTheme()
    }
}