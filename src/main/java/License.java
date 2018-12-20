package bllm;

import java.io.Serializable;
import java.time.*;
import java.time.LocalDate;

/**
 * Merchant chooses the desired denomination of payment. bllm tracks favored payment method but also
 * converts to dollars and satoshis at the time of payment.
 */
public class License implements Serializable {

  private static final long serialVersionUID = 1L;

  private String merchantWalletID;
  private LocalDate licenseGrantedDate;
  private LocalDate trialStartDate;
  private double satoshisSubmitted;
  private double dollarSubmitted;
  private double cost;
  private String unitsOfCost;
  private double actualPayment;
  private String unitsOfRequestedPayment;

  private String licenseID;
  private String transactionID;
  private int requiredConfirmations;
  private int licenseExpiresInDays;
  private int trialExpiresInDays;
  private int transactionExpiresInHours;

  public License() {}

  public void setLicenseID(String s) {
    this.licenseID = s;
  }

  public String getLicenseID() {
    return this.licenseID;
  }

  /**
   * A double. Could be in any units.
   *
   * @see actualPayment what was actually tendered
   * @see requestedPayment used as variable in other classes
   * @see _unitsOfCost to determine the units involved
   * @return cost number of units being requested
   */
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

  /** @return unitsOfCost */
  public String getUnitsOfCost() {
    return this.unitsOfCost;
  }

  /** @param _unitsOfCost what the merchant is requesting, possibly in dollars */
  public void setUnitsOfCost(String _unitsOfCost) {
    this.unitsOfCost = _unitsOfCost;
  }

  public String getUnitsOfRequestedPayment() {
    return this.unitsOfRequestedPayment;
  }

  /** @param _unitsOfRequestedPayment Bitcoin or Litecoin */
  public void setUnitsOfRequestedPayment(String _unitsOfRequestedPayment) {
    this.unitsOfRequestedPayment = _unitsOfRequestedPayment;
  }

  public double getActualPayment() {
    return this.actualPayment;
  }

  public void setActualPayment(double d) {
    this.actualPayment = d;
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

  /** @param _satoshisSubmitted what was paid whether Bitcoin or Litecoin */
  public void setSatoshisSubmitted(double _satoshisSubmitted) {
    this.satoshisSubmitted = _satoshisSubmitted;
  }

  public double getSatoshisSubmitted() {
    return this.satoshisSubmitted;
  }

  public double getLTCSubmitted() {
    return this.satoshisSubmitted;
  }

  /** @param _dollarSubmitted obtained by conversion at the time of payment */
  public void setDollarSubmitted(double _dollarSubmitted) {
    this.dollarSubmitted = _dollarSubmitted;
  }

  public double getDollarSubmitted() {
    return this.dollarSubmitted;
  }
}
