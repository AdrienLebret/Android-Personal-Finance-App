package com.adrienlebret.personalfinance;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adrienlebret.personalfinance.database.IncomeDatabase;
import com.adrienlebret.personalfinance.models.Income;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Adrien LEBRET
 */
public class IncomeListFragment extends Fragment{

    IncomeDatabase incomeDatabaseManager;
    ListView mListView;

    public static TextView mDateFrom;
    public static String dateA = "";
    Button mButtonDateFrom;

    public static TextView mDateTo;
    public static String dateB = "";
    Button mButtonDateTo;

    public static String currentDateSaved;
    Button mButtonResearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_income, container, false);

        mListView = v.findViewById(R.id.incomeLV);
        incomeDatabaseManager = new IncomeDatabase(this.getActivity().getApplicationContext());
        ArrayList<Income> incomeArrayList = incomeDatabaseManager.getAllIncome();
        ArrayList<String>list = new ArrayList<>();

        for (Income income:incomeArrayList){
            list.add(income.getIncomeCatagory()+"         "+income.getIncomeAmount()+"€         "+income.getIncomeDate()+"\n"+income.getIncomeDescription());
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this.getActivity().getApplicationContext(),R.layout.income_custom_listview, R.id.incomeTitleTVId,list);
        mListView.setAdapter(adapter);


        //========================
        // Search between 2 dates
        //========================

        //====================
        // DATE A = DATE FROM
        //====================

        mDateFrom = (TextView) v.findViewById(R.id.dateFrom);
        mButtonDateFrom = (Button) v.findViewById(R.id.buttonDateFrom);
        mButtonDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDateFragment1 dateFragment = new SelectDateFragment1();
                dateFragment.show(getFragmentManager(), "DatePicker");
            }
        });


        //==================
        // DATE B = DATE TO
        //==================
        mDateTo = (TextView) v.findViewById(R.id.dateTo);
        mButtonDateTo = (Button) v.findViewById(R.id.buttonDateTo);
        mButtonDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDateFragment2 dateFragment = new SelectDateFragment2();
                dateFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        //==========
        // Research
        //==========
        mButtonResearch = v.findViewById(R.id.buttonResearch);
        mButtonResearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    compareDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }

    /**
     * This method compare the date and display the list if Date A is before Date B
     */
    public void compareDate() throws ParseException{

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = format.parse(dateA);
        Date date2 = format.parse(dateB);

        // Date A < Date B so we display the list between this 2 dates
        if (date1.compareTo(date2) <=0){
            incomeDatabaseManager = new IncomeDatabase(getActivity().getApplicationContext());
            ArrayList<Income> incomeArrayList = incomeDatabaseManager.getAllIncomeBetween(dateA,dateB);
            ArrayList<String>list = new ArrayList<>();
            for (Income income:incomeArrayList){
                list.add(income.getIncomeCatagory()+"         "+income.getIncomeAmount()+"€         "+income.getIncomeDate()+"\n"+income.getIncomeDescription());
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.income_custom_listview, R.id.incomeTitleTVId,list);
            //Toast.makeText(getContext(), "A avant B", Toast.LENGTH_SHORT).show();
            mListView.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "Date A need to be before Date B", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * It's hard to call a datefpickerfragment when you're un a fragment
     * So I created this 2 class, of course I can use a different way (just one class for example)
     */

    public static class SelectDateFragment1 extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            //populateSetDate(yy, mm+1, dd);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            currentDateSaved = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
            mDateFrom.setText(currentDateSaved);

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

            dateA = year + "-" + monthSaved + "-" + daySaved;
        }
    }

    public static class SelectDateFragment2 extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            //populateSetDate(yy, mm+1, dd);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            currentDateSaved = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
            mDateTo.setText(currentDateSaved);

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

            dateB = year + "-" + monthSaved + "-" + daySaved;
        }
    }
}
