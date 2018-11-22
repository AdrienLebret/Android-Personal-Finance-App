package com.adrienlebret.personalfinance.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.adrienlebret.personalfinance.models.Expense;

import java.util.ArrayList;

/**
 * Created by Adrien LEBRET
 */
public class ExpenseDatabase {

    private Context context;
    private DatabasePersonalFinance databasePersonalFinance;

    public ExpenseDatabase(Context context) {
        this.context = context;
        databasePersonalFinance = new DatabasePersonalFinance(context);
    }

    /**
     * When adding or updating a row, request a writable reference to the database,
     * then instantiate ContentValues,
     * add the necessary data and call INSERT or UPDATE
     */
    public long addExpense(Expense expense){
        SQLiteDatabase sqLiteDatabase = databasePersonalFinance.getWritableDatabase(); // We will insert a new line in the database - Write mode enabled
        ContentValues contentValues = new ContentValues(); // values that we wnat to insert
        // We want to insert in the database,
        // the CATEGORY + DESCRIPTION + DATE + AMOUNT of the expense
        contentValues.put(DatabasePersonalFinance.EXPENSE_CATAGORY,expense.getExpenseCatagory());
        contentValues.put(DatabasePersonalFinance.EXPENSE_DESCRIPTION,expense.getExpenseDescription());
        contentValues.put(DatabasePersonalFinance.EXPENSE_DATE,expense.getIexpenseDate());
        contentValues.put(DatabasePersonalFinance.EXPENSE_AMOUNT,expense.getExpenseAmount());

        long dataInserted = sqLiteDatabase.insert(DatabasePersonalFinance.TABLE_EXPENSE,null,contentValues);

        return dataInserted;
    }

    /**
     * getAllExpense is a method that
     * @return all the expense of the DATABASE
     */
    public ArrayList<Expense> getAllExpense(){
        ArrayList<Expense>expenseArrayList = new ArrayList<>();
        String selectQuery = "select * from " + DatabasePersonalFinance.TABLE_EXPENSE + " ORDER BY " + DatabasePersonalFinance.EXPENSE_DATE + " ASC"; //query that is made to the database
        SQLiteDatabase sqLiteDatabase = databasePersonalFinance.getReadableDatabase(); // with this query, we will read the database
        Cursor cursor=sqLiteDatabase.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabasePersonalFinance.EXPENSE_ID));
                String expenseCatagory = cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.EXPENSE_CATAGORY));
                String expenseTitle = cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.EXPENSE_DESCRIPTION));
                String expenseDate = cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.EXPENSE_DATE));
                String expenseAmount = cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.EXPENSE_AMOUNT));
                Expense expense = new Expense(id,expenseCatagory,expenseTitle,expenseDate,expenseAmount);
                expenseArrayList.add(expense);
            }while(cursor.moveToNext());
        }

        return expenseArrayList;
    }

    /**
     * getAllExpense is a method that
     * @return all the expense of the DATABASE BETWEEN 2 DATES
     * And Date A is before Date B
     */
    public ArrayList<Expense> getAllExpenseBetween(String dateA, String dateB){
        ArrayList<Expense>expenseArrayList = new ArrayList<>();
        String selectQuery = "select * from " + DatabasePersonalFinance.TABLE_EXPENSE +
                " WHERE " + DatabasePersonalFinance.EXPENSE_DATE +
                " BETWEEN '" + dateA + "' AND '" + dateB + "'" +
                " ORDER BY " + DatabasePersonalFinance.EXPENSE_DATE + " ASC"; //query that is made to the database
        SQLiteDatabase sqLiteDatabase = databasePersonalFinance.getReadableDatabase(); // with this query, we will read the database
        Cursor cursor=sqLiteDatabase.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabasePersonalFinance.EXPENSE_ID));
                String expenseCatagory = cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.EXPENSE_CATAGORY));
                String expenseTitle = cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.EXPENSE_DESCRIPTION));
                String expenseDate = cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.EXPENSE_DATE));
                String expenseAmount = cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.EXPENSE_AMOUNT));
                Expense expense = new Expense(id,expenseCatagory,expenseTitle,expenseDate,expenseAmount);
                expenseArrayList.add(expense);
            }while(cursor.moveToNext());
        }

        return expenseArrayList;
    }
}
