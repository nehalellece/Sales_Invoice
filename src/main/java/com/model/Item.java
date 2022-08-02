package com.model;

public class Item {

    private int itemNumber;
    private String itemName;

    public Item(/*int itemNumber,*/ String itemName) {
//        this.itemNumber = itemNumber;
        this.itemName = itemName;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public String toString() {
        return "Item Data Item Number is :"+this.getItemNumber() + " , item name is :"+
                this.getItemName();
    }
}
