/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: ADMIN reports page
 */
package adminWindows;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import oracle.jdbc.pool.OracleDataSource;
import projectBark.mainAdminWindow;

public class adminReports {

    GridPane animalPane = new GridPane();
    GridPane donationPane = new GridPane();
    GridPane tripPane = new GridPane();
    GridPane purchasePane = new GridPane();
    GridPane animalTypePane = new GridPane();
    GridPane eventPane = new GridPane();

    TabPane tbPane = new TabPane();
    Tab tab1 = new Tab("Volunteer Report");
    Tab tab2 = new Tab("Animal Report");
    Tab tab3 = new Tab("Event Report");
    Tab tab4 = new Tab("Donation Report");
    Tab tab5 = new Tab("Trip Report");
    Tab tab6 = new Tab("Purchase Report");

    PieChart animalpie = new PieChart();
    PieChart volunteerPie = new PieChart();
    PieChart animalTypePie = new PieChart();

    TextArea txtADonation = new TextArea();
    TextArea txtATrip = new TextArea();
    public static TextArea txtAPurchase = new TextArea();
    TextArea txtEvent = new TextArea();
    Button btnView = new Button("View");
    Button btnViewEvent = new Button("View");
    public static ComboBox cbVName = new ComboBox();
    public static ComboBox<String> cbEvent = new ComboBox();
    final NumberAxis xxAxis = new NumberAxis();
    final NumberAxis yyAxis = new NumberAxis();

    // Reflect all of data to report
    ObservableList obBars = FXCollections.observableArrayList();
    public static ObservableList obTripList = FXCollections.observableArrayList();
    public static ObservableList obEventList = FXCollections.observableArrayList();
    public static ObservableList<PieChart.Data> obAnimalType = FXCollections.observableArrayList();

    // Class to create report for Admin
    public void createReports() {
        viewDonation();
        calcDonation();
        mainAdminWindow.reportsOverall.setAlignment(Pos.TOP_LEFT);
        mainAdminWindow.reportsOverall.setPadding(new Insets(10, 10, 10, 10));
        tbPane.setMinHeight(700);
        tbPane.setMinWidth(1100);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);

        bc.setTitle("Country Summary");
        yAxis.setLabel("Number of animals");

        tab1.setContent(volunteerPie);
        volunteerPie.setTitle("Volunteers based on status");

        PieChart.Data slice1 = new PieChart.Data("Applicants: " + numberOfVolunteersBasedOnStatus("applicant"), numberOfVolunteersBasedOnStatus("applicant"));
        PieChart.Data slice2 = new PieChart.Data("Volunteers in training: " + numberOfVolunteersBasedOnStatus("volunteerinTraining"), numberOfVolunteersBasedOnStatus("volunteerinTraining"));
        PieChart.Data slice3 = new PieChart.Data("Volunteers: " + numberOfVolunteersBasedOnStatus("volunteer"), numberOfVolunteersBasedOnStatus("volunteer"));
        PieChart.Data slice4 = new PieChart.Data("Admins: " + numberOfVolunteersBasedOnStatus("admin"), numberOfVolunteersBasedOnStatus("admin"));
        volunteerPie.getData().add(slice1);
        volunteerPie.getData().add(slice2);
        volunteerPie.getData().add(slice3);
        volunteerPie.getData().add(slice4);

        //pane for animal on the animal tab for reports---------------------------------------
        animalPane = new GridPane();

        tab2.setContent(animalPane);
        animalPane.add(animalpie, 0, 0);
        animalPane.add(animalTypePie, 1, 0);
        animalpie.setTitle("Animal Status Distribution");
        PieChart.Data S1 = new PieChart.Data("Animals Adopted " + animalsAdopted(1), animalsAdopted(1));
        PieChart.Data S2 = new PieChart.Data("Animals in Shelter: " + animalsAdopted(0), animalsAdopted(0));
        animalpie.getData().add(S1);
        animalpie.getData().add(S2);
        animalTypePie.setTitle("All Animal Species That were in the shelter");
        animalTypePie.setData(obAnimalType);
        animalTypePie.setVisible(true);

        //pane for event on the event tab for reports---------------------------------------
        tab3.setContent(eventPane);
        eventPane.add(txtEvent, 0, 0, 3, 1);
        eventPane.add(cbEvent, 1, 1);
        eventPane.add(btnViewEvent, 0, 1);
        eventPane.setVgap(5);
        eventPane.setHgap(5);
        txtEvent.setMinSize(550, 600);
        txtEvent.setEditable(false);
        btnViewEvent.setOnAction(e -> {
            String n = cbEvent.getSelectionModel().getSelectedItem();
            System.out.println(n);
            viewEventReport(n);
        });

        //pane for donation on the donation tab for reports---------------------------------------
        tab4.setContent(donationPane);
        donationPane.add(txtADonation, 0, 0);
        txtADonation.setMinSize(600, 700);
        txtADonation.setEditable(false);

        //pane for trip on the trip tab for reports---------------------------------------
        tab5.setContent(tripPane);
        tripPane.add(txtATrip, 0, 0, 3, 1);
        tripPane.add(cbVName, 1, 1);
        tripPane.add(btnView, 0, 1);
        tripPane.setHgap(5.5);
        txtATrip.setMinSize(550, 600);
        txtATrip.setEditable(false);
        btnView.setOnAction(v -> {
            txtATrip.clear();
            viewTripReport();
        });

        //pane for purchase on the purchase tab for reports---------------------------------------
        tab6.setContent(purchasePane);
        purchasePane.add(txtAPurchase, 0, 0);
        txtAPurchase.setMinSize(600, 700);
        txtAPurchase.setEditable(false);

