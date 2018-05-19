package com.project.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.project.liveat500px.view.PhotoListItem;

/**
 * Created by winai on 9/9/2017.
 */

public class PhotoListAdapter extends BaseAdapter{

    @Override
    public int getCount() {
        return 10000;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return new PhotoListItem(parent.getContext());
    }
}
