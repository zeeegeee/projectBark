/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: Contains the ability to clockin and clockout for the admin. 
 */
package projectBark;

import java.util.*;
import java.text.*;
import javafx.animation.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.cell.PropertyValueFactory;
import oracle.jdbc.pool.OracleDataSource;
import classes.Clock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class adminClockIn {
//Parts of this code was received from an outside source (https://gist.github.com/jewelsea/2658491)
    Connection dbConn;
    Statement commStmt;
    ResultSet dbResults;
    final ObservableList timeData = FXCollections.observableArrayList();
    public static Group ticks = new Group();
    public static final Line hourHand = new Line(0, 0, 0, -50);
    public static final Line minuteHand = new Line(0, 0, 0, -75);
    public static final Line secondHand = new Line(0, 15, 0, -88);
    public static final Circle spindle = new Circle(100, 100, 5);
    public static final Group analogueClock = new Group(ticks, spindle, hourHand, minuteHand, secondHand);

    // Controls
    Label currentTime = new Label();
    Label currentDate = new Label();
    Label lblTotal = new Label("Total Hours Volunteered");
    Label lblHrs = new Label();
    Button btnchkin = new Button("Check In");
    Button btnchkout = new Button("Check Out");
    TableView<String> clockTable = new TableView<>();
    static final Text digitalClock = new Text();

    public void startClock() {
        updateVolunteerHours();
        timeData.clear();
        viewClockIn();
        mainAdminWindow.aCheckPane.setAlignment(Pos.CENTER);
        mainAdminWindow.aCheckPane.setMinWidth(600);
        mainAdminWindow.aCheckPane.setVgap(5.5);
        mainAdminWindow.aCheckPane.setHgap(5.5);
        mainAdminWindow.aCheckPane.setPadding(new Insets(10, 10, 30, 10));
        HBox hbButton = new HBox();
        hbButton.setSpacing(4);
        hbButton.getChildren().addAll(btnchkin, btnchkout);
        mainAdminWindow.aCheckPane.add(clockTable, 5, 5);
        mainAdminWindow.aCheckPane.add(hbButton, 5, 2);
        mainAdminWindow.aCheckPane.add(lblTotal, 5, 6);
        mainAdminWindow.aCheckPane.add(lblHrs, 5, 7);
        clockTable.setMinWidth(550);

        TableColumn tblcDate = new TableColumn("Date");
        TableColumn tblcChkIn = new TableColumn("Check In Time");
        TableColumn tblcChkOut = new TableColumn("Check Out Time");

        tblcDate.setCellValueFactory(new PropertyValueFactory<Clock, String>("cDate"));
        tblcChkIn.setCellValueFactory(new PropertyValueFactory<Clock, String>("TimeIn"));
        tblcChkOut.setCellValueFactory(new PropertyValueFactory<Clock, String>("TimeOut"));
        clockTable.getColumns().addAll(tblcDate, tblcChkIn, tblcChkOut);

        tblcDate.setMinWidth(150);
        tblcChkIn.setMinWidth(150);
        tblcChkOut.setMinWidth(150);

        hourHand.setTranslateX(100);
        hourHand.setTranslateY(100);
        hourHand.setId("hourHand");

        minuteHand.setTranslateX(100);
        minuteHand.setTranslateY(100);
        minuteHand.setId("minuteHand");

        secondHand.setTranslateX(100);
        secondHand.setTranslateY(100);
        secondHand.setId("secondHand");

        spindle.setId("spindle");

        for (int i = 0; i < 12; i++) {
            Line tick = new Line(0, -83, 0, -93);
            tick.setTranslateX(100);
            tick.setTranslateY(100);
            tick.getStyleClass().add("tick");
            tick.getTransforms().add(new Rotate(i * (360 / 12)));
            ticks.getChildren().add(tick);
        }

        // construct the digitalClock pieces.
        digitalClock.setId("digitalClock");
        digitalClock.setStyle("-fx-font: 16 arial;");
        // determine the starting time.
        Calendar calendar = GregorianCalendar.getInstance();
        final double seedSecondDegrees = calendar.get(Calendar.SECOND) * (360 / 60);
        final double seedMinuteDegrees = (calendar.get(Calendar.MINUTE) + seedSecondDegrees / 360.0) * (360 / 60);
        final double seedHourDegrees = (calendar.get(Calendar.HOUR) + seedMinuteDegrees / 360.0) * (360 / 12);

        // define rotations to map the analogueClock to the current time.
        final Rotate hourRotate = new Rotate(seedHourDegrees);
        final Rotate minuteRotate = new Rotate(seedMinuteDegrees);
        final Rotate secondRotate = new Rotate(seedSecondDegrees);
        hourHand.getTransforms().add(hourRotate);
        minuteHand.getTransforms().add(minuteRotate);
        secondHand.getTransforms().add(secondRotate);

        // the hour hand rotates twice a day.
        final Timeline hourTime = new Timeline(
                new KeyFrame(
                        Duration.hours(12),
                        new KeyValue(
                                hourRotate.angleProperty(),
                                360 + seedHourDegrees,
                                Interpolator.LINEAR
                        )
                )
        );

        // the minute hand rotates once an hour.
        final Timeline minuteTime = new Timeline(
                new KeyFrame(
                        Duration.minutes(60),
                        new KeyValue(
                                minuteRotate.angleProperty(),
                                360 + seedMinuteDegrees,
                                Interpolator.LINEAR
                        )
                )
        );

        // move second hand rotates once a minute.
        final Timeline secondTime = new Timeline(
                new KeyFrame(
                        Duration.seconds(60),
                        new KeyValue(
                                secondRotate.angleProperty(),
                                360 + seedSecondDegrees,
                                Interpolator.LINEAR
                        )
                )
        );

        // the digital clock updates once a second.
        final Timeline digitalTime = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Calendar calendar = GregorianCalendar.getInstance();
                        String hourString = pad(2, '0', calendar.get(Calendar.HOUR) == 0 ? "12" : calendar.get(Calendar.HOUR) + "");
                        String minuteString = pad(2, '0', calendar.get(Calendar.MINUTE) + "");
                        String secondString = pad(2, '0', calendar.get(Calendar.SECOND) + "");
                        String ampmString = calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
                        digitalClock.setText(hourString + ":" + minuteString + ":" + secondString + " " + ampmString);
                    }
                }
                ),
                new KeyFrame(Duration.seconds(1))
        );

        // time never ends.
        hourTime.setCycleCount(Animation.INDEFINITE);
        minuteTime.setCycleCount(Animation.INDEFINITE);
        secondTime.setCycleCount(Animation.INDEFINITE);
        digitalTime.setCycleCount(Animation.INDEFINITE);

        // start the analogueClock.
        digitalTime.play();
        secondTime.play();
        minuteTime.play();
        hourTime.play();

        // layout the scene.
        final VBox layout = new VBox();
        layout.getChildren().addAll(analogueClock, digitalClock);
        layout.setAlignment(Pos.CENTER);

        mainAdminWindow.aCheckPane.add(layout, 5, 0);

        btnchkin.setOnAction(checkin
                -> {

            String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
            timeData.clear();
            createClockIn(timeStamp);
            viewClockIn();
        });

        btnchkout.setOnAction(checkout
                -> {
            String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

            timeData.clear();
            updateClockIn(timeStamp);
            viewClockIn();
            lblHrs.setText("" + hours());
            updateVolunteerHours();

        });

    }

    private String pad(int fieldWidth, char padChar, String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length(); i < fieldWidth; i++) {
            sb.append(padChar);
        }
        sb.append(s);

        return sb.toString();
    }

    public void sendDBCommand(String sqlQuery) {
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        String userID = "javauser";
        String userPASS = "javapass";
        OracleDataSource ds;

        System.out.println(sqlQuery);

        try {
            ds = new OracleDataSource();
            ds.setURL(URL);
            dbConn = ds.getConnection(userID, userPASS);
            commStmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbResults = commStmt.executeQuery(sqlQuery);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

    Calendar calendar = Calendar.getInstance();
    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

    public void createClockIn(String timeIn) {
        String sqlQuery = "";
        String clockID = UUID.randomUUID().toString().substring(0, 8);
        sqlQuery += "INSERT INTO ClockIn(CLOCKID, CDATE, TIMEIN, TIMEOUT, VUSERNAME) VALUES (";
        sqlQuery += "\'" + clockID + "\',";
        sqlQuery += "\'" + String.valueOf(sdf.format(date)).substring(0, 10) + "\',";
        sqlQuery += "\'" + timeIn + "\',";
        sqlQuery += "''" + ",";
        sqlQuery += "\'" + loginPage.txtuserName.getText() + "\')";
        sendDBCommand(sqlQuery);
    }

    public void updateClockIn(String timeout) {
        String sqlQ1 = "UPDATE clockIn SET TIMEOUT = '" + timeout + "' WHERE timein = (SELECT max(timein) FROM clockin)";
        sendDBCommand(sqlQ1);
    }

    // Enter values into Clock In sql table
    public void viewClockIn() {
        String query = "Select * from ClockIn where vusername = \'" + loginPage.txtuserName.getText() + "\' order by timein asc";

        try {
            sendDBCommand(query);
            while (dbResults.next()) {

                timeData.add(new Clock(
                        dbResults.getString("ClockID"),
                        dbResults.getString("cDate"),
                        dbResults.getString("TimeIn"),
                        dbResults.getString("TimeOut"),
                        dbResults.getString("VUSERNAME")
                ));
                clockTable.setItems(timeData);
            }
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print(sqle.toString());
        }
    }

    // Get total hours of volunteering
    public double hours() {
        String sqlQuery = "select * from clockin where vusername ='" + loginPage.txtuserName.getText() + "' and timeout <> '" + null + "'";
        sendDBCommand(sqlQuery);
        double totalHours = 0.0;
        try {
            while (dbResults.next()) {
                String timeIn = dbResults.getString("TimeIn");
                String timeOut = dbResults.getString("TimeOut");
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                Date date1 = (Date) sdf.parse(timeIn);
                System.out.print(date1);
                Date date2 = (Date) sdf.parse(timeOut);
                System.out.print(date2);
                totalHours += ((date2.getTime() - date1.getTime()) / (1000.0 * 3600.0));

            }
        } catch (SQLException sqle) {
            System.out.print(sqle.toString());
        } catch (ParseException ex) {
            Logger.getLogger(clockIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Math.round(totalHours * 4) / 4f;
    }

    // Update total hours of volunteering
    public void updateVolunteerHours() {
        String sqlQuery = "";
        sqlQuery = "update volunteer set vtotalhours =" + hours() + "where vusername = '" + loginPage.txtuserName.getText() + "' and timeout <> '" + null + "'";
        sendDBCommand(sqlQuery);
    }

}
