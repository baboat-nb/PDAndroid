package com.project.liveat500px.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.project.liveat500px.Dao.Patient;
import com.project.liveat500px.manager.http.HttpManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by baboat on 13/9/2560.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "PDProject";
    public static final String TABLE_NAME = "Patient";
    public static final String TABLE_NAME2 = "Alarm";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "PATID";
    public static final String COL_3 = "COUNTT";
    public static final String COL_4 = "PATHOUR";
    public static final String COL_5 = "PATMINUTE";
    public static final String COL_6 = "PATROUND";

    private SQLiteDatabase db;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,PATID INTEGER , COUNTT INTEGER )");

        db.execSQL("CREATE TABLE " + TABLE_NAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,PATHOUR INTEGER ,PATMINUTE INTEGER ,PATROUND VARCHAR)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME2);
        onCreate(db);
    }


    public boolean insertData(String patId) {
        boolean check = false;
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,patId);
        contentValues.put(COL_3,"0");
        long result = db.insert(TABLE_NAME,null,contentValues);

        db.close();
        if (result != -1){
            check = true;
        }
        return check;
    }
    public void setCountZero() {

        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_3,"0");
        long result = db.update(TABLE_NAME,contentValues,COL_1+ "="+"1",null);

        db.close();

    }

    public void insertAlarm(int hour , int minute , String round) {

        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4,hour);
        contentValues.put(COL_5,minute);
        contentValues.put(COL_6,round);
        long result = db.insert(TABLE_NAME2,null,contentValues);

        db.close();

    }
    public void updateTimeAlert(int hour , int minute , String round) {

        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4,hour);
        contentValues.put(COL_5,minute);
        long result = db.update(TABLE_NAME2,contentValues,COL_6+ " = "+round,null);

        db.close();

    }
    public List<Integer> getHourAlarm() {
        List<Integer> hour = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME2 + " ORDER BY " + COL_6 + " ASC ", null);
        while(res.moveToNext()) {
            if (hour == null) {
                hour = new ArrayList<>();
            }
            hour.add(res.getInt(1));
            System.out.println(res.getInt(1)+"LLL");
        }

        res.close();
        return hour;
    }

    public List<Integer> getMinuteAlarm() {
        List<Integer> minute  = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME2 +  " ORDER BY " + COL_6 + " ASC ", null);
        while(res.moveToNext()) {
            if (minute == null) {
                minute = new ArrayList<>();
            }
            minute.add(res.getInt(2));
        }
        res.close();
        return minute;
    }
    public void setCountOne() {

        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_3,"1");
        long result = db.update(TABLE_NAME,contentValues,COL_1+ "="+"1",null);

        db.close();

    }

    public List<Integer> getRoundAlarm() {
        List<Integer> round  = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME2+ " ORDER BY " + COL_6 + " ASC ", null);
        while(res.moveToNext()) {
            if (round == null) {
                round = new ArrayList<>();
            }
            round.add(res.getInt(3));
        }
        res.close();
        return round;
    }

    public boolean checkRound(String round) {
        boolean check  = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME2 + " WHERE "+COL_6+ " = "+round, null);
        if(res.moveToFirst()) {
            System.out.println(res.getInt(3));
            check = true;
        }
        res.close();
        return check;
    }
    public String getCountNoti() {
        String id  = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        if(res.moveToFirst())
            id = res.getString(2);
        res.close();
        return id;
    }

    public String getAllData() {
        String id  = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        if(res.moveToFirst())
            id = res.getString(1);
        res.close();
        return id;
    }




//    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
//        return db.query(TABLE_NAME, null, null, null, null, null, null);
//    }


}