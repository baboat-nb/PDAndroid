package com.project.liveat500px;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by Jenny on 10/11/17.
 */

public class MyValueFormatter2 implements IAxisValueFormatter {

    private DecimalFormat mFormat;

    public MyValueFormatter2() {
        mFormat = new DecimalFormat("###,###,##0"); // use one decimal
    }

//    @Override
//    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//        // write your logic here
//        return mFormat.format(value); // e.g. append a dollar-sign
//    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value);
    }
}