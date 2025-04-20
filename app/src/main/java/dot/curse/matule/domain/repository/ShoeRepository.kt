package dot.curse.matule.domain.repository

import dot.curse.matule.domain.model.shoe.Shoe

interface ShoeRepository {
    suspend fun getShoeById(id: Int): Result<Shoe>

    suspend fun getUserFavorites(userId: Int): Result<List<Shoe>>
    suspend fun addShoeToUserFavorites(userId: Int, shoeId: Int): Result<Boolean>
    suspend fun deleteShoeFromUserFavorites(userId: Int, shoeId: Int): Result<Boolean>
    suspend fun getUserCart(userId: Int): Result<List<Shoe>>
    suspend fun addShoeToUserCart(userId: Int, shoeId: Int): Result<Boolean>
    suspend fun deleteShoeFromUserCart(userId: Int, shoeId: Int): Result<Boolean>
    suspend fun clearUserCart(userId: Int): Result<Boolean>

    suspend fun getAllShoe(): Result<List<Shoe>>
    suspend fun getShoesByTag(tag: String): Result<List<Shoe>>
    suspend fun getShoesByCategory(category: String): Result<List<Shoe>>
}