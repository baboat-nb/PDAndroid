package com.project.liveat500px.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.liveat500px.Dao.RecordFood;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.manager.http.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class FoodFragment4 extends Fragment {

    //ListView listView;
    //PhotoListAdapter listAdapter;
    LinearLayout FoodChooseList;
    String ReceiveName, ReceiveUnit, ReceiveClass, ReceiveFId;
    TextView FoodReceiveName, FoodReceiveUnit, FoodReceiveClass;
    Button FoodSaveButton;
    DataBaseHelper db;
    EditText FoodEditNumber;

    public FoodFragment4() {
        super();
    }

    @SuppressWarnings("unused")
    public static FoodFragment4 newInstance() {
        FoodFragment4 fragment = new FoodFragment4();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        db = new DataBaseHelper(getActivity());
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food4, container, false);

        FoodReceiveName = (TextView) rootView.findViewById(R.id.FoodReceiveName);
        FoodReceiveUnit = (TextView) rootView.findViewById(R.id.FoodReceiveUnit);
        FoodReceiveClass = (TextView) rootView.findViewById(R.id.FoodReceiveClass);
        FoodSaveButton = (Button) rootView.findViewById(R.id.FoodSaveButton);
        FoodEditNumber = (EditText) rootView.findViewById(R.id.FoodEditNumber);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ReceiveName = bundle.getString("Food");
            ReceiveUnit = bundle.getString("Unit");
            ReceiveClass = bundle.getString("Classified");
            ReceiveFId = bundle.getString("fId");
        }


        FoodReceiveName.setText(ReceiveName);
        FoodReceiveUnit.setText(ReceiveUnit);
        FoodReceiveClass.setText(ReceiveClass);

        FoodChooseList = (LinearLayout) rootView.findViewById(R.id.FoodChooseList);
        FoodChooseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodFragment2 fragment = new FoodFragment2();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                fragmentTransaction.commit();
                fragmentTransaction.remove(new FoodFragment4());
            }
        });

        FoodSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = db.getAllData();
                String editUnit = FoodEditNumber.getText().toString();
                if(editUnit.isEmpty()) {
                    FoodEditNumber.setError("กรุณาระบุจำนวน");
                }else {
                    final int intEditUnit = Integer.parseInt(editUnit);
                    Call<RecordFood> call = HttpManager.getInstance().getService().save(ReceiveFId, intEditUnit, id);
                    call.enqueue(new Callback<RecordFood>() {
                        @Override
                        public void onResponse(Call<RecordFood> call, Response<RecordFood> response) {
                            FoodFragment fragment = new FoodFragment();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contentContainer, fragment);
                            fragmentTransaction.commit();
                        }

                        @Override
                        public void onFailure(Call<RecordFood> call, Throwable t) {

                        }
                    });
                }
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
