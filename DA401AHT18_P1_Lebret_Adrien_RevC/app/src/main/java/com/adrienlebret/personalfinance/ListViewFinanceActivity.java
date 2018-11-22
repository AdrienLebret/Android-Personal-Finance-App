package com.adrienlebret.personalfinance;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * Created by Adrien LEBRET
 */
public class ListViewFinanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_finance);

        BottomNavigationView bottomNavList = findViewById(R.id.bottom_navigation_list); // We create the Bottom Navigation List
        bottomNavList.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFrag = null; // Default fragment that we can see - Put 'null' if you want nothing

                    switch (item.getItemId()){
                        case R.id.inc_list:
                            selectedFrag = new IncomeListFragment();
                            break;
                        case R.id.exp_list:
                            selectedFrag = new ExpenseListFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFrag).commit(); // this action show the container that we are selected Income List or Expense List

                    return true; // we need to return a boolean to confirm that we are selected this item
                }
            };
}
