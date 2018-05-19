package com.project.liveat500px.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.liveat500px.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Fame on 9/23/2017.
 */

public class FoodMainAdaptor extends BaseAdapter{
    Context mContext;
    ArrayList<Integer> Mainicons;
    ArrayList<String> Mainfoods;
    ArrayList<Integer> Mainbackgrounds;
    ArrayList<String> Mainunits;
    ArrayList<String> Mainclassified;
    ArrayList<String> MainXunits;

    public FoodMainAdaptor(Context context, ArrayList<Integer> Mainicons,ArrayList<String> Mainfoods,ArrayList<Integer> Mainbackgrounds,
                           ArrayList<String> Mainunits,ArrayList<String> Mainclassified,ArrayList<String> MainXunits) {
        this.mContext = context;
        this.Mainicons = Mainicons;
        this.Mainfoods = Mainfoods;
        this.Mainbackgrounds = Mainbackgrounds;
        this.Mainunits = Mainunits;
        this.Mainclassified = Mainclassified;
        this.MainXunits = MainXunits;
    }

    public int getCount() {
        return Mainfoods.size();
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

        View row = mInflater.inflate(R.layout.fragmentmainfood, parent, false);

        CircleImageView FoodMainCircle = (CircleImageView) row.findViewById(R.id.FoodMainCircle);
        FoodMainCircle.setImageResource(Mainicons.get(position));
        FoodMainCircle.setBackgroundResource(Mainbackgrounds.get(position));

        TextView FoodMaintxt = (TextView) row.findViewById(R.id.FoodMaintxt);
        FoodMaintxt.setText(Mainfoods.get(position));

        TextView FoodMainCount = (TextView) row.findViewById(R.id.FoodMainCount);
        FoodMainCount.setText(MainXunits.get(position));

        TextView FoodMainUnit = (TextView) row.findViewById(R.id.FoodMainUnit);
        FoodMainUnit.setText(Mainunits.get(position));

        TextView FoodMainClass = (TextView) row.findViewById(R.id.FoodMainClass);
        FoodMainClass.setText(Mainclassified.get(position));

        return row;
    }
}
