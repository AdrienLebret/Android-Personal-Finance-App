package com.adrienlebret.personalfinance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Adrien LEBRET
 */
public class LoginActivity extends AppCompatActivity {

    private EditText mFirstName;
    private EditText mLastName;
    private Button mBtnLogin;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //========================
        // DO WE NEED TO LOG IN ?
        //========================

        SharedPreferences sP = getSharedPreferences(FIRSTNAME, MODE_PRIVATE);
        /*
        // We display the Main activity or Menu if we already know
        if(!sP.getString(FIRSTNAME, "").equals("")){
            Intent mainScreen = new Intent(this,MainActivity.class);
            startActivity(mainScreen);
            return;
        }
        */
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);

        mFirstName = (EditText) findViewById(R.id.firstName);
        mFirstName.setText(sharedPreferences.getString("firstName",""));
        mLastName = (EditText) findViewById(R.id.lastName);
        mLastName.setText(sharedPreferences.getString("lastName",""));

        mBtnLogin = (Button) findViewById(R.id.loginBtn);


        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData();

                Intent mainScreen = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(mainScreen);

                Toast.makeText(LoginActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveData(){

        //===================
        // SHARED PREFERENCE
        //===================
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String newFirstName = mFirstName.getText().toString();
        String newLastName = mLastName.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FIRSTNAME, newFirstName);
        editor.putString(LASTNAME, newLastName);
        editor.commit();
    }
}
