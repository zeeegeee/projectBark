/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: Shows the animals in the shelter for volunteer
 */
package projectBark;

import adminWindows.*;
import classes.animal;
import classes.customer;
import java.util.ArrayList;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import oracle.jdbc.pool.OracleDataSource;

public class viewAnimal {

    Connection dbConn;
    Statement commStmt;
    ResultSet dbResults;
    GridPane vCustInfoPane = new GridPane();
    GridPane vAnimalTablePane = new GridPane();
    TableView<animal> vAnimalTable = new TableView<>();
    // create controls
    String cid = "";
    Text title = new Text("Animal List");
    Text lblcust = new Text("Customer Information");
    Label lblcName = new Label("Name:");
    Label lblcEmail = new Label("Email:");
    Label lblcPhone = new Label("Phone Number:");
    Label lblAdoptionFee = new Label("Adoption Fee:    $");
    TextField txtAdoptionFee = new TextField();
    TextField txtcFName = new TextField();
    TextField txtcLName = new TextField();
    TextField txtcPhone = new TextField();
    TextField txtcEmail = new TextField();
    Button btnAdopt = new Button("Adopt");
    Button btnAddCustomer = new Button("Add Customer");
    ObservableList<String> customerNameList = FXCollections.observableArrayList();
    ComboBox comboBox = new ComboBox(customerNameList);
    Calendar calendar = Calendar.getInstance();
    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    public void startAnimal() {
        viewAdminAnimal.animaltableData.clear();
        // call the viewanimal() method
        viewanimal();
        // set prompttext to controls
        txtcFName.setPromptText("First Name");
        txtcLName.setPromptText("Last Name");
        txtcPhone.setPromptText("123-456-8910");
        txtcEmail.setPromptText("bark@mail.com");
        txtAdoptionFee.setPromptText("0.00");
        lblcName.setFocusTraversable(false);
        //setting the labels 
        TableColumn tblcAName = new TableColumn("Name");
        tblcAName.setMinWidth(100);
        TableColumn tblcASpecies = new TableColumn("Species");
        tblcASpecies.setMinWidth(100);
        TableColumn tblcAAge = new TableColumn("Age");
        tblcAAge.setMinWidth(60);
        TableColumn tblcAGender = new TableColumn("Gender");
        tblcAGender.setMinWidth(80);
        TableColumn tblcADescription = new TableColumn("Description");
        tblcADescription.setMinWidth(220);
        //set values for tableview cell
        tblcAName.setCellValueFactory(new PropertyValueFactory<animal, String>("aName"));
        tblcAName.setPrefWidth(100);
        tblcASpecies.setCellValueFactory(new PropertyValueFactory<animal, String>("aSpecies"));
        tblcASpecies.setPrefWidth(100);
        tblcAAge.setCellValueFactory(new PropertyValueFactory<animal, String>("aAge"));
        tblcAAge.setPrefWidth(60);
        tblcAGender.setCellValueFactory(new PropertyValueFactory<animal, String>("aGender"));
        tblcAGender.setPrefWidth(80);
        tblcADescription.setCellValueFactory(new PropertyValueFactory<animal, String>("aDescription"));
        tblcADescription.setPrefWidth(260);
        // store animaltableData to AnimalTable
        vAnimalTable.setItems(viewAdminAnimal.animaltableData);
        vAnimalTable.getColumns().addAll(tblcAName, tblcASpecies, tblcAAge, tblcAGender, tblcADescription);

        animal oldSelectedRow = vAnimalTable.getSelectionModel().getSelectedItem();
        // set vAnimalTable editable
        vAnimalTable.setEditable(true);
        tblcAAge.setCellFactory((TextFieldTableCell.forTableColumn()));
        tblcAAge.setOnEditCommit(
                new EventHandler<CellEditEvent<animal, String>>() {
            @Override
            public void handle(CellEditEvent<animal, String> a) {
                a.getTableView().getItems().get(
                        a.getTablePosition().getRow()).setAAge(a.getNewValue());
            }
        }
        );
        tblcADescription.setCellFactory((TextFieldTableCell.forTableColumn()));
        tblcADescription.setOnEditCommit(
                new EventHandler<CellEditEvent<animal, String>>() {
            @Override
            public void handle(CellEditEvent<animal, String> a) {
                a.getTableView().getItems().get(
                        a.getTablePosition().getRow()).setADescription(a.getNewValue());
            }
        }
        );

        title.setFont(new Font(20));
        lblcust.setFont(new Font(20));
        // add controls to panes
        vAnimalTablePane.add(title, 0, 0);
        vAnimalTablePane.add(vAnimalTable, 0, 1, 2, 1);
        vAnimalTablePane.add(lblAdoptionFee, 0, 2);
        vAnimalTablePane.add(txtAdoptionFee, 1, 2);
        vAnimalTablePane.add(btnAdopt, 0, 3);
        vAnimalTablePane.setVgap(5.5);
        vAnimalTablePane.setHgap(3);

        vCustInfoPane.add(lblcust, 0, 0);
        vCustInfoPane.add(lblcName, 0, 1);
        vCustInfoPane.add(lblcPhone, 0, 2);
        vCustInfoPane.add(lblcEmail, 0, 3);

        vCustInfoPane.add(txtcFName, 2, 1);
        vCustInfoPane.add(txtcLName, 3, 1);
        vCustInfoPane.add(txtcPhone, 2, 2);
        vCustInfoPane.add(txtcEmail, 2, 3);

        vCustInfoPane.add(btnAddCustomer, 0, 5);
        vCustInfoPane.add(comboBox, 2, 5);

        vCustInfoPane.setPadding(new Insets(10, 10, 10, 10));
        vCustInfoPane.setAlignment(Pos.TOP_LEFT);
        vCustInfoPane.setVgap(5.5);
        vCustInfoPane.setHgap(5.5);
        // add all panes to main pane
        mainVolunteerWindow.vAdoptPane.add(vAnimalTablePane, 0, 1);
        mainVolunteerWindow.vAdoptPane.add(vCustInfoPane, 0, 0);
        mainVolunteerWindow.vAdoptPane.setAlignment(Pos.CENTER);
        vAnimalTable.setMaxSize(600, 240);
        vAnimalTable.setEditable(true);

        mainVolunteerWindow.vAdoptPane.setMaxSize(1000, 1000);
        // when click on the btnAdopt button 
        btnAdopt.setOnAction(ba -> {

            createAdoption();
            updateAnimal();
            viewPurchaseReport();
            vAnimalTable.getItems().removeAll(
                    vAnimalTable.getSelectionModel().getSelectedItems()
            );

        });
        // when click on the btnAddCustomer button 
        btnAddCustomer.setOnAction(add -> {
            createCustomer();
            viewCustomer();
            txtcFName.clear();
            txtcLName.clear();
            txtcPhone.clear();
            txtcEmail.clear();
            txtAdoptionFee.clear();

        });

    }
    // connect to Database

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

