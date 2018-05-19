package com.project.liveat500px.Dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Fame on 9/20/2017.
 */

public class recAllFood {

    @SerializedName("recAllFood_id")  private int recAllFood_id;
    @SerializedName("recAllFood_recFoodId_fk")  private int recAllFood_recFoodId_fk;
    @SerializedName("unit")  private int unit;
    @SerializedName("recAllFood_foodId_fk")  private int recAllFood_foodId_fk;


    public int getRecAllFood_id() {
        return recAllFood_id;
    }

    public void setRecAllFood_id(int recAllFood_id) {
        this.recAllFood_id = recAllFood_id;
    }

    public int getRecAllFood_recFoodId_fk() {
        return recAllFood_recFoodId_fk;
    }

    public void setRecAllFood_recFoodId_fk(int recAllFood_recFoodId_fk) {
        this.recAllFood_recFoodId_fk = recAllFood_recFoodId_fk;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getRecAllFood_foodId_fk() {
        return recAllFood_foodId_fk;
    }

    public void setRecAllFood_foodId_fk(int recAllFood_foodId_fk) {
        this.recAllFood_foodId_fk = recAllFood_foodId_fk;
    }
}
