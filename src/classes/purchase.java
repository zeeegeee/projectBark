/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: This is the Purchase class 
 */
package classes;

public class purchase {

    String purchID;
    String aName;
    String cID;
    double purchFee;
    String pDate;

    // Constructors
    public purchase(String purchID, String aName, String cID, double purchFee, String pDate) {
        this.purchID = purchID;
        this.aName = aName;
        this.cID = cID;
        this.purchFee = purchFee;
        this.pDate = pDate;
    }
    // create getter
    public String getPurchID() {
        return purchID;
    }
    // create setter
    public void setPurchID(String purchID) {
        this.purchID = purchID;
    }
    // create getter
    public String getAName() {
        return aName;
    }
    // create setter
    public void setAName(String aName) {
        this.aName = aName;
    }
    // create getter
    public String getCID() {
        return cID;
    }
    // create setter
    public void setCID(String cID) {
        this.cID = cID;
    }
    // create getter
    public double getPurchFee() {
        return purchFee;
    }
    // create setter
    public void setPurchFee(double purchFee) {
        this.purchFee = purchFee;
    }
    // create getter
    public String getPDate() {
        return pDate;
    }
    // create setter
    public void setPDate(String pDate) {
        this.pDate = pDate;
    }

}