package com.project.liveat500px.Dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baboat on 20/10/2560.
 */

public class Food {
    @SerializedName("foodId")  private int foodId;
    @SerializedName("foodName")  private String foodName;
    @SerializedName("foodUnit")  private String foodUnit;
    @SerializedName("foodClassifier")  private String foodClassifier;
    @SerializedName("volume")  private String volume;
    @SerializedName("volumeType")  private String volumeType;
    @SerializedName("typeFood_id_fk")  private String typeFood_id_fk;


    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodUnit() {
        return foodUnit;
    }

    public void setFoodUnit(String foodUnit) {
        this.foodUnit = foodUnit;
    }

    public String getFoodClassifier() {
        return foodClassifier;
    }

    public void setFoodClassifier(String foodClassifier) {
        this.foodClassifier = foodClassifier;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(String volumeType) {
        this.volumeType = volumeType;
    }

    public String getTypeFood_id_fk() {
        return typeFood_id_fk;
    }

    public void setTypeFood_id_fk(String typeFood_id_fk) {
        this.typeFood_id_fk = typeFood_id_fk;
    }



}
