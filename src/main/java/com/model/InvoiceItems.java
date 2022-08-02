package com.model;

public class InvoiceItems {

    private Invoice invoice;
    private Item item;
    private double itemPrice;
    private int itemQun;

    private double itemTotal ;

    public InvoiceItems() {
    }

    public InvoiceItems(Invoice invoice, Item item, float itemPrice, int itemQun) {
        this.invoice = invoice;
        this.item = item;
        this.itemPrice = itemPrice;
        this.itemQun = itemQun;
        this.setItemTotal(this.itemQun*this.itemPrice);
    }

    public InvoiceItems(Invoice invoice, String item, float itemPrice, int itemQun) {
        this.invoice = invoice;
        this.item = new Item(item);
        this.itemPrice = itemPrice;
        this.itemQun = itemQun;
        this.setItemTotal(this.itemQun*this.itemPrice);
    }
    public void setItemTotal(double itemTot){
        this.itemTotal = itemTot;
    }
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    public void setItemName(String itemName)
    {
        this.item = new Item(itemName);
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemQun(int itemQun) {
        this.itemQun = itemQun;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public Item getItem() {
        return item;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getItemQun() {
        return itemQun;
    }

    public double getItemTotal(){
        return itemTotal;
    }

    public InvoiceItems createInvoiceItem(String[] metadata) {
        Invoice invoice1 = new Invoice(Integer.parseInt(metadata[0]));
        Item item1 = new Item(metadata[1]);
        Float itemPrice1 = Float.parseFloat(metadata[2]);
        int itemQun1 = Integer.parseInt(metadata[3]);




        return new InvoiceItems(invoice1, item1, itemPrice1,itemQun1);

    }
    public String getData()
    {
        return "" + this.getInvoice().getInvoiceNumber() + "," + this.getItem().getItemName() +
                "," + this.getItemPrice() + "," + this.getItemQun()+"\r\n";
    }

    @Override
    public String toString() {
        return "InvoiceItem Data \ninvoice number is :"+this.invoice.getInvoiceNumber() + " , item name is :"+
                this.getItem() + " , item price = "+ this.getItemPrice() + " , qun = "+ this.getItemQun()+"\n";
    }
}
