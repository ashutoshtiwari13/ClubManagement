package com.ruuha.bmscms;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ruuha on 9/26/2017.
 */

public class StudentList extends AppCompatActivity implements  SearchView.OnQueryTextListener{


    //private static String GMS_SEARCH_ACTION = "com.google.android.gms.actions.SEARCH_ACTION";
    private AppCompatActivity activity = StudentList.this;
    Context context = StudentList.this;
    private RecyclerView recyclerViewBeneficiary;
    private ArrayList<StudentGetterSetter> listStudentGetterSetter;
    private StudentRecyclerAdapter studentRecyclerAdapter;
    private DatabaseHelper databaseHelper;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initObjects();

       // handleIntent(getIntent());

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("USN") || intentThatStartedThisActivity.hasExtra("NAME")) {

            int id = getIntent().getExtras().getInt("USN");
            String email = getIntent().getExtras().getString("EMAIL");
            String branch= getIntent().getExtras().getString("BRANCH");
            String club= getIntent().getExtras().getString("CLUB");
            String contact = getIntent().getExtras().getString("CONTACT");



        }


        recyclerViewBeneficiary.setOnLongClickListener(new RecyclerView.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(activity, "Cannot Delete I", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }



    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewBeneficiary = (RecyclerView) findViewById(R.id.recyclerViewBeneficiary);
    }


    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listStudentGetterSetter = new ArrayList<>();
        studentRecyclerAdapter = new StudentRecyclerAdapter(listStudentGetterSetter, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewBeneficiary.setLayoutManager(mLayoutManager);
        recyclerViewBeneficiary.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBeneficiary.setHasFixedSize(true);
        recyclerViewBeneficiary.setAdapter(studentRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        getDataFromSQLite();

    }

//    private void handleIntent(Intent intent) {
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            doMySearch(query);
//        }
//    }

//    @Override
//    public boolean onSearchRequested() {
//        return super.onSearchRequested();
//    }
//    @Override
//    protected void onNewIntent(Intent intent) {
//        setIntent(intent);
//        handleIntent(intent);
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.beneficiary_search,menu);

       MenuItem  search=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);
        return true;

//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//        // Assumes current activity is the searchable activity
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
//
//        return true;
       // SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        //ComponentName componentName=new ComponentName(context,StudentList.class);
       // searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return  super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onQueryTextSubmit(String query ){

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText){

        newText= newText.toLowerCase();
        ArrayList<StudentGetterSetter> newList=new ArrayList<>();
        for(StudentGetterSetter studentGetterSetter :listStudentGetterSetter)
        {

            String name= studentGetterSetter.getName().toLowerCase();
            String usn=studentGetterSetter.getUsn().toLowerCase();
            if(name.contains(newText)){
                newList.add(studentGetterSetter);
            }
            else if (usn.contains(newText)){
                newList.add(studentGetterSetter);
            }
        }
        studentRecyclerAdapter.setFilter(newList);
        return true;

    }



    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try{
                listStudentGetterSetter.clear();
                listStudentGetterSetter.addAll(databaseHelper. getAllBeneficiary());}
                catch(Exception e){
                    Log.e("Error message :",e.getMessage());
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                studentRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}
