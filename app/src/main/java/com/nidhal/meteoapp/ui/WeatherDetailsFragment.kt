package com.nidhal.meteoapp.ui

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nidhal.meteoapp.R
import com.nidhal.meteoapp.data.model.WeatherResponse
import com.nidhal.meteoapp.databinding.FragmentWeatherDetailsBinding
import com.nidhal.meteoapp.ui.adapter.WeatherResultAdapter
import com.nidhal.meteoapp.ui.viewmodel.WeatherViewModel
import com.nidhal.meteoapp.utils.*
import com.nidhal.meteoapp.utils.AlertUtility.showNeutralAlert
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class WeatherDetailsFragment constructor(
    private val networkHelper: NetworkHelper
) : Fragment(R.layout.fragment_weather_details), View.OnClickListener {

    private var _binding: FragmentWeatherDetailsBinding? = null
    private val binding: FragmentWeatherDetailsBinding get() = requireNotNull(_binding)
    private val weatherViewModel: WeatherViewModel by viewModels()
    private var mCountDownTimer: CountDownTimer? = null
    private var currentCount = KEY_START_TIME_PROGRESS
    private var progressStatus = 0
    private var waitingMsgStatus = 1
    private var weatherByCityList = mutableListOf<WeatherResponse>()
    var handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var delay = KEY_START_TIME_WAITING_MSG
    private lateinit var weatherAdapter: WeatherResultAdapter
    private var currentCityWeather: WeatherResponse? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentWeatherDetailsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        weatherViewModel.waitingMsg.value = 1
        binding.btnRestart.setOnClickListener(this)

        weatherViewModel.waitingMsg.observe(viewLifecycleOwner, { status ->
            when (status) {
                1 -> {
                    binding.txtWaitingMsg.text = getString(R.string.first_waiting_msg)
                }
                2 -> {
                    binding.txtWaitingMsg.text = getString(R.string.second_waiting_msg)
                }
                3 -> {
                    binding.txtWaitingMsg.text = getString(R.string.third_waiting_msg)
                }
            }
        })

        weatherViewModel.progressCount.observe(
            viewLifecycleOwner, { status ->
                when (status) {
                    0 -> {
                        fetchWeatherByCity(getString(R.string.city_rennes))
                    }
                    10 -> {
                        fetchWeatherByCity(getString(R.string.city_paris))
                    }
                    20 -> {
                        fetchWeatherByCity(getString(R.string.city_nantes))
                    }
                    30 -> {
                        fetchWeatherByCity(getString(R.string.city_bordeaux))
                    }
                    40 -> {
                        fetchWeatherByCity(getString(R.string.city_lyon))
                    }
                }
            })
        startWaitingMsg()

    }

    private fun fetchWeatherByCity(city: String) {
        weatherViewModel.fetchWeatherByCity(city)
        weatherViewModel.weather.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let { cityWeather ->
                        if (currentCityWeather == null || currentCityWeather != cityWeather)
                            weatherByCityList.add(cityWeather)
                        currentCityWeather = cityWeather
                    }
                }
                Status.ERROR -> {
                    it.message?.let { errorMessage ->
                        showNeutralAlert(
                            activity,
                            getString(R.string.title_ws_problem),
                            errorMessage,
                            getString(R.string.ok),
                            object : NoticeDialogListener {
                                override fun onButtonClick(dialogInterface: DialogInterface) {
                                    dialogInterface.dismiss()
                                    activity?.onBackPressed()
                                }
                            }
                        )
                    }
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * start Timer of progressbar
     */
    private fun startProgressBar() {
        mCountDownTimer = object : CountDownTimer(currentCount, KEY_COUNT_DOWN) {
            override fun onTick(countToFinished: Long) {
                currentCount = countToFinished
                binding.progressBar.progress =
                    progressStatus * 100 / (KEY_START_TIME_PROGRESS.toInt() / KEY_COUNT_DOWN.toInt())
                weatherViewModel.progressCount.value = progressStatus
                binding.progressValue.text =
                    String.format(
                        getString(
                            R.string.progress_bar_value
                        ),
                        progressStatus * 100 / (KEY_START_TIME_PROGRESS.toInt() / KEY_COUNT_DOWN.toInt())
                    )
                progressStatus++
            }

            override fun onFinish() {
                binding.progressBar.progress = 100
                binding.txtWaitingMsg.visibility = View.INVISIBLE
                handler.removeCallbacks(runnable)
                binding.progressBar.visibility = View.GONE
                binding.progressValue.visibility = View.GONE
                binding.btnRestart.visibility = View.VISIBLE
                binding.tableWeatherResult.visibility = View.VISIBLE
                showResults()
            }
        }
        mCountDownTimer?.start()
    }

    private fun startWaitingMsg() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable, delay)
            waitingMsgStatus++
            if (waitingMsgStatus == 4)
                waitingMsgStatus = 1
            weatherViewModel.waitingMsg.value = waitingMsgStatus
        }.also { runnable = it }, delay)
    }

    private fun showResults() {
        weatherAdapter = WeatherResultAdapter(weatherByCityList)
        binding.tableWeatherResult.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.tableWeatherResult.adapter = weatherAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        mCountDownTimer?.cancel()
        mCountDownTimer = null
        weatherByCityList.clear()
    }

    override fun onResume() {
        super.onResume()
        startProgressBar()
    }

    override fun onPause() {
        super.onPause()
        mCountDownTimer?.cancel()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnRestart -> {
                if (networkHelper.isNetworkConnected()) {
                    progressStatus = 0
                    waitingMsgStatus = 1
                    currentCount = KEY_START_TIME_PROGRESS
                    binding.tableWeatherResult.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.progressValue.visibility = View.VISIBLE
                    binding.txtWaitingMsg.visibility = View.VISIBLE
                    binding.btnRestart.visibility = View.GONE
                    weatherByCityList.clear()
                    startProgressBar()
                    startWaitingMsg()
                } else {
                    showNeutralAlert(
                        activity,
                        getString(R.string.title_no_internet_connexion),
                        getString(R.string.msg_network_error),
                        getString(R.string.ok),
                        object : NoticeDialogListener {
                            override fun onButtonClick(dialogInterface: DialogInterface) {
                                dialogInterface.dismiss()
                            }
                        }
                    )
                }
            }
        }

    }
}

