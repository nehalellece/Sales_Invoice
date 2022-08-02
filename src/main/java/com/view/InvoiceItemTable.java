package com.view;

import com.model.Customer;
import com.model.Invoice;
import com.model.InvoiceItems;
import com.model.Item;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InvoiceItemTable extends AbstractTableModel {

    private List<InvoiceItems> invoiceItemsList;
    private DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

    public InvoiceItemTable(List<InvoiceItems> invoiceItemsList) {
        this.invoiceItemsList = invoiceItemsList;
    }

    public InvoiceItemTable() {

    }

    public void setInvoiceItemsList(List<InvoiceItems> invoiceItemsList) {
        this.invoiceItemsList = invoiceItemsList;
    }

    public List<InvoiceItems> getInvoiceItemsList() {
        return invoiceItemsList;
    }



    @Override
    public int getRowCount() {
        return invoiceItemsList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceItems invoiceItemsRow =invoiceItemsList.get(rowIndex);
        switch(columnIndex){
            case 0: return invoiceItemsRow.getItem().getItemName();
            case 1 : return invoiceItemsRow.getItemPrice();
            case 2 : return invoiceItemsRow.getItemQun();
            case 3 : return invoiceItemsRow.getItemTotal();
            default:
                return null;
        }
    }
    @Override
    public String getColumnName(int columnIndex){
        switch (columnIndex) {
            case 0 :
                return "Item Name";
            case 1 :
                return "item Price";
            case 2 :
                return "Count";
            case 3 :
                return "Line Total";
            default:
                return "";
        }
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0 :
                return String.class;
            case 1 :
                return Double.class;
            case 2 :
                return Integer.class;
            case 3 :
                return Double.class;
            default:
                return Object.class;
        }
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int colIndex) {
        if (rowIndex < 0 || rowIndex >= getRowCount()) {
            // throw exception
        }
        if (colIndex < 0 || colIndex >= getColumnCount()) {
            // throw exception
        }

        InvoiceItems invoiceItems = this.invoiceItemsList.get(rowIndex);

        switch (colIndex) {
            case 2:
                invoiceItems.setItemQun((Integer)aValue);
                invoiceItems.setItemTotal(invoiceItems.getItemQun()*invoiceItems.getItemPrice());
                break;
            case 1:
                invoiceItems.setItemPrice((double) aValue);


                break;
            case 0:
                invoiceItems.setItem(new Item((String)aValue));
                invoiceItems.setItemName((String) aValue);
        }
        fireTableRowsUpdated(rowIndex, rowIndex);

    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

}
