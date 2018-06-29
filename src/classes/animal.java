/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: This is the Animal class 
 */
package classes;

import java.util.ArrayList;

public class animal {

    public String aID;
    public String aSpecies;
    public String aName;
    public String aAge;
    public String aGender;
    public String aDescription;
    public String aPic;

    public static ArrayList<animal> animalArray = new ArrayList<>();
    public static int animalCount = 0;

    // Constructors
    public animal(String aid, String aName, String aSpecies, String aAge, String aGender, String aDescription, String aPic) {
        this.aSpecies = aSpecies;
        this.aAge = aAge;
        this.aGender = aGender;
        this.aName = aName;
        this.aDescription = aDescription;
        this.aID = aid;
        this.aPic = aPic;
    }
    
    public animal(String aid, String aName, String aSpecies, String aAge, String aGender, String aDescription) {
        this.aSpecies = aSpecies;
        this.aAge = aAge;
        this.aGender = aGender;
        this.aName = aName;
        this.aDescription = aDescription;
        this.aID = aid;
    }
    // create getter
    public String getAID() {
        return aID;
    }
    // create setter
    public void setAID(String aID) {
        this.aID = aID;
    }
    // create getter
    public String getASpecies() {
        return aSpecies;
    }
    // create getter
    public void setASpecies(String aSpecies) {
        this.aSpecies = aSpecies;
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
    public String getAAge() {
        return aAge;
    }
    // create getter
    public void setAAge(String aAge) {
        this.aAge = aAge;
    }
    // create getter
    public String getAGender() {
        return aGender;
    }
    // create setter
    public void setAGender(String aGender) {
        this.aGender = aGender;
    }
    // create getter
    public String getADescription() {
        return aDescription;
    }
    // create setter
    public void setADescription(String aDescription) {
        this.aDescription = aDescription;
    }
    // create getter
    public String getAPic() {
        return aPic;
    }
    // create setter
    public void setAPic(String aPic) {
        this.aPic = aPic;
    }

    public String toString() {
        return aName;
    }

}