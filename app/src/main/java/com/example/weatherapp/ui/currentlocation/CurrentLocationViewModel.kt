package com.example.weatherapp.ui.currentlocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.APIResponse.AllWeather
import com.example.weatherapp.GeolocationApi.Geolocation
import com.example.weatherapp.ReverseGeocoding.CurrentCity
import com.example.weatherapp.WeatherRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrentLocationViewModel(val repo: WeatherRepository) : ViewModel() {
//    private val _text = MutableLiveData<String>().apply {
//        value = "This is current location Fragment"
//    }
//    val text: LiveData<String> = _text

    var currentWeather = MutableLiveData<AllWeather>()
    var currentLocation = MutableLiveData<Geolocation>()
    var currentCity = MutableLiveData<CurrentCity>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }


    fun getCurrentWeather(latitude: String, longitude: String, apiKey: String, unit: String) {
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            var res = repo.getCurrentWeather(latitude, longitude, apiKey, unit)
            if(res.isSuccessful) {
                currentWeather.postValue(res.body())
            }

        }
    }


    fun getGeoloaction(googleApi: String) {
        CoroutineScope(Dispatchers.IO+ coroutineExceptionHandler).launch {
            var res = repo.getGeoloaction(googleApi)
            if(res.isSuccessful) {
                currentLocation.postValue(res.body())
            }

        }
    }

    fun getCurrentCity(latLng:String, cagedDataKey:String) {
        CoroutineScope(Dispatchers.IO+ coroutineExceptionHandler).launch {
            var res = repo.getCurrentCity(latLng, cagedDataKey)
            if(res.isSuccessful) {
                currentCity.postValue(res.body())
            }

        }
    }
}