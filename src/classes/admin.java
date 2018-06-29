/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: This is the Admin class 
 */
package classes;

public class admin {

    public int adminID;
    public String position;
    public String adminFName;
    public String adminLName;
    public String adminPhnNumber;
    public String adminEmail;

    public int empCount = 1;

    // Constructors
    public admin(String position, String eFName, String eLName, String phnNumber, String eEmail) {
        this.position = position;
        this.adminFName = eFName;
        this.adminLName = eLName;
        this.adminPhnNumber = phnNumber;
        this.adminEmail = eEmail;
        this.adminID = empCount;

        empCount++;
    }
    // create getter
    public int getAdminID() {
        return this.adminID;
    }
    // create getter
    public String getPosition() {
        return this.position;
    }
    // create getter
    public String getAdminFName() {
        return this.adminFName;
    }
    // create getter
    public String getAdminLName() {
        return this.adminLName;
    }
    // create getter
    public String getAdminPhnNumber() {
        return this.adminPhnNumber;
    }
    // create getter
    public String getAdminEmail() {
        return this.adminEmail;
    }
}