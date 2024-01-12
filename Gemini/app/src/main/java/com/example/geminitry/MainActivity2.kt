package com.example.geminitry

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class MainActivity2 : AppCompatActivity() {

    private lateinit var currentTemperatureTextView: TextView
    private lateinit var currentConditionsTextView: TextView
    private lateinit var hourlyForecastRecyclerView: RecyclerView

    private lateinit var updateWeatherButton: Button

    private val apiKey = "452a248cadae22db0797371cc96e6edd"
    private val apiUrl = "https://api.openweathermap.org/data/2.5/weather"

    private val locationPermissionCode = 123
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val hourlyNotificationHandler = Handler(Looper.getMainLooper())
    private val hourlyNotificationRunnable = object : Runnable {
        override fun run() {
            showHourlyNotification()
            hourlyNotificationHandler.postDelayed(this, 3600000) // Repeat every hour
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentTemperatureTextView = findViewById(R.id.currentTemperature)
        currentConditionsTextView = findViewById(R.id.currentConditions)
        hourlyForecastRecyclerView = findViewById(R.id.hourlyForecastRecyclerView)

        updateWeatherButton = findViewById(R.id.updateWeatherButton)

        hourlyForecastRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        updateWeatherButton.setOnClickListener {
            if (checkLocationPermission()) {
                updateWeather()
            } else {
                requestLocationPermission()
            }
        }

        // Start hourly notifications
        hourlyNotificationHandler.post(hourlyNotificationRunnable)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun updateWeather() {
        if (checkLocationPermission()) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        val lat = it.latitude
                        val lon = it.longitude

                        Log.d("WeatherApp", "Location: $lat, $lon")


                        val weatherUrl =
                            "$apiUrl?lat=$lat&lon=$lon&appid=$apiKey"

                        Log.d("WeatherApp", "Weather API URL: $weatherUrl")

                        GlobalScope.launch(Dispatchers.IO) {
                            try {
                                val response = URL(weatherUrl).readText()
                                Log.d("WeatherApp", "API Response: $response")
                                parseWeatherData(response)
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Log.e("WeatherApp", "Error fetching weather data: ${e.message}")

                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                    Log.e("WeatherApp", "Error getting location: ${e.message}")

                }
        } else {
            Log.d("WeatherApp", "Location permission not granted.")

        }
    }

    @SuppressLint("SetTextI18n")
    private fun parseWeatherData(response: String) {
        val jsonObject = JSONObject(response)

        // Parse and update current weather information
        val currentWeatherArray = jsonObject.getJSONArray("weather")
        if (currentWeatherArray.length() > 0) {
            val currentWeather = currentWeatherArray.getJSONObject(0)
            val currentConditions = currentWeather.getString("description")

            runOnUiThread {
                if (!isFinishing) {
                    currentConditionsTextView.text = "Conditions: $currentConditions"
                }
            }
        }


        val mainObject = jsonObject.getJSONObject("main")
        val currentTemperature = mainObject.getDouble("temp")

        runOnUiThread {
            if (!isFinishing) {
                currentTemperatureTextView.text = "Current Temperature: $currentTemperature °C"
            }
        }

        // Parse and update hourly forecast
        val hourlyForecast = parseHourlyForecast(jsonObject.getJSONArray("hourly"))
        runOnUiThread {
            if (!isFinishing) {
                hourlyForecastRecyclerView.adapter = HourlyForecastAdapter(hourlyForecast)
            }
        }


    }

    private fun parseHourlyForecast(hourlyArray: JSONArray): List<HourlyForecastItem> {
        val forecastList = mutableListOf<HourlyForecastItem>()
        for (i in 0 until hourlyArray.length()) {
            val item = hourlyArray.getJSONObject(i)
            val time = item.getString("dt") // Use "dt" for timestamp
            val temperature = item.getDouble("temp").toString()
            val condition = item.getJSONArray("weather").getJSONObject(0).getString("description")
            val forecastItem = HourlyForecastItem(time, temperature, condition)
            forecastList.add(forecastItem)
        }
        return forecastList
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun checkLocationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            locationPermissionCode
        )
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun showHourlyNotification() {

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "hourly_channel",
                "Hourly Weather Update",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }


        val builder = NotificationCompat.Builder(this, "hourly_channel")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Hourly Weather Update")
            .setContentText("Check the latest weather conditions.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        notificationManager.notify(1, builder.build())
    }
}
