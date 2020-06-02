package com.example.cars

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cars.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var snackbar: Snackbar
    private val carsAdapter = CarsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.carsRv.adapter = carsAdapter
        viewModel.getCars(true)
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getCars(true)
        }

        viewModel.connectionError.observe(this, Observer {
            if (it) showSnackBar()
        })

        viewModel.showProgress.observe(this, Observer {
            binding.swipeRefresh.isRefreshing = it
        })

        viewModel.fetchedCars.observe(this, Observer {
            if(it!=null) carsAdapter.addCars(it, viewModel.isRefresh())
        })

    }

    private fun showSnackBar() {
        snackbar = Snackbar.make(
            binding.root, viewModel.serverErrors
                ?: getString(R.string.no_connection), Snackbar.LENGTH_INDEFINITE
        ).setTextColor(Color.WHITE)
            .setActionTextColor(Color.YELLOW)
            .setAction(R.string.retry) {
                viewModel.getCars(viewModel.isRefresh())
                snackbar.dismiss()
            }
        snackbar.show()
    }
}
