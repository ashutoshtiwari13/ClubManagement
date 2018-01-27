package com.ruuha.bmscms;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.R.attr.label;

/**
 * Created by ruuha on 9/26/2017.
 */

public class StudentDetailsInput extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemSelectedListener {
    private final AppCompatActivity activity = StudentDetailsInput.this;

    Spinner spinner;
    DBHelperClub dbHelperClub;

    private NestedScrollView nestedScrollView;


    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutUsn;
    private TextInputLayout textInputLayoutBranch;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutContact;


    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextUsn;
    private TextInputEditText textInputEditTextBranch;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextContact;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewStudList,ap1;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private StudentGetterSetter studentGetterSetter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentdetail_new);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initObjects();
        initListeners();
    }

    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutUsn = (TextInputLayout) findViewById(R.id.textInputLayoutUsn);
        textInputLayoutBranch = (TextInputLayout) findViewById(R.id.textInputLayoutBranch);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutContact = (TextInputLayout) findViewById(R.id.textInputLayoutContact);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextUsn     = (TextInputEditText) findViewById(R.id.textInputEditTextUsn);
        textInputEditTextBranch = (TextInputEditText) findViewById(R.id.textInputEditTextBranch);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextContact = (TextInputEditText) findViewById(R.id.textInputEditTextContact);

        spinner=(Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        loadSpinnerData();

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewStudList = (AppCompatTextView) findViewById(R.id.appCompatTextViewStudList);

        ap1=(AppCompatTextView)findViewById(R.id.heading);
        Typeface typeFace1=Typeface.createFromAsset(getAssets(),"fonts/Raleway-ExtraBold.ttf");
        ap1.setTypeface(typeFace1);


    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewStudList.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        studentGetterSetter = new StudentGetterSetter();
    }




    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewStudList:
                Intent accountsIntent = new Intent(activity, StudentList.class);
                accountsIntent.putExtra("USN", textInputEditTextUsn.getText().toString().trim());
                accountsIntent.putExtra("NAME", textInputEditTextName.getText().toString().trim());
                accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
                accountsIntent.putExtra("BRANCH", textInputEditTextBranch.getText().toString().trim());
                accountsIntent.putExtra("CONTACT", textInputEditTextContact.getText().toString().trim());
                accountsIntent.putExtra("CLUB",spinner.getSelectedItem().toString());
                emptyInputEditText();

                startActivity(accountsIntent);
                break;
        }



    }

    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }

        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            studentGetterSetter.setUsn(textInputEditTextUsn.getText().toString().trim());
            studentGetterSetter.setName(textInputEditTextName.getText().toString().trim());
            studentGetterSetter.setEmail(textInputEditTextEmail.getText().toString().trim());
            studentGetterSetter.setBranch(textInputEditTextBranch.getText().toString().trim());
            studentGetterSetter.setClub(spinner.getSelectedItem().toString());
            studentGetterSetter.setContact(textInputEditTextContact.getText().toString().trim());


            databaseHelper.addBeneficiary(studentGetterSetter);

            // Snack Bar to show success message that record saved successfully
            Intent accountsIntent = new Intent(activity, StudentList.class);
            sendNotification();
            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT)
                    .show();

            accountsIntent.putExtra("USN", textInputEditTextUsn.getText().toString().trim());
            accountsIntent.putExtra("NAME", textInputEditTextName.getText().toString().trim());
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            accountsIntent.putExtra("BRANCH", textInputEditTextBranch.getText().toString().trim());
            accountsIntent.putExtra("CONTACT", textInputEditTextContact.getText().toString().trim());
            accountsIntent.putExtra("CLUB",spinner.getSelectedItem().toString());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }


    }

    private void sendNotification(){
        NotificationCompat.Builder mBuilder=(NotificationCompat.Builder)new NotificationCompat.Builder(this)
                                        .setSmallIcon(R.drawable.acc)
                                        .setContentTitle(" Registration Succesfull")
                                        .setContentText("You Succesfully made a  Registration to BMSCE ClubSystem");

        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,mBuilder.build());


    }
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextUsn.setText(null);
        textInputEditTextBranch.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextContact.setText(null);
        spinner.setSelection(0);
    }

//    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(inputLabel.getWindowToken(), 0);

    private void loadSpinnerData(){
        dbHelperClub=new DBHelperClub(this, "ClubDB.sqlite" ,null ,2);
        List<String> lables = dbHelperClub.getAllLabels();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "You selec " + label,
//                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
