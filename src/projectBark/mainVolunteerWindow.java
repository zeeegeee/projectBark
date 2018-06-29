/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: Contains the main template for volunteer window after the login
 */
package projectBark;

import classes.Volunteer;
import classes.Event;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oracle.jdbc.pool.OracleDataSource;
import classes.*;
import com.sun.javafx.scene.control.skin.DatePickerContent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import static projectBark.socialMediaWindow.profilePicture;
import projectBark.tasksWindow.*;

public class mainVolunteerWindow {

    TableView<Event> eventTable = new TableView<>();
    ObservableList<Event> eventVData = FXCollections.observableArrayList();
    DatePickerSkin calendar = new DatePickerSkin(new DatePicker());
    DatePickerContent pop = (DatePickerContent) calendar.getPopupContent();
    public String name;
    // create buttons for the menu
    Button a = new Button("HOME");
    Button b = new Button("EDIT PROFILE");
    Button c = new Button("CHECK IN/OUT");
    Button d = new Button("TRIPS");
    Button e = new Button("EVENTS/TASKS");
    Button f = new Button("LOGOUT");
    Button g = new Button("MENU");
    Button h = new Button();
    Button i = new Button("REPORTS");
    Button j = new Button("ADOPTION");
    Button k = new Button("ANIMALS");

    // create gridpanes
    GridPane mainPane = new GridPane();
    GridPane employeePane = new GridPane();
    GridPane adminPane = new GridPane();
    GridPane taskPane = new GridPane();
    GridPane tripPane = new GridPane();
    public static GridPane checkPane = new GridPane();
    public static GridPane vAdoptPane = new GridPane();
    GridPane eventPane = new GridPane();
    public static GridPane socialPane = new GridPane();
    public static GridPane vReportsOverall = new GridPane();
    public static GridPane vAnimalPane = new GridPane();
    VBox viewTasks = new VBox();
    Button viewMore = new Button("View More Info");
    public int minute;
    public int hour;
    public int second;
    public static final ObservableList timeData = FXCollections.observableArrayList();
    public static final ObservableList chouttimeData = FXCollections.observableArrayList();
    // create controls
    Label lblLocation = new Label("Location:");
    Label lblTask = new Label("Task:");
    Label lblMileage = new Label("Mileage:");
    Label lblDuration = new Label("Duration:");
    Label lblTitle = new Label("Trip Information");
    TextField txtLocation = new TextField();
    TextField txtTask = new TextField();
    TextField txtMileage = new TextField();
    TextField txtDuration = new TextField();
    Button btnAddTrip = new Button("Add Trip");
    ListView lvTask = new ListView();
    TableColumn tblcDuration = new TableColumn("Duration");
    TableColumn tblcMileage = new TableColumn("Mileage");
    TableColumn tblcTask = new TableColumn("Task");
    TableColumn tblcLocation = new TableColumn("Location");
    TableView tripTable = new TableView<>();
    ObservableList tripTableData = FXCollections.observableArrayList();
    public static double totalTHours = 0;
    public static double totalEHours = 0;
    public static ObservableList<String> specialization = FXCollections.observableArrayList("Nothing Selected",
            "Animal Health Care", "Feeding", "Enclosure Care", "Adopter Relations", "Event Volunteer");
    public static ComboBox<String> specializationCBO = new ComboBox(specialization);
    Button btnSign = new Button("Sign Up");

