package com.controller;

import com.model.Invoice;
import com.model.InvoiceItems;
import com.view.InvoiceItemTable;
import com.view.InvoiceTable;
import com.view.MainFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class AppListener implements ActionListener, ListSelectionListener {
    MainFrame mainFrame = null;
    FileOperations fileOperations = null;
    DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
    String invoiceFileName = null;
    String itemsFileName = null;

    public AppListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        fileOperations = new FileOperations(mainFrame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "L":
                loadFile();
                break;
            case "S":
                saveFiles();
                break;
            case "DeleteInvoice":
                deleteInv();
                break;
            case "DeleteItem":
                deleteItem();
                break;
            case "CreateNewItem":
                createNewItem();
                break;

            case "createNewInvoice" :
                try {
                    createNewInvoice();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mainFrame,
                            "Read Error\n" + ex.getMessage(), "Error parsing ", 0);

                }
                break;



        }

    }
    private void deleteItem() {
if(mainFrame.getInvoiceItemTbl().getSelectionModel().isSelectionEmpty()){}
else {
    mainFrame.getInvoiceItemTable().getInvoiceItemsList().remove(
            findInvoiceItemByNum(
                    (Integer) mainFrame.getInvoiceTbl().getValueAt(mainFrame.getInvoiceTbl().getSelectedRow(), 0),
                    mainFrame.getInvoiceItemTbl().getValueAt(mainFrame.getInvoiceItemTbl().getSelectedRow(), 0).toString()
            )

    );
    mainFrame.getInvoiceItemTable().fireTableDataChanged();
}
    }
    public void deleteInv() {
//        this.invoiceTbl.remove(this.invoiceTbl.getSelectedRow());
if (mainFrame.getInvoiceTbl().getSelectionModel().isSelectionEmpty()){}
else {
        mainFrame.getInvoiceList().remove(findInvoiceByNum(
                (Integer) mainFrame.getInvoiceTbl().getValueAt(mainFrame.getInvoiceTbl().getSelectedRow(),0)));
        mainFrame.getInvoiceTable().fireTableDataChanged();}
    }
    public void createNewInvoice() throws ParseException {
//        System.out.println("2");

        mainFrame.getInvoiceList().add(new Invoice(newInvoiceNumber(),
                dateFormat.parse(String.valueOf(LocalDate.now())),""));
        mainFrame.getInvoiceTable().fireTableDataChanged();

    }

    private int newInvoiceNumber() {
        int max = 0;
        for (Invoice invoice : mainFrame.getInvoiceList()) {
            if (invoice.getInvoiceNumber() > max) {
                max = invoice.getInvoiceNumber();

            }
        }
        return max + 1;
    }

    private void createNewItem()
    {
        System.out.println("createNewItem");
        Invoice invoice=findInvoiceByNum(
                (Integer) mainFrame.getInvoiceTbl().getValueAt(mainFrame.getInvoiceTbl().getSelectedRow(),0));
        mainFrame.getInvoiceItemTable().getInvoiceItemsList().add(
                new InvoiceItems(invoice,"",0,0)
        );
        mainFrame.getInvoiceItemTable().fireTableDataChanged();
    }
    private Invoice findInvoiceByNum(int invNum) {
        Invoice invoice = null;
        for (Invoice inv : mainFrame.getInvoiceList()) {
            if (invNum == inv.getInvoiceNumber()) {
                invoice = inv;
                break;
            }
        }
        return invoice;
    }
    private InvoiceItems findInvoiceItemByNum(int invNum , String itemNum) {
        InvoiceItems invoiceItems = null;
        for (InvoiceItems invItem : mainFrame.getInvoiceItemTable().getInvoiceItemsList()) {
            if (invNum==invItem.getInvoice().getInvoiceNumber() &
                    itemNum == invItem.getItem().getItemName()) {
                invoiceItems = invItem;
                break;
            }
        }
        return invoiceItems;
    }


    private void loadFile() {

        mainFrame.setInvoiceTable(new InvoiceTable());
        mainFrame.getInvoiceTbl().setModel(new DefaultTableModel());
        mainFrame.getInvoiceTbl().validate();

        mainFrame.setInvoiceItemTable(new InvoiceItemTable());
        mainFrame.getInvoiceItemTbl().setModel(new DefaultTableModel());
        mainFrame.getInvoiceItemTbl().validate();
        /*-----------------------------------------------*/
        JOptionPane.showMessageDialog(mainFrame, "Please, select Invoices file!", "Open Invoices", JOptionPane.WARNING_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        if ((fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION)) {
            this.invoiceFileName = fileChooser.getSelectedFile().getPath();
            File invoiceFile = fileChooser.getSelectedFile();
        }
        else this.invoiceFileName = "C:\\Users\\dell\\Documents\\Sales Invoice Generator\\InvoiceHeader.csv";

        fileOperations.setHeaderFileName(this.invoiceFileName);
        fileOperations.readHeaderFile();
        mainFrame.setInvoiceList( fileOperations.getInvoiceList());
        /***************************************************************************/
        JOptionPane.showMessageDialog(mainFrame, "Please, select Items file!", "Open Items", JOptionPane.WARNING_MESSAGE);
        JFileChooser fileChooserItems = new JFileChooser();
        if ((fileChooserItems.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION)) {
            this.itemsFileName = fileChooserItems.getSelectedFile().getPath();


        }
        else this.itemsFileName = "C:\\Users\\dell\\Documents\\Sales Invoice Generator\\InvoiceLine.csv";
        System.out.println(this.invoiceFileName+" fileName");
        System.out.println(this.itemsFileName+" fileItemName");

        fileOperations.setItemFileName(this.itemsFileName);
//            fileOperations.readItemFile("0");
        fileOperations.readItemFileInvoice();
        mainFrame.setInvoiceTable(new InvoiceTable(mainFrame,mainFrame.getInvoiceList()));
        mainFrame.getInvoiceTbl().setModel(mainFrame.getInvoiceTable());
        mainFrame.getInvoiceTbl().validate();

        fileOperations.readFiles();
            /*invTblModel = new DefaultTableModel();
//        invoiceTbl = new JTable();
            invItemTblModel = new DefaultTableModel();
            fileOperations = new FileOperations(this,this.fileName,this.fileItemName);
            fileOperations.readFiles();

            this.loadTables();*/
    }

    private void saveFiles()
    {
        File invoiceFile = null;
        File invoiceItemFile = null ;
        fileOperations.getData();
        /* */JOptionPane.showMessageDialog(mainFrame, "Please, select Invoices file!", "New Invoices", JOptionPane.WARNING_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        if ((fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION)) {
//            this.fileName = fileChooser.getSelectedFile().getPath();
            invoiceFile = fileChooser.getSelectedFile();
        }
        else invoiceFile = new File("C:\\Users\\dell\\Documents\\Sales Invoice Generator\\InvoiceHeader.csv");

        fileOperations.saveInvoiceFile(invoiceFile);
        ////////////////////////////////////////////
        JOptionPane.showMessageDialog(mainFrame, "Please, select Items file!", "New Items", JOptionPane.WARNING_MESSAGE);
        JFileChooser fileChooser1 = new JFileChooser();
        if ((fileChooser1.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION)) {
//            this.fileName = fileChooser1.getSelectedFile().getPath();
            invoiceItemFile = fileChooser1.getSelectedFile();
        }
        else invoiceItemFile =new File(this.itemsFileName);
        fileOperations.saveItemsFile(invoiceItemFile);
    }

    private void invoicesTableRowSelected() throws ParseException {
//        this.invoiceTable.reloadInvoiceJTable();
//        this.invoiceTable.addRow();

        int selectedRowIndex = mainFrame.getInvoiceTbl().getSelectedRow();
        if (selectedRowIndex >= 0) {
            Invoice invoice = mainFrame.getInvoiceTable().getInvoiceList().get(selectedRowIndex);
            mainFrame.getInvoiceCustNameTF().setText(invoice.getCustomerName());
            mainFrame.getInvoiceDateTF().setText(dateFormat.format(invoice.getInvoiceDate()));
            mainFrame.getInvNumLbl().setText(/*"" +*/ String.valueOf(invoice.getInvoiceNumber()));
            mainFrame.getInvTotalLbl().setText("" + invoice.getInvTotal());
            ArrayList<InvoiceItems> invoiceItems = invoice.getInvoiceItems();
            mainFrame.setInvoiceItemTable( new InvoiceItemTable(invoiceItems));
//            this.invoiceItemTable.setInvoiceItemsList(invoiceItems);
            mainFrame.getInvoiceItemTbl().setModel(mainFrame.getInvoiceItemTable());
            mainFrame.getInvoiceItemTable().fireTableDataChanged();

        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        try {
            invoicesTableRowSelected();
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
