package com.project.liveat500px.fragment;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.liveat500px.Dao.Food;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.adapter.FoodAdaptor;
import com.project.liveat500px.manager.http.HttpManager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class FoodFragment3 extends Fragment {

    //ListView listView;
    //PhotoListAdapter listAdapter;
    DataBaseHelper myDb;
    CircleImageView FoodCircle;
    TextView Foodtxt;
    String myInt, mySubject;
    ArrayList<String> foodList = null;
    ArrayList<String> units = null;
    ArrayList<String> classified = null;
    ArrayList<Integer> icons = null;
    ArrayList<Integer> backgrounds = null;
    ArrayList<String> fId = null;
    FoodAdaptor adap;
    ListView listFood;
    private Context context;
    TextView FoodSubject;

    public FoodFragment3() {
        super();
    }

    @SuppressWarnings("unused")
    public static FoodFragment3 newInstance() {
        FoodFragment3 fragment = new FoodFragment3();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        this.context = getActivity();

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food3, container, false);
        initInstances(rootView, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mySubject = bundle.getString("subject");
        }

        listFood = (ListView) rootView.findViewById(R.id.listFood);
        FoodCircle = (CircleImageView) rootView.findViewById(R.id.FoodCircle);
        Foodtxt = (TextView) rootView.findViewById(R.id.Foodtxt);
        FoodSubject = (TextView) rootView.findViewById(R.id.FoodSubject);
        FoodSubject.setText(mySubject);
        showFood();
        return rootView;

    }

    public void showFood() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            myInt = bundle.getString("typeId");
            mySubject = bundle.getString("subject");
        }

        Call<List<Food>> call = HttpManager.getInstance().getService().findFoodByTypeId(myInt);
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response != null) {
                    List<Food> food = response.body();

                    int FoodPic = 0;
                    int FoodBack = 0;
                    switch (myInt) {
                        case "1":
                            FoodPic = R.drawable.chickennoback;
                            FoodBack = R.drawable.chicken_circle;
                            break;
                        case "2":
                            FoodPic = R.drawable.ricenoback;
                            FoodBack = R.drawable.rice_circle;
                            break;
                        case "5":
                            FoodPic = R.drawable.oilnoback;
                            FoodBack = R.drawable.oil_circle;
                            break;
                        case "7":
                            FoodPic = R.drawable.carrotnoback;
                            FoodBack = R.drawable.vegetablelow_circle;
                            break;
                        case "8":
                            FoodPic = R.drawable.carrotnoback;
                            FoodBack = R.drawable.vegetablemedium_circle;
                            break;
                        case "9":
                            FoodPic = R.drawable.carrotnoback;
                            FoodBack = R.drawable.vegetablehigh_circle;
                            break;
                        case "10":
                            FoodPic = R.drawable.grapenoback;
                            FoodBack = R.drawable.fruitlow_circle;
                            break;
                        case "11":
                            FoodPic = R.drawable.grapenoback;
                            FoodBack = R.drawable.fruitmedium_circle;
                            break;
                        case "12":
                            FoodPic = R.drawable.grapenoback;
                            FoodBack = R.drawable.fruithigh_circle;
                            break;
                    }

//                    for (int i = 0 ; i <food.size() ; i++){
//                        icons = new ArrayList<Integer>();
//                        icons.add(FoodPic);
//                    }
                    fId = new ArrayList<String>();
                    backgrounds = new ArrayList<Integer>();
                    icons = new ArrayList<Integer>();
                    foodList = new ArrayList<String>();
                    units = new ArrayList<String>();
                    classified = new ArrayList<String>();
                    for (int i = 0; i < food.size(); i++) {
                        String foodDetail = String.valueOf(food.get(i).getFoodName());
                        String foodUnit = String.valueOf(food.get(i).getFoodUnit());
                        String foodClassified = String.valueOf(food.get(i).getFoodClassifier());
                        String foodId = String.valueOf(food.get(i).getFoodId());
                        fId.add(foodId);
                        units.add(foodUnit);
                        classified.add(foodClassified);
                        foodList.add(foodDetail);
                        icons.add(FoodPic);
                        backgrounds.add(FoodBack);
                    }

                    adap = new FoodAdaptor(getActivity(), icons, foodList, backgrounds, units, classified);
                    listFood.setAdapter(adap);

                    listFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Bundle bundle = new Bundle();
                            String sendFood = foodList.get(position);
                            String sendUnit = units.get(position);
                            String sendClassified = classified.get(position);
                            String sendId = fId.get(position);
                            FoodFragment4 fragment = new FoodFragment4();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contentContainer, fragment);
                            bundle.putString("Food", sendFood);
                            bundle.putString("Unit", sendUnit);
                            bundle.putString("Classified", sendClassified);
                            bundle.putString("fId", sendId);
                            fragment.setArguments(bundle);
                            fragmentTransaction.commit();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {

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
