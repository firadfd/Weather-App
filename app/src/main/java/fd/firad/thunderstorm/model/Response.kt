package fd.firad.thunderstorm.model

sealed class Response() {
    object Loading : Response()
    class Success(val weatherResponse: WeatherResponse) : Response()
    class Error(val error: String) : Response()
}
