package com.ruuha.bmscms;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.OnActionClickListener;
import com.dexafree.materialList.card.action.TextViewAction;
import com.dexafree.materialList.view.MaterialListView;
import com.squareup.picasso.RequestCreator;

import java.util.Random;

import static android.R.color.black;

/**
 * Created by ruuha on 10/3/2017.
 */

public class EventHandler extends AppCompatActivity {
    MaterialListView materialListView;

    private static final int MY_PERMISSION_REQUEST_SEND_SMS=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.event_handler);
        this.initializeViews();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventHandler.this, EventCrud.class);
                Context c = EventHandler.this;
                c.startActivity(i);
            }
        });

    }

    private void initializeViews() {
        materialListView = (MaterialListView) findViewById(R.id.material_listview);
    }

    private void bindData() {
        materialListView.getAdapter().clearAll();
        if(MyDataManager.getEvents().size()>0)
        {
            for (EventHelper eh : MyDataManager.getEvents()) {
                this.createCard(eh);
            }
        }

    }

    private int getRandomImage()
    {
        int[] images={R.drawable.img1,R.drawable.img4,R.drawable.img3,R.drawable.img4};
        int image=new Random().nextInt(images.length);
        return images[image];

    }

    private void createCard(final EventHelper eh) {
        Card card = new Card.Builder(this)
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_basic_image_buttons_card_layout)
                //.setLayout(R.layout.material_image_with_buttons_card)
                //.setLayout(R.layout.material_basic_buttons_card)
                //.setLayout(R.layout.material_welcome_card_layout)
                //.setLayout(R.layout.material_small_image_card)
                //.setLayout(R.layout.material_big_image_card_layout)
                .setTitle(eh.getName())
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setSubtitle(eh.getDate())
                .setSubtitleGravity(Gravity.CENTER_HORIZONTAL)
                .setDescription(eh.getDescription())
                .setDescriptionGravity(Gravity.CENTER_HORIZONTAL)
                .setDrawable(getRandomImage())
                .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                    @Override
                    public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                        //requestCreator.fit();
                        requestCreator.resize(121,121);
                    }
                })
                .addAction(R.id.right_text_button, new TextViewAction(this)
                        .setText("REGISTER")
                        .setTextResourceColor(R.color.textColorInTextView)
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
//                                Intent i = new Intent(EventHandler.this, EventCrud.class);
//                                Context c = EventHandler.this;
//                                c.startActivity(i);
                                AlertDialog.Builder mbuilder=new AlertDialog.Builder(EventHandler.this);
                                View mView=getLayoutInflater().inflate(R.layout.event_registerinflate,null);
                                final EditText mContact=(EditText)mView.findViewById(R.id.editContact);
                                Button btnEvent=(Button)mView.findViewById(R.id.btnregisterEvent);

                                final int PERMISSION_REQUEST_CODE = 1;

                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

                                    if (checkSelfPermission(Manifest.permission.SEND_SMS)
                                            == PackageManager.PERMISSION_DENIED) {

                                        Log.d("permission", "permission denied to SEND_SMS - requesting it");
                                        String[] permissions = {Manifest.permission.SEND_SMS};

                                        requestPermissions(permissions, PERMISSION_REQUEST_CODE);

                                    }
                                }


                                btnEvent.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String myNum=mContact.getText().toString().trim();
                                        String myMsg= "You successfully registered to " +eh.getName()+" which is to be conducted on"+" "+eh.getDate();
                                        if(myNum==null || myNum.equals("")){
                                            Toast.makeText(EventHandler.this, "Field cant be Empty", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(TextUtils.isDigitsOnly(myNum)){
                                            SmsManager smsManager=SmsManager.getDefault();
                                            smsManager.sendTextMessage(myNum,null,myMsg,null,null);
                                            Toast.makeText(EventHandler.this, "Registration message Sent", Toast.LENGTH_SHORT).show();

                                        }
                                        else{
                                            Toast.makeText(EventHandler.this, "Please Enter Integer only", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                                mbuilder.setView(mView);
                                AlertDialog dialog=mbuilder.create();
                                dialog.show();


                            }
                        }))
                .addAction(R.id.left_text_button, new TextViewAction(this)
                        .setText("EDIT")
                        .setTextResourceColor(R.color.textColorInTextView)
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                Intent i = new Intent(EventHandler.this, EventCrud.class);
                                i.putExtra("KEY_EVENT", eh.getId());
                                Context c = EventHandler.this;
                                c.startActivity(i);
                            }
                        }))
                .endConfig()
                .build();

        materialListView.getAdapter().add(card);
    }



    public void MyMessage(){

    }

    public void sendSms(View v){

        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            MyMessage();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},MY_PERMISSION_REQUEST_SEND_SMS);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        switch(requestCode){
            case MY_PERMISSION_REQUEST_SEND_SMS:
            {
                if(grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    MyMessage();
                }
                else{
                    Toast.makeText(this, "You don't have Required Permission", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        this.bindData();
    }





}
