/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectBark;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author labpatron
 */
public class loginPage {

    Label userName = new Label("User Name");
    public static TextField txtuserName = new TextField();

    public void start() {
        Stage primaryStage = null;

        Label passWord = new Label("Password");

        PasswordField txtpassWord = new PasswordField();
        txtpassWord.setPromptText("********");
        txtuserName.setPromptText("USERNAME");

        Button loginButton = new Button("Login");
        loginButton.setMinWidth(300);
        Button cancel = new Button("Cancel");

        //code from https://stackoverflow.com/questions/20984209/javafx-how-to-make-a-clickable-text        
        Hyperlink reset = new Hyperlink("Click Here ");

        TextFlow flow = new TextFlow(
                new Text("Forgot password or username? "), reset
        );
        //end of cited code 

        GridPane loginPane = new GridPane();
        loginPane.setHgap(30);
        loginPane.setVgap(10);
        loginPane.setAlignment(Pos.CENTER);
        // col row  add controls to loginPane
        loginPane.add(txtuserName, 0, 0, 2, 1);

        loginPane.add(txtpassWord, 0, 1, 2, 1);
        loginPane.add(loginButton, 0, 3, 2, 1);
        loginPane.add(flow, 0, 4, 2, 1);

        Scene loginScene = new Scene(loginPane, 400, 300);
        Stage loginStage = new Stage();
        loginStage.setTitle("Volunteer Login portal");
        loginStage.setScene(loginScene);
        loginPane.setVgap(10);
        txtpassWord.setAlignment(Pos.CENTER);
        txtuserName.setAlignment(Pos.CENTER);
        txtpassWord.setId("text-field");
        txtuserName.setId("text-field");
        loginPane.setStyle("-fx-background-color:white;");

        loginStage.show();

        //when login is chosen
        // creating the windows to call
        mainAdminWindow window2 = new mainAdminWindow();
        mainVolunteerWindow v = new mainVolunteerWindow();

        loginButton.setOnAction(log -> {
            try {
                // write sql statment for volunteer table
                String sql = "select vusername, vpassword, vstatus from volunteer";
                sendDBCommand(sql);
                try {
                    while (dbResults.next()) {
                        if (dbResults.getString(3).matches("3")) {
                            if (txtpassWord.getText().matches(dbResults.getString(2)) && txtuserName.getText().matches(dbResults.getString(1))) {
                                window2.window();
                                loginStage.hide();
                            }

                        } else if (dbResults.getString(3).matches("1") || dbResults.getString(3).matches("2") || dbResults.getString(3).matches("4")) {
                            if (txtpassWord.getText().matches(dbResults.getString(2)) && txtuserName.getText().matches(dbResults.getString(1))) {
                                v.window();

                                //closing the two other windows 
                                loginStage.hide();
                            } else if (txtpassWord.getText().matches(dbResults.getString(2)) && txtuserName.getText().matches(dbResults.getString(1))) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Wrong Username or Password");
                                alert.setHeaderText("Your information did not match");
                                alert.setContentText("Please try again");
                                alert.showAndWait();
                            }

                        } else if (dbResults.getString(3).matches("0")) {
                            if (txtpassWord.getText().matches(dbResults.getString(2)) && txtuserName.getText().matches(dbResults.getString(1))) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Welcome Back");
                                alert.setHeaderText("Your Application is currently under review");
                                alert.setContentText("Please wait for the application to be approved");
                                alert.showAndWait();
                            }

                        }
                    }

                } catch (SQLException sqle) {
                    System.out.print(sqle.toString());
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        });

        //depending on the username and password a different window will show
        //closing the other two windows 
        StackPane root = new StackPane();

        Scene scene = new Scene(root, 300, 250);

        //basic database search for username and password match needs to be done here 
        loginScene.getStylesheets().add("file:../Bark/adminWindowCss.css");
        // when click on the reset button 
        reset.setOnAction(d -> {
            Label Lemail = new Label("Email");
            TextField txtLemail = new TextField();
            Button sendEmail = new Button("Send Email");
            Button exit = new Button("Exit");
            txtLemail.setId("text-field");

            FlowPane pane = new FlowPane();

            pane.setStyle("-fx-background-color:white;");

            pane.setAlignment(Pos.CENTER);
            pane.setHgap(10);
            pane.setVgap(10);
            pane.getChildren().addAll(Lemail, txtLemail, sendEmail, exit);

            Scene forgotScene = new Scene(pane, 400, 100);
            Stage forgotStage = new Stage();
            forgotStage.setScene(forgotScene);
            forgotStage.show();
            forgotScene.getStylesheets().add("file:../Bark/adminWindowCss.css");

            sendEmail.setOnAction(k -> {
                //needs database integration
                String message = "Your username is " + txtuserName.getText() + " and your"
                        + " password is " + txtpassWord.getText();
                try {
                    //mail functionality to be worked on after all main functionality is satisfied

                } catch (Exception m) {
                    System.out.println(m.toString());
                }
                Label notification = new Label("Email with Password and Username has been sent");
                pane.getChildren().add(notification);
            });
            exit.setOnAction(ex -> {
                forgotStage.close();
            });
        });

    }
    Connection dbConn;
    Statement commStmt;
    ResultSet dbResults;

    // connect to database
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
