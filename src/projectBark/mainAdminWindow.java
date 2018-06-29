/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: Contains the main template for admin window after the login
 */
package projectBark;

import adminWindows.viewAdminAnimal;
import adminWindows.adminAdoption;
import adminWindows.adminReports;
import chatApplication.basicChatApplication;
import classes.Event;
import classes.Volunteer;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oracle.jdbc.pool.OracleDataSource;

public class mainAdminWindow {

    TextArea txtSchedule = new TextArea();

    public String name;
    // create buttons
    Button boxOne = new Button("Social");
    Button boxTwo = new Button("Post a Notification");
    Button boxThree = new Button("Create Events");
    Button boxFour = new Button("View Today's Schedule");
    Button boxFive = new Button("Reports");
    Button boxSix = new Button("View Applications");

    Button a = new Button("HOME");
    Button b = new Button("REPORTS");
    Button c = new Button("VOLUNTEERS");
    Button d = new Button("TASKS");
    Button e = new Button("EVENTS");
    Button f = new Button("LOGOUT");
    Button g = new Button("MENU");
    Button h = new Button();
    Button i = new Button("ANIMALS");
    Button j = new Button("ADOPTION");
    Button k = new Button("CHECK IN/OUT");

    //gridpanes
    GridPane mainPane = new GridPane();
    GridPane employeePane = new GridPane();
    GridPane adminPane = new GridPane();
    GridPane taskPane = new GridPane();
    GridPane evtPane = new GridPane();
    public static GridPane adminAdoptPane = new GridPane();
    public static GridPane animalPane = new GridPane();
    public static GridPane reportsOverall = new GridPane();
    public static GridPane tasksOverall = new GridPane();
    public static GridPane socialAPane = new GridPane();
    public static GridPane aCheckPane = new GridPane();
    public static VBox volunteerRequests = new VBox();
    DatePicker eventDatePicker = new DatePicker(LocalDate.now());
    //create volunteer tableview 
    ObservableList<Volunteer> volunteerData = FXCollections.observableArrayList();
    TableView<Volunteer> volunteerTable = new TableView<>();
    public static ObservableList<Event> eventData = FXCollections.observableArrayList();
    // set table cell for the table view
    TableColumn vFnamecol = new TableColumn("First Name");
    TableColumn vLnamecol = new TableColumn("Last Name");
    TableColumn vEmailcol = new TableColumn("Email");
    TableColumn vPhoneNumcol = new TableColumn("Phone");
    TableColumn vStreetcol = new TableColumn("Street");
    TableColumn vCitycol = new TableColumn("City");
    TableColumn vStatecol = new TableColumn("State");
    TableColumn vZipcol = new TableColumn("Zip");
    TableColumn dateOfBirthcol = new TableColumn("DOB");
    TableColumn vStatuscol = new TableColumn("Status");
    TableColumn vSpecializationcol = new TableColumn("Specilization");
    TableColumn evtNameCol = new TableColumn("Event Name");
    TableColumn evtDateCol = new TableColumn("Date");
    TableColumn evtTimeCol = new TableColumn("Time");
    TableColumn evtDurationCol = new TableColumn("Duration");
    TableColumn evtLocationCol = new TableColumn("Location");
    TableColumn volunteerHoursCol = new TableColumn("Total Hours");
    TableColumn<Volunteer, String> categoryCol = new TableColumn<>("vSpecialization");
    TableColumn evtMaxCol = new TableColumn("Max #");
    // create textfield 
    TextField txtEvtName = new TextField();
    TextField txtMaxNum = new TextField();
    TextField txtDuration = new TextField();
    TextField txtLocation = new TextField();
    TableView<Event> eventList = new TableView<Event>();
    public static ListView<String> requestList = new ListView<String>();
    public static ObservableList<String> requestListOBV = FXCollections.observableArrayList();
    TableView<Volunteer> applicationTable = new TableView<>();
    ObservableList<Volunteer> applicationtableData = FXCollections.observableArrayList();
    public static final Spinner<Integer> spinnerHour = new Spinner<Integer>();
    public static final Spinner<Integer> spinnerMins = new Spinner<Integer>();

    ObservableList<String> mentor = FXCollections.observableArrayList();
    ComboBox mentorCBO = new ComboBox(mentor);

    TextField spec = new TextField();

