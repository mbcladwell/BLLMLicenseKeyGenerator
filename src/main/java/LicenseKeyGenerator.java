package llmlkg;

import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.Toolkit;


import java.io.*;
//import java.io.IOException;
import java.net.*;
import java.time.LocalDate;
import java.util.*;
//import java.util.Date;
import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;


import javax.swing.JComponent;
//import java.awt.Color;
import javax.swing.SwingConstants;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import javax.swing.JTextField;

import java.time.temporal.Temporal;
import java.time.temporal.ChronoUnit;
import java.time.*;
import java.time.format.DateTimeFormatter;
/*
 * Terminology:
 * customer: person purchasing license
 * merchant: person selling license i.e. developer
 */

public class LicenseKeyGenerator extends JFrame implements ActionListener, DocumentListener {

private static final long serialVersionUID = 1L;

  //panel 1
  private static java.util.List<String> walletids = new java.util.ArrayList<String>();
  private boolean walletIDsFileSelected = false;
  private boolean costEntered = false;
  private String unitOfCost;
  private Double cost;
  private int requiredConfirmations;
  private int licenseExpiresInDays;
  private int transactionExpiresInHours;
  private int trialExpiresInDays;
  private boolean licensed;
  private JButton generateButton;
  private JButton getWalletIDsButton;
  private JTextField costField;
  private JLabel fileNameLabel;
  private JLabel directoryNameLabel;
  private JLabel numberOfWalletIDsLabel;
  private JComboBox<String> costunits;
  private JComboBox<Integer> confirmationsList;
  private JComboBox<Integer> expiresDaysList;  //license expiration
  private JComboBox<Integer> expiresList;     //transaction expiration
  private JComboBox<Integer> trialExpiresDaysList;
  // private JComboBox<Boolean> licenseFlagBox;

  //Panel 2  inspector

  private JButton getLicenseButton;
  private llm.License lic;
  private JLabel licenseExpiresInDaysLabel;
  private JLabel transactionExpiresInHoursLabel;
  private JLabel trialExpiresInDaysLabel;
  private JLabel licenseFileNameLabel;
   private JLabel walletIDLabel;
   private JLabel licenseIDLabel;
   private JLabel costLabel;
   private JLabel requiredConfirmationsLabel;
  private JLabel booleanValuesLabel;
  private JLabel trialStartDateLabel;
  
  private JLabel licenseGrantedDateLabel;
    
