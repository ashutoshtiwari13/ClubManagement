package com.ruuha.bmscms;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCore;

/**
 * Created by ruuha on 10/3/2017.
 */

public class Event extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        List<Class<? extends Rush>> rushClasses = new ArrayList<>();
        rushClasses.add(EventHelper.class);
        AndroidInitializeConfig config=new AndroidInitializeConfig(getApplicationContext(),rushClasses);
        RushCore.initialize(config);
    }
}
