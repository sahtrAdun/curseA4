package dot.curse.matule.domain.repository

import dot.curse.matule.domain.model.user.User
import dot.curse.matule.domain.model.user.UserPost

interface UserRepository {
    suspend fun getAllUsers(): Result<List<User>>
    suspend fun getUserById(userId: Int): Result<User>
    suspend fun addUser(user: UserPost): Result<User>
    suspend fun updateUser(user: UserPost): Result<User>
    suspend fun getUserByEmail(email: String): Result<User>
    suspend fun checkUserExists(email: String, password: String): Result<Boolean>
}