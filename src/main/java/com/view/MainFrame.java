package com.view;

import com.controller.AppListener;
import com.controller.FileOperations;
import com.model.Invoice;
import com.model.InvoiceItems;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainFrame extends JFrame {

    AppListener appListener=null;
    ///// Menu
    private JMenu menu;
    private JMenuBar menuBar;
    // Menu Item
    private JMenuItem load;
    private JMenuItem save;


    /////Buttons
    private  JButton createInvBtn;
    private JButton createItemsBtn;

    private JButton deleteInvBtn ;
    private JButton deleteItemBtn;

/////Text Fields
    private JTextField invoiceNumberTF;
    private JTextField invoiceCustNameTF ;
    private JTextField invoiceDateTF ;

    private JTextField newInvoiceNumberTF;
    private JTextField newInvoiceCustNameTF;
    private JTextField newInvoiceDateTF;


    ///// Labels
    private JLabel invoiceNumberLbl;
    private JLabel invoiceCutNameLbl;
    private JLabel invoiceDateLbl;
    private JLabel newInvoiceNumberLbl;
    private JLabel newInvoiceCustNameLbl;
    private JLabel newInvoiceDateLbl;

    JLabel jLabel5 = new JLabel();
    JLabel invNumLbl = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel invTotalLbl = new JLabel();

    ////////////// Tables
private JTable invoiceItemTbl;
private JTable invoiceTbl;

private JScrollPane invoiceScroll;
private JScrollPane invoiceItemScroll;


/////////
    private List<Invoice> invoiceList = new ArrayList<>();
    private InvoiceTable invoiceTable ;
    private InvoiceItemTable invoiceItemTable;

    ///
    private DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

    private FileOperations fileOperations = new FileOperations(this);
    private String fileName;
    private String fileItemName;
    public InvoiceItemTable getInvoiceItemTable() {
        return invoiceItemTable;
    }
    public JTable getInvoiceItemTbl() {
        return invoiceItemTbl;
    }
    public JTable getInvoiceTbl() {
        return invoiceTbl;
    }
    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }
    public InvoiceTable getInvoiceTable() {
        return invoiceTable;
    }
    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }
    public JTextField getInvoiceDateTF() {
        return invoiceDateTF;
    }
    public JTextField getInvoiceCustNameTF() {
        return invoiceCustNameTF;
    }
    public JLabel getInvNumLbl() {
        return invNumLbl;
    }
    public JLabel getInvoiceNumberLbl() {
        return invoiceNumberLbl;
    }
    public JLabel getInvTotalLbl() {
        return invTotalLbl;
    }
    public MainFrame() throws HeadlessException {

        appListener=new AppListener(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Menu
        menu = new JMenu();
        menuBar=new JMenuBar();

        menu.setText("File");

        load=new JMenuItem();
        load.addActionListener(appListener);
        load.setText("Load File");
        load.setActionCommand("L");
        load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMenuItemActionPerformed(evt);
            }
        });

        menu.add(load);

        save = new JMenuItem();
        save.addActionListener(appListener);
        save.setText("Save File");
        save.setActionCommand("S");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        menu.add(save);

        menuBar.add(menu);


        setJMenuBar(menuBar);

        //Tabels
        invoiceScroll = new JScrollPane();

        invoiceTbl = new JTable();
        invoiceTbl.getSelectionModel().addListSelectionListener(/*new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {


            }
        }*/appListener);


        invoiceTbl.setModel(new DefaultTableModel(new Object[][]{},new String[]{}));
        invoiceScroll.setViewportView(invoiceTbl);
        invoiceTbl.getAccessibleContext().setAccessibleName("");




        invoiceItemScroll = new JScrollPane();
        invoiceItemTbl = new JTable();

        invoiceItemTbl.setModel(new DefaultTableModel(new Object[][]{},new String[]{}));
        invoiceItemScroll.setViewportView(invoiceItemTbl);


        ///Buttons

        createInvBtn = new JButton("Create New Invoice");
        /*createInvBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                try {
                    createInvBtnActionPerformed(evt);
                } catch (ParseException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(MainFrame.super.getContentPane(),
                            "Read Error\n" +  e.getMessage(), "Error parsing ", 0);

                }
            }
        });*/
        createInvBtn.addActionListener(appListener);
        createInvBtn.setActionCommand("createNewInvoice");
        

        deleteInvBtn = new JButton("Delete Invoice");
        deleteInvBtn.addActionListener(appListener);
        /*deleteInvBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                deleteInvBtnActionPerformed(evt);
            }
        });*/
        deleteInvBtn.setActionCommand("DeleteInvoice");
        

        createItemsBtn = new JButton("Create New Line");
        createItemsBtn.setActionCommand("CreateNewItem");
        createItemsBtn.addActionListener(appListener);

        deleteItemBtn = new JButton("Delete Line");
        deleteItemBtn.setActionCommand("DeleteItem");
        deleteItemBtn.addActionListener(appListener);

        // Text Fields

        invoiceCustNameTF = new JTextField();
        invoiceDateTF = new JTextField();
        invoiceNumberTF = new JTextField();

        newInvoiceNumberTF = new JTextField();

        newInvoiceCustNameTF = new JTextField();
        newInvoiceDateTF = new JTextField();


        // Labels

        invoiceDateLbl = new JLabel("Invoice Date");
        invoiceCutNameLbl = new JLabel("Customer Name");
        invoiceNumberLbl  = new JLabel("Invoice Number");

        newInvoiceCustNameLbl = new JLabel("new Invoice Cust Name");
        newInvoiceDateLbl = new JLabel("new Invoice Date");
        newInvoiceNumberLbl = new JLabel("new Invoice Number");

        /////////// LAyout
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(groupLayout);

        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(invoiceScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(46, 46, 46)
                                                .addComponent(createInvBtn)
                                                .addGap(68, 68, 68)
                                                .addComponent(deleteInvBtn))
                                )
                                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, groupLayout.createSequentialGroup()
                                                                .addComponent(invoiceNumberLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                .addComponent(jLabel5))
                                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                                .addComponent(invNumLbl)
                                                                                .addGap(0, 0, Short.MAX_VALUE))))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(invoiceDateLbl)
                                                                        .addComponent(jLabel4))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(invTotalLbl)
                                                                        .addComponent(invoiceDateTF, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(invoiceItemScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addComponent(invoiceCutNameLbl)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(invoiceCustNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(72, 72, 72)
                                                .addComponent(createItemsBtn)
                                                .addGap(98, 98, 98)
                                                .addComponent(deleteItemBtn)))
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()

                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(invoiceNumberLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(invNumLbl)
                                                )
                                                .addGap(25, 25, 25)
                                                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(invoiceDateLbl)
                                                        .addComponent(invoiceDateTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGap(7, 7, 7)
                                                                .addComponent(invoiceCutNameLbl))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(invoiceCustNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(26, 26, 26)
                                                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel4)
                                                        .addComponent(invTotalLbl))
                                                .addGap(18, 18, 18)
                                                .addComponent(invoiceItemScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(createItemsBtn)
                                                        .addComponent(deleteItemBtn)))
                                        .addComponent(invoiceScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(groupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(createInvBtn)
                                        .addComponent(deleteInvBtn))
                                .addGap(104, 104, 104))
        );



        this.add(newInvoiceCustNameLbl);

        setVisible(true);

        pack();
    }

    private void deleteInvBtnActionPerformed(ActionEvent evt) {
        appListener.deleteInv();
    }

    private void createInvBtnActionPerformed(ActionEvent evt) throws ParseException {
        appListener.createNewInvoice();
    }

    private void saveMenuItemActionPerformed(ActionEvent evt) {
    }

    private void loadMenuItemActionPerformed(ActionEvent evt) {
    }

    public void setInvoiceTable(InvoiceTable invoiceTable) {
        this.invoiceTable = invoiceTable;
    }

    public void setInvoiceItemTable(InvoiceItemTable invoiceItemTable) {
        this.invoiceItemTable = invoiceItemTable;
    }



}
