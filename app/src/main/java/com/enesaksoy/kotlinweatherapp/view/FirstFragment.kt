package com.enesaksoy.kotlinweatherapp.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.enesaksoy.kotlinweatherapp.R
import com.enesaksoy.kotlinweatherapp.databinding.FirstFragmentBinding
import com.enesaksoy.kotlinweatherapp.util.Status
import com.enesaksoy.kotlinweatherapp.viewmodel.FirstViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import javax.inject.Inject
@AndroidEntryPoint
class FirstFragment : Fragment(R.layout.first_fragment) {
    @Inject
    lateinit var glide : RequestManager
    private lateinit var binding : FirstFragmentBinding
    private lateinit var viewModel : FirstViewModel
    private lateinit var sharedPreferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FirstFragmentBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(FirstViewModel::class.java)
        sharedPreferences = requireActivity().getSharedPreferences("com.enesaksoy.kotlinweatherapp.view",
            AppCompatActivity.MODE_PRIVATE
        )
        binding.searchIconImage.setOnClickListener {
            val searchText = binding.searchText.text.toString()
            sharedPreferences.edit().putString("cityName",searchText).apply()
            viewModel.getWeather(searchText)
        }
        val getCity = sharedPreferences.getString("cityName","izmir")
        viewModel.getWeather(getCity!!)
        observeOn()
    }

    private fun observeOn(){
        viewModel.getWeatherData.observe(viewLifecycleOwner, Observer {
            if(it.status == Status.SUCCESS){
                it.data?.let {
                    binding.tempText.text = DecimalFormat("##,##").format(it.main.temp - 273.15).toString()
                    binding.countryCodeText.text = it.sys.country
                    binding.cityNameText.text = it.name
                    glide.load("https://openweathermap.org/img/wn/${it.weather.get(0).icon}@2x.png").into(binding.iconImage)
                    println()
                    binding.descriptionText.text = "description:"+it.weather.get(0).description
                    binding.feelsLikeTempText.text = "feels like:"+DecimalFormat("##,##").format(it.main.feelsLike - 273.15).toString()
                    binding.gustText.text ="gust:"+it.wind.gust.toString()
                    binding.humidityText.text = "humidity:"+it.main.humidity.toString()
                    binding.progressBar.visibility = View.GONE
                }
            }else if(it.status == Status.LOADING){
                binding.progressBar.visibility = View.VISIBLE
                binding.errorText.visibility = View.GONE
            }else if(it.status == Status.ERROR){
                binding.progressBar.visibility = View.GONE
                binding.errorText.visibility = View.VISIBLE
                Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
            }
        })
    }
}