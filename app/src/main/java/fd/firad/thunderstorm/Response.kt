package fd.firad.thunderstorm

import fd.firad.thunderstorm.model.WeatherResponse

sealed class Response() {
    class Loading() : Response()
    class Success(val weatherResponse: WeatherResponse) : Response()
    class Error(val error: String) : Response()
}
