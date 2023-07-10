package fd.firad.thunderstorm.activity

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import fd.firad.thunderstorm.R
import fd.firad.thunderstorm.Response
import fd.firad.thunderstorm.databinding.ActivityMainBinding
import fd.firad.thunderstorm.viewmodel.WeatherViewModel


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)


        if (checkConnectivity()) {
            viewModel.getWeather("Rangpur")
            viewModel.weather.observe(this@MainActivity, Observer {
                Log.e("TAG", "onCreate: ${it.toString()}")
                when (it) {
                    is Response.Loading -> {
                        Toast.makeText(this@MainActivity, "Fetching Data", Toast.LENGTH_SHORT).show()
                    }

                    is Response.Error -> {
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                    }

                    is Response.Success -> {
                        binding.weatherData = it.weatherResponse
                    }
                }

            })
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
        }


        binding.searchCity.setOnClickListener {
            hideKeyboard(this)
            val city = binding.cityName.text.toString()
            if (city.isEmpty()) {
                Toast.makeText(this@MainActivity, "Enter a city", Toast.LENGTH_SHORT).show()
            } else if (city == "") {
                Toast.makeText(this@MainActivity, "Enter a city", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.getWeather(city)
                binding.city.text = city.toString()
            }
        }

    }

    private fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun checkConnectivity(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val wifiNetworkInfo: NetworkInfo? =
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val isWifiConnected = wifiNetworkInfo?.isConnected ?: false

        val mobileNetworkInfo: NetworkInfo? =
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val isMobileConnected = mobileNetworkInfo?.isConnected ?: false

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            val isInternetConnected =
                networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    ?: false

            isInternetConnected || (isWifiConnected && isMobileConnected)
        } else {
            isWifiConnected || isMobileConnected
        }
    }
}