package fd.firad.thunderstorm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fd.firad.thunderstorm.api.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    @Singleton
    fun provideBaseUrl() = "https://api.api-ninjas.com/"

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String): WeatherApiService =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build().create(WeatherApiService::class.java)
}