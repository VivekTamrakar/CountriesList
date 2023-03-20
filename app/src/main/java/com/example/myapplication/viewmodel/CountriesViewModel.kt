package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Country
import com.example.myapplication.repositories.CountriesRepository
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CountriesViewModel : ViewModel() {

    private val repositories = CountriesRepository()
    val lists = MutableLiveData<Result<List<Country>>>()

    fun fetchCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = repositories.countries()
                lists.postValue(Result.Success(list))
            } catch (ioe: IOException) {
                Log.e("CountriesViewModel", "IOException  $ioe")
                val message = ioe.message ?: "Unknown error, please check your internet"
                lists.postValue(Result.Failure(message))
            } catch (he: HttpException) {
                Log.e("CountriesViewModel", "HttpException $he")

                lists.postValue(Result.Failure(HTTPErrorCodesHelper.statusCodeMessage(he)))
            }
        }
    }
}

sealed class Result<out T> {
    data class Success<out R>(val value: R) : Result<R>()
    data class Failure(val message: String) : Result<Nothing>()
}

object HTTPErrorCodesHelper {

    fun statusCodeMessage(http: HttpException): String {
        return when (http.code()) {
            400 -> "Bad Request."
            401 -> "UnAuthenticated: The client must authenticate itself to get the requested response."
            403 -> "Forbidden: The client does not have access rights to the content."
            404 -> "Not Found: The server cannot find the requested resource."
            405 -> "Method Not Allowed."
            500 -> "Internal server error."
            501 -> "Not Implemented: The request method is not supported by the server and cannot be handled."
            502 -> "Bad Gateway."
            503 -> "Service Unavailable: The server is not ready to handle the request."
            else -> http.message()
        }
    }

}





