package com.ruuha.bmscms;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button login;
    Button register;
    TextView heading,subheading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         heading=(TextView)findViewById(R.id.textView14);
        Typeface typeFace1=Typeface.createFromAsset(getAssets(),"fonts/Raleway-ExtraBold.ttf");
        heading.setTypeface(typeFace1);


        subheading=(TextView)findViewById(R.id.textView);
        Typeface typeFace2=Typeface.createFromAsset(getAssets(),"fonts/Raleway-Thin.ttf");
        subheading.setTypeface(typeFace2);



        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.Register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(MainActivity.this,LoginPage.class);
                startActivity(go);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go2 = new Intent(MainActivity.this,RegisterPageCopy.class);
                startActivity(go2);
            }
        });


    }
}
