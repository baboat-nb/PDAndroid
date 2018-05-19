package com.project.liveat500px.Dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Fame on 11/7/2017.
 */

public class DialysisAlarm {

    @SerializedName("notiId")  private String notiId;
    @SerializedName("notiName")  private String notiName;
    @SerializedName("notiDate")  private Date notiDate;
    @SerializedName("notiType")  private int notiType;
    @SerializedName("patient_patId_fk")  private String patient_patId_fk;

    public String getNotiId() {
        return notiId;
    }

    public void setNotiId(String notiId) {
        this.notiId = notiId;
    }

    public String getNotiName() {
        return notiName;
    }

    public void setNotiName(String notiName) {
        this.notiName = notiName;
    }

    public Date getNotiDate() {
        return notiDate;
    }

    public void setNotiDate(Date notiDate) {
        this.notiDate = notiDate;
    }

    public int getNotiType() {
        return notiType;
    }

    public void setNotiType(int notiType) {
        this.notiType = notiType;
    }

    public String getPatient_patId_fk() {
        return patient_patId_fk;
    }

    public void setPatient_patId_fk(String patient_patId_fk) {
        this.patient_patId_fk = patient_patId_fk;
    }
}
