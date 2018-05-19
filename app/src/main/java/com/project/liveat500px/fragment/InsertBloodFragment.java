package com.project.liveat500px.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.project.liveat500px.Dao.BloodSample;
import com.project.liveat500px.Dao.Record;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.ChooseBloodActivity;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.manager.http.HttpManager;

import java.text.SimpleDateFormat;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class InsertBloodFragment extends Fragment {


    private DataBaseHelper myDb;
    private Context context;
    EditText InsertEditK,InsertEditP,InsertEditAlb;
    float EditK,EditP,EditAlb;
    Button InsertBloodButton;
    //ListView listView;
    //PhotoListAdapter listAdapter;

    public InsertBloodFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static InsertBloodFragment newInstance() {
        InsertBloodFragment fragment = new InsertBloodFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        init(savedInstanceState);
        myDb = new DataBaseHelper(context);
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_insertblood, container, false);

        InsertEditK = (EditText) rootView.findViewById(R.id.InsertEditK);
        InsertEditP = (EditText) rootView.findViewById(R.id.InsertEditP);
        InsertEditAlb = (EditText) rootView.findViewById(R.id.InsertEditAlb);
        InsertBloodButton = (Button) rootView.findViewById(R.id.InsertBloodButton);
        InsertBloodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = myDb.getAllData();
                EditK = Float.parseFloat(InsertEditK.getText().toString());
                EditP = Float.parseFloat(InsertEditP.getText().toString());
                EditAlb = Float.parseFloat(InsertEditAlb.getText().toString());
                Call<BloodSample> call = HttpManager.getInstance().getService().inputBloodSample(EditK,EditP,EditAlb,id);
                call.enqueue(new Callback<BloodSample>() {
                    @Override
                    public void onResponse(Call<BloodSample> call, Response<BloodSample> response) {
                        Intent intent = new Intent(getActivity(), ChooseBloodActivity.class);
                        getActivity().finish();
                        startActivity(intent);
//                        HistoryBloodFragment fragment = new HistoryBloodFragment();
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.contentBlood, fragment);
//                        fragmentTransaction.commit();
                        Toast.makeText(getActivity(),"บันทึกสำเร็จ",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<BloodSample> call, Throwable t) {

                    }
                });
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
