package com.adrienlebret.personalfinance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Adrien LEBRET
 */
public class MainActivity extends AppCompatActivity {
    /*
    AppCompatActivity = a class that describes an activity that supports an action bar.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //===========
        // LOAD DATA
        //===========
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String fn = sharedPreferences.getString("firstName","");
        String ln = sharedPreferences.getString("lastName","");

        TextView mTextHello = (TextView) findViewById(R.id.helloUser);
        mTextHello.setText("Hello " + fn + " " + ln);
    }

    /*
    Launching the different activities of the application
     */

    public void addIncome(View view) {
        Intent intent = new Intent(this,AddIncomeActivity.class);
        startActivity(intent);
    }
    public void addExpense(View view) {
        Intent intent = new Intent(this,AddExpenseActivity.class);
        startActivity(intent);
    }

    /*
    public void incomeList(View view) {
        Intent intent = new Intent(this,IncomeListActivity.class);
        startActivity(intent);
    }

    public void expenseList(View view) {
        Intent intent = new Intent(this,ExpenseListActivity.class);
        startActivity(intent);
    }
    */

    public void incexpList(View view) {
        Intent intent = new Intent(this,ListViewFinanceActivity.class);
        startActivity(intent);
    }

    public void financialSituation(View view) {
        Intent intent = new Intent(this,FinancialSituationActivity.class);
        startActivity(intent);
    }
}
