package com.controller;
import com.model.Invoice;
import com.model.InvoiceItems;
import com.view.MainFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileOperations  {


    private MainFrame mainFrame = null;
    private String headerFileName = null;
    private String itemFileName = null;
    private List<Invoice> invoiceList = new ArrayList<>();


    public FileOperations(MainFrame frame) {
        this.mainFrame=frame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void setHeaderFileName(String headerFileName) {
        this.headerFileName = headerFileName;
    }

    public void setItemFileName(String itemFileName) {
        this.itemFileName = itemFileName;
    }

    public FileOperations(MainFrame frame, String headerFileName , String itemFileName)
    {
        this.mainFrame=frame;
        this.headerFileName=headerFileName;
        this.itemFileName=itemFileName;
    }
    private DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public void readHeaderFile()
    {

        for (Invoice inv : this.invoiceList )
        {
            inv.getInvoiceItems().clear();
        }
        this.invoiceList = new ArrayList<>();
//        ArrayList<Invoice> invoiceArrayList = new ArrayList<>();
        Path filePath = Paths.get(this.headerFileName);
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(filePath, StandardCharsets.US_ASCII);
            for (String line = bufferedReader.readLine() ; line != null ; line = bufferedReader.readLine() )
            {
                String[] attributes = line.split(",");


                Invoice invoice =new Invoice().createInvoice(attributes);
//                invoice.toString();
                this.invoiceList.add(invoice);
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "File not found Error\n" + e.getMessage(), "Error", 0);
        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "file Error\n" + e.getMessage(), "Error", 0);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame,"2 Number Format Error\n" + e.getMessage(),"Error",0);
        }




    }
    public Invoice findInvoiceByNum(int invNum){
        Invoice invoice = null;
        for(Invoice inv : this.invoiceList) {
            if (invNum == inv.getInvoiceNumber()){
                invoice = inv;
                break;
            }
        }
        return invoice ;
    }
    public void readItemFileInvoice()
    {

//        ArrayList<InvoiceItems> invoiceItemsArrayList = new ArrayList<>();
        Path filePath = Paths.get(itemFileName);
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(filePath,StandardCharsets.US_ASCII);
            for (String line = bufferedReader.readLine() ; line != null ; line = bufferedReader.readLine())
            {
                String[] attributes = line.split(",");
//                long i = bufferedReader.lines().count() ;

//                System.out.println(attributes[0] +"  "+attributes[1]+"  "+attributes[2]+"  "+attributes[3]);
                Invoice invoice = findInvoiceByNum(Integer.parseInt(attributes[0]));
//                invoice.setInvoiceItemsArrayList(new ArrayList<>());
                InvoiceItems invoiceItems = new InvoiceItems().createInvoiceItem(attributes);
                invoice.getInvoiceItems().add(invoiceItems);
                /*try
                {
                    invoice.getInvoiceItems().add(invoiceItems);
                }catch (NullPointerException e)
                    {}
                */


            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "File not found Error\n" + e.getMessage(), "Error", 0);
        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "file Error\n" + e.getMessage(), "Error", 0);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame," 1 Number Format Error\n" + e.getMessage(),"Error",0);
        }finally {
//            return invoiceItemsArrayList;
        }

    }


    public ArrayList<InvoiceItems> readItemFile(String invoiceNumber)
    {
        ArrayList<InvoiceItems> invoiceItemsArrayList = new ArrayList<>();
        Path filePath = Paths.get(itemFileName);
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(filePath,StandardCharsets.US_ASCII);
            for (String line = bufferedReader.readLine() ; line != null ; line = bufferedReader.readLine())
            {
                String[] attributes = line.split(",");
//                long i = bufferedReader.lines().count() ;

//                System.out.println(attributes[0] +"  "+attributes[1]+"  "+attributes[2]+"  "+attributes[3]);
                InvoiceItems invoiceItems = new InvoiceItems().createInvoiceItem(attributes);
                if (invoiceNumber == "0")
                {

                    invoiceItemsArrayList.add(invoiceItems);
                }
                else if (String.valueOf(invoiceItems.getInvoice().getInvoiceNumber()).equals(invoiceNumber))
                {
                    invoiceItemsArrayList.add(invoiceItems);
                }


            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "File not found Error\n" + e.getMessage(), "Error", 0);
        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "Read Error\n" + e.getMessage(), "Error", 0);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame," 1 Number Format Error\n" + e.getMessage(),"Error",0);
        }finally {
            return invoiceItemsArrayList;
        }

    }
    public void readFiles()
    {
        List<Invoice> invoiceArrayList = this.invoiceList;
        ArrayList<InvoiceItems> invoiceItemsArrayList = this.readItemFile("0");
        for ( int i = 0 ; i < invoiceArrayList.size() ; i++)
        {
            System.out.println(invoiceArrayList.get(i).toString());
            /*System.out.println("Invoice Num "+ invoiceArrayList.get(i).getInvoiceNumber() +"\n"
                    +"{\n" + "Invoice " +invoiceArrayList.get(i).getInvoiceNumber() +
                    " Date ( " + invoiceArrayList.get(i).getInvoiceDate() + " ) , "
                    +invoiceArrayList.get(i).getInvoiceCustomer().getCustomerName()  +"\n" +"{");
            for ( int x = 0 ; x < invoiceItemsArrayList.size() ; x++ )
            {
                if (invoiceItemsArrayList.get(x).getInvoice().getInvoiceNumber()==invoiceArrayList.get(i).getInvoiceNumber())
                {
                    System.out.println(invoiceItemsArrayList.get(x).getItem().getItemName()+" , "+
                            invoiceItemsArrayList.get(x).getItemPrice()+ " , "+
                            invoiceItemsArrayList.get(x).getItemQun()+"\n"
                    );
                }

            }
            System.out.println("}");*/
        }
        /*for ( int i = 0 ; i < invTblModel.getRowCount() ; i ++ )
        {
            System.out.println("Invoice Num "+ invTblModel.getValueAt(i,0) +"\n"
                    +"{\n" + "Invoice " +invTblModel.getValueAt(i,0) + " Date ( " + invTblModel.getValueAt(i,1) + " ) , "
                    +invTblModel.getValueAt(i,2)  +"\n";
            for (int x = 0 ; x < invItemTblModel.getRowCount() ; x++)
            {
                System.out.println(invItemTblModel.getValueAt(x,0)+" , "+
                        invItemTblModel.getValueAt(x,1)+ " , "+
                        invItemTblModel.getValueAt(x,2)+ " , "+"\n" +" } "
                );

            }
            System.out.println("}");
        }*/
    }
    /* */
    public ArrayList<InvoiceItems> readItemFileWithoutItem(int invoiceNumber)
    {
        ArrayList<InvoiceItems> invoiceItemsArrayList = new ArrayList<>();
        Path filePath = Paths.get(itemFileName);
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(filePath,StandardCharsets.US_ASCII);
            for (String line = bufferedReader.readLine() ; line != null ; line = bufferedReader.readLine())
            {
                String[] attributes = line.split(",");
                InvoiceItems invoiceItems = new InvoiceItems().createInvoiceItem(attributes);

                if (invoiceItems.getInvoice().getInvoiceNumber()!=invoiceNumber)
                {
                    invoiceItemsArrayList.add(invoiceItems);
                }


            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "File Error\n" + e.getMessage(), "Error", 0);
        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "Read Error\n" + e.getMessage(), "Error", 0);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame,"3 Number Format Error\n" + e.getMessage(),"Error",0);
        }finally {
            return invoiceItemsArrayList;
        }

    }


    private String convertToString(ArrayList<Invoice> invoiceArrayList)
    {
//        FileOutputStream fileOutputStream = new
        String data = new String();
        for (int i = 0 ; i < invoiceArrayList.size() ; i++ )
        {
            data = data + invoiceArrayList.get(i).getInvoiceNumber()+","
                    +invoiceArrayList.get(i).getInvoiceDate().toString()+","
                    +invoiceArrayList.get(i).getInvoiceCustomer().getCustomerName().toString() + "\n" ;

        }
        return data;
    }
    private String convertItemToString(ArrayList<InvoiceItems> invoiceArrayList)
    {
//        FileOutputStream fileOutputStream = new
        String data = new String();
        for (int i = 0 ; i < invoiceArrayList.size() ; i++ )
        {
            data = data + invoiceArrayList.get(i).getInvoice().getInvoiceNumber()+","
                    +invoiceArrayList.get(i).getItem().toString()+","
                    +invoiceArrayList.get(i).getItemPrice()+","
                    +invoiceArrayList.get(i).getItemQun()+ "\n" ;

        }
        return data;
    }
    private String convertTblToString(DefaultTableModel defaultTableModel)
    {
        String data = new String();
        for (int i = 0 ; i < defaultTableModel.getRowCount() ; i++ )
        {
            data = data + defaultTableModel.getValueAt(i,0)+","
                    + defaultTableModel.getValueAt(i,1)+","
                    +defaultTableModel.getValueAt(i,2) + "\n" ;

        }
        return data;
    }

    private String invoicesTxt ;
    private String itemsTxt;
    public void getData()
    {
        this.invoicesTxt = new String("");
        this.itemsTxt = new String("");
        for (Invoice invoice : this.getInvoiceList()) {
            this.invoicesTxt += invoice.getData();
//            this.invoicesTxt += "\n";
            for (InvoiceItems invoiceItems : invoice.getInvoiceItems()) {
                this.itemsTxt += invoiceItems.getData();
//                this.itemsTxt += "\n";
            }
        }
    }
    public void saveInvoiceFile(File invFile )
    {

        //////////////////////////////////////////
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(invFile);
            byte[] b = this.invoicesTxt.getBytes();
            fileOutputStream.write(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "File Error\n" + e.getMessage(), "Error", 0);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "Read Error\n" + e.getMessage(), "Error", 0);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "Number Format Error\n" + e.getMessage(), "Error", 0);
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this.mainFrame, "Read Error\n" + e.getMessage(), "Error", 0);
            }

        }


    }
    public void saveItemsFile(File invItemFile )
    {

        //////////////////////////////////////////
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(invItemFile);
            byte[] b = this.itemsTxt.getBytes();
            fileOutputStream.write(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "File Error\n" + e.getMessage(), "Error", 0);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "Read Error\n" + e.getMessage(), "Error", 0);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "Number Format Error\n" + e.getMessage(), "Error", 0);
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this.mainFrame, "Read Error\n" + e.getMessage(), "Error", 0);
            }

        }


    }
    public FileOutputStream writeHeaderFile(DefaultTableModel defaultTableModel  )
    {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(headerFileName);
            byte[] b = convertTblToString(defaultTableModel).getBytes();
            fileOutputStream.write(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "File Error\n" + e.getMessage(), "Error", 0);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "Read Error\n" + e.getMessage(), "Error", 0);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "Number Format Error\n" + e.getMessage(), "Error", 0);
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this.mainFrame, "Read Error\n" + e.getMessage(), "Error", 0);
            }
            return fileOutputStream;
        }
    }

    private String convertItemTblToString(DefaultTableModel defaultTableModel)
    {
        String data = new String();
        for (int i = 0 ; i < defaultTableModel.getRowCount() ; i++ )
        {
            data = data + defaultTableModel.getValueAt(i,0)+","
                    + defaultTableModel.getValueAt(i,1)+","
                    + defaultTableModel.getValueAt(i,2)+","
                    +defaultTableModel.getValueAt(i,3) + "\n" ;

        }
        return data;
    }
    public FileOutputStream writeItemFile(DefaultTableModel defaultTableModel , int invoiceNumber   )
    {
        String data =readItemFileWithoutItem(invoiceNumber)+convertItemTblToString(defaultTableModel);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(itemFileName);
            byte[] b = data.getBytes();
            fileOutputStream.write(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "File Error\n" + e.getMessage(), "Error", 0);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "Read Error\n" + e.getMessage(), "Error", 0);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame, "Number Format Error\n" + e.getMessage(), "Error", 0);
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this.mainFrame, "Read Error\n" + e.getMessage(), "Error", 0);
            }
            return fileOutputStream;
        }
    }
}
