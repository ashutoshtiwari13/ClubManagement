package com.ruuha.bmscms;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

/**
 * Created by ruuha on 10/12/2017.
 */

public class AboutDev extends AppCompatActivity {

    TextView tv1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        tv1=(TextView)findViewById(R.id.textView7);
        Typeface typeFace1=Typeface.createFromAsset(getAssets(),"fonts/Raleway-ExtraBold.ttf");
        tv1.setTypeface(typeFace1);

    }

}
