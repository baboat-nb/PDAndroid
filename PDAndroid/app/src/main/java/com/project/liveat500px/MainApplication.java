package com.project.liveat500px;

import android.app.Application;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by winai on 9/9/2017.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Kanit-Regular.ttf")
                .setFontAttrId(R.attr.fontPath).build());

        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
