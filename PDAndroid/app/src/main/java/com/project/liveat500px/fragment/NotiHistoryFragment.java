package com.project.liveat500px.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.project.liveat500px.Dao.DialysisAlarm;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.adapter.NotificationAdaptor;
import com.project.liveat500px.manager.http.HttpManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotiHistoryFragment extends Fragment {

    private DataBaseHelper myDb;
    private Context context;
    private ListView listNotiHistory;
    TextView notiCreate, notiCalendar;
    private String id;
    public static ArrayList<String> listValue;
    ArrayList<String> notiHis, notiDate;
    ArrayList<Integer> notiType;
    NotificationAdaptor adp;
    int type;

    public NotiHistoryFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        this.context = getActivity();
        init(savedInstanceState);
        myDb = new DataBaseHelper(context);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_noti_history, container, false);
        notiCreate = (TextView) rootView.findViewById(R.id.notiCreate);
        notiCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmFragment fragment = new AlarmFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                fragmentTransaction.commit();
            }
        });
        notiCalendar = (TextView) rootView.findViewById(R.id.notiCalendar);
        notiCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertFragment fragment = new AlertFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                fragmentTransaction.commit();
            }
        });
        listNotiHistory = (ListView) rootView.findViewById(R.id.listNotiHistory);

        listValue = new ArrayList<String>();
        showListNoti();

        initInstances(rootView, savedInstanceState);
        return rootView;


    }

    private void showListNoti() {
        id = myDb.getAllData();
        Call<List<DialysisAlarm>> call = HttpManager.getInstance().getService().findNotiList(id);
        call.enqueue(new Callback<List<DialysisAlarm>>() {
            @Override
            public void onResponse(Call<List<DialysisAlarm>> call, Response<List<DialysisAlarm>> response) {
                if (response != null) {
                    List<DialysisAlarm> dia = response.body();
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    notiHis = new ArrayList<String>();
                    notiDate = new ArrayList<String>();
                    notiType = new ArrayList<Integer>();
                    for (int i = 0; i < dia.size(); i++) {
                        notiHis.add(dia.get(i).getNotiName());
                        notiDate.add(df.format(dia.get(i).getNotiDate()));

                        int pic = dia.get(i).getNotiType();
                        switch (pic) {
                            case 0:
                                pic = R.drawable.calen;
                                break;
                            case 1:
                                pic = R.drawable.graph;
                                break;
                            case 3:
                                pic = R.drawable.calen;
                                break;
                        }
                        notiType.add(pic);

                    }
                    adp = new NotificationAdaptor(getActivity(), notiHis, notiDate,notiType);
                    listNotiHistory.setAdapter(adp);
                    listNotiHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            type = notiType.get(position);
                            if(type == R.drawable.calen || type == R.drawable.calen){
                                AlertFragment fragment = new AlertFragment();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.contentContainer, fragment);
                                fragmentTransaction.commit();
                            }else if (type == R.drawable.graph){
                                GraphFragment fragment = new GraphFragment();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.contentContainer, fragment);
                                fragmentTransaction.commit();
                            }
                        }
                    });

                } else {
                    adp = new NotificationAdaptor(getActivity(), notiHis, notiDate,notiType);
                    listNotiHistory.setAdapter(adp);
                }
            }

            @Override
            public void onFailure(Call<List<DialysisAlarm>> call, Throwable t) {

            }
        });

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
