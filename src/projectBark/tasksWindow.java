/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: Allows the addition of tasks to be completed by volunteers
 */
package projectBark;

import static adminWindows.viewAdminAnimal.animaltableData;
import classes.animal;
import java.sql.*;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.*;
import oracle.jdbc.pool.OracleDataSource;

public class tasksWindow {

    // create the tasksPane
    GridPane tasksPane = new GridPane();
    // create listview 
    public static ListView<String> taskTable = new ListView();
    // create taskTableData as observableList
    public static ObservableList<String> taskTableData = FXCollections.observableArrayList();

    //Text title = new Text("Tasks of the Day"
    Label lblCreateTask = new Label("Tasks of the Day");
    TextField txtTask = new TextField();
    Button btnAddTask = new Button("Add Task");
    Button btnDelTask = new Button("Delete Task");

    String tempTask = "";
    HBox hb = new HBox();

    public void showTasks() {
        taskTableData.clear();
        viewTask();
        // add controls to tasksPane
        tasksPane.add(lblCreateTask, 0, 0);
        tasksPane.add(txtTask, 0, 2);
        tasksPane.add(hb, 0, 3);
        tasksPane.add(taskTable, 0, 1);
        tasksPane.setVgap(5.5);
        mainAdminWindow.tasksOverall.add(tasksPane, 0, 0);
        mainAdminWindow.tasksOverall.setAlignment(Pos.CENTER);

        //add controls to vbText Pane
        hb.getChildren().addAll(btnAddTask, btnDelTask);
        hb.setSpacing(5.5);
        // when click on the btnAddTask
        btnAddTask.setOnAction(e -> {
            Alert userPrompt = new Alert(Alert.AlertType.ERROR,
                    "Please Input Task",
                    ButtonType.OK);

            if ((txtTask.getText().isEmpty())) {
                userPrompt.show();
            } else {
                createTask();
                viewTask();
                txtTask.clear();
            }
        });
        // when click on the btnDelTask
        btnDelTask.setOnAction(del -> {
            deleteTask();
            viewTask();
        });
        // set the taskTable editable
        taskTable.setEditable(true);

        mainAdminWindow.tasksOverall.setMaxSize(1000, 1000);
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
        System.out.println(sqlQuery);
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

    // create the createTask method
    public void createTask() {
        String taskBID = UUID.randomUUID().toString().substring(0, 8);
        String sqlQuery = "";
        String sql = "";
        sql += "insert into task (taskdesc) values ('" + txtTask.getText() + "')";
        sendDBCommand(sql);
        sqlQuery += "INSERT INTO TASKbridge(TASKBID, TASKDESC, ANAME) VALUES (";
        sqlQuery += "'" + taskBID + "',";
        sqlQuery += "\'" + txtTask.getText() + "\',";
        sendDBCommand(sqlQuery);
    }

    // Enter values into Task sql table
    public void viewTask() {
        String query = "Select * from Task";

        try {
            sendDBCommand(query);
            taskTableData.clear();
            while (dbResults.next()) {

                taskTableData.add(
                        dbResults.getString("TASKDESC")
                );

                taskTable.setItems((taskTableData));
            }
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print("Catch");
        }
    }

    // Delete task selected
    public void deleteTask() {
        String sqlQuery = "DELETE FROM TASK WHERE taskdesc =\'" + (taskTable.getSelectionModel().getSelectedItem()) + "\'";
        sendDBCommand(sqlQuery);
    }

    // create the viewanimal method 
    public void viewanimal() {
        String query = "Select * from animal where adoptedStatus = " + 0;

        try {
            sendDBCommand(query);
            // store information to animaltableData
            while (dbResults.next()) {

                animaltableData.add(new animal(
                        dbResults.getString("AID"),
                        dbResults.getString("ANAME"),
                        dbResults.getString("ASPECIES"),
                        dbResults.getString("AAGE"),
                        dbResults.getString("AGENDER"),
                        dbResults.getString("ADESC")
                ));
            }

            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print("Catch");
        }
    }
}
