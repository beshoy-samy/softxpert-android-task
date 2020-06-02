package com.example.cars.network

import android.util.Log
import com.example.cars.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class RetrofitBuilder {

    companion object {

        private val logger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.i("Retrofit", message)
            }
        }

        private val loggingInterceptor =
            HttpLoggingInterceptor(logger).apply { level = HttpLoggingInterceptor.Level.BODY }

        private val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(OkHttpClient.Builder().apply {
                if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
            }.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    }

}