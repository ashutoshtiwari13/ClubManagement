package com.ruuha.bmscms;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import br.com.bloder.magic.view.MagicButton;

/**
 * Created by ruuha on 9/23/2017.
 */

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


   ImageButton listclubs,addclub,bemember,btnfind,btnEvent,btnAbout;
    MagicButton btnBmsce;

    public Toolbar toolbar;
  // private static int RC_SIGN_IN=0;
  // private FirebaseAuth mAuth;
    public TabLayout tabLayout;
    public ViewPager viewPager;

    TextView tv1,tv2,tv3,tv4,tv5,tv6;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity);

       /* mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            Toast.makeText(this, "Already Logged in", Toast.LENGTH_SHORT).show();
        }
        else{
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setProviders(
                            AuthUI.FACEBOOK_PROVIDER,
                            AuthUI.EMAIL_PROVIDER,
                            AuthUI.GOOGLE_PROVIDER )
                    .build(),RC_SIGN_IN);

        }*/


        tv1=(TextView)findViewById(R.id.textView15);
        Typeface typeFace1=Typeface.createFromAsset(getAssets(),"fonts/Raleway-ExtraBold.ttf");
        tv1.setTypeface(typeFace1);

        tv2=(TextView)findViewById(R.id.textView16);
        Typeface typeFace2=Typeface.createFromAsset(getAssets(),"fonts/Raleway-ExtraBold.ttf");
        tv2.setTypeface(typeFace2);

        tv3=(TextView)findViewById(R.id.textView17);
        Typeface typeFace3=Typeface.createFromAsset(getAssets(),"fonts/Raleway-ExtraBold.ttf");
        tv3.setTypeface(typeFace3);


        tv4=(TextView)findViewById(R.id.textView18);
        Typeface typeFace4=Typeface.createFromAsset(getAssets(),"fonts/Raleway-ExtraBold.ttf");
        tv4.setTypeface(typeFace4);

        tv5=(TextView)findViewById(R.id.textView19);
        Typeface typeFace5=Typeface.createFromAsset(getAssets(),"fonts/Raleway-ExtraBold.ttf");
        tv5.setTypeface(typeFace5);

        tv6=(TextView)findViewById(R.id.textView21);
        Typeface typeFace6=Typeface.createFromAsset(getAssets(),"fonts/Raleway-ExtraBold.ttf");
        tv6.setTypeface(typeFace6);


        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout) ;
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listclubs=(ImageButton)findViewById(R.id.list_club);
        addclub=(ImageButton)findViewById(R.id.add_club);
        bemember=(ImageButton)findViewById(R.id.be_member);
        btnfind=(ImageButton)findViewById(R.id.search);
        btnEvent=(ImageButton) findViewById(R.id.add_event);
        btnAbout=(ImageButton)findViewById(R.id.support);

//        btnFacebook = (MagicButton) findViewById(R.id.magic_button_facebook);
        btnBmsce = (MagicButton) findViewById(R.id.magic_button_bmsce);


//        btnFacebook.setMagicButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(HomePage.this, "Will move to Facebook soon", Toast.LENGTH_SHORT).show();
//            }
//        });
//
        btnBmsce.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePage.this, "Moving to Bmsce website", Toast.LENGTH_SHORT).show();
                Intent browserintent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bmsce.ac.in"));
                startActivity(browserintent);

            }
       });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go1 = new Intent(HomePage.this,AboutDev.class);
                 startActivity(go1);
            }
        });
        listclubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(HomePage.this,ClubList.class);
                startActivity(go);
            }
        });


        btnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(HomePage.this,StudentList.class);
                startActivity(go);
            }
        });


        addclub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go= new Intent(HomePage.this,ListClubs.class);
                startActivity(go);

            }
        });

        bemember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go= new Intent(HomePage.this,StudentDetailsInput.class);
                startActivity(go);

            }
        });

       btnEvent.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent go= new Intent(HomePage.this,EventHandler.class);
               startActivity(go);

           }
       });


    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            if(resultCode==RESULT_OK){
                Log.d("AUTH",mAuth.getCurrentUser().getEmail());

            }
            else{

                Toast.makeText(this, "Not authenticated", Toast.LENGTH_SHORT).show();
            }

        }
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.idItem1){
            Intent go= new Intent(HomePage.this,StudentDetailsInput.class);
            startActivity(go);
        }
        else if(id==R.id.idItem2){
            Intent go= new Intent(HomePage.this,EventHandler.class);
            startActivity(go);
        }
        else if(id==R.id.idItem3){
            Intent go= new Intent(HomePage.this,ListClubs.class);
            startActivity(go);
        }
        else if(id==R.id.idItem4){
            Intent go = new Intent(HomePage.this,ClubList.class);
            startActivity(go);
        }
        else if(id==R.id.idItem5){
            Intent go = new Intent(HomePage.this,StudentList.class);
            startActivity(go);
        }
        else if(id==R.id.idItem6){
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.idItem7){
            Toast.makeText(HomePage.this, "Moving to Bmsce website", Toast.LENGTH_SHORT).show();
            Intent browserintent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bmsce.ac.in"));
            startActivity(browserintent);
        }
        else if(id==R.id.idItem8){
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(HomePage.this, "User logged Out", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });


        }
        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
