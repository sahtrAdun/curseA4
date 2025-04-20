package dot.curse.matule.domain.repository

import android.content.Context
import android.net.Uri
import dot.curse.matule.domain.model.notification.Notification
import dot.curse.matule.domain.model.notification.NotificationDot
import dot.curse.matule.domain.model.order.Order
import dot.curse.matule.domain.model.order.OrderDot
import dot.curse.matule.domain.model.shoe.ShoeDot
import dot.curse.matule.domain.model.user.User
import dot.curse.matule.domain.model.user.UserDot
import dot.curse.matule.domain.model.user.UserPost

interface ApiRepository {
    suspend fun getAllUsers(): Result<List<UserDot>>
    suspend fun getUserById(userId: Int): Result<UserDot>
    suspend fun addUser(user: UserPost): Result<Boolean>
    suspend fun updateUser(user: User): Result<Boolean>
    suspend fun updateUserByEmail(user: UserPost): Result<Boolean>
    suspend fun getUserByEmail(email: String): Result<UserDot>
    suspend fun checkUserExists(email: String, password: String): Result<UserDot>
    suspend fun sendOtp(email: String): Result<Boolean>
    suspend fun checkOtp(email: String, otp: String): Result<Boolean>
    suspend fun uploadAvatar(context: Context, user: User, uri: Uri): Result<Boolean>

    suspend fun getShoeById(id: Int): Result<ShoeDot>
    suspend fun getUserFavorites(userId: Int): Result<List<ShoeDot>>
    suspend fun addShoeToUserFavorites(userId: Int, shoeId: Int): Result<Boolean>
    suspend fun deleteShoeFromUserFavorites(userId: Int, shoeId: Int): Result<Boolean>
    suspend fun getUserCart(userId: Int): Result<List<ShoeDot>>
    suspend fun addShoeToUserCart(userId: Int, shoeId: Int): Result<Boolean>
    suspend fun deleteShoeFromUserCart(userId: Int, shoeId: Int): Result<Boolean>
    suspend fun clearUserCart(userId: Int): Result<Boolean>

    suspend fun getAllShoe(): Result<List<ShoeDot>>
    suspend fun getShoesByTag(tag: String): Result<List<ShoeDot>>
    suspend fun getShoesByCategory(category: String): Result<List<ShoeDot>>

    suspend fun getNotificationByUserId(id: Int): Result<List<NotificationDot>>
    suspend fun sendNotificationToUser(not: Notification): Result<Boolean>

    suspend fun getAllUserOrders(id: Int): Result<List<OrderDot>>
    suspend fun deleteOrder(id: Int): Result<Boolean>
    suspend fun newOrder(order: Order): Result<Boolean>
}