    public void window() {

        updateStatus();
        //adding new applications on the applicant request window 
        returnApplicants();

        //setting the menu right vbox --------------------------------------------------------------
        VBox menuPane = new VBox();
        menuPane.setOpacity(0.86);
        menuPane.setMinWidth(250);
        menuPane.setPadding(new Insets(10, 10, 10, 10));
        //adding all the menu buttons to the menu 
        menuPane.getChildren().addAll(g, a, k, b, c, d, e, i, j, f);
        a.setMinWidth(235);
        b.setMinWidth(235);
        c.setMinWidth(235);
        d.setMinWidth(235);
        e.setMinWidth(235);
        f.setMinWidth(235);
        h.setMinWidth(780);
        g.setMinWidth(235);
        i.setMinWidth(235);
        j.setMinWidth(235);
        k.setMinWidth(235);

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
        menuPane.setOnMouseExited(o
                -> {
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
        Scene adminScene = new Scene(mainPane, 1400, 750);
        adminPane.setPadding(new Insets(10, 10, 10, 10));
        // adding all the panes and setting visible to false
        mainPane.add(adminPane, 1, 0);
        mainPane.add(employeePane, 1, 0);
        mainPane.add(taskPane, 1, 0);
        mainPane.add(evtPane, 1, 0);
        mainPane.add(animalPane, 1, 0);
        mainPane.add(adminAdoptPane, 1, 0);
        mainPane.add(reportsOverall, 1, 0, 2, 1);
        mainPane.add(volunteerRequests, 2, 0);
        mainPane.add(tasksOverall, 1, 0);
        mainPane.add(socialAPane, 1, 0, 3, 1);
        mainPane.add(aCheckPane, 1, 0);

        volunteerRequests.setVisible(true);
        socialAPane.setVisible(false);
        //boxes on the main page
        boxOne.setMinHeight(250);
        boxTwo.setMinHeight(250);
        boxThree.setMinHeight(250);
        boxFour.setMinHeight(250);
        boxFive.setMinHeight(250);
        boxOne.setMinHeight(250);
        boxSix.setMinHeight(250);

        boxOne.setMinWidth(250);
        boxTwo.setMinWidth(250);
        boxThree.setMinWidth(250);
        boxFour.setMinWidth(250);
        boxFive.setMinWidth(250);
        boxOne.setMinWidth(250);
        boxSix.setMinWidth(250);

        adminPane.setVgap(15);
        adminPane.setHgap(15);
        //col row

        adminPane.add(h, 1, 0, 3, 1);

        h.setDisable(true);
        h.setId("top");
        // add boxes to adminPane
        adminPane.add(boxOne, 1, 1);
        boxOne.setId("boxone");
        adminPane.add(boxTwo, 2, 1);
        boxTwo.setId("boxtwo");
        adminPane.add(boxThree, 3, 1);
        boxThree.setId("boxthree");
        adminPane.add(boxFour, 1, 2);
        boxFour.setId("boxfour");
        adminPane.add(boxFive, 2, 2);
        boxFive.setId("boxfive");
        adminPane.add(boxSix, 3, 2);
        boxSix.setId("boxsix");

        //Volunteer form -----------------------------------------------------------
        employeePane.setAlignment(Pos.TOP_LEFT);
        employeePane.setVgap(10);
        employeePane.setHgap(5);
        // ADDING THE LISTVIEW 
        vFnamecol.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vFname"));
        vLnamecol.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vLname"));
        vEmailcol.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vEmail"));
        vPhoneNumcol.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vPhone"));
        vStreetcol.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vStreet"));
        vCitycol.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vCity"));
        vStatecol.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vState"));
        vZipcol.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vZip"));
        dateOfBirthcol.setCellValueFactory(new PropertyValueFactory<Volunteer, Date>("vDOB"));
        vStatuscol.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vStatus"));
        volunteerHoursCol.setCellValueFactory(new PropertyValueFactory<Volunteer, Double>("vTotalHours"));

        vSpecializationcol.setCellValueFactory(new PropertyValueFactory<ComboBox, String>("vSpecialization"));

        volunteerTable.getColumns().addAll(vFnamecol, vLnamecol, vEmailcol, vPhoneNumcol, vStreetcol, vCitycol, vStatecol, vZipcol, dateOfBirthcol, vStatuscol, vSpecializationcol, volunteerHoursCol);

        volunteerTable.setEditable(true);
        //CODE FROM STACKOVERFLOW
        volunteerTable.setRowFactory(row -> new TableRow<Volunteer>() {
            @Override
            public void updateItem(Volunteer item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                } else {
                    //Now 'item' has all the info of the Person in this row
                    if (item.getVStatus().matches("0")) {
                        setStyle("-fx-background-color: tomato;");
                    } else if (item.getVStatus().matches("1")) {

                        setStyle("-fx-background-color: lightsalmon;");

                    } else if (item.getVStatus().matches("2") || item.getVStatus().matches("3") || item.getVStatus().matches("4")) {

                        setStyle("-fx-background-color: lightgreen;");

                    }
                }

            }

        });
        // set the cell value
        vFnamecol.setCellFactory((TextFieldTableCell.forTableColumn()));
        vFnamecol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Volunteer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Volunteer, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setVFname(t.getNewValue());
                //DATABASE HERE 
                String sqlQuery = "UPDATE volunteer SET vfname = \'";
                sqlQuery += t.getNewValue() + "\' where vusername ='" + loginPage.txtuserName.getText() + "'";

                sendDBCommand(sqlQuery);

            }
        });
        // set the cell value
        vLnamecol.setCellFactory((TextFieldTableCell.forTableColumn()));
        vLnamecol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Volunteer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Volunteer, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setVLname(t.getNewValue());
                //DATABASE HERE
                String sqlQuery = "UPDATE volunteer SET vlname = \'";
                sqlQuery += t.getNewValue() + "\' where vusername ='" + loginPage.txtuserName.getText() + "'";

                sendDBCommand(sqlQuery);
            }
        });
        // set the cell value
        vEmailcol.setCellFactory((TextFieldTableCell.forTableColumn()));
        vEmailcol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Volunteer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Volunteer, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setVEmail(t.getNewValue());
                //DATABASE HERE
                String sqlQuery = "UPDATE volunteer SET vemail = \'";
                sqlQuery += t.getNewValue() + "\' where vusername ='" + loginPage.txtuserName.getText() + "'";

                sendDBCommand(sqlQuery);
            }
        });
        // set the cell value
        vPhoneNumcol.setCellFactory((TextFieldTableCell.forTableColumn()));
        vPhoneNumcol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Volunteer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Volunteer, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setVPhone(t.getNewValue());
                //DATABASE HERE
                String sqlQuery = "UPDATE volunteer SET vphone = \'";
                sqlQuery += t.getNewValue() + "\' where vusername ='" + loginPage.txtuserName.getText() + "'";

                sendDBCommand(sqlQuery);
            }
        });
        // set the cell value
        vStreetcol.setCellFactory((TextFieldTableCell.forTableColumn()));
        vStreetcol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Volunteer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Volunteer, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setVStreet(t.getNewValue());
                //DATABASE HERE
                String sqlQuery = "UPDATE volunteer SET vstreet = \'";
                sqlQuery += t.getNewValue() + "\' where vusername ='" + loginPage.txtuserName.getText() + "'";

                sendDBCommand(sqlQuery);
            }
        });
        // set the cell value
        vCitycol.setCellFactory((TextFieldTableCell.forTableColumn()));
        vCitycol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Volunteer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Volunteer, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setVCity(t.getNewValue());
                //DATABASE HERE
                String sqlQuery = "UPDATE volunteer SET vcity = \'";
                sqlQuery += t.getNewValue() + "\' where vusername ='" + loginPage.txtuserName.getText() + "'";

                sendDBCommand(sqlQuery);
            }
        });
        // set the cell value
        vStatecol.setCellFactory((TextFieldTableCell.forTableColumn()));
        vStatecol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Volunteer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Volunteer, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setVState(t.getNewValue());
                //DATABASE HERE
                String sqlQuery = "UPDATE volunteer SET vstate = \'";
                sqlQuery += t.getNewValue() + "\' where vusername ='" + loginPage.txtuserName.getText() + "'";

                sendDBCommand(sqlQuery);
            }
        });
        // set the cell value
        vZipcol.setCellFactory((TextFieldTableCell.forTableColumn()));
        vZipcol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Volunteer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Volunteer, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setVZip(t.getNewValue());
                //DATABASE HERE
                String sqlQuery = "UPDATE volunteer SET vzip = \'";
                sqlQuery += t.getNewValue() + "\' where vusername ='" + loginPage.txtuserName.getText() + "'";

                sendDBCommand(sqlQuery);
            }
        });

        // set the cell value
        vStatuscol.setCellFactory((TextFieldTableCell.forTableColumn()));
        vStatuscol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Volunteer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Volunteer, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setVStatus(t.getNewValue());
                //DATABASE HERE
                String sqlQuery = "UPDATE volunteer SET vStatus = \'";
                sqlQuery += t.getNewValue() + "\' where vusername ='" + loginPage.txtuserName.getText() + "'";

                sendDBCommand(sqlQuery);
            }
        });
        // set the cell value
        vSpecializationcol.setCellFactory((TextFieldTableCell.forTableColumn()));

        vSpecializationcol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Volunteer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Volunteer, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setVSpecialization(t.getNewValue());
                //DATABASE HERE
                String sqlQuery = "UPDATE volunteer SET vSpecialization = \'";
                sqlQuery += t.getNewValue() + "\' where vusername ='" + volunteerTable.getSelectionModel().getSelectedItem().vUserName + "'";
                sendDBCommand(sqlQuery);

                String sqlQuery2 = "INSERT INTO SPECIALIZATION(SPECNAME) VALUES ('" + t.getNewValue() + "')";
                sendDBCommand(sqlQuery2);

            }
        });
        //buttons for volunteers
        Button delete = new Button("Delete Volunteer");

        employeePane.add(volunteerTable, 3, 1, 5, 8);

        employeePane.add(delete, 3, 9);

        employeePane.add(new Label("Add a Specialization"), 3, 10);
        Button add = new Button("Add");

        employeePane.add(spec, 4, 10);
        employeePane.add(add, 5, 10);
        add.setOnAction(t -> {
            mainVolunteerWindow.specialization.add(spec.getText());
            String sqlQuery = "INSERT INTO SPECIALIZATION(SPECNAME) VALUES ('" + spec.getText() + "')";
            sendDBCommand(sqlQuery);
            spec.clear();

        });
        // set the title and scene for stage
        adminPrimaryStage.setTitle("Welcome Back " + loginPage.txtuserName.getText());
        adminPrimaryStage.setScene(adminScene);
        adminPrimaryStage.show();

        delete.setOnAction(de -> {
            deleteVolunteer();
            viewVolunteer();
        });

        //task---------------------------------------------------------
        taskPane.setAlignment(Pos.CENTER_LEFT);
        taskPane.setVgap(10);
        taskPane.setHgap(10);
        TextArea temp2 = new TextArea();
        Label taskName = new Label("Name");
        TextField txtTaskName = new TextField();
        //setting the labels 
        taskPane.add(taskName, 0, 0);
        taskPane.add(txtTaskName, 1, 0);
        taskPane.add(temp2, 3, 0, 3, 8);

        //Home --------------------------------------------------------
        adminPane.setVisible(true);
        employeePane.setVisible(false);
        taskPane.setVisible(false);
        animalPane.setVisible(false);
        adminAdoptPane.setVisible(false);
        reportsOverall.setVisible(false);
        tasksOverall.setVisible(false);
        aCheckPane.setVisible(false);
        // when click on the a button 
        a.setOnAction(home -> {
            adminPane.setVisible(true);
            //hiding all other panes
            employeePane.setVisible(false);
            taskPane.setVisible(false);
            evtPane.setVisible(false);
            adminAdoptPane.setVisible(false);
            animalPane.setVisible(false);
            reportsOverall.setVisible(false);
            volunteerRequests.setVisible(true);
            tasksOverall.setVisible(false);
            socialAPane.setVisible(false);
            aCheckPane.setVisible(false);
        });

        adminReports ar = new adminReports();

        ar.createReports();
        // when click on the b button 
        b.setOnAction(zz -> {
            reportsOverall.setVisible(true);
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            evtPane.setVisible(false);
            adminAdoptPane.setVisible(false);
            animalPane.setVisible(false);
            employeePane.setVisible(false);
            volunteerRequests.setVisible(false);
            tasksOverall.setVisible(false);
            socialAPane.setVisible(false);
            aCheckPane.setVisible(false);

        });

        //Volunteer ---------------------------------------------------
        viewVolunteer();
        c.setOnAction(em -> {
            employeePane.setVisible(true);
            //hiding all other panes
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            evtPane.setVisible(false);
            adminAdoptPane.setVisible(false);
            animalPane.setVisible(false);
            reportsOverall.setVisible(false);
            volunteerRequests.setVisible(false);
            tasksOverall.setVisible(false);
            socialAPane.setVisible(false);
            aCheckPane.setVisible(false);
        });

        //Tasks
        tasksWindow tw = new tasksWindow();
        tw.showTasks();
        d.setOnAction(task -> {
            employeePane.setVisible(false);
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            evtPane.setVisible(false);
            adminAdoptPane.setVisible(false);
            animalPane.setVisible(false);
            reportsOverall.setVisible(false);
            volunteerRequests.setVisible(false);
            tasksOverall.setVisible(true);
            socialAPane.setVisible(false);
            aCheckPane.setVisible(false);
        });

        // Events--------------------------------------------------------
        evtNameCol.setCellValueFactory(new PropertyValueFactory<Event, String>("eventName"));
        evtDateCol.setCellValueFactory(new PropertyValueFactory<Event, String>("eventDate"));
        evtTimeCol.setCellValueFactory(new PropertyValueFactory<Event, String>("eventTime"));
        evtDurationCol.setCellValueFactory(new PropertyValueFactory<Event, Integer>("eventDuration"));
        evtLocationCol.setCellValueFactory(new PropertyValueFactory<Event, String>("eventLocation"));
        evtMaxCol.setCellValueFactory(new PropertyValueFactory<Event, Integer>("maxVolunteers"));

        eventList.getColumns().addAll(evtNameCol, evtDateCol, evtTimeCol, evtDurationCol, evtLocationCol, evtMaxCol);

        eventList.setRowFactory(row -> new TableRow<Event>() {
            @Override
            public void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                } else {

                    String sql = "select spotsleft from event where eid='" + item.eventID + "'";
                    sendDBCommand(sql);
                    try {
                        while (dbResults.next()) {
                            //Now 'item' has all the info of the Person in this row
                            if (dbResults.getInt(1) == 0) {
                                setStyle("-fx-background-color: tomato;");
                            } else if (dbResults.getInt(1) != 0) {
                                setStyle("-fx-background-color: lightgreen;");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        });
        VBox vbText = new VBox();
        eventList.setMinWidth(480);
        Button btnEvent = new Button("Add Event");
        Button btnUpdate = new Button("Update");
        btnEvent.setMinSize(20, 25);

        //Labels and textfields
        Label lblName = new Label("Event Name: ");
        txtEvtName.setMaxWidth(180);
        Label lblDate = new Label("Event Date: ");
        Label lblTime = new Label("Event Time: ");
        Label lblMaxNum = new Label("Maximum Volunteers: ");
        SpinnerValueFactory<Integer> valueHourFactory
                = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 24);
        spinnerHour.setValueFactory(valueHourFactory);
        spinnerHour.setMaxWidth(60);

        SpinnerValueFactory<Integer> valueMinsFactory
                = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59, 00);
        spinnerMins.setValueFactory(valueMinsFactory);
        spinnerMins.setMaxWidth(60);

        Label lblDuration = new Label("Duration");
        txtDuration.setMaxWidth(160);
        Label lblLocation = new Label("Location held: ");
        txtLocation.setMaxWidth(160);

        Text txt1 = new Text(" : ");
        txt1.setFont(Font.font(15));
        Button btnDelete = new Button("Delete Event");
        HBox hb = new HBox();
        hb.getChildren().addAll(spinnerHour, txt1, spinnerMins);

        //add controls to vbText Pane
        vbText.getChildren().addAll(lblName, txtEvtName, lblDate, eventDatePicker, lblTime, hb, lblDuration, txtDuration, lblLocation, txtLocation, lblMaxNum, txtMaxNum, btnEvent);
        vbText.setSpacing(5.5);

        btnEvent.disableProperty().bind(txtEvtName.textProperty().isEmpty());
        btnEvent.disableProperty().bind(txtLocation.textProperty().isEmpty());
        // when click on the btnEvent button 
        btnEvent.setOnAction(e -> {
            eventData.clear();
            createEvent();
            viewEvent();
            txtEvtName.clear();
            txtDuration.clear();
            txtLocation.clear();
            txtMaxNum.clear();

        });
        // when click on the btnUpdate button 
        btnUpdate.setOnAction(up -> {
            updateEvent();
            viewEvent();
        });
        // when click on the btnDelete button 
        btnDelete.setOnAction(del -> {
            deleteEvent();
            viewEvent();
        });

        //dimensions for calPane
        evtPane.setMinWidth(550);
        evtPane.setMinHeight(550);
        evtPane.add(vbText, 2, 2);
        evtPane.add(eventList, 5, 2);
        evtPane.add(btnDelete, 5, 3);

        evtPane.setAlignment(Pos.CENTER);
        evtPane.setVgap(10);
        evtPane.setHgap(10);

        evtPane.setVisible(false);
        viewEvent();
        // when click on the e button 
        e.setOnAction(t -> {
            employeePane.setVisible(false);
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            evtPane.setVisible(true);
            adminAdoptPane.setVisible(false);
            animalPane.setVisible(false);
            reportsOverall.setVisible(false);
            volunteerRequests.setVisible(false);
            tasksOverall.setVisible(false);
            socialAPane.setVisible(false);
            aCheckPane.setVisible(false);
        });

        viewAdminAnimal vaa = new viewAdminAnimal();
        vaa.windowAnimal();
        // when click on the i button 
        i.setOnAction(va -> {
            employeePane.setVisible(false);
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            evtPane.setVisible(false);
            adminAdoptPane.setVisible(false);
            animalPane.setVisible(true);
            reportsOverall.setVisible(false);
            volunteerRequests.setVisible(false);
            tasksOverall.setVisible(false);
            socialAPane.setVisible(false);
            aCheckPane.setVisible(false);
        });

        adminAdoption aa = new adminAdoption();
        aa.windowAdopt();
        // when click on the j button 
        j.setOnAction(adp -> {
            employeePane.setVisible(false);
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            evtPane.setVisible(false);
            adminAdoptPane.setVisible(true);
            animalPane.setVisible(false);
            reportsOverall.setVisible(false);
            volunteerRequests.setVisible(false);
            tasksOverall.setVisible(false);
            socialAPane.setVisible(false);
            aCheckPane.setVisible(false);
        });

        //Check In/Out--------------------------------------------------
        adminClockIn aci = new adminClockIn();
        aci.startClock();
        k.setOnAction(chk -> {
            employeePane.setVisible(false);
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            evtPane.setVisible(false);
            adminAdoptPane.setVisible(false);
            animalPane.setVisible(false);
            reportsOverall.setVisible(false);
            volunteerRequests.setVisible(false);
            tasksOverall.setVisible(false);
            socialAPane.setVisible(false);
            aCheckPane.setVisible(true);
        });

        //exit set on action ------------------------------------------------
        f.setOnAction(exit
                -> {

            adminPrimaryStage.close();
        });
        boxSix.setOnAction(t
                -> {
            adminPrimaryStage.close();

        });

        adminScene.getStylesheets().add("file:../Bark/adminWindowCss.css");

        socialMediaWindow social = new socialMediaWindow();
        social.window();
        socialAPane.add(social.pane, 0, 0);
        // when click on the boxOne button 
        boxOne.setOnAction(o -> {
            employeePane.setVisible(false);
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            evtPane.setVisible(false);
            adminAdoptPane.setVisible(false);
            animalPane.setVisible(false);
            reportsOverall.setVisible(false);
            volunteerRequests.setVisible(false);
            tasksOverall.setVisible(false);
            socialAPane.setVisible(true);
            aCheckPane.setVisible(false);
        });

        //post a notification to the groupchat
        boxTwo.setOnAction(not -> {
            basicChatApplication n = new basicChatApplication();

            n.window();

        });
        // when click on the boxThree button 
        boxThree.setOnAction(t -> {
            employeePane.setVisible(false);
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            evtPane.setVisible(true);
            adminAdoptPane.setVisible(false);
            animalPane.setVisible(false);
            reportsOverall.setVisible(false);
            volunteerRequests.setVisible(false);
            tasksOverall.setVisible(false);
            socialAPane.setVisible(false);
            aCheckPane.setVisible(false);
        });
        // when click on the boxFour button 
        boxFour.setOnAction(e -> {

            Text txtTitle = new Text("Today's Schedule");
            txtTitle.setFont(Font.font("Verdana", 20));
            insertTodaySchedule();

            txtSchedule.setMaxSize(550, 300);
            txtSchedule.setEditable(false);

            GridPane scheduleLayout = new GridPane();

            scheduleLayout.add(txtTitle, 0, 0);
            scheduleLayout.add(txtSchedule, 0, 1);
            scheduleLayout.setAlignment(Pos.CENTER);

            Scene secondScene = new Scene(scheduleLayout, 600, 350);

            Stage secondStage = new Stage();
            secondStage.setTitle("Today's Schedule");
            secondStage.setScene(secondScene);
            secondStage.show();

        });
        // when click on the boxFive button 
        boxFive.setOnAction(b -> {
            reportsOverall.setVisible(true);
            adminPane.setVisible(false);
            taskPane.setVisible(false);
            evtPane.setVisible(false);
            adminAdoptPane.setVisible(false);
            animalPane.setVisible(false);
            employeePane.setVisible(false);
            volunteerRequests.setVisible(false);
            tasksOverall.setVisible(false);
            socialAPane.setVisible(false);
            aCheckPane.setVisible(false);
        });

        // click on View Application button
        returnApplicants();
        // when click on the boxSix button 
        boxSix.setOnAction(t
                -> {

            Stage applicationStage = new Stage();
            GridPane applicationPane = new GridPane();
            applicationPane.setPadding(new Insets(10, 10, 10, 10));
            Scene applicationScene = new Scene(applicationPane, 865, 500);
            applicationStage.setTitle("View Application");
            applicationStage.setScene(applicationScene);
            applicationStage.show();
            applicationScene.getStylesheets().add("file:../Bark/adminWindowCss.css");
            applicationPane.setHgap(10);
            applicationPane.setVgap(10);

            //setting the labels 
            TableColumn tblcFName = new TableColumn("First Name");
            tblcFName.setMinWidth(100);
            TableColumn tblcLName = new TableColumn("Last Name");
            tblcLName.setMinWidth(100);
            TableColumn tblcPhone = new TableColumn("Phone Number");
            tblcPhone.setMinWidth(100);
            TableColumn tblcEmail = new TableColumn("Email");
            tblcEmail.setMinWidth(60);
            TableColumn tblcStreet = new TableColumn("Street");
            tblcStreet.setMinWidth(80);
            TableColumn tblcCity = new TableColumn("City");
            tblcCity.setMinWidth(80);
            TableColumn tblcState = new TableColumn("State");
            tblcState.setMinWidth(80);
            TableColumn tblcZip = new TableColumn("Zip");
            tblcZip.setMinWidth(80);
            TableColumn tblcDob = new TableColumn("Date of Birth");
            tblcDob.setMinWidth(80);
            TableColumn tblcUsername = new TableColumn("Username");
            tblcUsername.setMinWidth(80);
            // set values for the table cell
            tblcFName.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vFname"));
            tblcFName.setPrefWidth(100);
            tblcLName.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vLname"));
            tblcLName.setPrefWidth(100);
            tblcPhone.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vPhone"));
            tblcPhone.setPrefWidth(100);
            tblcEmail.setCellValueFactory(new PropertyValueFactory<Volunteer, Integer>("vEmail"));
            tblcEmail.setPrefWidth(60);
            tblcStreet.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vStreet"));
            tblcStreet.setPrefWidth(80);
            tblcCity.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vCity"));
            tblcCity.setPrefWidth(80);
            tblcState.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vState"));
            tblcState.setPrefWidth(80);
            tblcZip.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vZip"));
            tblcZip.setPrefWidth(80);
            tblcDob.setCellValueFactory(new PropertyValueFactory<Volunteer, Date>("vDOB"));
            tblcDob.setPrefWidth(80);
            tblcUsername.setCellValueFactory(new PropertyValueFactory<Volunteer, String>("vUserName"));
            tblcUsername.setPrefWidth(80);

            applicationTable.setItems(applicationtableData);
            applicationTable.getColumns().addAll(tblcFName, tblcLName, tblcPhone,
                    tblcEmail, tblcStreet, tblcCity, tblcState, tblcZip, tblcDob, tblcUsername);

            Button btnAcceptApp = new Button("Accept Application");
            populateMentor();
            // add controls to pane
            applicationPane.add(applicationTable, 0, 1, 3, 1);
            applicationPane.add(new Label("Mentor: "), 0, 2);
            applicationPane.add(mentorCBO, 1, 2);
            applicationPane.add(btnAcceptApp, 2, 2);
            applicationPane.setVgap(8.5);
            // when click on the btnAcceptApp button 
            btnAcceptApp.setOnAction(ac -> {
                try {
                    if (!applicationTable.getSelectionModel().isEmpty() && !mentorCBO.getSelectionModel().isEmpty()) {
                        Volunteer n = applicationTable.getSelectionModel().getSelectedItem();
                        String sqlQuery = "update JAVAUSER.VOLUNTEER set vstatus = 1 where vusername ='" + n.vUserName + "'";
                        sendDBCommand(sqlQuery);

                        applicationtableData.remove(applicationTable.getSelectionModel().getSelectedItem());

                        System.out.println(sqlQuery);
                        //needs to be done, havent figured it out
                        String username = mentorCBO.getSelectionModel().getSelectedItem().toString();
                        String[] split = username.split(":");
                        String secondSubString = split[1];

                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Please ensure Applicant and Mentor is chosen");
                        alert.setHeaderText("Error");
                        alert.setContentText("Select Applicant and Mentor");
                        alert.showAndWait();
                    }
                } catch (Exception m) {
                    // set alert
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Please ensure Applicant and Mentor is chosen");
                    alert.setHeaderText("Error");
                    alert.setContentText("Select Applicant and Mentor");
                    alert.showAndWait();
                }

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

    // create viewVolunteer method
    public void viewVolunteer() {
        String sqlQuery = "select * from volunteer";
        try {
            sendDBCommand(sqlQuery);
            volunteerData.clear();
            while (dbResults.next()) {
                volunteerData.add(new Volunteer(
                        dbResults.getString("vusername"),
                        dbResults.getString("vpassword"),
                        dbResults.getString("vfname"),
                        dbResults.getString("vlname"),
                        dbResults.getString("vemail"),
                        dbResults.getString("vphone"),
                        dbResults.getString("vstreet"),
                        dbResults.getString("vcity"),
                        dbResults.getString("vState"),
                        dbResults.getString("vzip"),
                        dbResults.getDate("vdob"),
                        dbResults.getString("vexp"),
                        dbResults.getString("monday"),
                        dbResults.getString("tuesday"),
                        dbResults.getString("wednesday"),
                        dbResults.getString("thursday"),
                        dbResults.getString("friday"),
                        dbResults.getString("saturday"),
                        dbResults.getString("sunday"),
                        dbResults.getString("vprofilepic"),
                        dbResults.getString("vspecialization"),
                        dbResults.getString("vstatus"),
                        dbResults.getDouble("vtotalhours")
                ));
                adminReports.obTripList.add(dbResults.getString("vusername"));
                adminReports.cbVName.setItems(adminReports.obTripList);
                volunteerTable.setItems(volunteerData);
            }
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print(sqle.toString());
        }
    }

    // crate deleteVolunteer
    public void deleteVolunteer() {
        if (volunteerTable.getSelectionModel().getSelectedItem().vUserName.matches(loginPage.txtuserName.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Delete");
            alert.setContentText("You can't delete yourself");
            alert.showAndWait();
        } else {
            String sqlQuery = "DELETE FROM Volunteer WHERE vusername = '"
                    + volunteerTable.getSelectionModel().getSelectedItem().vUserName + "'";
            sendDBCommand(sqlQuery);
        }
    }

    // create returnApplicants
    public void returnApplicants() {
        try {
            String sqlQuery = "select * from volunteer where vstatus = 0";
            sendDBCommand(sqlQuery);
            try {

                applicationtableData.clear();
                while (dbResults.next()) {
                    // get the volunteer info from the database
                    applicationtableData.add(new Volunteer(
                            dbResults.getString("vusername"),
                            dbResults.getString("vpassword"),
                            dbResults.getString("vfname"),
                            dbResults.getString("vlname"),
                            dbResults.getString("vemail"),
                            dbResults.getString("vphone"),
                            dbResults.getString("vstreet"),
                            dbResults.getString("vcity"),
                            dbResults.getString("vState"),
                            dbResults.getString("vzip"),
                            dbResults.getDate("vdob"),
                            dbResults.getString("vexp"),
                            dbResults.getString("monday"),
                            dbResults.getString("tuesday"),
                            dbResults.getString("wednesday"),
                            dbResults.getString("thursday"),
                            dbResults.getString("friday"),
                            dbResults.getString("saturday"),
                            dbResults.getString("sunday"),
                            dbResults.getString("vprofilepic"),
                            dbResults.getString("vspecialization"),
                            dbResults.getString("vstatus"))
                    );

                }
                commStmt.close();
                dbResults.close();
            } catch (SQLException sqle) {
                System.out.print(sqle.toString());
            }

        } catch (Exception m) {
            System.out.print(m.toString());
        }

    }

    public void createEvent() {
        String eid = UUID.randomUUID().toString().substring(0, 8);
        String sqlQuery = "";
        String time = "";
        if (spinnerMins.getValue() <= 10) {
            time = "0";
        }
        // write the sql statement for event table
        sqlQuery += "INSERT INTO Event(EID, ENAME, EMAX, EDATE, ETIME, ELOCATION, EDURATION, spotsleft) VALUES (";
        sqlQuery += "\'" + eid + "\',";
        sqlQuery += "\'" + txtEvtName.getText() + "\',";
        sqlQuery += txtMaxNum.getText() + ",";
        sqlQuery += "\'" + eventDatePicker.getValue() + "\',";
        sqlQuery += "\'" + spinnerHour.getValue().toString() + ":" + time + spinnerMins.getValue().toString() + "\',";
        sqlQuery += "\'" + txtLocation.getText() + "\',";
        sqlQuery += "\'" + txtDuration.getText() + "\',";
        sqlQuery += txtMaxNum.getText() + ")";

        sendDBCommand(sqlQuery);
    }

    // create viewEvent method
    public void viewEvent() {
        String query = "Select * from Event";

        try {
            sendDBCommand(query);
            eventData.clear();
            while (dbResults.next()) {
                adminReports.obEventList.add(dbResults.getString("ENAME"));
                eventData.add(new Event(
                        dbResults.getString("EID"),
                        dbResults.getString("ENAME"),
                        dbResults.getInt("EMAX"),
                        dbResults.getString("EDATE"),
                        dbResults.getString("ETIME"),
                        dbResults.getString("ELOCATION"),
                        dbResults.getInt("EDURATION")
                ));
                eventList.setItems(eventData);
                adminReports.cbEvent.setItems(adminReports.obEventList);
                VolunteerReports.cbVEvent.setItems(adminReports.obEventList);
            }
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    // Edit Event selected
    public void updateEvent() {
        String sqlQuery = "UPDATE EVENT SET ENAME = \'";
        sqlQuery += txtEvtName.getText() + "\',";
        sqlQuery += "EMAX = ";
        sqlQuery += txtMaxNum.getText() + ",";
        sqlQuery += "EDATE = \'";
        sqlQuery += eventDatePicker.getValue() + "\',";
        sqlQuery += "ETIME = \'";
        sqlQuery += spinnerHour.getValue().toString() + ":" + spinnerMins.getValue().toString() + "\',";
        sqlQuery += "ELOCATION = \'" + txtLocation.getText() + "\',";
        sqlQuery += "EDURATION = " + txtDuration.getText() + " WHERE eid = "
                + eventList.getSelectionModel().getSelectedItem().eventID;
        sendDBCommand(sqlQuery);
    }

    // Delete Event selected
    public void deleteEvent() {
        int index = eventList.getSelectionModel().getSelectedIndex() + 1;
        String sqlQuery = "DELETE FROM EVENT WHERE eid = '" + eventList.getSelectionModel().getSelectedItem().eventID + "'";
        sendDBCommand(sqlQuery);
    }

    // create populate mentor method
    public void populateMentor() {
        String query = "Select * from volunteer where vstatus = 3";
        try {
            sendDBCommand(query);
            mentor.clear();
            while (dbResults.next()) {
                mentor.add(dbResults.getString(3) + "," + dbResults.getString(4) + " - Username:" + dbResults.getString(1));
            }

            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print(sqle.toString());
        }
    }

    // create insertTodaySchedule
    public void insertTodaySchedule() {
        try {
            String name = "";
            String time = "";
            String in = "";
            String dats = "";
            Calendar calendar = Calendar.getInstance();
            java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sqlQuery = "select * from event where edate='" + sdf.format(date) + "'";
            sendDBCommand(sqlQuery);
            try {

                while (dbResults.next()) {
                    name = dbResults.getString(2).toString();
                    time = dbResults.getString(5);
                    dats = dbResults.getString(4);
                    in = dbResults.getString(3);
                    txtSchedule.appendText(name + " in " + time + " - " + dats + " Volunteers needed: " + in + "\n");

                }

            } catch (SQLException sqle) {
                System.out.print(sqle.toString());
            }

        } catch (Exception m) {
            System.out.print(m.toString());
        }
    }

    // create update Status method
    public void updateStatus() {
        String query = "update volunteer set vstatus = 2 where vusername=(select vusername from volunteer where vtotalhours >= 20 and vstatus <> 3)";
        sendDBCommand(query);
    }

}