    public void window() {
        //setting the menu right vbox --------------------------------------------------------------
        VBox menuPane = new VBox();
        menuPane.setOpacity(0.86);
        menuPane.setMinWidth(250);
        menuPane.setPadding(new Insets(10, 10, 10, 10));
        //adding all the menu buttons to the menue 
        menuPane.getChildren().addAll(g, a, b, c, d, e, k, j, i, f);
        // set the width size for the buttons
        a.setMinWidth(235);
        b.setMinWidth(235);
        c.setMinWidth(235);
        d.setMinWidth(235);
        e.setMinWidth(235);
        f.setMinWidth(235);
        h.setMinWidth(370);
        g.setMinWidth(235);
        i.setMinWidth(235);
        j.setMinWidth(235);
        k.setMinWidth(235);
        // set the height size for the buttons
        a.setMinHeight(75);
        b.setMinHeight(75);
        c.setMinHeight(75);
        d.setMinHeight(75);
        e.setMinHeight(75);
        f.setMinHeight(75);
        i.setMinHeight(75);
        j.setMinHeight(75);
        k.setMinHeight(75);

        f.setId("logoutButton");
        //setting menu items to false visible
        a.setVisible(false);
        b.setVisible(false);
        c.setVisible(false);
        d.setVisible(false);
        e.setVisible(false);
        f.setVisible(false);
        i.setVisible(false);
        j.setVisible(false);
        k.setVisible(false);

        //code for the hover over the menu Bar
        g.setOnMouseEntered(t -> {
            a.setVisible(true);
            b.setVisible(true);
            c.setVisible(true);
            d.setVisible(true);
            e.setVisible(true);
            f.setVisible(true);
            i.setVisible(true);
            j.setVisible(true);
            k.setVisible(true);
        });
        // when the mouse is not hover over the menupane
        menuPane.setOnMouseExited(o -> {
            a.setVisible(false);
            b.setVisible(false);
            c.setVisible(false);
            d.setVisible(false);
            e.setVisible(false);
            f.setVisible(false);
            i.setVisible(false);
            j.setVisible(false);
            k.setVisible(false);
        });
        menuPane.setSpacing(3);
        //adding menue to the main pane
        mainPane.add(menuPane, 0, 0);

        //main page setting ---------------------------------------------------------
        Stage adminPrimaryStage = new Stage();
        Scene adminScene = new Scene(mainPane, 1400, 800);
        adminPane.setPadding(new Insets(10, 10, 10, 10));

        // adding all the panes and setting visible to false
        mainPane.add(adminPane, 1, 0);
        mainPane.add(employeePane, 1, 0);
        mainPane.add(taskPane, 1, 0);
        mainPane.add(tripPane, 1, 0);
        mainPane.add(checkPane, 1, 0);
        mainPane.add(vAdoptPane, 1, 0);
        mainPane.add(eventPane, 1, 0);
        mainPane.add(socialPane, 1, 0);
        mainPane.add(vReportsOverall, 1, 0);
        mainPane.add(vAnimalPane, 1, 0);

        adminPane.setVgap(15);
        adminPane.setHgap(15);
        //col row

        adminPane.add(h, 1, 0, 2, 1);

        h.setDisable(true);
        h.setId("top");

        //Volunteer form -----------------------------------------------------------
        GridPane txtAreaPane = new GridPane();
        employeePane.setAlignment(Pos.CENTER);
        employeePane.setPadding(new Insets(15, 10, 10, 10));
        employeePane.setVgap(10);
        employeePane.setHgap(5);
        txtAreaPane.setHgap(5);
        txtAreaPane.setVgap(5);

        //lables for volunteers
        Label volunteerName = new Label("Name");
        Label volunteerEmail = new Label("Email");
        Label volunteerZip = new Label("Zip");
        Label volunteerState = new Label("State");
        Label volunteerCity = new Label("City");
        Label volunteerPhone = new Label("Phone Number");
        Label volunteerDOB = new Label("Date Of Birth");
        Label street = new Label("Street");
        Label lblPic = new Label("Profile Picture");
        Label lblMon = new Label("Monday");
        Label lblTue = new Label("Tuesday");
        Label lblWed = new Label("Wednesday");
        Label lblThur = new Label("Thursday");
        Label lblFri = new Label("Friday");
        Label lblSat = new Label("Saturday");
        Label lblSun = new Label("Sunday");
        Label lblAva = new Label("Time Availability");
        Label lblEdit = new Label("Edit Profile");
        Label lblPi = new Label("Personal Information");
        Label lblExp = new Label("Work Experience");
        lblAva.setStyle("-fx-font: 18 arial;");
        lblEdit.setStyle("-fx-font: 18 arial;");
        Button btnSave = new Button("Save");
        // create textfields for volunteers information
        TextField txtvolunteerfName = new TextField();
        TextField txtvolunteerlName = new TextField();
        TextField txtvolunteerStreet = new TextField();
        TextField txtZip = new TextField();
        TextField txtState = new TextField();
        TextField txtCity = new TextField();
        TextField txtPhone = new TextField();
        TextField txtDOB = new TextField();
        TextField txtEmail = new TextField();
        TextArea txtAPi = new TextArea();
        TextArea txtAExp = new TextArea();

        volunteerName.setFocusTraversable(false);
        //setting the labels 
        txtAreaPane.add(lblPi, 3, 0);
        txtAreaPane.add(txtAPi, 3, 1, 1, 3);
        txtAreaPane.add(lblExp, 3, 5);
        txtAreaPane.add(txtAExp, 3, 6, 1, 3);
        // add controls to employeepane
        employeePane.add(lblEdit, 0, 0);
        employeePane.add(volunteerName, 0, 1);
        employeePane.add(volunteerPhone, 0, 2);
        employeePane.add(volunteerEmail, 0, 3);
        employeePane.add(street, 0, 4);
        employeePane.add(volunteerCity, 0, 5);
        employeePane.add(volunteerState, 0, 6);
        employeePane.add(volunteerZip, 0, 7);
        employeePane.add(volunteerDOB, 0, 8);
        employeePane.add(txtvolunteerfName, 1, 1);
        employeePane.add(txtvolunteerlName, 2, 1);
        employeePane.add(txtPhone, 1, 2);
        employeePane.add(txtEmail, 1, 3);
        employeePane.add(txtvolunteerStreet, 1, 4);
        employeePane.add(txtCity, 1, 5);
        employeePane.add(txtState, 1, 6);
        employeePane.add(txtZip, 1, 7);
        employeePane.add(txtDOB, 1, 8);
        employeePane.add(lblPic, 0, 9);
        employeePane.add(lblAva, 0, 10);
        employeePane.add(lblMon, 0, 11);
        employeePane.add(lblTue, 0, 12);
        employeePane.add(lblWed, 0, 13);
        employeePane.add(lblThur, 0, 14);
        employeePane.add(lblFri, 0, 15);
        employeePane.add(lblSat, 0, 16);
        employeePane.add(lblSun, 0, 17);
        employeePane.add(btnSave, 0, 19, 4, 1);
        btnSave.setMinWidth(280);
        employeePane.add(txtAreaPane, 3, 0, 1, 17);
        txtDOB.setDisable(true);

        adminPrimaryStage.setTitle("Welcome Back " + loginPage.txtuserName.getText());
        adminPrimaryStage.setScene(adminScene);
        adminPrimaryStage.show();
        // create the comboBox for time slot 
        ObservableList<String> optionsMon = FXCollections.observableArrayList("Nothing Selected",
                "Morning", "Afternoon", "Evening", "All Day", "Not Available");
        ComboBox cboMon = new ComboBox(optionsMon);

        ObservableList<String> optionsTue = FXCollections.observableArrayList("Nothing Selected",
                "Morning", "Afternoon", "Evening", "All Day", "Not Available");
        ComboBox cboTue = new ComboBox(optionsTue);

        ObservableList<String> optionsWed = FXCollections.observableArrayList("Nothing Selected",
                "Morning", "Afternoon", "Evening", "All Day", "Not Available");
        ComboBox cboWed = new ComboBox(optionsTue);

        ObservableList<String> optionsThur = FXCollections.observableArrayList("Nothing Selected",
                "Morning", "Afternoon", "Evening", "All Day", "Not Available");
        ComboBox cboThur = new ComboBox(optionsTue);

        ObservableList<String> optionsFri = FXCollections.observableArrayList("Nothing Selected",
                "Morning", "Afternoon", "Evening", "All Day", "Not Available");
        ComboBox cboFri = new ComboBox(optionsTue);

        ObservableList<String> optionsSat = FXCollections.observableArrayList("Nothing Selected",
                "Morning", "Afternoon", "Evening", "All Day", "Not Available");
        ComboBox cboSat = new ComboBox(optionsTue);

        ObservableList<String> optionsSun = FXCollections.observableArrayList("Nothing Selected",
                "Morning", "Afternoon", "Evening", "All Day", "Not Available");
        ComboBox cboSun = new ComboBox(optionsTue);

        // create the comboBox for the animal image/profile pic
        ComboBox<String> comboBox = new ComboBox<>();
        // get values for the combobox
        comboBox.getItems().addAll("nopic", "blow", "bunny", "cat", "fox", "froggy",
                "octopus", "panda", "pup", "tigger");

        //populating volunteer information from the database
        String query = "Select * from volunteer where vusername = '" + loginPage.txtuserName.getText() + "'";
        try {

            sendDBCommand(query);
            int index = 0;
            while (dbResults.next()) {
                txtvolunteerfName.setText(dbResults.getString(3));
                txtvolunteerlName.setText(dbResults.getString(4));
                txtvolunteerStreet.setText(dbResults.getString(7));
                txtZip.setText(dbResults.getString(10));
                txtState.setText(dbResults.getString(9));
                txtCity.setText(dbResults.getString(8));
                txtPhone.setText(dbResults.getString(6));
                txtDOB.setText(dbResults.getString(11));
                txtEmail.setText(dbResults.getString(5));
                txtAPi.setText(dbResults.getString("vpi"));
                txtAExp.setText(dbResults.getString("vexp"));
                cboMon.getSelectionModel().selectFirst();
                cboTue.getSelectionModel().selectFirst();
                cboWed.getSelectionModel().selectFirst();
                cboThur.getSelectionModel().selectFirst();
                cboFri.getSelectionModel().selectFirst();
                cboSat.getSelectionModel().selectFirst();
                cboSun.getSelectionModel().selectFirst();
                //setting combo box defualt profile picture 
                comboBox.getSelectionModel().selectFirst();

            }
            // when click on the btnSave button 
            btnSave.setOnAction(sav -> {
                String sql = "update volunteer set vfname = '" + txtvolunteerfName.getText() + "'"
                        + ", vlname ='" + txtvolunteerlName.getText() + "'"
                        + ", vemail ='" + txtEmail.getText() + "'"
                        + ", vZip ='" + txtZip.getText() + "'"
                        + ", vstreet ='" + txtvolunteerStreet.getText() + "'"
                        + ", vphone ='" + txtPhone.getText() + "'"
                        + ", vstate ='" + txtState.getText() + "'"
                        + ", vcity ='" + txtCity.getText() + "'"
                        + ", vpi ='" + txtAPi.getText() + "'"
                        + ", vexp ='" + txtAExp.getText() + "'"
                        + ", monday ='" + cboMon.getSelectionModel().getSelectedItem().toString() + "'"
                        + ", tuesday ='" + cboTue.getSelectionModel().getSelectedItem().toString() + "'"
                        + ", wednesday ='" + cboWed.getSelectionModel().getSelectedItem().toString() + "'"
                        + ", thursday ='" + cboThur.getSelectionModel().getSelectedItem().toString() + "'"
                        + ", friday ='" + cboFri.getSelectionModel().getSelectedItem().toString() + "'"
                        + ", saturday ='" + cboSat.getSelectionModel().getSelectedItem().toString() + "'"
                        + ", vspecialization ='" + specializationCBO.getSelectionModel().getSelectedItem().toString() + "'"
                        + ", sunday ='" + cboSun.getSelectionModel().getSelectedItem().toString() + "'"
                        + ", vprofilepic ='../Bark/Images/" + comboBox.getSelectionModel().getSelectedItem().toString() + ".png'"
                        + "where vusername ='" + loginPage.txtuserName.getText() + "'";
                FileInputStream barkHeader = null;
                // set the barkheader image for profile picture
                try {
                    barkHeader = new FileInputStream("../Bark/Images/" + comboBox.getSelectionModel().getSelectedItem().toString() + ".png");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(mainVolunteerWindow.class.getName()).log(Level.SEVERE, null, ex);
                }

                socialMediaWindow.profilePicture = new Image(barkHeader);

                sendDBCommand(sql);

            });

        } catch (SQLException sqle) {
            System.out.print("volunteer not found in main volunteer window");
        }

        //Set the cellFactory property
        comboBox.setCellFactory(listview -> new StringImageCell());
        // Set the buttonCell property
        comboBox.setButtonCell(new StringImageCell());
        // add controls to employeePane
        employeePane.add(comboBox, 1, 9);
        employeePane.add(cboMon, 1, 11);
        employeePane.add(cboTue, 1, 12);
        employeePane.add(cboWed, 1, 13);
        employeePane.add(cboThur, 1, 14);
        employeePane.add(cboFri, 1, 15);
        employeePane.add(cboSat, 1, 16);
        employeePane.add(cboSun, 1, 17);
        employeePane.add(specializationCBO, 1, 18);
        employeePane.add(new Label("Specialization"), 0, 18);

        //task---------------------------------------------------------
        taskPane.setAlignment(Pos.CENTER_LEFT);
        taskPane.setVgap(10);
        taskPane.setHgap(10);
        TextArea temp2 = new TextArea();

        Label taskName = new Label("Name");

        TextField txtTaskName = new TextField();

        volunteerName.setFocusTraversable(false);
        //setting the labels 
        taskPane.add(taskName, 0, 0);

        taskPane.add(txtTaskName, 1, 0);

        taskPane.add(temp2, 3, 0, 3, 8);

        //Home --------------------------------------------------------
        adminPane.setVisible(false);
        employeePane.setVisible(false);
        taskPane.setVisible(false);
        tripPane.setVisible(false);
        checkPane.setVisible(false);
        vAdoptPane.setVisible(false);
        eventPane.setVisible(false);
        socialPane.setVisible(true);
        vReportsOverall.setVisible(false);
        vAnimalPane.setVisible(false);

        socialMediaWindow n = new socialMediaWindow();
        n.window();
        socialPane.add(n.pane, 0, 0);

        socialMediaWindow smw = new socialMediaWindow();
        smw.window();
        // when click on the a button. Home----------------------------------
        a.setOnAction(home -> {

            adminPane.setVisible(false);
            //hiding all other panes
            employeePane.setVisible(false);
            taskPane.setVisible(false);
            tripPane.setVisible(false);
            checkPane.setVisible(false);
            vAdoptPane.setVisible(false);
            eventPane.setVisible(false);
            socialPane.setVisible(true);
            vReportsOverall.setVisible(false);
            vAnimalPane.setVisible(false);

        });

        //Volunteer ---------------------------------------------------
        b.setOnAction(em -> {
            employeePane.setVisible(true);
            //hiding all other panes
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            tripPane.setVisible(false);
            checkPane.setVisible(false);
            vAdoptPane.setVisible(false);
            eventPane.setVisible(false);
            socialPane.setVisible(false);
            vReportsOverall.setVisible(false);
            vAnimalPane.setVisible(false);

        });

        //Check In/Out--------------------------------------------------
        clockIn cl = new clockIn();

        cl.start();
        c.setOnAction(check -> {
            employeePane.setVisible(false);
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            tripPane.setVisible(false);
            checkPane.setVisible(true);
            vAdoptPane.setVisible(false);
            eventPane.setVisible(false);
            socialPane.setVisible(false);
            vReportsOverall.setVisible(false);
            vAnimalPane.setVisible(false);
        });

        // Trips--------------------------------------------------------
        tblcLocation.setMinWidth(100);

        tblcTask.setMinWidth(150);

        tblcMileage.setMinWidth(80);

        tblcDuration.setMinWidth(80);
        // set the value for tableview cell
        tblcTask.setCellValueFactory(new PropertyValueFactory<trip, String>("tTask"));
        tblcTask.setPrefWidth(100);
        tblcLocation.setCellValueFactory(new PropertyValueFactory<trip, String>("tLocation"));
        tblcLocation.setPrefWidth(100);

        tblcMileage.setCellValueFactory(new PropertyValueFactory<trip, Integer>("tMileage"));
        tblcMileage.setPrefWidth(60);
        tblcDuration.setCellValueFactory(new PropertyValueFactory<trip, Integer>("tDuration"));
        tblcDuration.setPrefWidth(80);
        tripTable.setItems(tripTableData);
        tripTable.getColumns().addAll(tblcTask, tblcLocation, tblcMileage, tblcDuration);
        // add controls to tripPane
        tripPane.add(lblTitle, 0, 0);
        tripPane.add(lblLocation, 0, 2);
        tripPane.add(lblTask, 0, 1);
        tripPane.add(lblMileage, 0, 3);
        tripPane.add(lblDuration, 0, 4);
        tripPane.add(txtLocation, 1, 2);
        tripPane.add(txtTask, 1, 1);
        tripPane.add(txtMileage, 1, 3);
        tripPane.add(txtDuration, 1, 4);
        tripPane.add(btnAddTrip, 1, 5);
        tripPane.add(tripTable, 2, 1, 1, 6);
        tripPane.setAlignment(Pos.CENTER);
        tripPane.setVgap(5.5);
        tripPane.setHgap(5.5);
        // when click on the btnAddTrip
        btnAddTrip.setOnAction(t -> {
            createTrip();
            viewTrip();
            txtLocation.clear();
            txtTask.clear();
            txtMileage.clear();
            txtDuration.clear();

        });
        tripTableData.clear();
        viewTrip();
        // when click on the d button 
        d.setOnAction(t -> {
            employeePane.setVisible(false);
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            tripPane.setVisible(true);
            checkPane.setVisible(false);
            vAdoptPane.setVisible(false);
            eventPane.setVisible(false);
            socialPane.setVisible(false);
            vReportsOverall.setVisible(false);
            vAnimalPane.setVisible(false);

        });
        //EventTable
        TableView<Event> eventTable = new TableView<>();
        // create buttons
        Button btnSign = new Button("Sign Up");
        Button btnDone = new Button("Done");
        // set the tableColumn name
        TableColumn eName = new TableColumn("Event");
        TableColumn eDate = new TableColumn("Time");
        TableColumn eLocation = new TableColumn("Location");
        TableColumn eDuration = new TableColumn("Duration");
        eName.setMinWidth(150);
        eDate.setMinWidth(130);
        eLocation.setMinWidth(163);
        eDuration.setMinWidth(100);
        // set value for tableview cell
        eName.setCellValueFactory(
                new PropertyValueFactory<Event, String>("eventName"));
        eDate.setCellValueFactory(
                new PropertyValueFactory<Event, String>("eventTime"));
        eLocation.setCellValueFactory(
                new PropertyValueFactory<Event, String>("eventLocation"));
        eDuration.setCellValueFactory(
                new PropertyValueFactory<Event, String>("eventDuration"));
        eventTable.getColumns().addAll(eName, eDate, eLocation, eDuration);
        String time = mainAdminWindow.spinnerHour.getValue() + ":" + mainAdminWindow.spinnerMins.getValue() + ":00";

        try {
            DatePickerSkin calendar = new DatePickerSkin(new DatePicker());
            Node calInfo = calendar.getPopupContent();
            calInfo.setStyle("-fx-pref-width: 600");
            //dimensions for calPane
            ListView<String> taskList = new ListView<String>();
            Text title1 = new Text("Events");
            Text title2 = new Text("Tasks");
            // set size for eventPane 
            eventPane.setMinWidth(800);
            eventPane.setMaxWidth(800);
            eventPane.setMinHeight(500);
            eventPane.setMaxHeight(500);
            // add controls to eventPane
            eventPane.add(title1, 5, 1);
            eventPane.add(title2, 6, 1);
            eventPane.add(eventTable, 5, 3);
            eventPane.add(btnSign, 5, 4);
            eventPane.add(lvTask, 6, 2, 1, 2);
            eventPane.add(btnDone, 6, 4);
            eventPane.setAlignment(Pos.CENTER);
            eventPane.setVgap(10);
            eventPane.setHgap(10);

        } catch (Exception calendar) {
            calendar.printStackTrace();
        }

        viewTask();
        // when click on the btnDone button 
        btnDone.setOnAction(done -> {

            doneTask();
            viewTask();
        });
        // when click on the eventTable selected row 
        eventTable.setOnMouseClicked(e -> {
            btnSign.setOnAction(signupp -> {
                String eid = eventTable.getSelectionModel().getSelectedItem().eventID;
                createVAttendance(eid);

            });

        });
        // create the Calendar
        List<DateCell> dateCells;
        DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        DatePickerContent pop = (DatePickerContent) datePickerSkin.getPopupContent();
        pop.setPrefHeight(390);
        pop.setPrefWidth(600);

        eventPane.add(pop, 5, 2);
        dateCells = getAllDateCells(pop);

        for (DateCell cell : dateCells) {
            cell.addEventHandler(
                    MouseEvent.MOUSE_PRESSED, (e)
                    -> {

                //DB sql statement 
                String SQLquery = "Select * from Event where EDATE = '" + cell.getItem() + "'";

                try {
                    sendDBCommand(SQLquery);
                    mainAdminWindow.eventData.clear();
                    while (dbResults.next()) {
                        // set the dbResults for eventData
                        mainAdminWindow.eventData.add(new Event(
                                dbResults.getString("EID"),
                                dbResults.getString("ENAME"),
                                dbResults.getInt("EMAX"),
                                dbResults.getString("EDATE"),
                                dbResults.getString("ETIME"),
                                dbResults.getString("ELOCATION"),
                                dbResults.getInt("EDURATION")
                        ));
                        eventTable.setItems(mainAdminWindow.eventData);

                    }
                    commStmt.close();
                    dbResults.close();
                } catch (SQLException sqle) {
                    System.out.print(sqle.toString());
                }
                // end of DB

            }
            );

        }
        // when click on the e button 
        e.setOnAction(event -> {
            employeePane.setVisible(false);
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            tripPane.setVisible(false);
            checkPane.setVisible(false);
            vAdoptPane.setVisible(false);
            eventPane.setVisible(true);
            socialPane.setVisible(false);
            vReportsOverall.setVisible(false);
            vAnimalPane.setVisible(false);

        });

        //exit set on action ------------------------------------------------
        f.setOnAction(exit -> {

            adminPrimaryStage.close();
        });
        viewAnimal va = new viewAnimal();
        va.startAnimal();
        // when click on the j button 
        j.setOnAction(ad -> {
            employeePane.setVisible(false);
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            tripPane.setVisible(false);
            checkPane.setVisible(false);
            vAdoptPane.setVisible(true);
            eventPane.setVisible(false);
            socialPane.setVisible(false);
            vReportsOverall.setVisible(false);
            vAnimalPane.setVisible(false);
            va.viewCustomer();
        });
        VolunteerReports vr = new VolunteerReports();
        vr.showReports();
        // when click on the i button 
        i.setOnAction(vro -> {
            employeePane.setVisible(false);
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            tripPane.setVisible(false);
            checkPane.setVisible(false);
            vAdoptPane.setVisible(false);
            eventPane.setVisible(false);
            socialPane.setVisible(false);
            vReportsOverall.setVisible(true);
            vAnimalPane.setVisible(false);

        });

        viewVolunteerAnimal vva = new viewVolunteerAnimal();
        vva.windowAnimal();
        // when click on the k button 
        k.setOnAction(vA -> {
            employeePane.setVisible(false);
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            tripPane.setVisible(false);
            checkPane.setVisible(false);
            vAdoptPane.setVisible(false);
            eventPane.setVisible(false);
            socialPane.setVisible(false);
            vReportsOverall.setVisible(false);
            vAnimalPane.setVisible(true);

        });
        // set the css 
        adminScene.getStylesheets().add("file:../Bark/adminWindowCss.css");
    }

