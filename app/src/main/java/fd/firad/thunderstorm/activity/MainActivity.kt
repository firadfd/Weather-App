package fd.firad.thunderstorm.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import fd.firad.thunderstorm.R
import fd.firad.thunderstorm.databinding.ActivityMainBinding
import fd.firad.thunderstorm.viewmodel.WeatherViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)


        viewModel.getWeather("Rangpur")
        viewModel.weather.observe(this@MainActivity, Observer {
            Log.e("TAG", "onCreate: ${it.toString()}")
        })

    }
}