package com.view;

import com.model.Invoice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddInvoiceDialog extends JDialog implements ActionListener {
    private JTextField newCustNameTF;
    private JTextField newInvDateTF;
    private JLabel newCustNameLbl;
    private JLabel newInvDateLbl;
    private JButton okBtn;
    private JButton cancelBtn;
private MainFrame mainFrame = null;

    public AddInvoiceDialog(MainFrame frame) {
this.mainFrame=frame;
        newCustNameLbl = new JLabel("Customer Name:");
        newCustNameTF = new JTextField(20);
        newInvDateLbl = new JLabel("Invoice Date:");
        newInvDateTF = new JTextField(20);
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");

        okBtn.setActionCommand("createNewInv");
        cancelBtn.setActionCommand("cancelNewInv");
        okBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        setLayout(new GridLayout(3, 2));

        add(newInvDateLbl);
        add(newInvDateTF);
        add(newCustNameLbl);
        add(newCustNameTF);
        add(okBtn);
        add(cancelBtn);

        pack();

    }

    public JTextField getNewCustNameTF() {
        return newCustNameTF;
    }

    public JTextField getNewInvDateTF() {
        return newInvDateTF;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "CreateNewInv" :
                AddNewInvoice();
                break;


        }
    }
    private DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

    private int newInvoiceNumber() {
        int max = 0;
        for (Invoice invoice : mainFrame.getInvoiceList()) {
            if (invoice.getInvoiceNumber() > max) {
                max = invoice.getInvoiceNumber();

            }
        }
        return max + 1;
    }

    private void AddNewInvoice() {
        System.out.println("1");
        String newCustNameStr = this.getNewCustNameTF().getText();
        String newInvDateStr = this.getNewInvDateTF().getText();
        this.setVisible(false);
        this.dispose();

        try {
            Date invDate = dateFormat.parse(newInvDateStr);
            int newInvoiceNumber = newInvoiceNumber();
            Invoice invoice = new Invoice(newInvoiceNumber,invDate, newCustNameStr);
            mainFrame.getInvoiceList().add(invoice);
            mainFrame.getInvoiceTable().fireTableDataChanged();

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Wrong date Format", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();

        }
    }
}

