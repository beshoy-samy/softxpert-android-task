package com.example.cars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.cars.data.Car
import com.example.cars.databinding.ItemCarBinding

class CarsAdapter : RecyclerView.Adapter<CarsAdapter.CarVH>() {

    private var cars = mutableListOf<Car>()
    val allCars: List<Car>
        get() = cars


    fun addCars(newCars: MutableList<Car>, refresh: Boolean) {
        if (refresh) {
            cars = newCars
            notifyDataSetChanged()
        }
        else{
            cars.addAll(newCars)
            notifyItemRangeInserted(cars.size - 1, newCars.size)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarVH {
        return CarVH.from(parent)
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    override fun onBindViewHolder(holder: CarVH, position: Int) {
        holder.bind(cars[position])
    }

    class CarVH(private val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(car: Car) {
            Glide.with(binding.img.context)
                .load(car.imageUrl)
                .placeholder(R.drawable.ic_broken_image)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.img)

            binding.brand.text = car.brand
            binding.year.text = car.constructionYear
            binding.year.visibility =
                if (car.constructionYear == null) View.INVISIBLE else View.VISIBLE
            binding.used.text =
                if (car.used) binding.used.context.getString(R.string.used) else binding.used.context.getString(
                    R.string.new_car
                )
        }

        companion object {
            fun from(parent: ViewGroup): CarVH {
                return CarVH(
                    ItemCarBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

    }

}