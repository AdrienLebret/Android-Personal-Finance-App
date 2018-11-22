package com.adrienlebret.personalfinance.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.adrienlebret.personalfinance.models.Income;

import java.util.ArrayList;

/**
 * Created by Adrien LEBRET
 */
public class IncomeDatabase {
    private Context context;
    private DatabasePersonalFinance databasePersonalFinance;

    public IncomeDatabase(Context context) {
        this.context = context;
        databasePersonalFinance = new DatabasePersonalFinance(context);
    }

    /**
     * When adding or updating a row, request a writable reference to the database,
     * then instantiate ContentValues,
     * add the necessary data and call INSERT or UPDATE
     */
    public long addIncome(Income income){
        SQLiteDatabase sqLiteDatabase= databasePersonalFinance.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabasePersonalFinance.INCOME_CATAGORY,income.getIncomeCatagory());
        contentValues.put(DatabasePersonalFinance.INCOME_DESCRIPTION,income.getIncomeDescription());
        contentValues.put(DatabasePersonalFinance.INCOME_DATE,income.getIncomeDate());
        contentValues.put(DatabasePersonalFinance.INCOME_AMOUNT,income.getIncomeAmount());
        long dataInserted=sqLiteDatabase.insert(DatabasePersonalFinance.TABLE_INCOME,null,contentValues);
        sqLiteDatabase.close();
        return dataInserted;
    }

    /**
     * Method for modifying an income
     * @param income
     * @return the data, updated !
     */
    public long updateIncome(Income income){
        SQLiteDatabase sqLiteDatabase= databasePersonalFinance.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabasePersonalFinance.INCOME_CATAGORY,income.getIncomeCatagory());
        contentValues.put(DatabasePersonalFinance.INCOME_DESCRIPTION,income.getIncomeDescription());
        contentValues.put(DatabasePersonalFinance.INCOME_DATE,income.getIncomeDate());
        contentValues.put(DatabasePersonalFinance.INCOME_AMOUNT,income.getIncomeAmount());
        long dataUpdated=sqLiteDatabase.update(DatabasePersonalFinance.TABLE_INCOME,contentValues, DatabasePersonalFinance.INCOME_ID+" =? ",new String[]{String.valueOf(income.getId())});
        sqLiteDatabase.close();
        return dataUpdated;
    }

    public ArrayList<Income>getAllIncome(){
        ArrayList<Income>incomeArrayList=new ArrayList<>();
        String selectQuery = "select * from "+ DatabasePersonalFinance.TABLE_INCOME + " ORDER BY " + DatabasePersonalFinance.INCOME_DATE;
        SQLiteDatabase sqLiteDatabase= databasePersonalFinance.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex(DatabasePersonalFinance.INCOME_ID));
                String incomeCatagory=cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.INCOME_CATAGORY));
                String incomeDescription=cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.INCOME_DESCRIPTION));
                String incomeDate=cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.INCOME_DATE));
                String incomeAmount=cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.INCOME_AMOUNT));
                Income income=new Income(id,incomeCatagory,incomeDescription,incomeDate,incomeAmount);
                incomeArrayList.add(income);
            }while(cursor.moveToNext());
        }
        return incomeArrayList;
    }


    public ArrayList<Income>getAllIncomeBetween(String dateA, String dateB){
        ArrayList<Income>incomeArrayList = new ArrayList<>();
        String selectQuery = "select * from "+ DatabasePersonalFinance.TABLE_INCOME +
                " WHERE " + DatabasePersonalFinance.INCOME_DATE +
                " BETWEEN '" + dateA + "' AND '" + dateB + "'" +
                " ORDER BY " + DatabasePersonalFinance.INCOME_DATE;
        SQLiteDatabase sqLiteDatabase= databasePersonalFinance.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex(DatabasePersonalFinance.INCOME_ID));
                String incomeCatagory=cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.INCOME_CATAGORY));
                String incomeDescription=cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.INCOME_DESCRIPTION));
                String incomeDate=cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.INCOME_DATE));
                String incomeAmount=cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.INCOME_AMOUNT));
                Income income=new Income(id,incomeCatagory,incomeDescription,incomeDate,incomeAmount);
                incomeArrayList.add(income);
            }while(cursor.moveToNext());
        }
        return incomeArrayList;
    }




    public Income getSingleIncome(int id){
        SQLiteDatabase sqLiteDatabase = databasePersonalFinance.getReadableDatabase();
        String selectQuery = "select * from "+ DatabasePersonalFinance.TABLE_INCOME+" where id = "+id;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        Income income = null;

        if (cursor.moveToFirst()){
            String incomeCatagory=cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.INCOME_CATAGORY));
            String incomeDescription=cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.INCOME_DESCRIPTION));
            String incomeDate=cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.INCOME_DATE));
            String incomeAmount=cursor.getString(cursor.getColumnIndex(DatabasePersonalFinance.INCOME_AMOUNT));
            income=new Income(id,incomeCatagory,incomeDescription,incomeDate,incomeAmount);
        }
        return income;
    }
}