    // create the createAdoption method
    public void createAdoption() {
        String purchid = UUID.randomUUID().toString().substring(0, 8);
        String cid = UUID.randomUUID().toString().substring(0, 8);
        String[] split = comboBox.getSelectionModel().getSelectedItem().toString().split(" ");
        String sqlQuery = "";
        String sqlQuery2 = "";
        String id = "";
        try {
            sqlQuery2 = "select cid from customer where cfname = '" + split[0] + "' and clname = '" + split[1] + "'";
            sendDBCommand(sqlQuery2);
            while (dbResults.next()) {
                id = dbResults.getString("cid");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        // write the sql statement for the purchase table
        sqlQuery += "INSERT INTO Purchase(PURCHID, ANAME, CID, PURCHFEE,PDATE) VALUES (";
        sqlQuery += "\'" + purchid + "\',";
        sqlQuery += "\'" + vAnimalTable.getSelectionModel().getSelectedItem() + "\',";
        sqlQuery += "\'" + id + "',";
        sqlQuery += "\'" + txtAdoptionFee.getText() + "\',";
        sqlQuery += "\'" + String.valueOf(sdf.format(date)) + "\')";

        sendDBCommand(sqlQuery);

    }

    // create the createCustomer method
    public String createCustomer() {
        String cid = UUID.randomUUID().toString().substring(0, 8);
        String sqlQuery = "";
        sqlQuery += "INSERT INTO Customer(CID,CFNAME, CLNAME, CPHONENUMBER, CEMAIL) VALUES (";
        sqlQuery += "\'" + cid + "\',";
        sqlQuery += "\'" + txtcFName.getText() + "\',";
        sqlQuery += "\'" + txtcLName.getText() + "\',";
        sqlQuery += "\'" + txtcPhone.getText() + "\',";
        sqlQuery += "\'" + txtcEmail.getText() + "\')";

        sendDBCommand(sqlQuery);
        return cid;
    }

    // create viewCustomer method
    public void viewCustomer() {
        customerNameList.clear();
        String sqlQuery = "";
        sqlQuery += "Select * from customer";
        try {
            sendDBCommand(sqlQuery);
            while (dbResults.next()) {
                customerNameList.add(
                        dbResults.getString("CFNAME") + " " + dbResults.getString("CLNAME")
                );
            }

        } catch (SQLException sqle) {
            System.out.print(sqle.toString());
        }

    }

    // create the viewanimal method
    public void viewanimal() {
        String query = "Select * from animal";

        try {
            sendDBCommand(query);
            viewAdminAnimal.animaltableData.clear();
            while (dbResults.next()) {
                // store information to animaltableData
                viewAdminAnimal.animaltableData.add(new animal(
                        dbResults.getString("AID"),
                        dbResults.getString("ASPECIES"),
                        dbResults.getString("ANAME"),
                        dbResults.getString("AAGE"),
                        dbResults.getString("AGENDER"),
                        dbResults.getString("ADESC")
                ));
                vAnimalTable.setItems(viewAdminAnimal.animaltableData);
            }
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print(sqle.toString());
        }
    }

    // create the updateAnimal method
    public void updateAnimal() {
        String sqlQ1 = "UPDATE animal SET adoptedStatus = " + 1 + " WHERE aid = '" + vAnimalTable.getSelectionModel().getSelectedItem().aID + "'";
        sendDBCommand(sqlQ1);
    }

    // create the viewPurchaseReport method
    public void viewPurchaseReport() {
        String query = "Select purchase.pdate, purchase.purchid, purchase.purchfee, purchase.aname, customer.cfname, customer.clname from purchase inner join customer on purchase.cid = customer.cid ";
        String output = "";
        try {
            sendDBCommand(query);
            // write the sql statement for the purchase table
            while (dbResults.next()) {
                output += "Date: " + dbResults.getString("pdate") + "\n";
                output += "Purchase ID: " + dbResults.getString("purchid") + "\n";
                output += "Customer Name: " + dbResults.getString("cfname") + " " + dbResults.getString("clname") + "\n";
                output += "Animal Name: " + dbResults.getString("aname") + "\n";
                output += "Adoption Fee: $" + dbResults.getString("purchfee") + "\n\n\n";
            }
            adminReports.txtAPurchase.appendText("\nCustomer Purchase History \n --------------------------------------------------------------------------------------------------------------------- \n\n"
                    + output);
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print(sqle.toString());
        }
    }

}
