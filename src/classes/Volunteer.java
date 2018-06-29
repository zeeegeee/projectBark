/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: This is the Volunteer class 
*/
package classes;

import java.util.Date;

public class Volunteer {
    public String vUserName;
    public String vPassword;
    public String vFname;
    public String vLname;
    public String vEmail;
    public String vPhone;
    public String vStreet;
    public String vCity;
    public String vState;
    public String vZip;
    public Date vDOB;
    public String vPI;
    public String vExp;
    public String monday;
    public String tuesday;
    public String wednesday;
    public String thursday;
    public String friday;
    public String saturday;
    public String sunday;
    public String vSpecialization;
    public String vStatus;
    public double vTotalHours;
    public double totalMileage;

    // Constructors
    public Volunteer(String vUserName, String vPassword, String vFname, String vLname, String vEmail, String vPhone, String vStreet, String vCity, String vState, String vZip, Date vDOB, String vPI, String vExp, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday, String vSpecialization, String vStatus) {
        this.vUserName = vUserName;
        this.vPassword = vPassword;
        this.vFname = vFname;
        this.vLname = vLname;
        this.vEmail = vEmail;
        this.vPhone = vPhone;
        this.vStreet = vStreet;
        this.vCity = vCity;
        this.vState = vState;
        this.vZip = vZip;
        this.vDOB = vDOB;
        this.vPI = vPI;
        this.vExp = vExp;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.vSpecialization = vSpecialization;
        this.vStatus = vStatus;
    }
    
    public Volunteer(String vUserName, String vPassword, String vFname, String vLname, String vEmail, String vPhone, String vStreet, String vCity, String vState, String vZip, Date vDOB, String vPI, String vExp, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday, String vSpecialization, String vStatus, double totalHours) {
        this.vUserName = vUserName;
        this.vPassword = vPassword;
        this.vFname = vFname;
        this.vLname = vLname;
        this.vEmail = vEmail;
        this.vPhone = vPhone;
        this.vStreet = vStreet;
        this.vCity = vCity;
        this.vState = vState;
        this.vZip = vZip;
        this.vDOB = vDOB;
        this.vPI = vPI;
        this.vExp = vExp;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.vSpecialization = vSpecialization;
        this.vStatus = vStatus;
        this.vTotalHours = totalHours;
    }
    
    public double getVTotalHours() {
        return vTotalHours;
    }
    // create setter 
    public void setVTotalHours(double totalHours) {
        this.vTotalHours = totalHours;
    }
    // create getter
    public String getVUserName() {
        return vUserName;
    }
    // create setter 
    public void setVUserName(String vUserName) {
        this.vUserName = vUserName;
    }
    // create getter
    public String getVPassword() {
        return vPassword;
    }
    // create setter 
    public void setVPassword(String vPassword) {
        this.vPassword = vPassword;
    }
    // create getter
    public String getVFname() {
        return vFname;
    }
    // create setter 
    public void setVFname(String vFname) {
        this.vFname = vFname;
    }
    // create getter
    public String getVLname() {
        return vLname;
    }
    // create setter 
    public void setVLname(String vLname) {
        this.vLname = vLname;
    }
    // create getter
    public String getVEmail() {
        return vEmail;
    }
    // create setter 
    public void setVEmail(String vEmail) {
        this.vEmail = vEmail;
    }
    // create getter
    public String getVPhone() {
        return vPhone;
    }
    // create setter 
    public void setVPhone(String vPhone) {
        this.vPhone = vPhone;
    }
    // create getter
    public String getVStreet() {
        return vStreet;
    }
    // create setter 
    public void setVStreet(String vStreet) {
        this.vStreet = vStreet;
    }
    // create getter
    public String getVCity() {
        return vCity;
    }
    // create setter 
    public void setVCity(String vCity) {
        this.vCity = vCity;
    }
    // create getter
    public String getVState() {
        return vState;
    }
    // create setter 
    public void setVState(String vState) {
        this.vState = vState;
    }
    // create getter
    public String getVZip() {
        return vZip;
    }
    // create setter 
    public void setVZip(String vZip) {
        this.vZip = vZip;
    }
    // create getter
    public Date getVDOB() {
        return vDOB;
    }
    // create setter 
    public void setVDOB(Date vDOB) {
        this.vDOB = vDOB;
    }
    // create getter
    public String getVPI() {
        return vPI;
    }
    // create setter 
    public void setVPI(String vPI) {
        this.vPI = vPI;
    }
    // create getter
    public String getVExp() {
        return vExp;
    }
    // create setter 
    public void setVExp(String vExp) {
        this.vExp = vExp;
    }
    // create getter
    public String getMonday() {
        return monday;
    }
    // create setter 
    public void setMonday(String monday) {
        this.monday = monday;
    }
    // create getter
    public String getTuesday() {
        return tuesday;
    }
    // create setter 
    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }
    // create getter
    public String getWednesday() {
        return wednesday;
    }
    // create setter 
    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }
    // create getter
    public String getThursday() {
        return thursday;
    }
    // create setter 
    public void setThursday(String thursday) {
        this.thursday = thursday;
    }
    // create getter
    public String getFriday() {
        return friday;
    }
    // create setter 
    public void setFriday(String friday) {
        this.friday = friday;
    }
    // create getter
    public String getSaturday() {
        return saturday;
    }
    // create setter 
    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }
    // create getter
    public String getSunday() {
        return sunday;
    }
    // create setter 
    public void setSunday(String sunday) {
        this.sunday = sunday;
    }
    // create getter
    public String getVSpecialization() {
        return vSpecialization;
    }
    // create setter 
    public void setVSpecialization(String vSpecialization) {
        this.vSpecialization = vSpecialization;
    }
    // create getter
    public String getVStatus() {
        return vStatus;
    }
    // create setter 
    public void setVStatus(String vStatus) {
        this.vStatus = vStatus;
    }
    // create getter
    public String toString()
    {
        return this.vFname + " " + this.vLname;
    }
    
}