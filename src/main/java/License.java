package llm;

import java.io.Serializable;
import java.time.*;
import java.time.LocalDate;

public class License implements Serializable {

  private static final long serialVersionUID = 1L;
  private String merchantWalletID;
  private LocalDate licenseGrantedDate;
  private LocalDate trialStartDate;
  private double satoshisSubmitted;
  private double dollarSubmitted;
  private double cost;
  private double amountPaid;

  private String licenseID;
  private String transactionID;
  private int requiredConfirmations;
  private int licenseExpiresInDays;
  private int trialExpiresInDays;
  private String unitsOfCost;
  private int transactionExpiresInHours;

  public License() {}

  public void setLicenseID(String s) {
    this.licenseID = s;
  }

  public String getLicenseID() {
    return this.licenseID;
  }

  public double getAmountPaid() {
    return this.amountPaid;
  }

  public double getCost() {
    return this.cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public LocalDate getLicenseGrantedDate() {
    return this.licenseGrantedDate;
  }

  public void setLicenseGrantedDate(LocalDate ld) {
    this.licenseGrantedDate = ld;
  }

  public LocalDate getTrialStartDate() {
    return this.trialStartDate;
  }

  public void setTrialStartDate(LocalDate ld) {
    this.trialStartDate = ld;
  }

  public String getTransactionID() {
    return this.transactionID;
  }

  public void setTransactionID(String s) {
    this.transactionID = s;
  }

  public int getLicenseExpiresInDays() {
    return licenseExpiresInDays;
  }

  public void setLicenseExpiresInDays(int eid) {
    this.licenseExpiresInDays = eid;
  }

  public int getTrialExpiresInDays() {
    return trialExpiresInDays;
  }

  public void setTrialExpiresInDays(int tid) {
    this.trialExpiresInDays = tid;
  }

  public int getTransactionExpiresInHours() {
    return this.transactionExpiresInHours;
  }

  public void setTransactionExpiresInHours(int i) {
    this.transactionExpiresInHours = i;
  }

  public String getUnitsOfCost() {
    return this.unitsOfCost;
  }

  public void setUnitsOfCost(String s) {
    this.unitsOfCost = s;
  }

  public int getRequiredConfirmations() {
    return this.requiredConfirmations;
  }

  public void setRequiredConfirmations(int i) {
    this.requiredConfirmations = i;
  }

  public String getMerchantWalletID() {
    return this.merchantWalletID;
  }

  public void setMerchantWalletID(String s) {
    this.merchantWalletID = s;
  }

  public void setSatoshisSubmitted(double d) {
    this.satoshisSubmitted = d;
  }

  public double getLTCSubmitted() {
    return this.satoshisSubmitted;
  }

  public void setDollarSubmitted(double d) {
    this.dollarSubmitted = d;
  }

  public double getDollarSubmitted() {
    return this.dollarSubmitted;
  }
}
