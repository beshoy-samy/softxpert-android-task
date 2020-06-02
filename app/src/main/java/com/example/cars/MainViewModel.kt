package com.example.cars

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cars.data.Car
import com.example.cars.data.CarsResponse
import com.example.cars.network.CarsAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    private val disposable = CompositeDisposable()
    private var page = 1
    private val cars = MutableLiveData<MutableList<Car>?>(null)
    val fetchedCars: LiveData<MutableList<Car>?>
        get() = cars

    //Live data to handle connection errors and progress
    private val progress = MutableLiveData(false)
    val showProgress: LiveData<Boolean>
        get() = progress
    private val connectionErrorData = MutableLiveData(false)
    var serverErrors: String? = null
    val connectionError: LiveData<Boolean>
        get() = connectionErrorData

    fun getCars(isRefresh: Boolean) {
        page = if (isRefresh) 1 else page + 1
        progress.value = true
        disposable.add(
            CarsAPI.service.getCars(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(carsFetched, error)
        )
    }

    private val carsFetched = fun(response: CarsResponse) {
        progress.value = false
        if (response.isSuccess) {
            cars.value = response.cars
            connectionErrorData.value = false
        } else {
            serverErrors = response.error.message
            connectionErrorData.value = true
        }
    }

    private val error = fun(throwable: Throwable) {
        progress.value = false
        connectionErrorData.value = true
        Log.e("Retrofit", "error fetching item", throwable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun isRefresh(): Boolean {
        return page == 1
    }

}