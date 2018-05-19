package com.project.liveat500px.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.project.liveat500px.Dao.BloodSample;
import com.project.liveat500px.Dao.Food;
import com.project.liveat500px.Dao.Patient;
import com.project.liveat500px.Dao.RecordFood;
import com.project.liveat500px.Dao.recAllFood;
import com.project.liveat500px.R;
import com.project.liveat500px.activity.DataBaseHelper;
import com.project.liveat500px.adapter.CustomAdaptor;
import com.project.liveat500px.adapter.FoodMainAdaptor;
import com.project.liveat500px.manager.http.HttpManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class FoodFragment extends Fragment {

    //ListView listView;
    //PhotoListAdapter listAdapter;
    Context context;
    FancyButton FoodBtnCreate;
    TextView FoodMeatNum, FoodFruitNum, FoodVegetableNum, FoodRiceNum, FoodOilNum, FoodNewDate, FoodDetail;
    TextView TotalMeat, TotalFruit, TotalVeget, TotalRice, TotalOil, MaxMeat, MaxFruit, MaxVeget, MaxRice, MaxOil;
    String id;
    DataBaseHelper db;
    ListView FoodMainList;
    ArrayList<String> fname = null;
    ArrayList<String> funits = null;
    ArrayList<String> fclassified = null;
    ArrayList<Integer> ficons = null;
    ArrayList<String> fxUnit = null;
    ArrayList<Integer> fbackgrounds = null;
    private FoodMainAdaptor adop;
    LinearLayout FoodLayoutDatail;
    int meat = 0;
    int vetget = 0;
    int fruit = 0;
    int rice = 0;
    int fat = 0;
    int x = 0;

    public FoodFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static FoodFragment newInstance() {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        this.context = getActivity();
        db = new DataBaseHelper(getActivity());
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_food, container, false);
        id = db.getAllData();
        FoodMeatNum = (TextView) rootView.findViewById(R.id.FoodMeatNum);
        FoodFruitNum = (TextView) rootView.findViewById(R.id.FoodFruitNum);
        FoodVegetableNum = (TextView) rootView.findViewById(R.id.FoodVegetableNum);
        FoodRiceNum = (TextView) rootView.findViewById(R.id.FoodRiceNum);
        FoodOilNum = (TextView) rootView.findViewById(R.id.FoodOilNum);
        FoodNewDate = (TextView) rootView.findViewById(R.id.FoodNewDate);
        FoodDetail = (TextView) rootView.findViewById(R.id.FoodDetail);
        FoodMainList = (ListView) rootView.findViewById(R.id.FoodMainList);
        FoodLayoutDatail = (LinearLayout) rootView.findViewById(R.id.FoodLayoutDetail);


        Date d = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String setDate = df.format(d);
        String cutDate = setDate.substring(0, 2);
        switch (cutDate) {
            case "01":
                cutDate = "1";
                break;
            case "02":
                cutDate = "2";
                break;
            case "03":
                cutDate = "3";
                break;
            case "04":
                cutDate = "4";
                break;
            case "05":
                cutDate = "5";
                break;
            case "06":
                cutDate = "6";
                break;
            case "07":
                cutDate = "7";
                break;
            case "08":
                cutDate = "8";
                break;
            case "09":
                cutDate = "9";
                break;
        }
        String cutMonth = setDate.substring(2, 6);
        String cutYear = setDate.substring(6, 10);
        int cy = Integer.parseInt(cutYear)+543;
        switch (cutMonth) {
            case "-01-":
                cutMonth = " มกราคม ";
                break;
            case "-02-":
                cutMonth = " กุมภาพันธ์ ";
                break;
            case "-03-":
                cutMonth = " มีนาคม ";
                break;
            case "-04-":
                cutMonth = " เมษายน ";
                break;
            case "-05-":
                cutMonth = " พฤษภาคม ";
                break;
            case "-06-":
                cutMonth = " มิถุนายน ";
                break;
            case "-07-":
                cutMonth = " กรกฏาคม ";
                break;
            case "-08-":
                cutMonth = " สิงหาคม ";
                break;
            case "-09-":
                cutMonth = " กันยายน ";
                break;
            case "-10-":
                cutMonth = " ตุลาคม ";
                break;
            case "-11-":
                cutMonth = " พฤศจิกายน ";
                break;
            case "-12-":
                cutMonth = " ธันวาคม ";
                break;
        }
        String finalDate = cutDate + cutMonth + cy;
        FoodNewDate.setText(finalDate);


        Call<Patient> call = HttpManager.getInstance().getService().findPatient(id);
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response != null) {
                    Patient p = response.body();

                    int patHeight = p.getPatHeight();
                    int age = p.getPatAge();
                    String patSex = p.getPatSex();
                    int realbmi;
                    if (patSex.equalsIgnoreCase("ชาย")) {
                        realbmi = patHeight - 100;
                    } else {
                        realbmi = patHeight - 105;
                    }

                    if (realbmi < 50 && age >= 60) {
                        meat = 12;
                        fruit = 1;
                        vetget = 4;
                        rice = 7;
                        fat = 6;
                    } else if (realbmi >= 50 && realbmi < 55 && age >= 60) {
                        meat = 14;
                        fruit = 1;
                        vetget = 4;
                        rice = 7;
                        fat = 6;
                    } else if (realbmi >= 55 && realbmi < 60 && age >= 60) {
                        meat = 15;
                        fruit = 1;
                        vetget = 4;
                        rice = 7;
                        fat = 6;
                    } else if (realbmi >= 60 && realbmi < 65 && age >= 60) {
                        meat = 16;
                        fruit = 2;
                        vetget = 4;
                        rice = 9;
                        fat = 7;
                    } else if (realbmi >= 65 && realbmi < 70 && age >= 60) {
                        meat = 18;
                        fruit = 2;
                        vetget = 5;
                        rice = 9;
                        fat = 8;
                    } else if (realbmi >= 70 && age >= 60) {
                        meat = 19;
                        fruit = 3;
                        vetget = 5;
                        rice = 9;
                        fat = 8;
                    } else if (realbmi < 50 && age < 60) {
                        meat = 12;
                        fruit = 2;
                        vetget = 4;
                        rice = 7;
                        fat = 8;
                    } else if (realbmi >= 50 && realbmi < 55 && age < 60) {
                        meat = 14;
                        fruit = 2;
                        vetget = 4;
                        rice = 7;
                        fat = 8;
                    } else if (realbmi >= 55 && realbmi < 60 && age < 60) {
                        meat = 15;
                        fruit = 2;
                        vetget = 4;
                        rice = 8;
                        fat = 8;
                    } else if (realbmi >= 60 && realbmi < 65 && age < 60) {
                        meat = 16;
                        fruit = 2;
                        vetget = 4;
                        rice = 10;
                        fat = 10;
                    } else if (realbmi >= 65 && realbmi < 70 && age < 60) {
                        meat = 18;
                        fruit = 3;
                        vetget = 5;
                        rice = 9;
                        fat = 8;
                    } else if (realbmi >= 70 && age < 60) {
                        meat = 19;
                        fruit = 3;
                        vetget = 5;
                        rice = 10;
                        fat = 10;
                    }

                    String setmeat = meat + "";
                    String setfruit = fruit + "";
                    String setveget = vetget + "";
                    String setrice = rice + "";
                    String setoil = fat + "";
                    FoodMeatNum.setText(setmeat);
                    FoodFruitNum.setText(setfruit);
                    FoodVegetableNum.setText(setveget);
                    FoodRiceNum.setText(setrice);
                    FoodOilNum.setText(setoil);
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {

            }
        });


        FoodLayoutDatail.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("ปริมาณสารอาหารที่เลือก");
                // alert.setMessage("Message");
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View rv = inflater.inflate(R.layout.popup_window, null);
                TotalMeat = (TextView) rv.findViewById(R.id.TotalMeat);
                TotalFruit = (TextView) rv.findViewById(R.id.TotalFruit);
                TotalVeget = (TextView) rv.findViewById(R.id.TotalVeget);
                TotalRice = (TextView) rv.findViewById(R.id.TotalRice);
                TotalOil = (TextView) rv.findViewById(R.id.TotalOil);
                MaxMeat = (TextView) rv.findViewById(R.id.MaxMeat);
                MaxFruit = (TextView) rv.findViewById(R.id.MaxFruit);
                MaxVeget = (TextView) rv.findViewById(R.id.MaxVeget);
                MaxRice = (TextView) rv.findViewById(R.id.MaxRice);
                MaxOil = (TextView) rv.findViewById(R.id.MaxOil);
                Call<RecordFood> callTotal = HttpManager.getInstance().getService().findTotal(id);
                callTotal.enqueue(new Callback<RecordFood>() {
                    @Override
                    public void onResponse(Call<RecordFood> call, Response<RecordFood> response) {
                        RecordFood rf = response.body();

                        int tMeat = rf.getRecFood_totalMeat();
                        int tFruit = rf.getRecFood_totalFruit();
                        int tVeget = rf.getRecFood_totalVeget();
                        int tRice = rf.getRecFood_totalFlour();
                        int tOil = rf.getRecFood_Oil();

                        String totalMeat = tMeat + "";
                        String totalFruit = tFruit + "";
                        String totalVeget = tVeget + "";
                        String totalRice = tRice + "";
                        String totalOil = tOil + "";

                        TotalMeat.setText(totalMeat);
                        TotalFruit.setText(totalFruit);
                        TotalVeget.setText(totalVeget);
                        TotalRice.setText(totalRice);
                        TotalOil.setText(totalOil);

                        if (tMeat > meat) {
                            MaxMeat.setVisibility(View.VISIBLE);
                        } else if (tMeat <= meat) {
                            MaxMeat.setVisibility(View.INVISIBLE);
                        }

                        if (tFruit > fruit) {
                            MaxFruit.setVisibility(View.VISIBLE);
                        } else if (tFruit <= fruit) {
                            MaxFruit.setVisibility(View.INVISIBLE);
                        }

                        if (tVeget > vetget) {
                            MaxVeget.setVisibility(View.VISIBLE);
                        } else if (tVeget <= vetget) {
                            MaxVeget.setVisibility(View.INVISIBLE);
                        }

                        if (tRice > rice) {
                            MaxRice.setVisibility(View.VISIBLE);
                        } else if (tRice <= rice) {
                            MaxRice.setVisibility(View.INVISIBLE);
                        }

                        if (tOil > fat) {
                            MaxOil.setVisibility(View.VISIBLE);
                        } else if (tOil <= fat) {
                            MaxOil.setVisibility(View.INVISIBLE);
                        }

                    }

                    @Override
                    public void onFailure(Call<RecordFood> call, Throwable t) {

                    }
                });

                alert.setView(rv);

                alert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Your action here
                    }
                });


                alert.show();

            }

        });

        Call<List<recAllFood>> callrecAllFood = HttpManager.getInstance().getService().findFoodByPatId(id);
        callrecAllFood.enqueue(new Callback<List<recAllFood>>() {
            @Override
            public void onResponse(Call<List<recAllFood>> call, Response<List<recAllFood>> response) {
                final List<recAllFood> r = response.body();
                fxUnit = new ArrayList<String>();
                for (int i = 0; i < r.size(); i++) {
                    String xUnit = String.valueOf(r.get(i).getUnit());
                    fxUnit.add(xUnit);
                }

                Call<List<Food>> callFood = HttpManager.getInstance().getService().findFoodByFoodId(r);
                callFood.enqueue(new Callback<List<Food>>() {
                    @Override
                    public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {

                        List<Food> food = response.body();
                        fbackgrounds = new ArrayList<Integer>();
                        ficons = new ArrayList<Integer>();
                        fname = new ArrayList<String>();
                        funits = new ArrayList<String>();
                        fclassified = new ArrayList<String>();

                        for (int i = 0; i < food.size(); i++) {
                            String fName = String.valueOf(food.get(i).getFoodName());
                            String fClass = String.valueOf(food.get(i).getFoodClassifier());
                            String fUnit = String.valueOf(food.get(i).getFoodUnit());
                            String fkId = String.valueOf(food.get(i).getTypeFood_id_fk());
                            int FoodPic = 0;
                            int FoodBack = 0;
                            switch (fkId) {
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

                            funits.add(fUnit);
                            fclassified.add(fClass);
                            fname.add(fName);
                            ficons.add(FoodPic);
                            fbackgrounds.add(FoodBack);

                        }

                        adop = new FoodMainAdaptor(getActivity(), ficons, fname, fbackgrounds, funits, fclassified, fxUnit);

                        FoodMainList.setAdapter(adop);

                        FoodMainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                final recAllFood raf = r.get(i);

                                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                alert.setTitle("คุณต้องการลบใช่หรือไม่");
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                alert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        final Call<Void> callDelete = HttpManager.getInstance().getService().deleteFood(raf);
                                        callDelete.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                FoodFragment fragment = new FoodFragment();
                                                FragmentManager fragmentManager = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.contentContainer, fragment);
                                                fragmentTransaction.commit();
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {

                                            }
                                        });

                                    }
                                });

                                alert.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                    }
                                });


                                alert.show();


                            }
                        });
                    }


                    @Override
                    public void onFailure(Call<List<Food>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<recAllFood>> call, Throwable t) {

            }
        });
        FoodBtnCreate = (FancyButton) rootView.findViewById(R.id.FoodBtnCreate);
        FoodBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<BloodSample> callB = HttpManager.getInstance().getService().findBloodSample(id);
                callB.enqueue(new Callback<BloodSample>() {
                    @Override
                    public void onResponse(Call<BloodSample> call, Response<BloodSample> response) {
                        BloodSample bsp = response.body();
                        String potassium = bsp.getGroupOfPo();
                        String subpotassium = potassium.substring(10, potassium.length());
                        if(subpotassium.equals("ต่ำ")){
                            FoodFragment2_High fragment = new FoodFragment2_High();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contentContainer, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }else if (subpotassium.equals("สูง")){
                            FoodFragment2_Low fragment = new FoodFragment2_Low();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contentContainer, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }else if (subpotassium.equals("ปานกลาง")){
                            FoodFragment2 fragment = new FoodFragment2();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contentContainer, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
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
