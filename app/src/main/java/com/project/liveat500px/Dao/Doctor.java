package com.project.liveat500px.Dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


/**
 * Created by Fame on 9/20/2017.
 */

public class Doctor {

    @SerializedName("docId") private int docId;
    @SerializedName("docUsername") private String docUsername;
    @SerializedName("docPassword") private String docPassword;
    @SerializedName("docFirstname") private String docFirstname;
    @SerializedName("docLastname") private String docLastname;
    @SerializedName("docHospName") private String docHospName;
    @SerializedName("status") private String status;

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getDocUsername() {
        return docUsername;
    }

    public void setDocUsername(String docUsername) {
        this.docUsername = docUsername;
    }

    public String getDocPassword() {
        return docPassword;
    }

    public void setDocPassword(String docPassword) {
        this.docPassword = docPassword;
    }

    public String getDocFirstname() {
        return docFirstname;
    }

    public void setDocFirstname(String docFirstname) {
        this.docFirstname = docFirstname;
    }

    public String getDocLastname() {
        return docLastname;
    }

    public void setDocLastname(String docLastname) {
        this.docLastname = docLastname;
    }

    public String getDocHospName() {
        return docHospName;
    }

    public void setDocHospName(String docHospName) {
        this.docHospName = docHospName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
