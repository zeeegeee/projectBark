/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: Contains a few reports that volunteers can view, this is different from the admin reports because it shows less 
            reports
 */
package projectBark;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import oracle.jdbc.pool.OracleDataSource;

public class VolunteerReports {

    TabPane tbPane = new TabPane();
    Tab tab1 = new Tab("Volunteer Report");
    Tab tab2 = new Tab("Animal Report");

    PieChart vAnimalPie = new PieChart();
    PieChart vVolunteerPie = new PieChart();

    TextArea txtEvent = new TextArea();

    Button btnView = new Button("View");
    Button btnViewEvent = new Button("View");

    ComboBox cbVName = new ComboBox();
    public static ComboBox<String> cbVEvent = new ComboBox();

    public static ObservableList<PieChart.Data> vObAnimalType = FXCollections.observableArrayList();

    // Show report
    public void showReports() {
        mainVolunteerWindow.vReportsOverall.setAlignment(Pos.TOP_LEFT);
        mainVolunteerWindow.vReportsOverall.setPadding(new Insets(10, 10, 10, 10));
        tbPane.setMinHeight(700);
        tbPane.setMinWidth(1100);

        tab1.setContent(vVolunteerPie);
        vVolunteerPie.setTitle("Volunteers based on status");

        PieChart.Data slice1 = new PieChart.Data("Applicants: " + numberOfVolunteersBasedOnStatus("applicant"), numberOfVolunteersBasedOnStatus("applicant"));
        PieChart.Data slice2 = new PieChart.Data("Volunteers in training: " + numberOfVolunteersBasedOnStatus("volunteerinTraining"), numberOfVolunteersBasedOnStatus("volunteerinTraining"));
        PieChart.Data slice3 = new PieChart.Data("Volunteers: " + numberOfVolunteersBasedOnStatus("volunteer"), numberOfVolunteersBasedOnStatus("volunteer"));
        PieChart.Data slice4 = new PieChart.Data("Admins: " + numberOfVolunteersBasedOnStatus("admin"), numberOfVolunteersBasedOnStatus("admin"));
        vVolunteerPie.getData().add(slice1);
        vVolunteerPie.getData().add(slice2);
        vVolunteerPie.getData().add(slice3);
        vVolunteerPie.getData().add(slice4);

        //pane for animal on the animal tab for reports---------------------------------------
        tab2.setContent(vAnimalPie);
        vAnimalPie.setTitle("Animal Status Distribution");
        PieChart.Data S1 = new PieChart.Data("Animals Adopted " + animalsAdopted(1), animalsAdopted(1));
        PieChart.Data S2 = new PieChart.Data("Animals in Shelter: " + animalsAdopted(0), animalsAdopted(0));
        vAnimalPie.getData().add(S1);
        vAnimalPie.getData().add(S2);

        //pane for animal on the animal tab for reports---------------------------------------
        tab1.setClosable(false);
        tab2.setClosable(false);

        tbPane.getTabs().add(tab1);
        tbPane.getTabs().add(tab2);

        mainVolunteerWindow.vReportsOverall.add(tbPane, 0, 0);

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

    // View Report for Adoption
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

}
