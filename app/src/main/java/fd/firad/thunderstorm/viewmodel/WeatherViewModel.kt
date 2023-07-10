package fd.firad.thunderstorm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fd.firad.thunderstorm.model.WeatherResponse
import fd.firad.thunderstorm.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {
    private val _weather = MutableLiveData<WeatherResponse>()
    val weather: LiveData<WeatherResponse>
        get() = _weather

    fun getWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getWeather(city).let { result ->
                if (result.isSuccessful) {
                    _weather.postValue(result.body())
                } else {
                    Log.d("TAG", "getAllData: ${result.code()}")
                }

            }
        }
    }
}