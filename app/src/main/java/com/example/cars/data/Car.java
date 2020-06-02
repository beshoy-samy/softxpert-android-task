
package com.example.cars.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Car {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("constructionYear")
    @Expose
    private String constructionYear;
    @SerializedName("isUsed")
    @Expose
    private Boolean isUsed;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;


    public Integer getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getConstructionYear() {
        return constructionYear;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
