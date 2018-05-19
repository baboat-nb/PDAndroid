package com.project.liveat500px.Dao;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.Date;


/**
 * Created by Fame on 9/20/2017.
 */

public class Dialysis {

    @SerializedName("diaId") private String diaId;
    @SerializedName("volDiaIn") private int volDiaIn;
    @SerializedName("timeDiaIn_start") private Date timeDiaIn_start;
    @SerializedName("timeDiaIn_end") private Date timeDiaIn_end;
    @SerializedName("volDiaOut") private int volDiaOut;
    @SerializedName("timeDiaOut_start") private Date timeDiaOut_start;
    @SerializedName("timeDiaOut_end") private Date timeDiaOut_end;
    @SerializedName("desDiaLiquid") private String desDiaLiquid;
    @SerializedName("profit") private int profit;
    @SerializedName("urinate") private int urinate;
    @SerializedName("intensity") private double intensity;
    @SerializedName("record_recId_fk") private int record_recId_fk;

    public String getDiaId() {
        return diaId;
    }

    public void setDiaId(String diaId) {
        this.diaId = diaId;
    }

    public int getVolDiaIn() {
        return volDiaIn;
    }

    public void setVolDiaIn(int volDiaIn) {
        this.volDiaIn = volDiaIn;
    }

    public Date getTimeDiaIn_start() {
        return timeDiaIn_start;
    }

    public void setTimeDiaIn_start(Date timeDiaIn_start) {
        this.timeDiaIn_start = timeDiaIn_start;
    }

    public Date getTimeDiaIn_end() {
        return timeDiaIn_end;
    }

    public void setTimeDiaIn_end(Date timeDiaIn_end) {
        this.timeDiaIn_end = timeDiaIn_end;
    }

    public int getVolDiaOut() {
        return volDiaOut;
    }

    public void setVolDiaOut(int volDiaOut) {
        this.volDiaOut = volDiaOut;
    }

    public Date getTimeDiaOut_start() {
        return timeDiaOut_start;
    }

    public void setTimeDiaOut_start(Date timeDiaOut_start) {
        this.timeDiaOut_start = timeDiaOut_start;
    }

    public Date getTimeDiaOut_end() {
        return timeDiaOut_end;
    }

    public void setTimeDiaOut_end(Date timeDiaOut_end) {
        this.timeDiaOut_end = timeDiaOut_end;
    }

    public String getDesDiaLiquid() {
        return desDiaLiquid;
    }

    public void setDesDiaLiquid(String desDiaLiquid) {
        this.desDiaLiquid = desDiaLiquid;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getUrinate() {
        return urinate;
    }

    public void setUrinate(int urinate) {
        this.urinate = urinate;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public int getRecord_recId_fk() {
        return record_recId_fk;
    }

    public void setRecord_recId_fk(int record_recId_fk) {
        this.record_recId_fk = record_recId_fk;
    }
}
