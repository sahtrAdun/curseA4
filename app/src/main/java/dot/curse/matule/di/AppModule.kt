package dot.curse.matule.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dot.curse.matule.data.api.HttpClientProvider
import dot.curse.matule.data.storage.SharedManager
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
}