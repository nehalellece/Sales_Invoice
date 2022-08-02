package com.model;

public class Customer {
    private int customerID;
    private String customerName;


    public Customer(/*int customerID,*/ String customerName) {
//        this.customerID = customerID;
        this.customerName = customerName;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String toString() {
        return "Customer Data Customer Number is :"+this.getCustomerID() + " , Customer name is :"+
                this.getCustomerName();
    }
}
