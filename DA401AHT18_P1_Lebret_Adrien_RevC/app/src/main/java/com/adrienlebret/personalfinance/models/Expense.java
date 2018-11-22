package com.adrienlebret.personalfinance.models;

public class Expense {
    private  int id;
    private String expenseCatagory;
    private String expenseDescription;
    private String iexpenseDate;
    private String expenseAmount;

    /**
     * Object Expense with the
     * @param id
     */
    public Expense(int id, String expenseCatagory, String expenseDescription, String iexpenseDate, String expenseAmount) {
        this.id = id;
        this.expenseCatagory = expenseCatagory;
        this.expenseDescription = expenseDescription;
        this.iexpenseDate = iexpenseDate;
        this.expenseAmount = expenseAmount;
    }

    /**
     * Object Expense without
     * id
     */
    public Expense(String expenseCatagory, String expenseDescription, String iexpenseDate, String expenseAmount) {
        this.expenseCatagory = expenseCatagory;
        this.expenseDescription = expenseDescription;
        this.iexpenseDate = iexpenseDate;
        this.expenseAmount = expenseAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpenseCatagory() {
        return expenseCatagory;
    }

    public void setExpenseCatagory(String expenseCatagory) {
        this.expenseCatagory = expenseCatagory;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public String getIexpenseDate() {
        return iexpenseDate;
    }

    public void setIexpenseDate(String iexpenseDate) {
        this.iexpenseDate = iexpenseDate;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }
}
