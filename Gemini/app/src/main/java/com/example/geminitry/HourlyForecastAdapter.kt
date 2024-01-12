package com.example.geminitry

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HourlyForecastAdapter(private val hourlyForecast: List<HourlyForecastItem>) :
    RecyclerView.Adapter<HourlyForecastAdapter.HourlyForecastViewHolder>() {

    class HourlyForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val temperatureTextView: TextView = itemView.findViewById(R.id.temperatureTextView)
        val conditionTextView: TextView = itemView.findViewById(R.id.conditionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hourly_forecast, parent, false)
        return HourlyForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) {
        val forecastItem = hourlyForecast[position]
        holder.timeTextView.text = forecastItem.time
        holder.temperatureTextView.text = forecastItem.temperature
        holder.conditionTextView.text = forecastItem.condition
    }

    override fun getItemCount(): Int {
        return hourlyForecast.size
    }
}
