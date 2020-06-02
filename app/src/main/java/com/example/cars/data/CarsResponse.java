
package com.example.cars.data;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarsResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<Car> cars = null;
    @SerializedName("error")
    @Expose
    private Error error;

    public Integer getStatus() {
        return status;
    }

    public List<Car> getCars() {
        return cars;
    }

    public Error getError() {
        return error;
    }

    public boolean isSuccess() {
        return error == null;
    }

}