package com.view;

import com.model.Customer;
import com.model.Invoice;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@SuppressWarnings("serial")
public class InvoiceTable extends AbstractTableModel {
    private List<Invoice> invoiceList ;
    private DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
private MainFrame mainFrame;



    public InvoiceTable(MainFrame mainFrame , List<Invoice> invoices)
    {
        this.mainFrame=mainFrame;
        this.invoiceList = invoices;
    }

    public InvoiceTable() {

    }

    public List<Invoice> getInvoiceList()
    {
        return this.invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    @Override
    public int getRowCount() {
        return invoiceList.size();
    }

    @Override
    public int getColumnCount() {
        return 4 ;
    }



    @Override
    public String getColumnName(int columnIndex)
    {
        switch (columnIndex){
            case 0 :
                return "invoice number";
            case 1 :
                return "Customer Name" ;
            case 2 :
                return "Invoice Date";
            case 3 :
                return "Invoice Total";
            default:
                return "";

        }
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Invoice invoiceRow = invoiceList.get(rowIndex);
        switch (columnIndex){
            case  0 : return  invoiceRow.getInvoiceNumber();
            case  1 : return  invoiceRow.getCustomerName();
            case  2 : return  dateFormat.format(invoiceRow.getInvoiceDate());
            case  3 : return  invoiceRow.getInvTotal();
            default: return  null;
        }
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0 :
                return Integer.class;
            case 1 :
                return String.class;
            case 2 :
                return String.class;
            case 3 :
                return Double.class;
            default:
                return Object.class;
        }
    }



    public void reloadInvoiceJTable() throws ParseException {
        System.out.println("loading Invoice table..");
        clearModel();
        for (int i = 0; i < 20; i++) {
            addRow();
        }
    }
    private int newInvoiceNumber() {
        int max = 0;
        for (Invoice invoice : this.getInvoiceList()) {
            if (invoice.getInvoiceNumber() > max) {
                max = invoice.getInvoiceNumber();

            }
        }
        return max + 1;
    }
    public void clearModel() {
        this.invoiceList.clear();
        fireTableDataChanged();
    }

    public void addRow() throws ParseException {
        invoiceList.add(new Invoice(newInvoiceNumber(), dateFormat.parse(String.valueOf(LocalDate.now())),""));
        System.out.println(getRowCount());
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }





    @Override
    public void setValueAt(Object aValue, int rowIndex, int colIndex) {
        if (rowIndex < 0 || rowIndex >= getRowCount()) {
            // throw exception
        }
        if (colIndex < 0 || colIndex >= getColumnCount()) {
            // throw exception
        }

        Invoice invoice = this.invoiceList.get(rowIndex);

        switch (colIndex) {
            case 2:
                try {
                    invoice.setInvoiceDate(dateFormat.parse((String) aValue));
                } catch (ParseException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(mainFrame,"InvoiceTable.setValueAt parse Error\n" + e.getMessage(),"Error",0);

                }
                break;
            case 1:
                invoice.setInvoiceCustomer(new Customer(aValue.toString()));

                break;
            case 0:
                invoice.setInvoiceNumber((Integer) aValue);
        }
        fireTableRowsUpdated(rowIndex, rowIndex);

    }
}
