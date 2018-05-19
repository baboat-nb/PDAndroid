package com.project.liveat500px.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.project.liveat500px.Dao.BloodSample;
import com.project.liveat500px.Dao.Record;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.ChooseBloodActivity;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.manager.http.HttpDateResponse;
import com.project.liveat500px.manager.http.HttpManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class ChooseBloodFragment extends Fragment {


    private DataBaseHelper myDb;
    private Context context;
    RadioGroup ChooseKGroup, ChoosePGroup, ChooseAlbGroup;
    RadioButton checkedKRadioButton, checkedPRadioButton, checkedAlbRadioButton;
    RadioButton OptionK1, OptionK2, OptionK3, OptionP1, OptionP2, OptionP3, OptionAlb1, OptionAlb2, OptionAlb3;
    RadioButton Kradio,Pradio,Albradio;
    String Ktext, Ptext, Albtext;
    Button ChooseBloodButton;
    //ListView listView;
    //PhotoListAdapter listAdapter;

    public ChooseBloodFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ChooseBloodFragment newInstance() {
        ChooseBloodFragment fragment = new ChooseBloodFragment();
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
        final View rootView = inflater.inflate(R.layout.fragment_chooseblood, container, false);

        ChooseBloodButton = (Button) rootView.findViewById(R.id.ChooseBloodButton);

        OptionK2 = (RadioButton) rootView.findViewById(R.id.OptionK2);
        OptionP2 = (RadioButton) rootView.findViewById(R.id.OptionP2);
        OptionAlb2 = (RadioButton) rootView.findViewById(R.id.OptionAlb2);

        OptionK2.setChecked(true);
        OptionP2.setChecked(true);
        OptionAlb2.setChecked(true);

        ChooseKGroup = (RadioGroup) rootView.findViewById(R.id.ChooseKGroup);
        int checkedK = ChooseKGroup.getCheckedRadioButtonId();
        Kradio = (RadioButton) rootView.findViewById(checkedK);
        Ktext = Kradio.getText().toString();
        ChooseKGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int KcheckedId) {
                checkedKRadioButton = (RadioButton) rootView.findViewById(KcheckedId);
                Ktext = checkedKRadioButton.getText().toString();
            }
        });

        ChoosePGroup = (RadioGroup) rootView.findViewById(R.id.ChoosePGroup);
        int checkedP = ChoosePGroup.getCheckedRadioButtonId();
        Pradio = (RadioButton) rootView.findViewById(checkedP);
        Ptext = Pradio.getText().toString();
        ChoosePGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int PcheckedId) {
                checkedPRadioButton = (RadioButton) rootView.findViewById(PcheckedId);
                Ptext = checkedPRadioButton.getText().toString();
            }
        });

        ChooseAlbGroup = (RadioGroup) rootView.findViewById(R.id.ChooseAlbGroup);
        int checkedAlb = ChooseAlbGroup.getCheckedRadioButtonId();
        Albradio = (RadioButton) rootView.findViewById(checkedAlb);
        Albtext = Albradio.getText().toString();
        ChooseAlbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int AlbcheckedId) {
                checkedAlbRadioButton = (RadioButton) rootView.findViewById(AlbcheckedId);
                Albtext = checkedAlbRadioButton.getText().toString();
            }
        });

        ChooseBloodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = myDb.getAllData();
                Call<BloodSample> call = HttpManager.getInstance().getService().fixBloodSample(Ktext, Ptext, Albtext, id);
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
                        Toast.makeText(getActivity(), "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();

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
