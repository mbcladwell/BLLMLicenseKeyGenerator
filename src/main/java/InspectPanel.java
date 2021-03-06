package bllmlkg;

import bllm.License;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.security.*;
import java.util.logging.*;
import javax.crypto.*;
import javax.swing.*;
import javax.swing.event.*;

public class InspectPanel extends javax.swing.JPanel {

  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  private LicenseKeyGenerator parent;
  private LicenseReaderWriter licReaderWriter;
  private JButton getLicenseButton;
  private JLabel label;
  private JLabel licenseFileNameLabel;
  private JLabel walletIDLabel;
  private JLabel licenseExpiresInDaysLabel;
  private JLabel transactionExpiresInHoursLabel;
  private JLabel trialExpiresInDaysLabel;
  private JLabel licenseIDLabel;
  private JLabel costLabel;
  private JLabel requiredConfirmationsLabel;
  private JLabel booleanValuesLabel;
  private JLabel trialStartDateLabel;
  private JLabel licenseGrantedDateLabel;
  private JLabel keyStringLabel;
  private JLabel unitsOfRequestedPaymentLabel;
  private JLabel paymentAmountLabel;

  private License lic;
  private SecretKey key64;
  private Cipher cipher;

  public InspectPanel(LicenseKeyGenerator _parent) {
    this.setLayout(new java.awt.GridBagLayout());
    this.parent = _parent;
    this.licReaderWriter = new LicenseReaderWriter(parent, parent.getKeyString());
    LOGGER.setLevel(Level.INFO);
    java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
    getLicenseButton = new JButton("Select license file...");
    getLicenseButton.setToolTipText("Select file");

    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_END;
    c.insets = new java.awt.Insets(5, 5, 5, 5);
    c.weightx = 1.0D;
    c.weighty = 1.0D;
    add(getLicenseButton, c);
    // getLicenseButton.addActionListener(this);
    getLicenseButton.addActionListener(
        (new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            final JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(InspectPanel.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
              File file = fc.getSelectedFile();
              // InspectPanel.this.readLicenseFile(file.getPath());
              LOGGER.info("parent.getKeyString() in readLicenseFile: " + parent.getKeyString());

              lic = licReaderWriter.readLicense(file.getPath());

              licenseFileNameLabel.setText(file.getPath());
              licenseExpiresInDaysLabel.setText(String.valueOf(lic.getLicenseExpiresInDays()));
              licenseIDLabel.setText(String.valueOf(lic.getLicenseID()));
              costLabel.setText(
                  String.valueOf(lic.getCost()) + "  " + String.valueOf(lic.getUnitsOfCost()));
              transactionExpiresInHoursLabel.setText(
                  String.valueOf(lic.getTransactionExpiresInHours()));
              trialExpiresInDaysLabel.setText(String.valueOf(lic.getTrialExpiresInDays()));
              requiredConfirmationsLabel.setText(String.valueOf(lic.getRequiredConfirmations()));
              walletIDLabel.setText(String.valueOf(lic.getMerchantWalletID()));
              trialStartDateLabel.setText(String.valueOf(lic.getTrialStartDate()));
              licenseGrantedDateLabel.setText(String.valueOf(lic.getLicenseGrantedDate()));
              unitsOfRequestedPaymentLabel.setText(lic.getUnitsOfRequestedPayment());
              paymentAmountLabel.setText(String.valueOf(lic.getActualPayment()));
              keyStringLabel.setText(parent.getKeyString());
            }
          }
        }));

    label = new JLabel("Filename: ");
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_END;
    add(label, c);

    licenseFileNameLabel = new JLabel("NA");
    c.gridx = 2;
    c.gridy = 1;
    c.gridwidth = 3;
    c.anchor = GridBagConstraints.LINE_START;
    add(licenseFileNameLabel, c);

    label = new JLabel("Wallet ID: ");
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_END;
    add(label, c);

    walletIDLabel = new JLabel("NA");
    c.gridx = 2;
    c.gridy = 2;
    c.gridwidth = 3;
    c.anchor = GridBagConstraints.LINE_START;
    add(walletIDLabel, c);

    label = new JLabel("License ID:: ");
    c.gridx = 0;
    c.gridy = 3;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_END;
    add(label, c);

    licenseIDLabel = new JLabel("NA");
    c.gridx = 2;
    c.gridy = 3;
    c.gridwidth = 3;
    c.anchor = GridBagConstraints.LINE_START;
    add(licenseIDLabel, c);

    label = new JLabel("Cost: ");
    c.gridx = 0;
    c.gridy = 4;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_END;
    add(label, c);

    costLabel = new JLabel("NA");
    c.gridx = 2;
    c.gridy = 4;
    c.gridwidth = 3;
    c.anchor = GridBagConstraints.LINE_START;
    add(costLabel, c);

    label = new JLabel("License expiration (days): ");
    c.gridx = 0;
    c.gridy = 5;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_END;
    add(label, c);

    licenseExpiresInDaysLabel = new JLabel("NA");
    c.gridx = 2;
    c.gridy = 5;
    c.gridwidth = 3;
    c.anchor = GridBagConstraints.LINE_START;
    add(licenseExpiresInDaysLabel, c);

    label = new JLabel("Trial period expiration (days): ");
    c.gridx = 0;
    c.gridy = 7;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_END;
    add(label, c);

    trialExpiresInDaysLabel = new JLabel("NA");
    c.gridx = 2;
    c.gridy = 7;
    c.gridwidth = 3;
    c.anchor = GridBagConstraints.LINE_START;
    add(trialExpiresInDaysLabel, c);

    label = new JLabel("Transaction expiration (hours): ");
    c.gridx = 0;
    c.gridy = 8;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_END;
    add(label, c);

    transactionExpiresInHoursLabel = new JLabel("NA");
    c.gridx = 2;
    c.gridy = 8;
    c.gridwidth = 3;
    c.anchor = GridBagConstraints.LINE_START;
    add(transactionExpiresInHoursLabel, c);

    label = new JLabel("Required confirmations: ");
    c.gridx = 0;
    c.gridy = 9;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_END;
    add(label, c);

    requiredConfirmationsLabel = new JLabel("NA");
    c.gridx = 2;
    c.gridy = 9;
    c.gridwidth = 3;
    c.anchor = GridBagConstraints.LINE_START;
    add(requiredConfirmationsLabel, c);

    label = new JLabel("Trial start date:");
    c.gridx = 0;
    c.gridy = 10;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_END;
    add(label, c);

    trialStartDateLabel = new JLabel("NA");
    c.gridx = 2;
    c.gridy = 10;
    c.gridwidth = 3;
    c.anchor = GridBagConstraints.LINE_START;
    add(trialStartDateLabel, c);

    label = new JLabel("License granted date:");
    c.gridx = 0;
    c.gridy = 11;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_END;
    add(label, c);

    licenseGrantedDateLabel = new JLabel("NA");
    c.gridx = 2;
    c.gridy = 11;
    c.gridwidth = 3;
    c.anchor = GridBagConstraints.LINE_START;
    add(licenseGrantedDateLabel, c);

    label = new JLabel("Units of payment: ");
    c.gridx = 0;
    c.gridy = 12;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    add(label, c);

    unitsOfRequestedPaymentLabel = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 12;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;
    add(unitsOfRequestedPaymentLabel, c);

    label = new JLabel("Amount of payment: ");
    c.gridx = 0;
    c.gridy = 13;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.LINE_END;
    add(label, c);

    paymentAmountLabel = new JLabel("NA");
    c.gridx = 1;
    c.gridy = 13;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_START;
    add(paymentAmountLabel, c);

    c.gridx = 0;
    c.gridy = 14;
    c.gridwidth = 5;
    c.fill = GridBagConstraints.HORIZONTAL;
    add(new JSeparator(JSeparator.HORIZONTAL), c);

    label = new JLabel("From LicenseKeyGenerator--Key string:");
    c.gridx = 0;
    c.gridy = 15;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_END;
    c.fill = GridBagConstraints.NONE;
    add(label, c);

    keyStringLabel = new JLabel("NA");
    c.gridx = 2;
    c.gridy = 15;
    c.gridwidth = 3;
    c.anchor = GridBagConstraints.LINE_START;
    add(keyStringLabel, c);

    c.gridx = 0;
    c.gridy = 16;
    c.gridwidth = 5;
    c.fill = GridBagConstraints.HORIZONTAL;
    add(new JSeparator(JSeparator.HORIZONTAL), c);

    JButton exitButton = new JButton("Exit");
    c.gridx = 0;
    c.gridy = 17;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.LINE_END;

    add(exitButton, c);
    exitButton.addActionListener(
        (new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            parent.dispose();
          }
        }));
  }
}
