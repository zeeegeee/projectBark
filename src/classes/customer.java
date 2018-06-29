/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: This is the Customer class 
 */
package classes;

public class customer {

    public String cID;
    public String cFName;
    public String cLName;
    public String cPhoneNumber;
    public String cEmail;

    // Constructors
    public customer(String cID, String fName, String lName, String phoneNumber, String email) {
        this.cFName = fName;
        this.cLName = lName;
        this.cPhoneNumber = phoneNumber;
        this.cEmail = email;
        this.cID = cID;
    }
    // create getter
    public String getCID() {
        return this.cID;
    }
    // create getter
    public String getCFName() {
        return this.cFName;
    }
    // create getter
    public String getCLName() {
        return this.cLName;
    }
    // create getter
    public String getCPhoneNumber() {
        return this.cPhoneNumber;
    }
    // create getter
    public String getCEmail() {
        return this.cEmail;
    }
    // create setter
    public void setCFName(String cFName) {
        this.cFName = cFName;
    }
    // create setter
    public void setCLName(String cLName) {
        this.cLName = cLName;
    }
    // create setter
    public void setCPhoneNumber(String cPhoneNumber) {
        this.cPhoneNumber = cPhoneNumber;
    }
    // create setter
    public void setCEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    public String toString() {
        return "\nCustomerID #:" + this.cID;
    }

}