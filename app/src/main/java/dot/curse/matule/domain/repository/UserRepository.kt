package dot.curse.matule.domain.repository

import android.content.Context
import android.net.Uri
import dot.curse.matule.domain.model.user.User
import dot.curse.matule.domain.model.user.UserPost

interface UserRepository {
    suspend fun getAllUsers(): Result<List<User>>
    suspend fun getUserById(userId: Int): Result<User>
    suspend fun addUser(user: UserPost): Result<Boolean>
    suspend fun updateUser(user: User): Result<Boolean>
    suspend fun updateUserByEmail(user: UserPost): Result<Boolean>
    suspend fun getUserByEmail(email: String): Result<User>
    suspend fun checkUserExists(email: String, password: String): Result<User>
    suspend fun sendOtp(email: String): Result<Boolean>
    suspend fun checkOtp(email: String, otp: String): Result<Boolean>
    suspend fun uploadAvatar(context: Context, user: User, uri: Uri): Result<Boolean>
}