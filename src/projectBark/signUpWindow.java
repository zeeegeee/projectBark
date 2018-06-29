/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: Sign up registration window allows volunteers to register as an applicant
 */
package projectBark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import oracle.jdbc.pool.OracleDataSource;

public class signUpWindow {

    // crate primaryStage 
    Stage primaryStage = new Stage();

    public void signUpWindow() throws FileNotFoundException {
        GridPane rootPane = new GridPane();
        rootPane.setStyle("-fx-background-color:white;");

        //left half of the page
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(50, 40, 50, 40));
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setStyle("-fx-background-color:white;");
        // create labels
        Label sign = new Label("REGISTER");
        Label volunteerName = new Label("Name");
        Label volunteerEmail = new Label("Email");
        Label volunteerZip = new Label("Zip");
        Label volunteerState = new Label("State");
        Label volunteerCity = new Label("City");
        Label volunteerPhone = new Label("Phone Number");
        Label volunteerDOB = new Label("Date Of Birth");
        Label street = new Label("Street");
        Label username = new Label("Username");
        Label password = new Label("Password");

        // create textfields and set text for textfields
        TextField txtvolunteerfName = new TextField();
        txtvolunteerfName.setPromptText("First Name");
        TextField txtvolunteerlName = new TextField();
        txtvolunteerlName.setPromptText("Last Name");
        TextField txtvolunteerStreet = new TextField();
        txtvolunteerStreet.setPromptText("Street Address");
        TextField txtZip = new TextField();
        txtZip.setPromptText("12345");
        TextField txtState = new TextField();
        txtState.setPromptText("VA");
        TextField txtCity = new TextField();
        txtCity.setPromptText("city");
        TextField txtPhone = new TextField();
        txtPhone.setPromptText("123-456-8910");
        TextField txtDOB = new TextField();
        txtDOB.setPromptText("01/01/1990");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("bark@mail.com");
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Enter a UserName");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Enter a Password");
        PasswordField txtPassword2 = new PasswordField();
        txtPassword2.setPromptText("Verify Password");
        TextArea experience = new TextArea();
        experience.setPromptText("Please describe your volunteering experience");
        experience.setWrapText(true);
        // create buttons
        Button signUp = new Button("Send Application");
        Button cancel = new Button("Cancel");

        //setting css ids 
        txtvolunteerfName.setId("text-field");
        txtvolunteerlName.setId("text-field");
        txtvolunteerStreet.setId("text-field");
        txtZip.setId("text-field");
        txtState.setId("text-field");
        txtCity.setId("text-field");
        txtPhone.setId("text-field");
        txtDOB.setId("text-field");
        txtEmail.setId("text-field");
        txtPassword.setId("text-field");
        txtPassword2.setId("text-field");
        txtUsername.setId("text-field");

        //ensuring passwords match
        signUp.setDisable(false);

        pane.add(sign, 0, 0, 2, 1);
        sign.setStyle("-fx-font-weight: bold;");
        sign.setStyle("-fx-font: 30px \"Sans Serif\";");
        // add controls to pane        
        pane.add(volunteerName, 0, 1);
        pane.add(volunteerPhone, 0, 2);
        pane.add(volunteerEmail, 0, 3);
        pane.add(street, 0, 4);
        pane.add(volunteerCity, 0, 5);
        pane.add(volunteerState, 0, 6);
        pane.add(volunteerZip, 0, 7);
        pane.add(volunteerDOB, 0, 8);
        pane.add(new Label("Experience"), 0, 9, 2, 1);
        pane.add(username, 0, 10);
        pane.add(password, 0, 11);
        pane.add(txtvolunteerfName, 1, 1);
        pane.add(txtvolunteerlName, 2, 1);
        pane.add(txtPhone, 1, 2, 2, 1);
        pane.add(txtEmail, 1, 3, 2, 1);
        pane.add(txtvolunteerStreet, 1, 4, 2, 1);
        pane.add(txtCity, 1, 5, 2, 1);
        pane.add(txtState, 1, 6, 2, 1);
        pane.add(txtZip, 1, 7, 2, 1);
        pane.add(txtDOB, 1, 8, 2, 1);
        pane.add(experience, 1, 9, 2, 1);
        pane.add(txtUsername, 1, 10, 2, 1);
        pane.add(txtPassword, 1, 11);
        pane.add(txtPassword2, 2, 11);
        pane.add(signUp, 2, 13, 2, 1);
        pane.add(cancel, 1, 13);
        cancel.setMinWidth(175);
        signUp.setMinWidth(175);

        // when click on the signup button      
        signUp.setOnAction(not -> {
            txtPassword.setOnMousePressed(u -> {
                signUp.setDisable(false);
                txtPassword.setStyle("-fx-background-color: whitesmoke ;");
                txtPassword2.setStyle("-fx-background-color: whitsmoke ;");
            });

            try {
                //calling all usernames from the database
                String sql = "select vusername from volunteer";
                sendDBCommand(sql);

                String testing = "";
                while (dbResults.next()) {
                    if (dbResults.getString(1).matches(txtUsername.getText())) {
                        testing = "true";
                    } else if (!txtPassword.getText().matches(txtPassword2.getText())) {
                        testing = "wrong";
                    }

                }
                // if the username already exist, alert the user
                if (testing == "true") {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Username already exists");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please choose another Username");
                    alert.showAndWait();
                } else if (testing == "wrong") {
                    txtPassword.setStyle("-fx-background-color: lightcoral ;");
                    txtPassword2.setStyle("-fx-background-color: lightcoral ;");
                    signUp.setDisable(true);
                } // pop up the window " your application is in review"
                else {
                    Alert acceptance = new Alert(AlertType.CONFIRMATION);
                    acceptance.setTitle("Thank you");
                    acceptance.setHeaderText("Your Application is in review");
                    acceptance.showAndWait();

                    //formating phone number 
                    String str = txtPhone.getText();
                    str = str.replaceAll("[^\\d.]", "");
                    java.text.MessageFormat phoneMsgFmt = new java.text.MessageFormat("({0})-{1}-{2}");
                    //suposing a grouping of 3-3-4 -  formating help found at https://stackoverflow.com/questions/5114762/how-do-format-a-phone-number-as-a-string-in-java
                    String[] phoneNumArr = {str.substring(0, 3),
                        str.substring(3, 6),
                        str.substring(6)};
                    // create the sql statement for the volunteer table
                    String insertStatment = "INSERT INTO JAVAUSER.VOLUNTEER (vusername, VPASSWORD, VFNAME, VLNAME, VEMAIL, VPHONE, VSTREET, VCITY, VSTATE, VZIP, VDOB, VPI, VEXP, VSTATUS, MONDAY, TUESDAY, WEDNESDAY,"
                            + "THURSDAY, FRIDAY, SATURDAY, SUNDAY, VPROFILEPIC, VSPECIALIZATION) values (";
                    insertStatment += "'" + txtUsername.getText() + "',";
                    insertStatment += "'" + txtPassword.getText() + "',";
                    insertStatment += "'" + txtvolunteerfName.getText() + "',";
                    insertStatment += "'" + txtvolunteerlName.getText() + "',";
                    insertStatment += "'" + txtEmail.getText() + "',";
                    insertStatment += "'" + phoneMsgFmt.format(phoneNumArr) + "',";
                    insertStatment += "'" + txtvolunteerStreet.getText() + "',";
                    insertStatment += "'" + txtCity.getText() + "',";
                    insertStatment += "'" + txtState.getText() + "',";
                    insertStatment += "" + Integer.parseInt(txtZip.getText()) + ",";

                    insertStatment += "to_date('" + txtDOB.getText() + "','mm-dd-yyyy'),";
                    insertStatment += "'" + null + "',";
                    insertStatment += "'" + experience.getText() + "',";
                    insertStatment += "'" + 0 + "',";
                    insertStatment += "'" + null + "',";
                    insertStatment += "'" + null + "',";
                    insertStatment += "'" + null + "',";
                    insertStatment += "'" + null + "',";
                    insertStatment += "'" + null + "',";
                    insertStatment += "'" + null + "',";
                    insertStatment += "'" + null + "',";
                    insertStatment += "'../Bark/Images/nopic.png',";
                    insertStatment += "'" + null + "')";

                    sendDBCommand(insertStatment);
                }

            } catch (SQLException sqle) {
                System.out.print(sqle.toString());
            }
        });
        // when click on the cancel button 
        cancel.setOnAction(v
                -> {
            primaryStage.close();
        });

        //right half of the page with the dog picture
        GridPane imagePane = new GridPane();
        imagePane.setStyle("-fx-background-color:white;");
        FileInputStream imgReader = new FileInputStream("../Bark/Images/image.jpg");
        Image img = new Image(imgReader);
        ImageView imgView = new ImageView(img);
        imagePane.add(imgView, 0, 0);
        imagePane.setAlignment(Pos.BOTTOM_RIGHT);

        //adding the two gridpanes to the main root pane
        rootPane.add(pane, 0, 0);
        rootPane.add(imagePane, 1, 0, 1, 2);
        // set scene
        Scene scene = new Scene(rootPane, 1200, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register as a volunteer");
        primaryStage.show();

        scene.getStylesheets().add("file:../Bark/adminWindowCss.css");

    }

    Connection dbConn;
    Statement commStmt;
    ResultSet dbResults;

    // connect to the database
    public void sendDBCommand(String sqlQuery) {
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        String userID = "javauser"; // Change to YOUR Oracle username
        String userPASS = "javapass"; // Change to YOUR Oracle password
        OracleDataSource ds;
        try {
            ds = new OracleDataSource();
            ds.setURL(URL);
            dbConn = ds.getConnection(userID, userPASS);
            commStmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbResults = commStmt.executeQuery(sqlQuery); // Sends the Query to the DB
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

}
