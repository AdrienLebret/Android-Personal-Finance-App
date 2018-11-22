package com.adrienlebret.personalfinance;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.adrienlebret.personalfinance.database.ExpenseDatabase;
import com.adrienlebret.personalfinance.database.IncomeDatabase;
import com.adrienlebret.personalfinance.models.Expense;
import com.adrienlebret.personalfinance.models.Income;

import java.util.ArrayList;

/**
 * Created by Adrien LEBRET
 */
public class FinancialSituationActivity extends AppCompatActivity {

    //===============================================
    // The different characteristics of the activity
    //===============================================
    private TextView mHelloUser; // TO DO - The name depend of the login session
    private TextView mTotalIncome;
    private TextView mTotalExpense;
    private TextView mFinanceSituation;

    //================
    // Variables used
    //================
    private int resultIncome = 0;
    private int resultExpense = 0;
    private int resultSituation = 0;

    //==========
    // Database
    //==========
    ExpenseDatabase expenseDatabaseManager;
    IncomeDatabase incomeDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_situation);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String fn = sharedPreferences.getString("firstName","");
        String ln = sharedPreferences.getString("lastName","");
        mHelloUser = (TextView)findViewById(R.id.helloUser);
        mHelloUser.setText("Hello " + fn + " " + ln);

        mTotalIncome = (TextView)findViewById(R.id.totalIncome);
        mTotalExpense = (TextView)findViewById(R.id.totalExpense);
        mFinanceSituation = (TextView)findViewById(R.id.fincanceSituation);

        mTotalIncome.setText(String.valueOf(calculateTotalIncome()));
        mTotalExpense.setText(String.valueOf(calculateTotalExpense()));
        // setText ask a string and not a int, that's suck... so 2 possibilities :("" + int) ou String.valueOf(int)
        calculteFinanceSituation();
    }

    /**
     * The following 2 methods change the result
     */
    public int calculateTotalIncome() {
        incomeDatabaseManager = new IncomeDatabase(this);
        ArrayList<Income> incomeArrayList = incomeDatabaseManager.getAllIncome();

        for (Income income:incomeArrayList){
            resultIncome += Integer.parseInt(income.getIncomeAmount());
        }
        return resultIncome;
    }

    public int calculateTotalExpense(){
        expenseDatabaseManager = new ExpenseDatabase(this);
        ArrayList<Expense> expenseArrayList = expenseDatabaseManager.getAllExpense();

        for (Expense expense:expenseArrayList){
            resultExpense += Integer.parseInt(expense.getExpenseAmount());
        }
        return resultExpense;
    }

    /**
     * This method calculates AND changes the text
     * itself because I want the text to change color according to the result
     */
    public void calculteFinanceSituation(){
        resultSituation = resultIncome - resultExpense;

        if(resultSituation < 0){
            mFinanceSituation.setTextColor(Color.RED);
            mFinanceSituation.setText(String.valueOf(resultSituation) + " €");
        } else {
            mFinanceSituation.setTextColor(Color.GREEN);
            mFinanceSituation.setText("+"+String.valueOf(resultSituation) + " €");
        }



    }
}
