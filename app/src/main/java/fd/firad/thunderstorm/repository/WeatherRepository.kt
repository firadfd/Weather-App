package fd.firad.thunderstorm.repository

import fd.firad.thunderstorm.api.WeatherApiService
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: WeatherApiService) {
    suspend fun getWeather(city: String) = apiService.getWeather(city)
}