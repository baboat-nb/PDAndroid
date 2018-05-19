package com.project.liveat500px.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.project.liveat500px.R;
import com.project.liveat500px.Service.AlarmReciever;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by baboat on 20/10/2560.
 */

public class NotificationAdaptor extends BaseAdapter {

    Context mContext;
    ArrayList<String> notiHis;
    ArrayList<String> notiDate;
    ArrayList<Integer> notiType;

    public NotificationAdaptor(Context context, ArrayList<String> notiHis, ArrayList<String> notiDate , ArrayList<Integer> notiType) {
        this.mContext = context;
        this.notiHis = notiHis;
        this.notiDate = notiDate;
        this.notiType = notiType;
    }

    public int getCount() {
        return notiHis.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return arg0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        View row = mInflater.inflate(R.layout.fragmentnotification, parent, false);
//
//        CircleImageView date = (CircleImageView) row.findViewById(R.id.FoodCircle);
//        date.setImageResource(icons.get(position));
//        date.setBackgroundResource(backgrounds.get(position));

        Date newdate = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String now = df.format(newdate);
        String dateNow = now.substring(0, 2);
        int intdateNow = Integer.parseInt(dateNow);
        int intdateYesterday = intdateNow - 1;
        String monthNow = now.substring(3, 5);
        String yearNow = now.substring(6, 10);
        String dateYesterday = intdateYesterday + "-" + monthNow + "-" + yearNow;

        TextView NotiHis = (TextView) row.findViewById(R.id.NotiHis);
        NotiHis.setText(notiHis.get(position));
        TextView NotiDate = (TextView) row.findViewById(R.id.NotiDate);

        String nDate = notiDate.get(position).substring(0, 2);
        String nMonth = notiDate.get(position).substring(2, 6);
        switch (nMonth) {
            case "-01-":
                nMonth = " มกราคม ";
                break;
            case "-02-":
                nMonth = " กุมภาพันธ์ ";
                break;
            case "-03-":
                nMonth = " มีนาคม ";
                break;
            case "-04-":
                nMonth = " เมษายน ";
                break;
            case "-05-":
                nMonth = " พฤษภาคม ";
                break;
            case "-06-":
                nMonth = " มิถุนายน ";
                break;
            case "-07-":
                nMonth = " กรกฏาคม ";
                break;
            case "-08-":
                nMonth = " สิงหาคม ";
                break;
            case "-09-":
                nMonth = " กันยายน ";
                break;
            case "-10-":
                nMonth = " ตุลาคม ";
                break;
            case "-11-":
                nMonth = " พฤศจิกายน ";
                break;
            case "-12-":
                nMonth = " ธันวาคม ";
                break;
        }
        String nYear = notiDate.get(position).substring(6, 10);
        int intNYear = Integer.parseInt(nYear)+543;
        String nFull = nDate + nMonth + intNYear;

        if (notiDate.get(position).equals(now)) {
            nFull = "วันนี้";
        } else if (notiDate.get(position).equals(dateYesterday)) {
            nFull = "เมื่อวานนี้";
        }

        NotiDate.setText(nFull);

        ImageView NotiIcon = (ImageView) row.findViewById(R.id.NotiIcon);
        NotiIcon.setImageResource(notiType.get(position));

        return row;

    }

}
