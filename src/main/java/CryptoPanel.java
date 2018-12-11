package bllmlkg;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class CryptoPanel extends javax.swing.JPanel
    // implements java.awt.event.ActionListener, javax.swing.event.DocumentListener {
    implements java.awt.event.ActionListener {

  private static final long serialVersionUID = 1L;
  private JLabel text1 = new JLabel("License.ser files are encrypted.");
  private JLabel text2 = new JLabel("Enter a private key below or use the one provided.");
  private JLabel text3 = new JLabel("Private key: ");
  private JTextField keyField;
  private JButton updateButton;
  private JButton cancelButton;
  private JLabel text4 = new JLabel("This new key must be utilized in the DialogLicenseManager");
  private JLabel text5 = new JLabel("constructor!");
  private LicenseKeyGenerator parent;

  private JLabel checkmarkLabel;

  public CryptoPanel(LicenseKeyGenerator _parent) {
    this.setLayout(new java.awt.GridBagLayout());
    this.parent = _parent;
    java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();

    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 5;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new java.awt.Insets(5, 5, 5, 5);
    //    c.weightx = 1.0D;
    // c.weighty = 1.0D;
    add(text1, c);

    c.gridy = 1;
    add(text2, c);

    c.gridy = 2;
    c.gridwidth = 1;
    add(text3, c);

    keyField = new JTextField();
    keyField.setText("nszpx5U5Kt6d91JB3CW31n3SiNjSUzcZ");
    c.gridx = 1;
    c.gridy = 2;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridwidth = 4;
    add(keyField, c);
    keyField.getDocument().addDocumentListener(this);

    updateButton = new JButton("Update key");
    c.gridx = 0;
    c.gridy = 3;
    c.fill = GridBagConstraints.NONE;
    c.gridwidth = 2;
    updateButton.setMnemonic(KeyEvent.VK_U);
    add(updateButton, c);
    updateButton.addActionListener(this);

    checkmarkLabel = new JLabel();
    c.gridx = 2;
    c.gridy = 3;
    c.gridwidth = 1;
    add(checkmarkLabel, c);

    c.gridx = 0;
    c.gridy = 4;
    c.gridwidth = 5;
    c.anchor = GridBagConstraints.LINE_START;
    add(text4, c);
    text4.setForeground(Color.RED);
    text4.setVisible(false);

    c.gridx = 0;
    c.gridy = 5;
    c.gridwidth = 5;
    c.anchor = GridBagConstraints.LINE_START;
    add(text5, c);
    text5.setForeground(Color.RED);
    text5.setVisible(false);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == updateButton) {
      try {
        checkmarkLabel.setIcon(
            new javax.swing.ImageIcon(CryptoPanel.class.getResource("images/green-check.png")));
        text4.setVisible(true);
        text5.setVisible(true);

      } catch (NumberFormatException nfe) {

      }
    }
  }
  /*
    public void changedUpdate(DocumentEvent e) {
      // Plain text components don't fire these events.
    }

    public void removeUpdate(DocumentEvent e) {
      // Plain text components don't fire these events.
    }

    public void insertUpdate(DocumentEvent e) {}
  }
  */
}
