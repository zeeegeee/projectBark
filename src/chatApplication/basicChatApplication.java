/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: This is the basic chat application which does not contain network implementaition
 */
package chatApplication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oracle.jdbc.pool.OracleDataSource;
import projectBark.loginPage;

public class basicChatApplication {

    Stage primaryStage;
    //buttons 
    Button send = new Button("Send");
    Button cancel = new Button("Cancel");
    //labels 
    public static ArrayList<Label> messages = new ArrayList<Label>();
    //textfield 
    public static TextField tf = new TextField();
    //tables 
    ListView<Label> texts = new ListView<Label>();
    ObservableList<Label> textsOBV = FXCollections.observableArrayList(messages);
    public int index = 0;
    VBox box = new VBox();

    public void window() {
        messages.clear();
        textsOBV.clear();

        loadMessages();

        cancel.setOnAction(o -> {
            tf.clear();
        });
        primaryStage = new Stage();
        GridPane pane = new GridPane();
        Scene primaryScene = new Scene(pane, 825, 625);
        ScrollPane scrollPane = new ScrollPane();
        pane.setPadding(new Insets(25, 10, 10, 10));

        texts.setMinSize(800, 500);

        pane.setVgap(10);
        pane.setHgap(10);
        // add controls to pane
        scrollPane.setContent(box);
        pane.add(texts, 0, 0, 2, 1);
        pane.add(tf, 0, 1, 2, 1);
        pane.add(send, 0, 2);
        pane.add(cancel, 1, 2);

        send.setOnAction(sendMessage -> {

            messages.add(new Label(tf.getText()));
            messages.get(index).setId("yourmessage");

            messages.get(index).setAlignment(Pos.CENTER_RIGHT);
            box.getChildren().add(messages.get(index));
            textsOBV.add(messages.get(index));
            addMessageToDatabase(tf.getText());
            index++;

        });
        texts.setItems(textsOBV);
        primaryStage.setTitle("BarkBox");
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        //css
        pane.getStylesheets().add("file:../Bark/adminWindowCss.css");

    }

    Connection dbConn;
    Statement commStmt;
    ResultSet dbResults;

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
            e.printStackTrace();
        }
    }

    // Add messages to DB
    public void addMessageToDatabase(String message) {
        String sqlQuery = "";
        sqlQuery += "INSERT INTO groupChat (vusername, chattext) VALUES (";
        sqlQuery += "\'" + loginPage.txtuserName.getText() + "\',";
        sqlQuery += "\'" + message + "\')";
        sendDBCommand(sqlQuery);

    }

    // Load messages 
    public void loadMessages() {

        try {
            String sqlQuery = "select * from groupchat";
            sendDBCommand(sqlQuery);
            try {
                messages.clear();

                while (dbResults.next()) {

                    if (!dbResults.getString(1).matches(loginPage.txtuserName.getText())) {
                        messages.add(new Label("@" + dbResults.getString(1) + ": " + dbResults.getString(2)));
                        messages.get(index).setId("message");

                        messages.get(index).setAlignment(Pos.CENTER_LEFT);
                        box.getChildren().add(messages.get(index));
                        textsOBV.add(messages.get(index));
                        index++;
                    } else if (dbResults.getString(1).matches(loginPage.txtuserName.getText())) {
                        messages.add(new Label(dbResults.getString(2)));
                        messages.get(index).setId("yourmessage");

                        messages.get(index).setAlignment(Pos.CENTER_RIGHT);
                        box.getChildren().add(messages.get(index));
                        textsOBV.add(messages.get(index));
                        index++;

                    }

                }

            } catch (SQLException sqle) {
                System.out.print(sqle.toString());
            }

        } catch (Exception m) {
            System.out.print(m.toString());
        }
    }

}
