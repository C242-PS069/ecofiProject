package com.dicoding.ecofiproject.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.ecofiproject.data.api.ApiConfig
import com.dicoding.ecofiproject.data.response.PredictionResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response as RetrofitResponse

class ScanViewModel : ViewModel() {

    fun predictImage(token: String, imagePart: MultipartBody.Part): LiveData<PredictionResponse?> {
        val result = MutableLiveData<PredictionResponse?>()
        val apiService = ApiConfig.getApiService()

        apiService.predictImage("Bearer $token", imagePart)
            .enqueue(object : Callback<PredictionResponse> {
                override fun onResponse(
                    call: Call<PredictionResponse>,
                    response: RetrofitResponse<PredictionResponse>
                ) {
                    if (response.isSuccessful) {
                        result.postValue(response.body()) // Return the response body
                    } else {
                        result.postValue(null) // Error case
                    }
                }

                override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                    result.postValue(null) // Error case
                }
            })

        return result
    }
}
