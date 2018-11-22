package com.adrienlebret.personalfinance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.adrienlebret.personalfinance.database.IncomeDatabase;
import com.adrienlebret.personalfinance.models.Income;

import java.util.ArrayList;

/**
 * Created by Adrien LEBRET
 *
 * Activity that we replace by a fragment / we didn't use it but there
 * are some problems when we would like to delete it
 */
public class IncomeListActivity extends AppCompatActivity {
    EditText mIncomeCatagory;
    EditText mIncomeDescription;
    EditText mIncomeDate;
    EditText mIncomeAmount;
    IncomeDatabase incomeDatabase;
    ListView listView;
    boolean isUpdating=false;
    Button saveIncomeBtn;
    int selectincomeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomelist);
        //mIncomeCatagory=findViewById(R.id.incomeCatagory);
        mIncomeDescription=findViewById(R.id.incomeDescription);
        //mIncomeDate=findViewById(R.id.incomeDate);
        mIncomeAmount=findViewById(R.id.incomeAmount);
        //saveIncomeBtn=findViewById(R.id.saveIncomeBtn);
        listView=findViewById(R.id.incomeLV);
        incomeDatabase =new IncomeDatabase(this);
        final ArrayList<Income>incomeArrayList= incomeDatabase.getAllIncome();
        ArrayList<String>list=new ArrayList<>();
        for (Income income:incomeArrayList){
            list.add(income.getIncomeCatagory()+"       "+income.getIncomeAmount()+"\n"+income.getIncomeDescription());
        }
        ArrayAdapter<String>adapter=new ArrayAdapter<>(this,R.layout.income_custom_listview, R.id.incomeTitleTVId,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Income income= incomeDatabase.getSingleIncome(incomeArrayList.get(i).getId());
                mIncomeCatagory.setText(income.getIncomeCatagory());
                mIncomeDescription.setText(income.getIncomeDescription());
                mIncomeDate.setText(income.getIncomeDate());
                mIncomeAmount.setText(income.getIncomeAmount());
                saveIncomeBtn.setText("Update");
                isUpdating=true;
                selectincomeId=income.getId();
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
