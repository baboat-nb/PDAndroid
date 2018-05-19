package com.project.liveat500px.Dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Fame on 9/20/2017.
 */

public class Record {

    @SerializedName("recId")  private String recId;
    @SerializedName("recDate")  private Date recDate;
    @SerializedName("recRound")  private int recRound;
    @SerializedName("totalProfit")  private int totalProfit;
    @SerializedName("totalUrinate")  private int totalUrinate;
    @SerializedName("recCount")  private int recCount;
    @SerializedName("patient_patId_fk")  private String patient_patId_fk;

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }

    public int getRecRound() {
        return recRound;
    }

    public void setRecRound(int recRound) {
        this.recRound = recRound;
    }

    public int getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(int totalProfit) {
        this.totalProfit = totalProfit;
    }

    public int getTotalUrinate() {
        return totalUrinate;
    }

    public void setTotalUrinate(int totalUrinate) {
        this.totalUrinate = totalUrinate;
    }

    public int getRecCount() {
        return recCount;
    }

    public void setRecCount(int recCount) {
        this.recCount = recCount;
    }

    public String getPatient_patId_fk() {
        return patient_patId_fk;
    }

    public void setPatient_patId_fk(String patient_patId_fk) {
        this.patient_patId_fk = patient_patId_fk;
    }
}
