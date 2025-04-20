package dot.curse.matule.data.repository

import dot.curse.matule.domain.model.shoe.Shoe
import dot.curse.matule.domain.model.shoe.toShoe
import dot.curse.matule.domain.repository.ApiRepository
import dot.curse.matule.domain.repository.ShoeRepository
import javax.inject.Inject

class ShoeRepositoryImpl @Inject constructor(
    private val api: ApiRepository
) : ShoeRepository {
    override suspend fun getShoeById(id: Int): Result<Shoe> {
        return api.getShoeById(id).map { it.toShoe() }
    }

    override suspend fun getUserFavorites(userId: Int): Result<List<Shoe>> {
        val response = api.getUserFavorites(userId)
        return response.map { shoeDot ->
            shoeDot.map { it.toShoe() }
        }
    }

    override suspend fun addShoeToUserFavorites(userId: Int, shoeId: Int): Result<Boolean> {
        val response = api.addShoeToUserFavorites(userId, shoeId)
        return response
    }

    override suspend fun deleteShoeFromUserFavorites(userId: Int, shoeId: Int): Result<Boolean> {
        val response = api.deleteShoeFromUserFavorites(userId, shoeId)
        return response
    }

    override suspend fun getUserCart(userId: Int): Result<List<Shoe>> {
        val response = api.getUserCart(userId)
        return response.map { shoeDot ->
            shoeDot.map { it.toShoe() }
        }
    }

    override suspend fun addShoeToUserCart(userId: Int, shoeId: Int): Result<Boolean> {
        val response = api.addShoeToUserCart(userId, shoeId)
        return response
    }

    override suspend fun deleteShoeFromUserCart(userId: Int, shoeId: Int): Result<Boolean> {
        val response = api.deleteShoeFromUserCart(userId, shoeId)
        return response
    }

    override suspend fun clearUserCart(userId: Int): Result<Boolean> {
        return api.clearUserCart(userId)
    }

    override suspend fun getAllShoe(): Result<List<Shoe>> {
        val response = api.getAllShoe()
        return response.map { shoeDot ->
            shoeDot.map { it.toShoe() }
        }
    }

    override suspend fun getShoesByTag(tag: String): Result<List<Shoe>> {
        val response = api.getShoesByTag(tag)
        return response.map { shoeDot ->
            shoeDot.map { it.toShoe() }
        }
    }

    override suspend fun getShoesByCategory(category: String): Result<List<Shoe>> {
        val response = api.getShoesByCategory(category)
        return response.map { shoeDot ->
            shoeDot.map { it.toShoe() }
        }
    }

}