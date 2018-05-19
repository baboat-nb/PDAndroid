package com.project.liveat500px.Dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Fame on 9/20/2017.
 */

public class RecordFood {

    @SerializedName("recFood_id")  private int recFood_id;
    @SerializedName("recFood_totalMeat")  private int recFood_totalMeat;
    @SerializedName("recFood_totalFlour")  private int recFood_totalFlour;
    @SerializedName("recFood_totalVeget")  private int recFood_totalVeget;
    @SerializedName("recFood_totalFruit")  private int recFood_totalFruit;
    @SerializedName("recFood_Oil")  private int recFood_Oil;
    @SerializedName("recFood_totalDrink")  private int recFood_totalDrink;
    @SerializedName("recFood_totalSugar")  private int recFood_totalSugar;
    @SerializedName("recFood_totalSodium")  private int recFood_totalSodium;
    @SerializedName("recAllFood_date")  private Date recAllFood_date;
    @SerializedName("patient_patId_fk")  private int patient_patId_fk;

    public int getRecFood_id() {
        return recFood_id;
    }

    public void setRecFood_id(int recFood_id) {
        this.recFood_id = recFood_id;
    }

    public int getRecFood_totalMeat() {
        return recFood_totalMeat;
    }

    public void setRecFood_totalMeat(int recFood_totalMeat) {
        this.recFood_totalMeat = recFood_totalMeat;
    }

    public int getRecFood_totalFlour() {
        return recFood_totalFlour;
    }

    public void setRecFood_totalFlour(int recFood_totalFlour) {
        this.recFood_totalFlour = recFood_totalFlour;
    }

    public int getRecFood_totalVeget() {
        return recFood_totalVeget;
    }

    public void setRecFood_totalVeget(int recFood_totalVeget) {
        this.recFood_totalVeget = recFood_totalVeget;
    }

    public int getRecFood_totalFruit() {
        return recFood_totalFruit;
    }

    public void setRecFood_totalFruit(int recFood_totalFruit) {
        this.recFood_totalFruit = recFood_totalFruit;
    }

    public int getRecFood_Oil() {
        return recFood_Oil;
    }

    public void setRecFood_Oil(int recFood_Oil) {
        this.recFood_Oil = recFood_Oil;
    }

    public int getRecFood_totalDrink() {
        return recFood_totalDrink;
    }

    public void setRecFood_totalDrink(int recFood_totalDrink) {
        this.recFood_totalDrink = recFood_totalDrink;
    }

    public int getRecFood_totalSugar() {
        return recFood_totalSugar;
    }

    public void setRecFood_totalSugar(int recFood_totalSugar) {
        this.recFood_totalSugar = recFood_totalSugar;
    }

    public int getRecFood_totalSodium() {
        return recFood_totalSodium;
    }

    public void setRecFood_totalSodium(int recFood_totalSodium) {
        this.recFood_totalSodium = recFood_totalSodium;
    }

    public Date getRecAllFood_date() {
        return recAllFood_date;
    }

    public void setRecAllFood_date(Date recAllFood_date) {
        this.recAllFood_date = recAllFood_date;
    }

    public int getPatient_patId_fk() {
        return patient_patId_fk;
    }

    public void setPatient_patId_fk(int patient_patId_fk) {
        this.patient_patId_fk = patient_patId_fk;
    }
}
