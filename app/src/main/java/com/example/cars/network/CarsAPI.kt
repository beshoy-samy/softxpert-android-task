package com.example.cars.network

import com.example.cars.data.CarsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CarsApiService {
    @GET("cars")
    fun getCars(@Query("page") page: Int): Observable<CarsResponse>

}

object CarsAPI {
    val service: CarsApiService by lazy {
        RetrofitBuilder.retrofit.create(CarsApiService::class.java)
    }
}