package com.project.liveat500px.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.liveat500px.Dao.Dialysis;
import com.project.liveat500px.Dao.Record;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.adapter.CustomAdaptor;
import com.project.liveat500px.manager.http.HttpDateResponse;
import com.project.liveat500px.manager.http.HttpManager;
import com.project.liveat500px.manager.http.HttpTimeResponse;
import com.robertlevonyan.views.customfloatingactionbutton.CustomFloatingActionButton;
import com.robertlevonyan.views.customfloatingactionbutton.OnFabClickListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class MainFragment extends Fragment {

    DataBaseHelper myDb;
    private Context context;
    private ListView listDateAndRound;
    FancyButton custom_fab;
    private TextView dateIsNull;
    private TextView roundIsNull;
    SimpleDateFormat parseDate;
    private CustomAdaptor adapter ;
    ArrayList<String> dateList = null;
    ArrayList<String> roundList = null;
    ArrayList<String> idList = null;
    private TextView idIsNull;
    private Bundle bundle;
    private Fragment fragment;
    private String roundBunble;
    private String dateBundle;
    private String recIdBundle;
    //ListView listView;
    //PhotoListAdapter listAdapter;


    public MainFragment() {
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

        final View rootView = inflater.inflate(R.layout.fragment_a, container, false);


        roundIsNull = (TextView) rootView.findViewById(R.id.roundIsNull);
        listDateAndRound = (ListView) rootView.findViewById(R.id.listDateAndRound);
        idIsNull = (TextView) rootView.findViewById(R.id.recIdIsNull);

        custom_fab = (FancyButton) rootView.findViewById(R.id.custom_fab);
        custom_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainFragment2 fragment = new MainFragment2();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        showDateAndRound();

        initInstances(rootView, savedInstanceState);
        return rootView;


    }

    public void showDateAndRound(){
        String id = myDb.getAllData();
        parseDate = new SimpleDateFormat("dd-MM-yyyy");
        Call<List<Record>> call = HttpManager.getInstance().getService().findDateAndRound(id);
        call.enqueue(new Callback<List<Record>>() {
            @Override
            public void onResponse(Call<List<Record>> call, final Response<List<Record>> response) {
                if(response != null) {

                    List<Record> record = response.body();
                    dateList = new ArrayList<String>();
                    roundList = new ArrayList<String>();
                    idList = new ArrayList<String>();
                    for (int i = 0 ; i <record.size() ; i++){
                        String date = parseDate.format(record.get(i).getRecDate());
                        String round = String.valueOf(record.get(i).getRecRound());
                        String id = String.valueOf(record.get(i).getRecId());
                        dateList.add(date);
                        roundList.add(round);
                        idList.add(id);
                    }
                    adapter = new CustomAdaptor(getActivity(), dateList, roundList,idList);
                    listDateAndRound.setAdapter(adapter);

                    listDateAndRound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            roundBunble = roundList.get(position);
                            dateBundle = dateList.get(position);
                            recIdBundle = idList.get(position);
                            Call<Dialysis> callCheckDia = HttpTimeResponse.getInstance().getService().findDialysis(idList.get(position));
                            callCheckDia.enqueue(new Callback<Dialysis>() {
                                @Override
                                public void onResponse(Call<Dialysis> call, Response<Dialysis> response) {
                                    bundle = new Bundle();

                                    if(response != null){
                                        Dialysis checkDia = response.body();
                                        if(checkDia.getTimeDiaIn_end() == null){
                                            System.out.println("in end");
                                            fragment = new MainFragment5();
                                            bundle.putString("diaId",checkDia.getDiaId());
                                            bundle.putString("date",dateBundle);
                                            bundle.putString("round" , roundBunble);
                                            bundle.putString("recId" , recIdBundle);
                                            fragment.setArguments(bundle);
                                            getFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).addToBackStack(null).commit();
                                        }else if(checkDia.getTimeDiaOut_start() == null){
                                            System.out.println("out start");
                                            fragment = new MainFragment6();
                                            bundle.putString("ditId",checkDia.getDiaId());
                                            bundle.putString("date",dateBundle);
                                            bundle.putString("round" , roundBunble);
                                            bundle.putString("recId" , recIdBundle);
                                            fragment.setArguments(bundle);
                                            getFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).addToBackStack(null).commit();
                                            //a6
                                        }else if(checkDia.getTimeDiaOut_end() == null){
                                            System.out.println("out end");
                                            fragment = new MainFragment7();
                                            bundle.putString("ditId",checkDia.getDiaId());
                                            bundle.putString("date",dateBundle);
                                            bundle.putString("round" , roundBunble);
                                            bundle.putString("recId" , recIdBundle);
                                            fragment.setArguments(bundle);
                                            getFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).addToBackStack(null).commit();
                                            //7
                                        }
                                    }else{
                                        System.out.println("callback checkDia"+response.errorBody());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Dialysis> call, Throwable t) {
                                    System.out.println("failure checkDia" + t.toString());
                                }
                            });

                        }
                    });

                }else{
                    System.out.println("round and date error "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Record>> call, Throwable t) {
                System.out.println("round and date fillure " + t.toString());
            }
        });
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(final View rootView, Bundle savedInstanceState) {
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
