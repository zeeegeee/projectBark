/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: ADMIN ADOPTION PAGE 
 */
package adminWindows;

import classes.customer;
import classes.animal;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import projectBark.mainAdminWindow;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import oracle.jdbc.pool.OracleDataSource;

public class adminAdoption {

    Connection dbConn;
    Statement commStmt;
    ResultSet dbResults;
    GridPane topPane = new GridPane();
    GridPane bottomPane = new GridPane();
    GridPane midPane = new GridPane();

    // Create tableview for animal and customer
    TableView<animal> animalTable = new TableView<>();
    TableView<customer> customerTable = new TableView<>();
    ObservableList<customer> customerTableData = FXCollections.observableArrayList();
    public static ObservableList<String> animalNamesData = FXCollections.observableArrayList();
    public static ComboBox cbAnimal = new ComboBox();
    Text title = new Text("Animal List");
    Text titleCust = new Text("Select Customer for Adoption:");
    Label lblcust = new Label("Customer Information");
    Label lblcName = new Label("Name:");
    Label lblcEmail = new Label("Email:");
    Label lblcPhone = new Label("Phone Number:");
    Label lblAdoptionFee = new Label("Adoption Fee:      $");
    Label lblAnimal = new Label("Animal");
    TextField txtAdopt = new TextField();
    TextField txtcFName = new TextField();
    TextField txtcLName = new TextField();
    TextField txtcPhone = new TextField();
    TextField txtcEmail = new TextField();
    Button btnAdopt = new Button("Adopt");
    Button btnDelete = new Button("Delete");
    Button btnAdd = new Button("Add Customer");
    ComboBox cbAnimals = new ComboBox();

