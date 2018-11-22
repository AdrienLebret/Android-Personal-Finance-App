package com.adrienlebret.personalfinance.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Adrien LEBRET
 *
 * The class SQLiteOpenHelper includes support for creating and
 * maintaining the database, as well as supplies the database itself.
 */
public class DatabasePersonalFinance extends SQLiteOpenHelper {

    // Basics information for the DATABASE
    static final String DATABASE_NAME = "income_expense";
    static final int DATABASE_VERSION = 1;

    //==============================
    // Creation of the income table
    //==============================
    static final String TABLE_INCOME = "income";

    static final String INCOME_ID = "id";
    static final String INCOME_CATAGORY = "incomeCatagory";
    static final String INCOME_DESCRIPTION = "incomeDescription";
    static final String INCOME_DATE = "incomeDate";
    static final String INCOME_AMOUNT = "incomeAmount";

    private static final String CREATE_TABLE_INCOME =
            "create table " + TABLE_INCOME + "(" +
                    INCOME_ID + " integer primary key autoincrement," +
                    INCOME_CATAGORY + " text," +
                    INCOME_DESCRIPTION + " text," +
                    INCOME_DATE + " datetime," +
                    INCOME_AMOUNT + " text);";


    //===============================
    // Creation of the expense table
    //===============================
    static final String TABLE_EXPENSE="expense";

    static final String EXPENSE_ID = "id";
    static final String EXPENSE_CATAGORY = "expenseCatagory";
    static final String EXPENSE_DESCRIPTION = "expenseDescription";
    static final String EXPENSE_DATE = "expenseDate";
    static final String EXPENSE_AMOUNT = "expenseAmount";

    private static final String CREATE_TABLE_EXPENSE =
            "create table "+ TABLE_EXPENSE + "(" +
                    EXPENSE_ID +" integer primary key autoincrement," +
                    EXPENSE_CATAGORY + " text," +
                    EXPENSE_DESCRIPTION+" text," +
                    EXPENSE_DATE + " datetime," +
                    EXPENSE_AMOUNT + " text);";


    //=========================================
    // Creation of the context of the database
    //=========================================
    private Context context;
    public DatabasePersonalFinance(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL(CREATE_TABLE_INCOME);
            sqLiteDatabase.execSQL(CREATE_TABLE_EXPENSE);
        }catch(Exception e)
        {
            Toast.makeText(context,"Exception : "+e,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try{
            sqLiteDatabase.execSQL("drop table if exists " + TABLE_INCOME);
            onCreate(sqLiteDatabase);
            sqLiteDatabase.execSQL("drop table if exists " + TABLE_EXPENSE);
            onCreate(sqLiteDatabase);
        }catch(Exception e)
        {
            Toast.makeText(context,"Exception : "+e,Toast.LENGTH_SHORT).show();
        }
    }
}
