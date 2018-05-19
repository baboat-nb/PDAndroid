package com.project.liveat500px.activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onesignal.OneSignal;
import com.project.liveat500px.Dao.Patient;
import com.project.liveat500px.R;
import com.project.liveat500px.Service.NotiGraphService;
import com.project.liveat500px.Service.NotiService;
import com.project.liveat500px.fragment.AlarmFragment;
import com.project.liveat500px.fragment.AlertFragment;
import com.project.liveat500px.fragment.DataFragment;
import com.project.liveat500px.fragment.FoodFragment;
import com.project.liveat500px.fragment.GraphFragment;
import com.project.liveat500px.fragment.MainFragment;
import com.project.liveat500px.fragment.NotiHistoryFragment;
import com.project.liveat500px.manager.http.HttpManager;
import com.squareup.picasso.Picasso;
import com.ss.bottomnavigation.BottomNavigation;
import com.ss.bottomnavigation.TabItem;
import com.ss.bottomnavigation.events.OnSelectedItemChangeListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    TextView txtedit;
    TabItem tab_create;
    TabItem tab_data;
    TabItem tab_graph;
    TabItem tab_food;
    TabItem tab_alert;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    BottomNavigation bottom_navigation;
    //    TextView phone;
    DataBaseHelper myDb;
    TextView txtname;
    TextView txtsurname;
    CircleImageView profile_image;
    TextView phoneKidney;
    TextView phoneHospital;
    private final int IMG_REQUEST = 1;
    public static final int GET_FROM_GALLERY = 3;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    TextView MainChangePassword;
    Button btnlogout;
    Bitmap bitmap;
    private final Context mContext = this;
    LinearLayout MainBloodResult,CallKidney,CallHospital;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startService(new Intent(this,NotiService.class));
        startService(new Intent(this,NotiGraphService.class));

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

//        Thread t = new Thread() {
//
//            @Override
//            public void run() {
//                try {
//                    while (!isInterrupted()) {
//                        Thread.sleep(800);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//                                String name = prefs.getString("name", null);
//                                String lastName = prefs.getString("lastName", null);
//                                String telDia = prefs.getString("telDia", null);
//                                String telHos = prefs.getString("telHos", null);
//                                txtname.setText(name);
//                                txtsurname.setText(lastName);
//                                phoneHospital.setText(telHos);
//                                phoneKidney.setText(telDia);
//                            }
//                        });
//                    }
//                } catch (InterruptedException e) {
//                }
//            }
//        };
//
//        t.start();

        myDb = new DataBaseHelper(this);

        initInstances();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, MainFragment.newInstance())
                    .commit();
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }

