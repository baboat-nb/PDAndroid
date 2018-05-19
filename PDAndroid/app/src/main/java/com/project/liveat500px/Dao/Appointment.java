package com.project.liveat500px.Dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by baboat on 20/10/2560.
 */

public class Appointment {
    @SerializedName("appId")  private int appId;
    @SerializedName("appName")  private String appName;
    @SerializedName("appDate")  private Date appDate;
    @SerializedName("appTime")  private String appTime;
    @SerializedName("patient_patId_fk")  private String patient_patId_fk;


    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    public String getPatient_patId_fk() {
        return patient_patId_fk;
    }

    public void setPatient_patId_fk(String patient_patId_fk) {
        this.patient_patId_fk = patient_patId_fk;
    }
}
