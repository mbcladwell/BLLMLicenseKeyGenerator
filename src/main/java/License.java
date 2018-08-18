package llm;

import java.io.Serializable;
import java.time.LocalDate;
//import java.time.Instant;
//import java.time.temporal.Temporal;
//import java.time.temporal.ChronoUnit;
import java.time.*;


public class License implements Serializable {

  private static final long serialVersionUID = 1L;
  private String merchantWalletID;
  private LocalDate licenseGrantedDate;
  private double ltcSubmitted;
  private double dollarSubmitted;
  private double cost;
  private double amountPaid;

  private String licenseID;
  private String transactionID;
  private int requiredConfirmations;
  private int expiresInDays; // license expiration
  private String unitsOfCost;
  private int expiresInHours; // transaction expiration
  private boolean licensed;

  public License() {
    
  }

  public boolean getLicensed() {
    try{
    if( (this.expiresInDays - Period.between(this.licenseGrantedDate, LocalDate.now()).getDays()) > 0){return true;}else{return false;}
    }catch(NullPointerException npe){return false;}
  }

  //  public void setLicensed(boolean b) {
  //  this.licensed = b;
  // }

  public void setLicenseID( String s){
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


  public String getTransactionID() {
    return this.transactionID;
  }

  public void setTransactionID(String s) {
    this.transactionID = s;
  }

  public int getExpiresInDays() {
    return expiresInDays;
  }

  public void setExpiresInDays(int eid) {
    this.expiresInDays = eid;
  }

  public int getExpiresInHours() {
    return this.expiresInHours;
  }

  public void setExpiresInHours(int i) {
    this.expiresInHours = i;
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

  public void setLTCSubmitted(double d) {
    this.ltcSubmitted = d;
  }

  public double getLTCSubmitted() {
    return this.ltcSubmitted;
  }

  public void setDollarSubmitted(double d) {
    this.dollarSubmitted = d;
  }

  public double getDollarSubmitted() {
    return this.dollarSubmitted;
  }
}
