package dot.curse.matule.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dot.curse.matule.data.api.HttpClientProvider
import dot.curse.matule.data.repository.ApiRepositoryImpl
import dot.curse.matule.data.repository.NotificationRepositoryImpl
import dot.curse.matule.data.repository.OrderRepositoryImpl
import dot.curse.matule.data.repository.ShoeRepositoryImpl
import dot.curse.matule.data.repository.UserRepositoryImpl
import dot.curse.matule.data.storage.SharedManager
import dot.curse.matule.domain.repository.ApiRepository
import dot.curse.matule.domain.repository.NotificationRepository
import dot.curse.matule.domain.repository.OrderRepository
import dot.curse.matule.domain.repository.ShoeRepository
import dot.curse.matule.domain.repository.UserRepository
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedManager(@ApplicationContext context: Context): SharedManager {
        return SharedManager(context)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientProvider.client
    }

    @Provides
    @Singleton
    fun provideApiRepository(httpClient: HttpClient): ApiRepository {
        return ApiRepositoryImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: ApiRepository): UserRepository {
        return UserRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideShoeRepository(api: ApiRepository): ShoeRepository {
        return ShoeRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(api: ApiRepository): NotificationRepository {
        return NotificationRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(api: ApiRepository): OrderRepository {
        return OrderRepositoryImpl(api)
    }

}