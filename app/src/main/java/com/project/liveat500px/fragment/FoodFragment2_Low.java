package com.project.liveat500px.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.liveat500px.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class FoodFragment2_Low extends Fragment {

    //ListView listView;
    //PhotoListAdapter listAdapter;
    LinearLayout FoodLayoutMeat;
    LinearLayout FoodLayoutRice;
    LinearLayout FoodLayoutLowVegetable;
    LinearLayout FoodLayoutLowFruit;
    LinearLayout FoodLayoutOil;
    TextView LowShowAll;

    public FoodFragment2_Low() {
        super();
    }

    @SuppressWarnings("unused")
    public static FoodFragment2_Low newInstance() {
        FoodFragment2_Low fragment = new FoodFragment2_Low();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food2_low, container, false);
        FoodLayoutMeat = (LinearLayout) rootView.findViewById(R.id.FoodLayoutMeat_Low);
        FoodLayoutRice = (LinearLayout) rootView.findViewById(R.id.FoodLayoutRice_Low);
        FoodLayoutLowVegetable = (LinearLayout) rootView.findViewById(R.id.FoodLayoutLowVegetable_Low);
        FoodLayoutLowFruit = (LinearLayout) rootView.findViewById(R.id.FoodLayoutLowFruit_Low);
        FoodLayoutOil = (LinearLayout) rootView.findViewById(R.id.FoodLayoutOil_Low);
        LowShowAll = (TextView) rootView.findViewById(R.id.LowShowAll);

        LowShowAll.setPaintFlags(LowShowAll.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        final Bundle bundle = new Bundle();

        LowShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodFragment2 fragment = new FoodFragment2();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        FoodLayoutMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodFragment3 fragment = new FoodFragment3();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                bundle.putString("typeId", "1");
                bundle.putString("subject", "เนื้อสัตว์");
                fragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        FoodLayoutRice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodFragment3 fragment = new FoodFragment3();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                bundle.putString("typeId", "2");
                bundle.putString("subject", "ข้าว/แป้ง");
                fragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        FoodLayoutLowVegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodFragment3 fragment = new FoodFragment3();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                bundle.putString("typeId", "7");
                bundle.putString("subject", "ผักโพแทสเซียมต่ำ");
                fragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        FoodLayoutLowFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodFragment3 fragment = new FoodFragment3();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                bundle.putString("typeId", "10");
                bundle.putString("subject", "ผลไม้โพแทสเซียมต่ำ");
                fragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        FoodLayoutOil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodFragment3 fragment = new FoodFragment3();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                bundle.putString("typeId", "5");
                bundle.putString("subject", "ไขมัน/น้ำมัน");
                fragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        initInstances(rootView, savedInstanceState);
        return rootView;

    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        //listView = (ListView) rootView.findViewById(R.id.listView);
        //listAdapter = new PhotoListAdapter();
        //listView.setAdapter(listAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }


}
