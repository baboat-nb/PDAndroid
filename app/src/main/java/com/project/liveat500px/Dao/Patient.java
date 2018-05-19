package com.project.liveat500px.Dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by baboat on 13/9/2560.
 */

public class Patient {

    @SerializedName("patId")  private String patId;
    @SerializedName("patUsername")  private String patUsername;
    @SerializedName("patPassword")  private String patPassword;
    @SerializedName("patFirstname")  private String patFirstname;
    @SerializedName("patLastname")  private String patLastname;
    @SerializedName("patSex")  private String patSex;
    @SerializedName("patBirthday")  private Date patBirthday;
    @SerializedName("patImage")  private String patImage;
    @SerializedName("hospName")  private String hospName;
    @SerializedName("tel")  private String tel;
    @SerializedName("telCenter")  private String telCenter;
    @SerializedName("telNurse")  private String telNurse;
    @SerializedName("weight")  private int weight;
    @SerializedName("patHeight")  private int patHeight;
    @SerializedName("bloodPressure")  private String bloodPressure;
    @SerializedName("patAge")  private int patAge;
    @SerializedName("email")  private String email;
    @SerializedName("doctor_docId_fk")  private String doctor_docId_fk;
    @SerializedName("notiStatus")  private int notiStatus;

    public int getNotiStatus() {
        return notiStatus;
    }

    public void setNotiStatus(int notiStatus) {
        this.notiStatus = notiStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDoctor_docId_fk() {
        return doctor_docId_fk;
    }

    public void setDoctor_docId_fk(String doctor_docId_fk) {
        this.doctor_docId_fk = doctor_docId_fk;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getPatUsername() {
        return patUsername;
    }

    public void setPatUsername(String patUsername) {
        this.patUsername = patUsername;
    }

    public String getPatPassword() {
        return patPassword;
    }

    public void setPatPassword(String patPassword) {
        this.patPassword = patPassword;
    }

    public String getPatFirstname() {
        return patFirstname;
    }

    public void setPatFirstname(String patFirstname) {
        this.patFirstname = patFirstname;
    }

    public String getPatLastname() {
        return patLastname;
    }

    public void setPatLastname(String patLastname) {
        this.patLastname = patLastname;
    }

    public String getPatSex() {
        return patSex;
    }

    public void setPatSex(String patSex) {
        this.patSex = patSex;
    }

    public Date getPatBirthday() {
        return patBirthday;
    }

    public void setPatBirthday(Date patBirthday) {
        this.patBirthday = patBirthday;
    }

    public String getPatImage() {
        return patImage;
    }

    public void setPatImage(String patImage) {
        this.patImage = patImage;
    }

    public String getHospName() {
        return hospName;
    }

    public void setHospName(String hospName) {
        this.hospName = hospName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTelCenter() {
        return telCenter;
    }

    public void setTelCenter(String telCenter) {
        this.telCenter = telCenter;
    }

    public String getTelNurse() {
        return telNurse;
    }

    public void setTelNurse(String telNurse) {
        this.telNurse = telNurse;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPatHeight() {
        return patHeight;
    }

    public void setPatHeight(int patHeight) {
        this.patHeight = patHeight;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getPatAge() {
        return patAge;
    }

    public void setPatAge(int patAge) {
        this.patAge = patAge;
    }
}
