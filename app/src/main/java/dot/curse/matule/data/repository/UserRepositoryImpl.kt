package dot.curse.matule.data.repository

import dot.curse.matule.domain.model.user.User
import dot.curse.matule.domain.model.user.UserPost
import dot.curse.matule.domain.model.user.toUser
import dot.curse.matule.domain.repository.ApiRepository
import dot.curse.matule.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: ApiRepository
) : UserRepository {

    override suspend fun getAllUsers(): Result<List<User>> {
        val userDotResult = api.getAllUsers()
        return userDotResult.map { userDotList ->
            userDotList.map { it.toUser() }
        }
    }

    override suspend fun getUserById(userId: Int): Result<User> {
        val userDotResult = api.getUserById(userId)
        return userDotResult.map { userDot ->
            userDot.toUser()
        }
    }

    override suspend fun addUser(user: UserPost): Result<Boolean> {
        val userDotResult = api.addUser(user)
        return userDotResult
    }

    override suspend fun updateUser(user: User): Result<User> {
        val userDotResult = api.updateUser(user)
        return userDotResult.map { userDot ->
            userDot.toUser()
        }
    }

    override suspend fun updateUserByEmail(user: User): Result<User> {
        val userDotResult = api.updateUserByEmail(user)
        return userDotResult.map { userDot ->
            userDot.toUser()
        }
    }

    override suspend fun getUserByEmail(email: String): Result<User> {
        val userDotResult = api.getUserByEmail(email)
        return userDotResult.map { userDot ->
            userDot.toUser()
        }
    }

    override suspend fun checkUserExists(email: String, password: String): Result<User> {
        val userDotResult = api.checkUserExists(email, password)
        return userDotResult.map { userDot ->
            userDot.toUser()
        }
    }

    override suspend fun sendOtp(email: String): Result<Boolean> {
        return api.sendOtp(email)
    }

    override suspend fun checkOtp(email: String, otp: String): Result<Boolean> {
        return api.checkOtp(email, otp)
    }


}