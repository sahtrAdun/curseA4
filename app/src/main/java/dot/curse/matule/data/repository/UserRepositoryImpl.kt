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
        val userDtoResult = api.getAllUsers()
        return userDtoResult.map { userDtoList ->
            userDtoList.map { it.toUser() }
        }
    }

    override suspend fun getUserById(userId: Int): Result<User> {
        val userDtoResult = api.getUserById(userId)
        return userDtoResult.map { userDto ->
            userDto.toUser()
        }
    }

    override suspend fun addUser(user: UserPost): Result<User> {
        val userDtoResult = api.addUser(user)
        return userDtoResult.map { userDto ->
            userDto.toUser()
        }
    }

    override suspend fun updateUser(user: UserPost): Result<User> {
        val userDtoResult = api.updateUser(user)
        return userDtoResult.map { userDto ->
            userDto.toUser()
        }
    }

    override suspend fun getUserByEmail(email: String): Result<User> {
        val userDtoResult = api.getUserByEmail(email)
        return userDtoResult.map { userDto ->
            userDto.toUser()
        }
    }

    override suspend fun checkUserExists(email: String, password: String): Result<Boolean> {
        return api.checkUserExists(email, password)
    }


}