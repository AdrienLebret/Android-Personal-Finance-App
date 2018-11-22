package com.adrienlebret.personalfinance.models;

public class Income {
    private  int id;
    private String incomeCatagory;
    private String incomeDescription;
    private String incomeDate;
    private String incomeAmount;

    /**
     * Object Income with the
     * @param id
     */
    public Income(int id, String incomeCatagory, String incomeDescription, String incomeDate, String incomeAmount) {
        this.id = id;
        this.incomeCatagory = incomeCatagory;
        this.incomeDescription = incomeDescription;
        this.incomeDate = incomeDate;
        this.incomeAmount = incomeAmount;
    }

    /**
     * Object Income without
     * id
     */
    public Income(String incomeCatagory, String incomeDescription, String incomeDate, String incomeAmount) {
        this.incomeCatagory = incomeCatagory;
        this.incomeDescription = incomeDescription;
        this.incomeDate = incomeDate;
        this.incomeAmount = incomeAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIncomeCatagory() {
        return incomeCatagory;
    }

    public void setIncomeCatagory(String incomeCatagory) {
        this.incomeCatagory = incomeCatagory;
    }

    public String getIncomeDescription() {
        return incomeDescription;
    }

    public void setIncomeDescription(String incomeDescription) {
        this.incomeDescription = incomeDescription;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
    }

    public String getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(String incomeAmount) {
        this.incomeAmount = incomeAmount;
    }
}