        tab1.setClosable(false);
        tab2.setClosable(false);
        tab3.setClosable(false);
        tab4.setClosable(false);
        tab5.setClosable(false);
        tab6.setClosable(false);

        tbPane.getTabs().add(tab1);
        tbPane.getTabs().add(tab2);
        tbPane.getTabs().add(tab3);
        tbPane.getTabs().add(tab4);
        tbPane.getTabs().add(tab5);
        tbPane.getTabs().add(tab6);

        mainAdminWindow.reportsOverall.add(tbPane, 0, 0);

        animalPane.setVgap(10);
        donationPane.setVgap(10);
        tripPane.setVgap(10);
        purchasePane.setVgap(10);
        animalTypePane.setVgap(10);
        eventPane.setVgap(10);

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

    // Get the date for purchased(adopted) animal
    public int animalsAdopted(int status) {
        int num = 0;
        String query = "Select adoptedstatus from animal";
        try {
            sendDBCommand(query);

            while (dbResults.next()) {
                switch (status) {
                    case 1:

                        if (dbResults.getString(1).matches("1")) {
                            num++;
                        }
                        break;
                    case 0:
                        if (dbResults.getString(1).matches("0")) {
                            num++;
                        }
                        break;
                }
            }
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print(sqle.toString());
        }
        return num;
    }

    //report showing the number of volunteers based on status
    public int numberOfVolunteersBasedOnStatus(String volunteerType) {
        String query = "Select vstatus from volunteer";
        int num = 0;

        try {
            sendDBCommand(query);

            while (dbResults.next()) {
                switch (volunteerType) {
                    case "applicant":
                        if (dbResults.getString(1).matches("0")) {
                            num++;
                        }
                        break;
                    case "volunteerinTraining":
                        if (dbResults.getString(1).matches("1")) {
                            num++;
                        }
                        break;
                    case "volunteer":
                        if (dbResults.getString(1).matches("2")) {
                            num++;
                        }
                        break;
                    case "admin":
                        if (dbResults.getString(1).matches("3")) {
                            num++;
                        }
                        break;

                }
            }

            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print(sqle.toString());
        }
        return num;
    }

    // View Donation report
    public void viewDonation() {
        String query = "Select * from donor";
        String output = "";
        try {
            sendDBCommand(query);
            while (dbResults.next()) {
                output += "Category: " + dbResults.getString("DCATEGORY") + "\t\t\t\t\t\t\t\t\t\t\t";
                output += "Amount: $" + dbResults.getString("DAMOUNT") + ".00\n";
                output += "Donor Contact Information: \n";
                output += "Name: " + dbResults.getString("DNAME") + "\n";
                output += "Phone: " + dbResults.getString("DPHONE") + "\n";
                output += "Address: " + dbResults.getString("DADDRESS") + ", " + dbResults.getString("DCITY") + ", " + dbResults.getString("DSTATE") + dbResults.getString("DZIP") + "\n\n\n";
            }
            txtADonation.appendText("\nDonations History \n ------------------------------------------------------------------------------------------------------------------------ \n\n" + output);
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print("Catch");
        }
    }

    // Calculate total donation amount
    public void calcDonation() {
        String query = "Select damount from donor";
        double total = 0;
        try {
            sendDBCommand(query);
            while (dbResults.next()) {
                total += dbResults.getInt("damount");
            }
            txtADonation.appendText("Total Donation Amount: $" + total + "0");
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print(sqle.toString());
        }
    }

    // View Trip Report
    public void viewTripReport() {
        String query = "Select * from trip where vusername = '" + cbVName.getSelectionModel().getSelectedItem() + "'";
        String output = "";
        double mileage = 0;
        String name = "";
        try {
            sendDBCommand(query);

            while (dbResults.next()) {
                name = dbResults.getString("vusername");
                output += "Trip Task: " + dbResults.getString("ttask") + "\n";
                output += "Location: " + dbResults.getString("tlocation") + "\n";
                output += "Duration: " + dbResults.getString("tduration") + "\n";
                output += "Mileage: " + dbResults.getInt("tmileage") + "\n\n";
                mileage += dbResults.getInt("tmileage");
            }
            txtATrip.appendText("\nTrip History \n ------------------------------------------------------------------------------------------------------------- \n\n"
                    + "Name: " + name + "\n\n" + output + "\n\nTotal Mileage: " + mileage + "\nReimbursement Amount: $" + String.format("%.2f", mileage * .1));
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print(sqle.toString());
        }
    }

    // View Event Report
    public void viewEventReport(String eid) {
        String name = "";
        String location = "";
        int spots = 0;
        int max = 0;
        String query = "Select event.ename, event.eid, event.elocation, event.spotsleft, event.emax, event.edate, volunteer.vfname, volunteer.vlname from event inner join vattendance on event.eid = "
                + "vattendance.eid inner join volunteer on vattendance.vusername = volunteer.vusername where event.ename = '" + eid + "'";
        String output = "";

        try {
            sendDBCommand(query);
            while (dbResults.next()) {
                name = dbResults.getString("ename");
                location = dbResults.getString("elocation");
                spots = dbResults.getInt("spotsleft");
                max = dbResults.getInt("emax");

                output += "Volunteer Names: " + dbResults.getString("vlName") + ", " + dbResults.getString("vfName") + "\n\n\n";

            }
            txtEvent.setText("\nEvent History \n ---------------------------------------------------------------------------------------------------- \n\n" + "Event Name: " + name + "\t\t\t\t\t\t\t\t\t\t\t\n"
                    + "Event Information: \nLocation: " + location + "\nVolunteers Needed: " + spots + " \tMax Volunteers: " + max + "\n" + output);

            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