//    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//    String name = prefs.getString("name", null);
//    String lastname = prefs.getString("lastname", null);
//    String telDia = prefs.getString("telDia", null);
//    String telHos = prefs.getString("telHos", null);
//        System.out.println("BOAT "+name+lastname+telDia+telHos);
//        txtname.setText(name);
//        txtsurname.setText(lastname);
//        phoneHospital.setText(telHos);
//        phoneKidney.setText(telDia);

    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        final BottomNavigation bottomNavigation = (BottomNavigation) findViewById(R.id.bottom_navigation);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        tab_create = (TabItem) findViewById(R.id.tab_create);
        tab_data = (TabItem) findViewById(R.id.tab_data);
        tab_graph = (TabItem) findViewById(R.id.tab_graph);
        tab_food = (TabItem) findViewById(R.id.tab_food);
        tab_alert = (TabItem) findViewById(R.id.tab_alert);
        txtedit = (TextView) findViewById(R.id.txtedit);
        txtname = (TextView) findViewById(R.id.txtname);
        txtsurname = (TextView) findViewById(R.id.txtsurname);
        btnlogout = (Button) findViewById(R.id.btnlogout);
        phoneKidney = (TextView) findViewById(R.id.phoneKidney);
        phoneHospital = (TextView) findViewById(R.id.phoneHospital);
        MainChangePassword = (TextView) findViewById(R.id.MainChangePassword);
        MainBloodResult = (LinearLayout) findViewById(R.id.MainBloodResult);
        CallKidney = (LinearLayout) findViewById(R.id.CallKidney);
        CallHospital = (LinearLayout) findViewById(R.id.CallHospital);

        String id = myDb.getAllData();
        Call call = HttpManager.getInstance().getService().findPatient(id);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (response != null) {
                        Patient patient = (Patient) response.body();
                        txtname.setText(patient.getPatFirstname());
                        txtsurname.setText(patient.getPatLastname());
                        String phoneH = patient.getTelNurse() + "";
                        String phonek = patient.getTelCenter() + "";
                        if(phoneH.equalsIgnoreCase("null")) {
                            phoneHospital.setText("-");
                        }else if (phoneH.isEmpty()){
                            phoneHospital.setText("-");
                        }else{
                            phoneHospital.setText(phoneH);
                        }

                        if(phonek.equalsIgnoreCase("null")) {
                            phoneKidney.setText("-");
                        }else if (phonek.isEmpty()){
                            phoneKidney.setText("-");
                        }else{
                            phoneKidney.setText(phonek);
                        }
                        Picasso.with(getApplicationContext())
                                .load("http://13.229.94.27:8080"+patient.getPatImage())
                                .placeholder(R.drawable.ic_launcher) // เอารูปโหลดมาใส่ให้ที
                                .into(profile_image);
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

        MainBloodResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChooseBloodActivity.class);
                startActivity(intent);
            }
        });

        MainChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, EditPassActivity.class);
                startActivity(intent);
            }
        });
        profile_image = (CircleImageView) findViewById(R.id.profile_image);

        uploadImage();// method uploadImage

        CallKidney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_no = phoneKidney.getText().toString().replaceAll("-", "");
                Intent sIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone_no));
                sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(sIntent);
            }
        });


        CallHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_no = phoneHospital.getText().toString().replaceAll("-", "");
                Intent sIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone_no));
                sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(sIntent);
            }
        });
        txtedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditProActivity.class);
                startActivity(intent);
            }
        });


        actionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        );

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApplicationContext().deleteDatabase("PDProject");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        bottom_navigation = (BottomNavigation) findViewById(R.id.bottom_navigation);
        bottomNavigation.setDefaultItem(0);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Kanit-Regular.ttf");
        bottomNavigation.setTypeface(font);

        bottomNavigation.setOnSelectedItemChangeListener(new OnSelectedItemChangeListener() {
            @Override
            public void onSelectedItemChanged(int itemId) {
                switch (itemId) {
                    case R.id.tab_create:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contentContainer, new MainFragment()).commit();
                        break;
                    case R.id.tab_data:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contentContainer, new DataFragment()).commit();
                        break;
                    case R.id.tab_graph:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contentContainer, new GraphFragment()).commit();
                        break;
                    case R.id.tab_food:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contentContainer, new FoodFragment()).commit();
                        break;
                    case R.id.tab_alert:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contentContainer, new NotiHistoryFragment()).commit();
                        break;
                }
            }
        });
    }

    public void uploadImage() {

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage();
            }
        });
    }


    public String getRealPathFromUri(final Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(mContext, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(mContext, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(mContext, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(mContext, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private String getDataColumn(Context context, Uri uri, String selection,
                                 String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    private void selectedImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == GET_FROM_GALLERY && resultCode == MainActivity.RESULT_OK) {
//            Uri selectedImage = data.getData();
//            Bitmap bitmap = null;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                BitMapToString(bitmap);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_image:
                selectImage();
                break;

        }
    }
    public void uploadImageToServer(final Uri fileUri) {

        String id = myDb.getAllData();
        String filePath = getRealPathFromUri(fileUri);
        if (filePath != null && !filePath.isEmpty()) {
            File file = new File(filePath);

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            // MultipartBody.Part is used to send also the actual filename
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            Call<Patient> upload = HttpManager.getInstance().getService().uploadImage(body, id);
            upload.enqueue(new Callback<Patient>() {
                @Override
                public void onResponse(Call<Patient> call, Response<Patient> response) {
                    if (response != null) {
                        System.out.println("upload success");
                    } else {
                        System.out.println("upload error" + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<Patient> call, Throwable t) {
                    System.out.println("upload error" + t.toString());
                }
            });
        }
    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == MainActivity.RESULT_OK && data != null) {
            Uri fileUri = data.getData();
            System.out.println("uri " + fileUri);
            if (fileUri != null) {
                uploadImageToServer(fileUri); // uploads the file to the web service
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                    profile_image.setImageBitmap(bitmap);
                    profile_image.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    //click hambergur icon
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



}