package dot.curse.matule.domain.repository

import dot.curse.matule.domain.model.user.User
import dot.curse.matule.domain.model.user.UserDot
import dot.curse.matule.domain.model.user.UserPost

interface ApiRepository {
    suspend fun getAllUsers(): Result<List<UserDot>>
    suspend fun getUserById(userId: Int): Result<UserDot>
    suspend fun addUser(user: UserPost): Result<Boolean>
    suspend fun updateUser(user: User): Result<UserDot>
    suspend fun updateUserByEmail(user: User): Result<UserDot>
    suspend fun getUserByEmail(email: String): Result<UserDot>
    suspend fun checkUserExists(email: String, password: String): Result<UserDot>
    suspend fun sendOtp(email: String): Result<Boolean>
    suspend fun checkOtp(email: String, otp: String): Result<Boolean>
}