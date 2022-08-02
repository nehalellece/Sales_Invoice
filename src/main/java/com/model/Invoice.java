package com.model;

import com.view.InvoiceTable;
import com.view.MainFrame;

import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice {

    private int invoiceNumber;
    private Date invoiceDate;
    private Customer invoiceCustomer;
    private MainFrame mainFrame;
    private ArrayList<InvoiceItems> invoiceItemsArrayList;
    private DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");


    public Invoice(int invoiceNumber, Date invoiceDate, Customer invoiceCustomer) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.invoiceCustomer = invoiceCustomer;
    }
    public Invoice(int invoiceNumber, Date invoiceDate, String customerName) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.invoiceCustomer = new Customer(customerName);
    }


    public Invoice(int invoiceNumber, String invoiceDate, Customer invoiceCustomer) {
        this.invoiceNumber = invoiceNumber;
        try {
            this.invoiceDate = new SimpleDateFormat("dd-mm-yyyy").parse(invoiceDate);

        } catch (ParseException e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Date Format Error\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);

        }
        this.invoiceCustomer = invoiceCustomer;
    }
    public Invoice(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setInvoiceCustomer(Customer invoiceCustomer) {
        this.invoiceCustomer = invoiceCustomer;
    }

    public void setItems(ArrayList<InvoiceItems> invoiceItems)
    {
        this.invoiceItemsArrayList = invoiceItems;
    }
    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public Customer getInvoiceCustomer() {
        return invoiceCustomer;
    }
    public String getCustomerName()
    {
        return this.getInvoiceCustomer().getCustomerName();
    }

    public ArrayList<InvoiceItems> getInvoiceItems() {
        if(this.invoiceItemsArrayList==null)
            this.invoiceItemsArrayList = new ArrayList<>();
        return invoiceItemsArrayList;
    }

    public void setInvoiceItemsArrayList(ArrayList<InvoiceItems> invoiceItemsArrayList) {
        this.invoiceItemsArrayList = invoiceItemsArrayList;
    }

    public Invoice(){}


    public void createInvoice(InvoiceItems invoiceItems)
    {
        this.getInvoiceItems().add(invoiceItems);
    }
    public double getInvTotal()
    {
        double invTotal = 0.0;
        for (InvoiceItems invoiceItems :getInvoiceItems()){
            invTotal += invoiceItems.getItemTotal();
        }
        return invTotal;
    }
    public  Invoice createInvoice(String[] metadata)  {

//        Date invoiceDate;
        Date invoiceDate = null;
        int invoiceNumber = 0;
        try {  invoiceNumber= Integer.parseInt(metadata[0]);
            invoiceDate = dateFormat.parse(metadata[1]);
        }catch (NumberFormatException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame,"Invoice.createInvoice Number Format Error\n" + e.getMessage(),"Error",0);

        }
        catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame,"Invoice.createInvoice parse Error\n" + e.getMessage(),"Error",0);

        }
        String customerName = metadata[2];


        return new Invoice(invoiceNumber, invoiceDate, new Customer(customerName));
        /*try {
         */

//            System.out.println(metadata[1]);
//            invoiceDate = formatter.parse(new String(metadata[1]));
//        invoiceDate = metadata[1];
//            System.out.println(invoiceDate);
        /*} catch (ParseException e) {
            throw new RuntimeException(e);
        }*/

    }
    public String getData()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        return "" + this.getInvoiceNumber()+","+dateFormat.format(this.getInvoiceDate())+","+
                this.getCustomerName()+"\r\n";
    }

    @Override
    public String toString() {

        String str="InvoiceFrame{\n" + "invNum=" + this.getInvoiceNumber() + ", customerName=" + this.getCustomerName() +
                ", invDate=" + this.getInvoiceDate() +"/n" ;
        for(InvoiceItems invoiceItems: getInvoiceItems()){
            str += "\n\t" + invoiceItems.toString();
        }
        str+="\n"+'}';
        return str;
        /*
        return "Invoice Data:  invoice number is :"+this.getInvoiceNumber() + " , Invoice Date is :"+
                this.getInvoiceDate() + " , Customer Name = "+ this.getInvoiceCustomer().getCustomerName();
*/
    }
}
