package fd.firad.thunderstorm.api

import fd.firad.thunderstorm.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/weather")
    @Headers("X-Api-Key : BWOtKQhIszseK/7towc+uQ==dUl4yVj06BKU8irp")
    suspend fun getWeather(@Query("city") city: String): Response<WeatherResponse>

}