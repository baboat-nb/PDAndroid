package com.project.liveat500px.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.liveat500px.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by baboat on 20/10/2560.
 */

public class FoodAdaptor extends BaseAdapter {

    Context mContext;
    ArrayList<Integer> icons;
    ArrayList<String> foods;
    ArrayList<Integer> backgrounds;
    ArrayList<String> units;
    ArrayList<String> classified;

    public FoodAdaptor(Context context, ArrayList<Integer> icons, ArrayList<String> foods ,ArrayList<Integer> backgrounds,ArrayList<String> units,ArrayList<String> classified ) {
        this.mContext = context;
        this.icons = icons;
        this.foods = foods;
        this.backgrounds = backgrounds;
        this.units = units;
        this.classified = classified;
    }

    public int getCount() {
        return foods.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return arg0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        View row = mInflater.inflate(R.layout.fragmentfood, parent, false);

        CircleImageView date = (CircleImageView) row.findViewById(R.id.FoodCircle);
        date.setImageResource(icons.get(position));
        date.setBackgroundResource(backgrounds.get(position));

        TextView round = (TextView) row.findViewById(R.id.Foodtxt);
        round.setText(foods.get(position));

        TextView unit = (TextView) row.findViewById(R.id.FoodUnit);
        unit.setText(units.get(position));
        TextView classi = (TextView) row.findViewById(R.id.FoodClass);
        classi.setText(classified.get(position));
        return row;
    }
}
