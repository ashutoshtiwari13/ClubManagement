package com.ruuha.bmscms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.phoneNumber;

/**
 * Created by ruuha on 9/12/2017.
 */

public class RegisterPageCopy extends AppCompatActivity {
    private Context context;

    SQLiteDBHelper helper = new SQLiteDBHelper(this);
    Button register;
    TextView uname, bno, em, pass, head,heading;
    String un, b, ema, p;
    Context ctx = this;
    RadioGroup radioGroup;
    String value;
    EditText username, email, password;
   // EditText branch;
    RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        username = (EditText) findViewById(R.id.et_u_name);

     //  branch = (EditText) findViewById(R.id.et_branch);
        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);

        uname = (TextView) findViewById(R.id.linearLayout5);
        em = (TextView) findViewById(R.id.constraintLayout3);
        pass = (TextView) findViewById(R.id.password);
        bno = (TextView) findViewById(R.id.linearLayout6);
        head = (TextView) findViewById(R.id.textView13);
        register = (Button) findViewById(R.id.register_but);


        heading=(TextView)findViewById(R.id.textView13);
        Typeface typeFace2=Typeface.createFromAsset(getAssets(),"fonts/Raleway-ExtraBold.ttf");
        heading.setTypeface(typeFace2);

//        cse=(RadioButton)findViewById(R.id.cse);
//        ise=(RadioButton)findViewById(R.id.ise);
//        eee=(RadioButton)findViewById(R.id.eee);
//        ece=(RadioButton)findViewById(R.id.ece);
//        mech=(RadioButton)findViewById(R.id.mech);
//        chem=(RadioButton)findViewById(R.id.chem);

       radioGroup = (RadioGroup)findViewById(R.id.radio_Q1);

        int i=radioGroup.getCheckedRadioButtonId();
        if(i!=-1) {
            rb = (RadioButton) radioGroup.findViewById(i);
             value=rb.getText().toString();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Registered Succesfully !!", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(RegisterPageCopy.this,OTPRegister.class);
//                startActivity(i);
                login();


                username.setText("");
                email.setText("");
                password.setText("");
                radioGroup.clearCheck();

            }
        });
    }




    public void login() {

        if (TextUtils.isEmpty(username.getText().toString().trim())|| TextUtils.isEmpty(password.getText().toString().trim()) ||  TextUtils.isEmpty(email.getText().toString().trim())) {
            username.setError("Field Can't be Empty");
            // branch.setError("Field Can't be Empty");
            email.setError("Field Can't be Empty");
            password.setError("Field Can't be Empty");
//           hideKeyboardFrom(username);
//            hideKeyboardFrom(branch);
//            hideKeyboardFrom(email);
//            hideKeyboardFrom(password);
        }
        if(!usernameValidator(username.getText().toString())){

            username.setError("Must Contain a-z, 0-9, underscore, hyphen with Length at least 5 characters and maximum length of 15");
           // hideKeyboardFrom(username);

        }

        if (!emailValidator(email.getText().toString())) {
            email.setError("Enter A valid Email address");
           // hideKeyboardFrom(email);
        }
        else{


            un = username.getText().toString();
            b = value;
            ema = email.getText().toString();
            p = password.getText().toString();


            ContactRegister c = new ContactRegister();
            c.setName(un);
            c.setBranch(b);
            c.setEmail(ema);
            c.setPass(p);

            helper.insertContact(c);

            Intent i = new Intent(RegisterPageCopy.this,OTPRegister.class);
            startActivity(i);
            finish();

        }

    }


    public boolean emailValidator(String email){


        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern=Pattern.compile(EMAIL_PATTERN);
        matcher=pattern.matcher(email);
        return matcher.matches();
    }

    public boolean usernameValidator(String username){


        Pattern pattern;
        Matcher matcher;
        final String UNAME_PATTERN="^[a-z0-9_-]{5,15}$";
        pattern=Pattern.compile(UNAME_PATTERN);
        matcher=pattern.matcher(username);
        return matcher.matches();
    }







}