    public void windowAdopt() {
        // Pop up adoption window to adopt animals
        customerTableData.clear();
        viewAdminAnimal.animaltableData.clear();
        viewCustomer();
        viewanimal();
        txtcFName.setPromptText("First Name");
        txtcLName.setPromptText("Last Name");
        txtcPhone.setPromptText("123-456-8910");
        txtcEmail.setPromptText("bark@mail.com");
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
        // set cell for tableview
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

        animalTable.getColumns().addAll(tblcAName, tblcASpecies, tblcAAge, tblcAGender, tblcADescription);

        animal oldSelectedRow = animalTable.getSelectionModel().getSelectedItem();

        TableColumn tblcCFName = new TableColumn("First Name");
        tblcCFName.setMinWidth(100);
        TableColumn tblcCLName = new TableColumn("Last Name");
        tblcCLName.setMinWidth(100);
        TableColumn tblcCPhoneNumber = new TableColumn("Phone Number");
        tblcCPhoneNumber.setMinWidth(100);
        TableColumn tblcCEmail = new TableColumn("Email");
        tblcCEmail.setMinWidth(100);
        // set cellvalue for tableview
        tblcCFName.setCellValueFactory(new PropertyValueFactory<customer, String>("cFName"));
        tblcCFName.setPrefWidth(150);
        tblcCLName.setCellValueFactory(new PropertyValueFactory<customer, String>("cLName"));
        tblcCLName.setPrefWidth(150);
        tblcCPhoneNumber.setCellValueFactory(new PropertyValueFactory<customer, String>("cPhoneNumber"));
        tblcCPhoneNumber.setPrefWidth(150);
        tblcCEmail.setCellValueFactory(new PropertyValueFactory<customer, String>("cEmail"));
        tblcCEmail.setPrefWidth(150);
        customerTable.getColumns().addAll(tblcCFName, tblcCLName, tblcCPhoneNumber, tblcCEmail);

        // Make Customer tableview editable
        customerTable.setEditable(true);
        tblcCFName.setCellFactory((TextFieldTableCell.forTableColumn()));
        tblcCFName.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<customer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<customer, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setCFName(t.getNewValue());
                //DATABASE HERE 
                String sqlQuery = "UPDATE customer SET cfname = \'";
                sqlQuery += t.getNewValue() + "\' WHERE cid = '"
                        + customerTable.getSelectionModel().getSelectedItem().cID + "'";

                sendDBCommand(sqlQuery);
            }
        });

        tblcCLName.setCellFactory((TextFieldTableCell.forTableColumn()));
        tblcCLName.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<customer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<customer, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setCLName(t.getNewValue());
                //DATABASE HERE 
                String sqlQuery = "UPDATE customer SET clname = \'";
                sqlQuery += t.getNewValue() + "\' WHERE cid = '"
                        + customerTable.getSelectionModel().getSelectedItem().cID + "'";

                sendDBCommand(sqlQuery);
            }
        });

        tblcCPhoneNumber.setCellFactory((TextFieldTableCell.forTableColumn()));
        tblcCPhoneNumber.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<customer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<customer, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setCPhoneNumber(t.getNewValue());
                //DATABASE HERE 
                String sqlQuery = "UPDATE customer SET cphonenumber = \'";
                sqlQuery += t.getNewValue() + "\' WHERE cid = '"
                        + customerTable.getSelectionModel().getSelectedItem().cID + "'";

                sendDBCommand(sqlQuery);
            }
        });

        tblcCEmail.setCellFactory((TextFieldTableCell.forTableColumn()));
        tblcCEmail.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<customer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<customer, String> t) {
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setCEmail(t.getNewValue());
                //DATABASE HERE 
                String sqlQuery = "UPDATE customer SET cemail = \'";
                sqlQuery += t.getNewValue() + "\' WHERE cid = '"
                        + customerTable.getSelectionModel().getSelectedItem().cID + "'";

                sendDBCommand(sqlQuery);
            }
        });

        title.setFont(new Font(20));
        // add controls to panes
        midPane.add(titleCust, 0, 0, 4, 1);
        midPane.add(customerTable, 0, 1, 3, 1);
        midPane.add(lblAnimal, 0, 2, 2, 1);
        midPane.add(lblAdoptionFee, 0, 3, 2, 1);
        midPane.add(cbAnimal, 1, 2);
        midPane.add(txtAdopt, 2, 3);
        txtAdopt.setPromptText("0.00");
        midPane.add(btnAdopt, 0, 4);
        midPane.setPadding(new Insets(10, 10, 10, 10));
        midPane.setAlignment(Pos.TOP_LEFT);
        midPane.setVgap(5.5);
        midPane.setHgap(3);

        bottomPane.add(lblcust, 0, 0, 2, 1);
        bottomPane.add(lblcName, 0, 1, 2, 1);
        bottomPane.add(lblcPhone, 0, 2, 2, 1);
        bottomPane.add(lblcEmail, 0, 3, 2, 1);
        bottomPane.add(txtcFName, 3, 1);
        bottomPane.add(txtcLName, 4, 1);
        bottomPane.add(txtcPhone, 3, 2);
        bottomPane.add(txtcEmail, 3, 3);

        bottomPane.add(btnAdd, 0, 6);
        bottomPane.add(btnDelete, 1, 6);
        bottomPane.setPadding(new Insets(10, 10, 10, 10));
        bottomPane.setAlignment(Pos.TOP_LEFT);
        bottomPane.setVgap(3);
        bottomPane.setHgap(3);

        mainAdminWindow.adminAdoptPane.add(topPane, 0, 0);
        mainAdminWindow.adminAdoptPane.add(midPane, 0, 2);
        mainAdminWindow.adminAdoptPane.add(bottomPane, 0, 1);
        mainAdminWindow.adminAdoptPane.setAlignment(Pos.CENTER);
        animalTable.setMaxSize(600, 240);
        customerTable.setMinSize(600, 240);
        animalTable.setEditable(true);

        mainAdminWindow.adminAdoptPane.setMaxSize(1000, 1000);

        // Add Customer
        btnAdd.setOnAction(a -> {
            createCustomer();
            viewCustomer();
            viewanimal();

        });

        // Adopt animal
        btnAdopt.setOnAction(e -> {

            createPurchase();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Purchase Successful!");
            alert.showAndWait();
            updateAnimal();
            viewPurchaseReport();
            txtAdopt.clear();
            txtcFName.clear();
            txtcLName.clear();
            txtcPhone.clear();
            txtcEmail.clear();

        });

        btnDelete.setOnAction(d -> {
            deleteCustomer();
            viewCustomer();
        });

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

    // Add Customer to Customer sql table
    public void createCustomer() {
        String cid = UUID.randomUUID().toString().substring(0, 8);
        String sqlQuery = "";
        sqlQuery += "INSERT INTO Customer(CID,CFNAME, CLNAME, CPHONENUMBER, CEMAIL) VALUES (";
        sqlQuery += "\'" + cid + "\',";
        sqlQuery += "\'" + txtcFName.getText() + "\',";
        sqlQuery += "\'" + txtcLName.getText() + "\',";
        sqlQuery += "\'" + txtcPhone.getText() + "\',";
        sqlQuery += "\'" + txtcEmail.getText() + "\')";

        sendDBCommand(sqlQuery);
    }

    // Enter values into Customer sql table
    public void viewCustomer() {
        String query = "Select * from Customer";

        try {
            sendDBCommand(query);
            customerTableData.clear();
            while (dbResults.next()) {

                customerTableData.add(new customer(
                        dbResults.getString("cID"),
                        dbResults.getString("cFName"),
                        dbResults.getString("cLName"),
                        dbResults.getString("cPhoneNumber"),
                        dbResults.getString("cEmail")
                ));
                customerTable.setItems(customerTableData);
            }
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print(sqle.toString());
        }
    }

    // Delete Customer selected
    public void deleteCustomer() {
        String sqlQuery = "DELETE FROM Customer WHERE cid = '"
                + customerTable.getSelectionModel().getSelectedItem().cID + "'";
        sendDBCommand(sqlQuery);
    }

    // View all of animals from Animal sql table
    public void viewanimal() {
        String query = "Select * from animal where adoptedstatus = 0";

        try {
            sendDBCommand(query);
            viewAdminAnimal.animaltableData.clear();
            while (dbResults.next()) {

                viewAdminAnimal.animaltableData.add(new animal(
                        dbResults.getString("AID"),
                        dbResults.getString("ANAME"),
                        dbResults.getString("ASPECIES"),
                        dbResults.getString("AAGE"),
                        dbResults.getString("AGENDER"),
                        dbResults.getString("ADESC")
                ));
                adminAdoption.animalNamesData.add(dbResults.getString("ANAME"));
                cbAnimal.setItems(adminAdoption.animalNamesData);
                animalTable.setItems(viewAdminAnimal.animaltableData);
            }
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print(sqle.toString());
        }
    }

    Calendar calendar = Calendar.getInstance();
    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    // Create purchase table in purchase sql table
    public void createPurchase() {
        String purchid = UUID.randomUUID().toString().substring(0, 8);
        String sqlQuery = "";
        sqlQuery += "INSERT INTO Purchase(PURCHID,ANAME,CID,PURCHFEE,PDATE) VALUES (";
        sqlQuery += "\'" + purchid + "\',";
        sqlQuery += "\'" + cbAnimal.getSelectionModel().getSelectedItem() + "\',";
        sqlQuery += "\'" + customerTable.getSelectionModel().getSelectedItem().getCID() + "\',";
        sqlQuery += "\'" + txtAdopt.getText() + "\',";
        sqlQuery += "\'" + String.valueOf(sdf.format(date)) + "\')";

        sendDBCommand(sqlQuery);
    }

    // Update the status of unadopted animals tp "adopted"
    public void updateAnimal() {
        String sqlQ1 = "UPDATE animal SET adoptedStatus = " + 1 + " WHERE aname = '" + cbAnimal.getSelectionModel().getSelectedItem().toString() + "'";

        sendDBCommand(sqlQ1);

    }

    // View purchase report in report page from sql
    public void viewPurchaseReport() {
        String query = "Select purchase.pdate, purchase.purchid, purchase.purchfee, purchase.aname, customer.cfname, customer.clname from purchase inner join customer on purchase.cid = customer.cid ";
        String output = "";
        try {
            sendDBCommand(query);
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