  //  private JLabel isLicensedLabel;
  
 
  public LicenseKeyGenerator(){
  this.setTitle("LLM License Key Generator  " + LocalDate.now() );
    this.setResizable(true);

    // Image img =
    //new ImageIcon(LicenseKeyGenerator.class.getResource("../../../resources/main/ltc.png")).getImage();
    // this.setIconImage(img);
  
    JTabbedPane tabbedPane = new JTabbedPane();
    JComponent panel1 = new JPanel(new GridBagLayout());
    tabbedPane.addTab("Generate", null, panel1,
                  "Generate license keys");
    tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

    GridBagConstraints c = new GridBagConstraints();

    getWalletIDsButton = new JButton("Select the file containing wallet IDs...");
    // clipboardButton.setMnemonic(KeyEvent.VK_Y);
    getWalletIDsButton.setToolTipText("Select file");
	
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    c.insets = new Insets(5,5,5,5);  
    c.weightx = 1;
    c.weighty = 1;
    panel1.add(getWalletIDsButton, c);

    getWalletIDsButton.addActionListener(this);


    JLabel label = new JLabel("File name: ");
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(label, c);

    fileNameLabel  = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 1;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;
    panel1.add(fileNameLabel, c);

    
     label = new JLabel("Working directory: ");
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(label, c);

    directoryNameLabel = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 2;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;
    panel1.add( directoryNameLabel, c);

     label = new JLabel("Number of wallet IDs: ");
    c.gridx = 0;
    c.gridy = 3;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(label, c);

    numberOfWalletIDsLabel = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 3;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_START;
    panel1.add(numberOfWalletIDsLabel, c);


    
    label = new JLabel("Required number of confirmations: ");
    c.gridx = 0;
    c.gridy = 4;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(label, c);
    
    Integer[] confirmationOptions = { 0, 3, 6 };

    confirmationsList = new JComboBox<>(confirmationOptions);
    confirmationsList.setSelectedIndex(1);
   c.gridx = 1;
    c.gridy = 4;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;
   
    panel1.add(confirmationsList, c);
 
    label = new JLabel("Transaction expires in how many hours?: ");
    c.gridx = 0;
    c.gridy = 5;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(label, c);

    Integer[] expiresHours = { 1, 3, 12, 24, 48, 72, 168, 720, 8760 };

    expiresList = new JComboBox<>(expiresHours);
    expiresList.setSelectedIndex(4);
   c.gridx = 1;
    c.gridy = 5;
    c.gridwidth = 2;
       c.anchor = GridBagConstraints.LINE_START;

    panel1.add(expiresList, c);

        label = new JLabel("Trial period expires in how many days?: ");
    c.gridx = 0;
    c.gridy = 6;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(label, c);

    Integer[] trialExpiresDays = { 1, 7, 30, 180 };

    trialExpiresDaysList = new JComboBox<>(trialExpiresDays);
    trialExpiresDaysList.setSelectedIndex(3);
   c.gridx = 1;
    c.gridy = 6;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;

    panel1.add(trialExpiresDaysList, c);

    
    label = new JLabel("License expires in how many days?: ");
    c.gridx = 0;
    c.gridy = 7;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(label, c);

    Integer[] expiresDays = { 7, 30, 365, 3650, 36500  };

    expiresDaysList = new JComboBox<>(expiresDays);
    expiresDaysList.setSelectedIndex(4);
   c.gridx = 1;
    c.gridy = 7;
    c.gridwidth = 2;
      c.anchor = GridBagConstraints.LINE_START;

    
    panel1.add(expiresDaysList, c);

    
    label = new JLabel("License cost and currency: ");
    c.gridx = 0;
    c.gridy = 8;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(label, c);

    costField = new JTextField(5);
    c.gridx = 1;
    c.gridy = 8;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_START;
    panel1.add(costField, c);
    costField.getDocument().addDocumentListener(this);
    
    
    String[] unitOfCost = { "Dollars", "Litecoin"  };

    costunits = new JComboBox<>(unitOfCost);
    costunits.setSelectedIndex(0);
   c.gridx = 2;
    c.gridy = 8;
    c.gridwidth = 1;
    
    panel1.add(costunits, c);

    /*
   label = new JLabel("License flag: ");
    c.gridx = 0;
    c.gridy = 8;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(label, c);


  
    Vector<Boolean> booleanVector = new Vector<Boolean>();
    booleanVector.add(Boolean.TRUE);
    booleanVector.add(Boolean.FALSE);
    
    licenseFlagBox = new JComboBox<>(booleanVector);
    licenseFlagBox.setSelectedIndex(1);
   c.gridx = 1;
    c.gridy = 8;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_START;
    
    panel1.add(licenseFlagBox, c);
    */
    
    JButton cancelButton = new JButton("Cancel");
    c.gridx = 0;
    c.gridy = 9;
    c.gridwidth = 1;
      c.anchor = GridBagConstraints.LINE_END;
 
    panel1.add(cancelButton, c);
     cancelButton.addActionListener(
        (new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            dispose();
          }
        }));
    
    generateButton = new JButton("Generate licenses");
