package com.nidhal.meteoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nidhal.meteoapp.BuildConfig
import com.nidhal.meteoapp.R
import com.nidhal.meteoapp.data.model.WeatherResponse
import com.nidhal.meteoapp.databinding.ItemWeatherResultBinding
import com.nidhal.meteoapp.utils.KEY_KELVIN_TO_CELSIUS

class WeatherResultAdapter (private val weatherResults: List<WeatherResponse>)
    : RecyclerView.Adapter<WeatherResultAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherResultBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun getItemCount() = weatherResults.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        with(holder){
            with(weatherResults[position]) {
                binding.cityName.text = this.name
                binding.MaxMinTemp.text = String.format(
                    holder.itemView.context.getString(
                        R.string.max_min_temp),
                        this.main?.temp_max?.minus(KEY_KELVIN_TO_CELSIUS),
                        this.main?.temp_min?.minus(KEY_KELVIN_TO_CELSIUS)
                    )
                binding.feltTemp.text = String.format(
                    holder.itemView.context.getString(
                        R.string.felt_temperature),
                        this.main?.feels_like?.minus(KEY_KELVIN_TO_CELSIUS)
                    )
                binding.currentTemp.text =  String.format(
                        holder.itemView.context.getString(
                            R.string.temperature_in_celsius),
                this.main?.temp?.minus(KEY_KELVIN_TO_CELSIUS)
                )
                Glide.with(holder.itemView.context)
                    .load(BuildConfig.IMG_URL + this.weather?.get(0)?.icon + "@2x.png")
                    .circleCrop()
                    .skipMemoryCache(true)
                    .into(binding.imgWeather)


            }
        }
    }

    inner class WeatherViewHolder(val binding: ItemWeatherResultBinding)
        :RecyclerView.ViewHolder(binding.root)

}