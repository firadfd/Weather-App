package fd.firad.thunderstorm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fd.firad.thunderstorm.Response
import fd.firad.thunderstorm.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {
    private val _weather = MutableLiveData<Response>()
    val weather: LiveData<Response>
        get() = _weather

    fun getWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getWeather(city).let { result ->
                try {
                    if (result.isSuccessful) {
                        _weather.postValue(Response.Success(result.body()!!))
                    } else {
                        _weather.postValue(Response.Error("API Error"))
                    }
                } catch (e: Exception) {
                    _weather.postValue(Response.Error(e.message.toString()))
                }


            }
        }
    }
}