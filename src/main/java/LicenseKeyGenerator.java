package bllmlkg;

import bllm.License;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;
import java.util.List;
import java.util.logging.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.*;

public class LicenseKeyGenerator extends javax.swing.JFrame
    implements java.awt.event.ActionListener, javax.swing.event.DocumentListener {
  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  private static List<String> walletids = new java.util.ArrayList<String>();
  private boolean walletIDsFileSelected = false;
  private boolean costEntered = false;

  private String unitOfCost;
  private Double cost;
  private int requiredConfirmations;
  private int licenseExpiresInDays;
  private int transactionExpiresInHours;
  public int trialExpiresInDays;
  private String unitOfPayment;
  private boolean licensed;
  private JButton generateButton;
  private JButton getWalletIDsButton;
  private JTextField costField;
  private JLabel fileNameLabel;
  private JLabel directoryNameLabel;
  private JLabel numberOfWalletIDsLabel;
  private JLabel cryptoLabel;
  private JLabel favoredCrytpocurrencyLabel;

  private JComboBox<String> costunits;
  private String[] cryptoOptionsWSelect = {"Select", "Bitcoin", "Litecoin"};
  private String[] cryptoOptions = {"Bitcoin", "Litecoin"};
  private String[] unitOfCostList = {"Dollars"};
  private DefaultComboBoxModel<String> litecoinUnitOfCostList =
      new DefaultComboBoxModel<String>(new String[] {"Dollars", "Litecoin"});
  private DefaultComboBoxModel<String> bitcoinUnitOfCostList =
      new DefaultComboBoxModel<String>(new String[] {"Dollars", "Bitcoin"});
  private JComboBox<String> cryptocurrencyList;
  private JComboBox<Integer> confirmationsList;
  private JComboBox<Integer> expiresDaysList;
  private JComboBox<Integer> expiresList;
  private JComboBox<Integer> trialExpiresDaysList;
  private License lic;
  private SecretKey key64;
  private Cipher cipher;
  private String keyString = new String("nszpx5U5Kt6d91JB3CW31n3SiNjSUzcZ");

  public LicenseKeyGenerator() {
    setTitle("BLLM License Key Generator  " + java.time.LocalDate.now());
    setResizable(true);
    LOGGER.setLevel(Level.INFO);

    // https://randomkeygen.com/
    // https://stackoverflow.com/questions/16950833/is-there-an-easy-way-to-encrypt-a-java-object

    JTabbedPane tabbedPane = new JTabbedPane();
    JComponent panel1 = new javax.swing.JPanel(new java.awt.GridBagLayout());
    tabbedPane.addTab("Generate", null, panel1, "Generate license keys");
    tabbedPane.setMnemonicAt(0, 49);

    java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();

    getWalletIDsButton = new JButton("Select the file containing wallet IDs...");

    getWalletIDsButton.setToolTipText("Select file");

    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    c.insets = new java.awt.Insets(5, 5, 5, 5);
    c.weightx = 1.0D;
    c.weighty = 1.0D;
    panel1.add(getWalletIDsButton, c);
    getWalletIDsButton.addActionListener(this);

    JLabel label = new JLabel("File name: ");
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(label, c);

    fileNameLabel = new JLabel("NA");
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
    panel1.add(directoryNameLabel, c);

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

    cryptoLabel = new JLabel();
    c.gridx = 2;
    c.gridy = 3;
    c.gridwidth = 1;
    c.gridheight = 5;
    c.weightx = 1.0D;
    c.weighty = 1.0D;
    panel1.add(cryptoLabel, c);

    favoredCrytpocurrencyLabel = new JLabel("Favored cryptocurrency: ");
    c.gridx = 0;
    c.gridy = 4;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(favoredCrytpocurrencyLabel, c);
    favoredCrytpocurrencyLabel.setVisible(false);

    cryptocurrencyList = new JComboBox<String>(cryptoOptionsWSelect);
    cryptocurrencyList.setSelectedIndex(0);
    c.gridx = 1;
    c.gridy = 4;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;

    panel1.add(cryptocurrencyList, c);
    cryptocurrencyList.setVisible(false);
    cryptocurrencyList.addActionListener(this);

    label = new JLabel("Required number of confirmations: ");
    c.gridx = 0;
    c.gridy = 5;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(label, c);

    Integer[] confirmationOptions = {0, 3, 6};

    confirmationsList = new JComboBox<Integer>(confirmationOptions);
    confirmationsList.setSelectedIndex(1);
    c.gridx = 1;
    c.gridy = 5;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;

    panel1.add(confirmationsList, c);

    label = new JLabel("Transaction expires in how many hours?: ");
    c.gridx = 0;
    c.gridy = 6;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(label, c);

    Integer[] expiresHours = {
      Integer.valueOf(1),
      Integer.valueOf(3),
      Integer.valueOf(12),
      Integer.valueOf(24),
      Integer.valueOf(48),
      Integer.valueOf(72),
      Integer.valueOf(168),
      Integer.valueOf(720),
      Integer.valueOf(8760)
    };

    expiresList = new JComboBox<Integer>(expiresHours);
    expiresList.setSelectedIndex(4);
    c.gridx = 1;
    c.gridy = 6;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;

    panel1.add(expiresList, c);

    label = new JLabel("Trial period expires in how many days?: ");
    c.gridx = 0;
    c.gridy = 7;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(label, c);

    Integer[] trialExpiresDays = {
      Integer.valueOf(0),
      Integer.valueOf(3),
      Integer.valueOf(7),
      Integer.valueOf(14),
      Integer.valueOf(30),
      Integer.valueOf(180)
    };

    trialExpiresDaysList = new JComboBox<Integer>(trialExpiresDays);
    trialExpiresDaysList.setSelectedIndex(3);
    c.gridx = 1;
    c.gridy = 7;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;

    panel1.add(trialExpiresDaysList, c);

    label = new JLabel("License expires in how many days?: ");
    c.gridx = 0;
    c.gridy = 8;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(label, c);

    Integer[] expiresDays = {
      Integer.valueOf(30),
      Integer.valueOf(365),
      Integer.valueOf(1000),
      Integer.valueOf(3650),
      Integer.valueOf(36500)
    };

    expiresDaysList = new JComboBox<Integer>(expiresDays);
    expiresDaysList.setSelectedIndex(4);
    c.gridx = 1;
    c.gridy = 8;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;

    panel1.add(expiresDaysList, c);

    label = new JLabel("License cost and currency: ");
    c.gridx = 0;
    c.gridy = 9;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    panel1.add(label, c);

    costField = new JTextField(5);
    c.gridx = 1;
    c.gridy = 9;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_START;
    panel1.add(costField, c);
    costField.getDocument().addDocumentListener(this);

    costunits = new JComboBox<String>(unitOfCostList);
    costunits.setSelectedIndex(0);
    costunits.setEnabled(false);
    c.gridx = 2;
    c.gridy = 9;
    c.gridwidth = 1;

    panel1.add(costunits, c);

    JButton cancelButton = new JButton("Cancel");
    c.gridx = 0;
    c.gridy = 10;
    c.gridwidth = 1;
    cancelButton.setMnemonic(KeyEvent.VK_C);

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
    c.gridy = 10;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;

    panel1.add(generateButton, c);
    generateButton.setEnabled(false);
    generateButton.addActionListener(this);

    JComponent panel2 = new InspectPanel(this);
    tabbedPane.addTab("Inspect", null, panel2, "View license parameters");
    tabbedPane.setMnemonicAt(1, 50);

    JComponent panel3 = new CryptoPanel(this);
    tabbedPane.addTab("Encrypt", null, panel3, "View license parameters");
    tabbedPane.setMnemonicAt(1, 50);

    getContentPane().add(tabbedPane, "Center");
    pack();
    setLocation(
        (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - getWidth() / 2,
        (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - getHeight() / 2);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == generateButton) {
      try {
        cost = Double.valueOf(Double.parseDouble(costField.getText()));
      } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog(
            this, "Invalid number entered for cost.", "NumberFormatException", 0);
      }

      unitOfCost = String.valueOf(costunits.getSelectedItem());
      requiredConfirmations = ((Integer) confirmationsList.getSelectedItem()).intValue();
      licenseExpiresInDays = ((Integer) expiresDaysList.getSelectedItem()).intValue();
      transactionExpiresInHours = ((Integer) expiresList.getSelectedItem()).intValue();
      trialExpiresInDays = ((Integer) trialExpiresDaysList.getSelectedItem()).intValue();

      generateLicenses();
      walletIDsFileSelected = true;
      if ((walletIDsFileSelected) && (costEntered)) {
        generateButton.setEnabled(true);
      }
    }

    if (e.getSource() == getWalletIDsButton) {
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

    if (e.getSource() == cryptocurrencyList) {
      // cryptocurrencyList.removeItem("Select");

      switch ((String) cryptocurrencyList.getSelectedItem()) {
        case "Bitcoin":
          unitOfPayment = "Bitcoin";
          costunits.setModel(bitcoinUnitOfCostList);
          displayCryptoIcon();

          break;
        case "Litecoin":
          unitOfPayment = "Litecoin";
          costunits.setModel(litecoinUnitOfCostList);

          displayCryptoIcon();
      }
    }
  }

  public void insertUpdate(DocumentEvent e) {
    try {
      if (Double.parseDouble(costField.getText()) >= 0) {
        costEntered = true;
        if ((walletIDsFileSelected) && (costEntered)) {
          generateButton.setEnabled(true);
        }
      }
    } catch (NumberFormatException nfe) {
      JOptionPane.showMessageDialog(
          this,
          "Invalid number entered for cost.",
          "NumberFormatException",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  public void removeUpdate(DocumentEvent e) {
    if ((walletIDsFileSelected) && (costEntered)) {
      generateButton.setEnabled(true);
    } else {
      generateButton.setEnabled(false);
    }
  }

  public void changedUpdate(DocumentEvent e) {
    // Plain text components don't fire these events.
  }

  public void readWalletIDfile() {
    try {
      java.io.FileReader fileReader = new java.io.FileReader(fileNameLabel.getText());
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = null;
      while ((line = bufferedReader.readLine()) != null) {
        walletids.add(line);
      }
      bufferedReader.close();
      numberOfWalletIDsLabel.setText(String.valueOf(walletids.size()));
      walletIDsFileSelected = true;
      String firstCharacter = Character.toString((walletids.get(0)).charAt(0));

      switch (firstCharacter) {
        case "1":
          unitOfPayment = "Bitcoin";
          costunits.addItem("Bitcoin");
          displayCryptoIcon();
          break;
        case "3":
          favoredCrytpocurrencyLabel.setVisible(true);
          cryptocurrencyList.setVisible(true);
          break;
        case "L":
          unitOfPayment = "Litecoin";
          costunits.addItem("Litecoin");
          displayCryptoIcon();
          break;
        case "M":
          favoredCrytpocurrencyLabel.setVisible(true);
          cryptocurrencyList.setVisible(true);
          break;
        default:
          JOptionPane.showMessageDialog(this, "Invalid wallet ids.", "Error", 0);
      }

    } catch (IOException ex) {
      LOGGER.info("IOException is caught");
    }
  }

  public void displayCryptoIcon() {
    costunits.setSelectedIndex(0);
    costunits.setEnabled(true);
    getWalletIDsButton.setEnabled(false);

    switch (unitOfPayment) {
      case "Bitcoin":
        cryptoLabel.setIcon(
            new javax.swing.ImageIcon(LicenseKeyGenerator.class.getResource("images/btc2.png")));
        break;
      case "Litecoin":
        cryptoLabel.setIcon(
            new javax.swing.ImageIcon(LicenseKeyGenerator.class.getResource("images/ltc2.png")));
    }

    if ((walletIDsFileSelected) && (costEntered)) {
      generateButton.setEnabled(true);
    }
    pack();
  }

  public void generateLicenses() {
    LOGGER.info("Secret key: " + keyString);

    try {
      for (int i = 1; i < walletids.size(); i++) {
        String uuid = java.util.UUID.randomUUID().toString();
        License lic = new License();
        lic.setLicenseID(uuid);
        lic.setCost(cost.doubleValue());
        lic.setUnitsOfCost(unitOfCost);
        lic.setUnitsOfPayment(unitOfPayment);
        lic.setRequiredConfirmations(requiredConfirmations);
        lic.setLicenseExpiresInDays(licenseExpiresInDays);
        lic.setTransactionExpiresInHours(transactionExpiresInHours);
        lic.setTrialExpiresInDays(trialExpiresInDays);
        lic.setMerchantWalletID(walletids.get(i));
        (walletids.get(i)).length();

        // String string = new String("nszpx5U5Kt6d91JB3CW31n3SiNjSUzcZ");
        key64 = new SecretKeySpec(getKeyString().getBytes(), "Blowfish");
        cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, key64);
        SealedObject sealedObject = new SealedObject(lic, cipher);
        String dirname =
            new String(
                (walletids.get(i))
                    .substring((walletids.get(i)).length() - 8, (walletids.get(i)).length()));

        new File(directoryNameLabel.getText() + "/license/" + dirname).mkdirs();

        String fileName =
            new String(directoryNameLabel.getText() + "/license/" + dirname + "/license.ser");

        CipherOutputStream cipherOutputStream =
            new CipherOutputStream(
                new BufferedOutputStream(new FileOutputStream(fileName)), cipher);
        ObjectOutputStream outputStream = new ObjectOutputStream(cipherOutputStream);
        outputStream.writeObject(sealedObject);
        outputStream.close();
      }
      JOptionPane.showMessageDialog(this, "Licenses have been serialized.", "Information", 1);
    } catch (IOException ex) {
      System.out.println("IOException is caught");
    } catch (IllegalBlockSizeException ex) {

    } catch (NoSuchAlgorithmException ex) {

    } catch (InvalidKeyException ex) {

    } catch (NoSuchPaddingException ex) {

    }
  }

  public void setKeyString(String s) {
    this.keyString = s;
  }

  public String getKeyString() {
    return this.keyString;
  }

  public static void main(String[] args) {
    new LicenseKeyGenerator();
  }
}