c.gridx = 1;
    c.gridy = 9;
    c.gridwidth = 2;
      c.anchor = GridBagConstraints.LINE_START;
 
    panel1.add(generateButton, c);
    generateButton.setEnabled(false);
    generateButton.addActionListener(this);
         
    
    


    //Inspect panel 2
    JComponent panel2 = new JPanel(new GridBagLayout());
    tabbedPane.addTab("Inspect", null, panel2,
		      "View license parameters");
    tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

       getLicenseButton = new JButton("Select license file...");
    // clipboardButton.setMnemonic(KeyEvent.VK_Y);
    getLicenseButton.setToolTipText("Select file");
	
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    c.insets = new Insets(5,5,5,5);  
    c.weightx = 1;
    c.weighty = 1;
    panel2.add(getLicenseButton, c);
    getLicenseButton.addActionListener(this);

    label = new JLabel("Filename: ");
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel2.add(label, c);

    licenseFileNameLabel = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 1;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;
    panel2.add(licenseFileNameLabel, c);
      
    label = new JLabel("Wallet ID: ");
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel2.add(label, c);

    walletIDLabel = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 2;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;
    panel2.add(walletIDLabel, c);
  
    label = new JLabel("License ID:: ");
    c.gridx = 0;
    c.gridy = 3;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel2.add(label, c);

    licenseIDLabel = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 3;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;
    panel2.add(licenseIDLabel, c);

 
        label = new JLabel("Cost: ");
    c.gridx = 0;
    c.gridy = 4;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel2.add(label, c);

        costLabel = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 4;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_START;
    panel2.add(costLabel, c);

     
    label = new JLabel("License expiration (days): ");
    c.gridx = 0;
    c.gridy = 5;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel2.add(label, c);

    licenseExpiresInDaysLabel = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 5;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_START;
    panel2.add(licenseExpiresInDaysLabel, c);
    /*
    label = new JLabel("Trial period start date: ");
    c.gridx = 0;
    c.gridy = 6;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel2.add(label, c);

    trialStartDateLabel = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 6;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_START;
    panel2.add(trialStartDateLabel, c);
    */
    label = new JLabel("Trial period expiration (days): ");
    c.gridx = 0;
    c.gridy = 7;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel2.add(label, c);

    trialExpiresInDaysLabel = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 7;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_START;
    panel2.add(trialExpiresInDaysLabel, c);

    
    label = new JLabel("Transaction expiration (hours): ");
    c.gridx = 0;
    c.gridy = 8;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel2.add(label, c);

    transactionExpiresInHoursLabel = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 8;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_START;
    panel2.add(transactionExpiresInHoursLabel, c);

    
    label = new JLabel("Required confirmations: ");
    c.gridx = 0;
    c.gridy = 9;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel2.add(label, c);

    requiredConfirmationsLabel = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 9;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_START;
    panel2.add(requiredConfirmationsLabel, c);

    
    label = new JLabel("Trial start date:");
    c.gridx = 0;
    c.gridy = 10;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel2.add(label, c);

    trialStartDateLabel = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 10;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_START;
    panel2.add(trialStartDateLabel, c);

        label = new JLabel("License granted date:");
    c.gridx = 0;
    c.gridy = 11;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel2.add(label, c);

    licenseGrantedDateLabel = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 11;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_START;
    panel2.add(licenseGrantedDateLabel, c);

      JButton exitButton = new JButton("Exit");
    c.gridx = 0;
    c.gridy = 12;
    c.gridwidth = 1;
      c.anchor = GridBagConstraints.LINE_END;
 
    panel2.add(exitButton, c);
     exitButton.addActionListener(
        (new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            dispose();
          }
        }));
    
    //set up panels
  this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
    this.pack();
    this.setLocation(
        (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - getWidth() / 2,
        (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - getHeight() / 2);
    this.setVisible(true);


  }

  //ActionListeners
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == generateButton){
      try{
	this.cost = Double.parseDouble(costField.getText());
      }catch(NumberFormatException nfe){
	JOptionPane.showMessageDialog(this,
				      "Invalid number entered for cost.",
				      "NumberFormatException",
				      JOptionPane.ERROR_MESSAGE);
      }
      this.unitOfCost = String.valueOf(costunits.getSelectedItem());
      this.requiredConfirmations = (Integer)confirmationsList.getSelectedItem();
      this.licenseExpiresInDays = (Integer)expiresDaysList.getSelectedItem();
      this.transactionExpiresInHours = (Integer)expiresList.getSelectedItem();
      this.trialExpiresInDays = (Integer)trialExpiresDaysList.getSelectedItem();
      //      this.licensed = (Boolean)licenseFlagBox.getSelectedItem();
      this.generateLicenses();
      this.walletIDsFileSelected = true;
	    if( walletIDsFileSelected && costEntered){
	      generateButton.setEnabled(true);
	    }
      
    }
  

    if(e.getSource() == getWalletIDsButton){
    	  final JFileChooser fc = new JFileChooser();
	  int returnVal = fc.showOpenDialog(LicenseKeyGenerator.this);
	  if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

	    this.fileNameLabel.setText(file.getPath());
	    this.directoryNameLabel.setText(file.getParent());
	    this.readWalletIDfile();
        } else {
           
        }
    }

      if(e.getSource() == getLicenseButton){
	final JFileChooser fc = new JFileChooser();
	  int returnVal = fc.showOpenDialog(LicenseKeyGenerator.this);
	  if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();	   
	    this.readLicenseFile(file.getPath());
        } else {
           
        }
	
      }
 
  }

  public void insertUpdate(DocumentEvent e) {
           try{
	 if (Double.parseDouble(costField.getText()) >= 0){
	   this.costEntered = true;
	   if( this.walletIDsFileSelected && this.costEntered){
	     this.generateButton.setEnabled(true);
	   }
	 }
       }catch(NumberFormatException nfe){
	 JOptionPane.showMessageDialog(this,
				       "Invalid number entered for cost.",
				       "NumberFormatException",
				       JOptionPane.ERROR_MESSAGE);
       }
        }
        public void removeUpdate(DocumentEvent e) {
	   if( this.walletIDsFileSelected && this.costEntered){
	     this.generateButton.setEnabled(true);
	   } else {this.generateButton.setEnabled(false);}
          
        }
        public void changedUpdate(DocumentEvent e) {
            //Plain text components don't fire these events.
        }

  

  public void readWalletIDfile(){

    try {
      FileReader fileReader = new FileReader(this.fileNameLabel.getText());
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = null;
      while ((line = bufferedReader.readLine()) != null) {
        walletids.add(line);
      }
      bufferedReader.close();
      numberOfWalletIDsLabel.setText(String.valueOf(walletids.size()));
      this.walletIDsFileSelected = true;
      if( this.walletIDsFileSelected && this.costEntered){
	this.generateButton.setEnabled(true);
      }
      this.pack();
 } catch (IOException ex) {
      System.out.println("IOException is caught");
    }
    
  }

  public void generateLicenses(){

    try {

      for (int i = 1; i < walletids.size(); i++) {

        String uuid = UUID.randomUUID().toString();
        llm.License lic = new llm.License();
	lic.setLicenseID(uuid);
        lic.setCost(this.cost);
        lic.setUnitsOfCost(this.unitOfCost);
        lic.setRequiredConfirmations(this.requiredConfirmations);
        lic.setLicenseExpiresInDays(this.licenseExpiresInDays);
        lic.setTransactionExpiresInHours(this.transactionExpiresInHours);
	lic.setTrialExpiresInDays(this.trialExpiresInDays);
        lic.setMerchantWalletID(walletids.get(i));
        walletids.get(i).length();

        String dirname =
            new String(
                walletids
                    .get(i)
                    .substring(walletids.get(i).length() - 8, walletids.get(i).length()));

        new File( this.directoryNameLabel.getText() + "/license/" + dirname).mkdirs();
        String filename = new String( this.directoryNameLabel.getText() + "/license/" + dirname + "/license.ser");

        // Saving of object in a file
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);

        // Method for serialization of object
        out.writeObject(lic);

        out.close();
        file.close();
      }
      	JOptionPane.showMessageDialog(this,
				      "Licenses have been serialized.",
				      "Information",
				      JOptionPane.INFORMATION_MESSAGE);

    } catch (IOException ex) {
      System.out.println("IOException is caught");
    }
  }

  public void readLicenseFile(String filename){
     

    try {
      // Saving of object in a file
      FileInputStream file = new FileInputStream(filename);
      ObjectInputStream in = new ObjectInputStream(file);

      // Method for serialization of object
       lic = (llm.License) in.readObject();

      in.close();
      file.close();
      this.licenseFileNameLabel.setText(filename);
      this.licenseExpiresInDaysLabel.setText(String.valueOf(lic.getLicenseExpiresInDays()));
      this.licenseIDLabel.setText(String.valueOf(lic.getLicenseID()));
      this.costLabel.setText(String.valueOf(lic.getCost()) + "  " + String.valueOf(lic.getUnitsOfCost()));
      this.transactionExpiresInHoursLabel.setText(String.valueOf(lic.getTransactionExpiresInHours()));
      this.trialExpiresInDaysLabel.setText(String.valueOf(lic.getTrialExpiresInDays()));
      this.requiredConfirmationsLabel.setText(String.valueOf(lic.getRequiredConfirmations()));
      this.walletIDLabel.setText(String.valueOf(lic.getMerchantWalletID()));
      this.trialStartDateLabel.setText(String.valueOf(lic.getTrialStartDate()));
      this.licenseGrantedDateLabel.setText(String.valueOf(lic.getLicenseGrantedDate()));
      
  
      
      this.pack();

    } catch (IOException ex) {
      System.out.println("IOException is caught");
    } catch (ClassNotFoundException ex) {
      System.out.println("ClassNotFoundException is caught");
    }
      
    }        
  
  
  public static void  main( String[ ] args){
    new LicenseKeyGenerator();
    
  }
  
}
