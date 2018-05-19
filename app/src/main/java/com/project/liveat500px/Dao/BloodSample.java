package com.project.liveat500px.Dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by baboat on 20/10/2560.
 */

public class BloodSample {
    @SerializedName("bSample_Id")  private int bSample_Id;
    @SerializedName("bPotass")  private float bPotass;
    @SerializedName("bPhos")  private float bPhos;
    @SerializedName("bAlb")  private float bAlb;
    @SerializedName("dateAddBS")  private Date dateAddBS;
    @SerializedName("groupOfPo")  private String groupOfPo;
    @SerializedName("groupOfPhos")  private String groupOfPhos;
    @SerializedName("groupOfAlb")  private String groupOfAlb;
    @SerializedName("patient_patId_fk")  private int patient_patId_fk;

    public int getbSample_Id() {
        return bSample_Id;
    }

    public void setbSample_Id(int bSample_Id) {
        this.bSample_Id = bSample_Id;
    }

    public float getbPotass() {
        return bPotass;
    }

    public void setbPotass(float bPotass) {
        this.bPotass = bPotass;
    }

    public float getbPhos() {
        return bPhos;
    }

    public void setbPhos(float bPhos) {
        this.bPhos = bPhos;
    }

    public float getbAlb() {
        return bAlb;
    }

    public void setbAlb(float bAlb) {
        this.bAlb = bAlb;
    }

    public Date getDateAddBS() {
        return dateAddBS;
    }

    public void setDateAddBS(Date dateAddBS) {
        this.dateAddBS = dateAddBS;
    }

    public String getGroupOfPo() {
        return groupOfPo;
    }

    public void setGroupOfPo(String groupOfPo) {
        this.groupOfPo = groupOfPo;
    }

    public String getGroupOfPhos() {
        return groupOfPhos;
    }

    public void setGroupOfPhos(String groupOfPhos) {
        this.groupOfPhos = groupOfPhos;
    }

    public String getGroupOfAlb() {
        return groupOfAlb;
    }

    public void setGroupOfAlb(String groupOfAlb) {
        this.groupOfAlb = groupOfAlb;
    }

    public int getPatient_patId_fk() {
        return patient_patId_fk;
    }

    public void setPatient_patId_fk(int patient_patId_fk) {
        this.patient_patId_fk = patient_patId_fk;
    }
}
