package com.adrienlebret.personalfinance;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.adrienlebret.personalfinance.database.ExpenseDatabase;
import com.adrienlebret.personalfinance.models.Expense;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Adrien LEBRET
 */
public class AddExpenseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    //===============================================
    // The different characteristics of the activity
    //===============================================
    //private EditText mExpenseCatagory;
    private EditText mExpenseDescription;
    private Button mButtonDate;
    private TextView mExpenseDate;
    private String dateSaved;
    private EditText mExpenseAmount;
    ExpenseDatabase expenseDatabaseManager;

    private Spinner mExpenseCatagory;
    ArrayAdapter <CharSequence> adapter;
    private String mCategoryChosen;

    @Override
    /**
     * Creation of the Activity : Add Exepense
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexpense);// allows you to indicate the graphical interface of our activity.

        //mExpenseCatagory=findViewById(R.id.expenseCatagory);
        mExpenseDescription = findViewById(R.id.expenseDescription);

        mExpenseDate = findViewById(R.id.expenseDate);
        mButtonDate = (Button) findViewById(R.id.buttonDate);
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        mExpenseAmount = findViewById(R.id.expenseAmount);
        expenseDatabaseManager = new ExpenseDatabase(this);

        mExpenseCatagory = findViewById(R.id.expenseCatagory);
        adapter = ArrayAdapter.createFromResource(this, R.array.categoryExpense, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mExpenseCatagory.setAdapter(adapter);
        mExpenseCatagory.setOnItemSelectedListener(this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());

        mExpenseDate.setText(currentDateString);

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
     * Save the expenditure added by the user
     */
    public void saveExpense(View view) {

        // The variable 'exp' retrieves the category, amount, Description and date provided by the user
        Expense exp = new Expense(mCategoryChosen,mExpenseDescription.getText().toString(),dateSaved,mExpenseAmount.getText().toString());

        long insertedRow = expenseDatabaseManager.addExpense(exp);
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
