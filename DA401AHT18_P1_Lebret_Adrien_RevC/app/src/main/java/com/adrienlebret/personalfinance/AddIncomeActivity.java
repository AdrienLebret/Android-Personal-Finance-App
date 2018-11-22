package com.adrienlebret.personalfinance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adrienlebret.personalfinance.database.ExpenseDatabase;
import com.adrienlebret.personalfinance.database.IncomeDatabase;
import com.adrienlebret.personalfinance.models.Expense;
import com.adrienlebret.personalfinance.models.Income;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by Adrien LEBRET
 */
public class AddIncomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    //===============================================
    // The different characteristics of the activity
    //===============================================
    //private EditText mIncomeCatagory;
    private EditText mIncomeDescription;
    private Button mButtonDate;
    private TextView mIncomeDate;
    private String dateSaved;
    private EditText mIncomeAmount;
    IncomeDatabase incomeDatabaseManager;

    private Spinner mIncomeCatagory;
    ArrayAdapter<CharSequence> adapter;
    private String mCategoryChosen;

    @Override
    /**
     * Creation of the Activity : Add Exepense
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addincome);// allows you to indicate the graphical interface of our activity.

        //mIncomeCatagory=findViewById(R.id.incomeCatagory);
        mIncomeDescription = findViewById(R.id.incomeDescription);

        mIncomeDate = findViewById(R.id.incomeDate);
        mButtonDate = (Button) findViewById(R.id.buttonDate);
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        mIncomeAmount = findViewById(R.id.incomeAmount);
        incomeDatabaseManager = new IncomeDatabase(this);

        mIncomeCatagory = findViewById(R.id.incomeCatagory);
        adapter = ArrayAdapter.createFromResource(this, R.array.categoryIncome, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mIncomeCatagory.setAdapter(adapter);
        mIncomeCatagory.setOnItemSelectedListener(this);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        mIncomeDate.setText(currentDateString);


        // Save of the date
        // Lot of problems to save, so some changements were did

        String daySaved;
        if (dayOfMonth < 10){
            daySaved = "0" + dayOfMonth;
        } else {
            daySaved = dayOfMonth + "";
        }

        month++; // In SQL, the months of the year start at 0 and not at 1, so it must be incremented

        String monthSaved;
        if (month < 10){
            monthSaved = "0" + month;
        } else {
            monthSaved = month + "";
        }

        dateSaved = year + "-" + monthSaved + "-" + daySaved;
    }

    /**
     * Save the income added by the user
     */
    public void saveIncome(View view) {

        // The variable 'exp' retrieves the category, amount, Description and date provided by the user
        Income exp = new Income(mCategoryChosen,mIncomeDescription.getText().toString(),dateSaved,mIncomeAmount.getText().toString());

        long insertedRow = incomeDatabaseManager.addIncome(exp);
        if (insertedRow>0){
            Toast.makeText(this,"Successful insertion "+insertedRow, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Something's not right..."+ insertedRow, Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
    }

    /**
     * Methods for the spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mCategoryChosen = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), mCategoryChosen, Toast.LENGTH_SHORT).show(); // Show that this category has been selected correctly
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