    // create the StringImageCell method
    static class StringImageCell extends ListCell<String> {

        Label label;

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setItem(null);
                setGraphic(null);
            } else {
                setText(item);
                ImageView image = getImageView(item);

                label = new Label("", image);
                setGraphic(label);
            }
        }

    }

    // create the getImageView method
    private static ImageView getImageView(String imageName) {
        ImageView imageView = null;
        switch (imageName) {
            case "blow":
                imageView = new ImageView(new Image("file:images/" + imageName + ".png"));
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                break;
            case "bunny":
                imageView = new ImageView(new Image("file:images/" + imageName + ".png"));
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                break;
            case "cat":
                imageView = new ImageView(new Image("file:images/" + imageName + ".png"));
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                break;
            case "fox":
                imageView = new ImageView(new Image("file:images/" + imageName + ".png"));
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                break;
            case "froggy":
                imageView = new ImageView(new Image("file:images/" + imageName + ".png"));
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                break;
            case "octopus":
                imageView = new ImageView(new Image("file:images/" + imageName + ".png"));
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                break;
            case "panda":
                imageView = new ImageView(new Image("file:images/" + imageName + ".png"));
                imageView.setFitHeight(30);

                imageView.setFitWidth(30);
                break;
            case "pup":
                imageView = new ImageView(new Image("file:images/" + imageName + ".png"));
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                break;
            case "tigger":
                imageView = new ImageView(new Image("file:images/" + imageName + ".png"));
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                break;
            case "nopic":
                imageView = new ImageView(new Image("file:images/nopic.png"));
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                break;
        }
        return imageView;

    }

    // create the createVAttendance method
    public void createVAttendance(String eid) {

        String sql2 = "select vusername from vattendance where eid = '" + eid + "'";

        String testing = "";

        try {
            sendDBCommand(sql2);
            while (dbResults.next()) {
                if (dbResults.getString(1).matches(loginPage.txtuserName.getText())) {
                    testing = "true";
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        if (testing == "true") {

            Alert alreadyReg = new Alert(AlertType.ERROR);
            alreadyReg.setTitle("Error");
            alreadyReg.setHeaderText("Please choose another event");
            alreadyReg.setContentText("You Have Already Registered");
            alreadyReg.showAndWait();
        } else {
            String vaid = UUID.randomUUID().toString().substring(0, 8);
            // create sql statement for vattendance
            String sqlQuery = "";
            sqlQuery += "INSERT INTO VATTENDANCE(VAID, EID, VUSERNAME) VALUES (";
            sqlQuery += "\'" + vaid + "\', ";
            sqlQuery += "\'" + eid + "\', ";
            sqlQuery += "\'" + loginPage.txtuserName.getText() + "\')";
            updateSpots(eid);
            sendDBCommand(sqlQuery);
            Alert good = new Alert(AlertType.CONFIRMATION);
            good.setTitle("Thank You For Registering");

            good.showAndWait();

        }

    }

//    INSERT INTO JAVAUSER.VATTENDANCE (VAID, EID, VUSERNAME) 
//	VALUES ('1', '3dfef54d', 'volunteer');
//    
//    public void signupSuccess() throws SQLException {
//        String query = "Select count(eID) from vAttendance where eID = '" + eventTable.getSelectionModel().getSelectedItem().eventID + "' GROUP BY EID";
//        sendDBCommand(query);
//        int countid = dbResults.getInt(1);
//        System.out.println(countid);
//        if(countid <= eventTable.getSelectionModel().getSelectedItem().maxVolunteers)
//        {
//            Alert acceptance = new Alert(AlertType.INFORMATION);
//                        acceptance.setTitle("Sign Up Confirmation");
//                        acceptance.setHeaderText("You Have Successfully Signed Up!");
//                        acceptance.showAndWait(); 
//        }
//        else
//        {
//            Alert acceptance = new Alert(AlertType.ERROR);
//                        acceptance.setTitle("Sign Up Confirmation");
//                        acceptance.setHeaderText("The Maximum Amount of Volunteers for this Event has been reached");
//                        acceptance.showAndWait(); 
//        }
//    }
    public void createTrip() {

        String ttid = UUID.randomUUID().toString().substring(0, 8);

        String sqlQuery = "";
        sqlQuery += "INSERT INTO trip(TID, TTASK, TLOCATION, TMILEAGE, TDURATION, vusername) VALUES (";
        sqlQuery += "\'" + ttid + "\',";
        sqlQuery += "\'" + txtTask.getText() + "\',";
        sqlQuery += "\'" + txtLocation.getText() + "\',";
        sqlQuery += txtMileage.getText() + ",";
        sqlQuery += txtDuration.getText() + ",";
        sqlQuery += "\'" + loginPage.txtuserName.getText() + "\')";

        sendDBCommand(sqlQuery);
    }

    // Enter values into trip sql table
    public void viewTrip() {
        String query = "Select * from trip";

        try {
            tripTableData.clear();
            sendDBCommand(query);
            while (dbResults.next()) {

                tripTableData.add(new trip(
                        dbResults.getString("TID"),
                        dbResults.getString("TTASK"),
                        dbResults.getString("TLOCATION"),
                        dbResults.getInt("TMILEAGE"),
                        dbResults.getInt("TDURATION"),
                        dbResults.getString("vusername")
                ));
            }
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print("Catch");
        }
    }

    // Edit trip selected
    public void updateTrip() {
        String sqlQuery = "UPDATE trip SET TTASK = \'";
        sqlQuery += txtTask.getText() + "\',";
        sqlQuery += "TLOCATION = \'";
        sqlQuery += txtLocation.getText() + "\',";
        sqlQuery += "TMILEAGE = \'";
        sqlQuery += txtMileage.getText() + "\',";
        sqlQuery += "TDURATION = \'" + txtDuration.getText() + "\',";
        sqlQuery += "vusername = \'" + loginPage.txtuserName.getText() + "\'";
        sqlQuery += "\' WHERE rownum =\'" + tripTable.getSelectionModel().getSelectedIndex() + "\'";
        sendDBCommand(sqlQuery);
    }

    // Delete trip selected
    public void deletetrip() {
        String sqlQuery = "DELETE FROM trip WHERE rownum =\'" + tripTable.getSelectionModel().getSelectedIndex() + "\'";
        sendDBCommand(sqlQuery);
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
            System.out.println(e.toString());
        }

    }
// get date cells from calendar

    private static List<DateCell> getAllDateCells(DatePickerContent cellContent) {
        List<DateCell> selectCell = new ArrayList<>();

        for (Node n : cellContent.getChildren()) {
            System.out.println("node " + n + n.getClass());
            if (n instanceof GridPane) {
                GridPane datepane = (GridPane) n;
                for (Node gChild : datepane.getChildren()) {
//                 System.out.println("grid node: " + gChild + gChild.getClass());
                    if (gChild instanceof DateCell) {
                        selectCell.add((DateCell) gChild);
                    }
                }
            }
        }

        return selectCell;
    }

    // Enter values into Task sql table
    public void viewTask() {
        String query = "Select * from Task";

        try {
            sendDBCommand(query);
            tasksWindow.taskTableData.clear();
            while (dbResults.next()) {

                tasksWindow.taskTableData.add(
                        dbResults.getString("TASKDESC")
                );

                lvTask.setItems((tasksWindow.taskTableData));
            }
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print("Catch");
        }
    }

    // Delete task selected
    public void doneTask() {
        String sqlQuery = "DELETE FROM TASK WHERE taskdesc =\'" + lvTask.getSelectionModel().getSelectedItem() + "\'";
        sendDBCommand(sqlQuery);
    }

    public void updateSpots(String eid) {
        int spotsleft = 0;
        String sql1 = "select spotsleft from event where eid='" + eid + "'";

        try {
            sendDBCommand(sql1);
            while (dbResults.next()) {
                if (dbResults.getInt(1) == 0) {
                    Alert error = new Alert(AlertType.ERROR);
                    error.setTitle("No More Spots Left");
                    error.setHeaderText("Please choose another event");
                    error.showAndWait();
                } else {
                    spotsleft = (dbResults.getInt(1) - 1);
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        String sql3 = "update event set spotsleft =" + spotsleft + "where eid = '" + eid + "'";
        sendDBCommand(sql3);
    }
}
