package com.project.liveat500px.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.liveat500px.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Fame on 9/23/2017.
 */

public class CustomAdaptor extends BaseAdapter{
    Context mContext;
    ArrayList<String> dates;
    ArrayList<String> rounds;
    ArrayList<String> ids;

    public CustomAdaptor(Context context, ArrayList<String> dates, ArrayList<String> rounds , ArrayList<String> ids) {
        this.mContext = context;
        this.dates = dates;
        this.rounds = rounds;
        this.ids = ids;
    }

    public int getCount() {
        return dates.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return arg0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        View row = mInflater.inflate(R.layout.fragmenttext, parent, false);

        TextView date = (TextView)row.findViewById(R.id.dateIsNull);

        String d = dates.get(position).substring(0,2);
        switch (d) {
            case "01":
                d = "1";
                break;
            case "02":
                d = "2";
                break;
            case "03":
                d = "3";
                break;
            case "04":
                d = "4";
                break;
            case "05":
                d = "5";
                break;
            case "06":
                d = "6";
                break;
            case "07":
                d = "7";
                break;
            case "08":
                d = "8";
                break;
            case "09":
                d = "9";
                break;
        }
        String cutMonth = dates.get(position).substring(2,6);
        switch (cutMonth) {
            case "-01-":
                cutMonth = " มกราคม ";
                break;
            case "-02-":
                cutMonth = " กุมภาพันธ์ ";
                break;
            case "-03-":
                cutMonth = " มีนาคม ";
                break;
            case "-04-":
                cutMonth = " เมษายน ";
                break;
            case "-05-":
                cutMonth = " พฤษภาคม ";
                break;
            case "-06-":
                cutMonth = " มิถุนายน ";
                break;
            case "-07-":
                cutMonth = " กรกฏาคม ";
                break;
            case "-08-":
                cutMonth = " สิงหาคม ";
                break;
            case "-09-":
                cutMonth = " กันยายน ";
                break;
            case "-10-":
                cutMonth = " ตุลาคม ";
                break;
            case "-11-":
                cutMonth = " พฤศจิกายน ";
                break;
            case "-12-":
                cutMonth = " ธันวาคม ";
                break;
        }
        String y = dates.get(position).substring(6,10);
        int inty = Integer.parseInt(y)+543;
        date.setText(d+cutMonth+inty);

        TextView round  = (TextView) row.findViewById(R.id.roundIsNull);
        round.setText(rounds.get(position));

        TextView id = (TextView) row.findViewById(R.id.recIdIsNull);
        id.setText(ids.get(position));

        return row;
    }
}
