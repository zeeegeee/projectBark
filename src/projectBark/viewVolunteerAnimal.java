/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: Shows the animals in the shelter for volunteer
 */
package projectBark;

import adminWindows.adminAdoption;
import adminWindows.viewAdminAnimal;
import static adminWindows.viewAdminAnimal.animaltableData;
import classes.animal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.UUID;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import projectBark.mainAdminWindow;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.pool.OracleDataSource;

public class viewVolunteerAnimal {

    Connection dbConn;
    Statement commStmt;
    ResultSet dbResults;
    GridPane topPane = new GridPane();
    GridPane midPane = new GridPane();
    GridPane imagePane = new GridPane();
    // create tableview for animalTable
    public static TableView<animal> animalTable = new TableView<>();
    // Create controls
    Button btnAdd = new Button("Add");
    Button btnDelete = new Button("Delete");
    Button btnAdopt = new Button("Adopt");
    Label lblSpecies = new Label("Species");
    Label lblName = new Label("Name");
    Label lblAge = new Label("Age");
    Label lblGender = new Label("Gender");
    Label lblDescription = new Label("Description");
    TextField txtASpecies = new TextField();
    TextField txtAName = new TextField();
    TextField txtAAge = new TextField();
    ComboBox<String> cboGender = new ComboBox<>();
    TextField txtADesc = new TextField();
    Text title = new Text("Animal List");

    Label lblcust = new Label("Customer Information");
    Label lblcName = new Label("Name:");
    Label lblcEmail = new Label("Email:");
    Label lblcPhone = new Label("Phone Number:");
    TextField txtcFName = new TextField();
    TextField txtcLName = new TextField();
    TextField txtcPhone = new TextField();
    TextField txtcEmail = new TextField();
    ImageView imgView = new ImageView();
    String fileLocation;
    Label lblDrag = new Label("Drag Animal Picture Here");

    public void windowAnimal() {
        // call viewanimal() method
        viewanimal();
        // clear data from animaltableData
        viewAdminAnimal.animaltableData.clear();
        viewanimal();
        // set PromptText 
        txtcFName.setPromptText("First Name");
        txtcLName.setPromptText("Last Name");
        txtcPhone.setPromptText("123-456-8910");
        txtcEmail.setPromptText("bark@mail.com");
        lblcName.setFocusTraversable(false);
        //setting the labels for tableColumn
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
        // set the value for the tableview cell
        tblcAName.setCellValueFactory(new PropertyValueFactory<animal, String>("aName"));
        tblcAName.setPrefWidth(100);
        tblcASpecies.setCellValueFactory(new PropertyValueFactory<animal, String>("aSpecies"));
        tblcASpecies.setPrefWidth(100);
        tblcAAge.setCellValueFactory(new PropertyValueFactory<animal, Integer>("aAge"));
        tblcAAge.setPrefWidth(60);
        tblcAGender.setCellValueFactory(new PropertyValueFactory<animal, String>("aGender"));
        tblcAGender.setPrefWidth(80);
        tblcADescription.setCellValueFactory(new PropertyValueFactory<animal, String>("aDescription"));
        tblcADescription.setPrefWidth(260);

        animalTable.setItems(viewAdminAnimal.animaltableData);
        animalTable.getColumns().addAll(tblcAName, tblcASpecies, tblcAAge, tblcAGender, tblcADescription);

        animal oldSelectedRow = animalTable.getSelectionModel().getSelectedItem();
        // make the animalTable editable
        animalTable.setEditable(true);

        tblcAName.setCellFactory((TextFieldTableCell.forTableColumn()));
        tblcAName.setOnEditCommit(
                new EventHandler<CellEditEvent<animal, String>>() {
            @Override
            public void handle(CellEditEvent<animal, String> a) {
                a.getTableView().getItems().get(
                        a.getTablePosition().getRow()).setAName(a.getNewValue());

                //DATABASE HERE 
                String sqlQuery = "UPDATE animal SET aname = \'";
                sqlQuery += a.getNewValue() + "\' WHERE aid = '"
                        + animalTable.getSelectionModel().getSelectedItem().aID + "'";

                sendDBCommand(sqlQuery);
            }
        });

        tblcASpecies.setCellFactory((TextFieldTableCell.forTableColumn()));
        tblcASpecies.setOnEditCommit(
                new EventHandler<CellEditEvent<animal, String>>() {
            @Override
            public void handle(CellEditEvent<animal, String> a) {
                a.getTableView().getItems().get(
                        a.getTablePosition().getRow()).setASpecies(a.getNewValue());

                //DATABASE HERE 
                String sqlQuery = "UPDATE animal SET aspecies = \'";
                sqlQuery += a.getNewValue() + "\' WHERE aid = '"
                        + animalTable.getSelectionModel().getSelectedItem().aID + "'";

                sendDBCommand(sqlQuery);
            }
        });

        tblcAAge.setCellFactory((TextFieldTableCell.forTableColumn()));
        tblcAAge.setOnEditCommit(
                new EventHandler<CellEditEvent<animal, String>>() {
            @Override
            public void handle(CellEditEvent<animal, String> a) {
                a.getTableView().getItems().get(
                        a.getTablePosition().getRow()).setAAge(a.getNewValue());

                //DATABASE HERE 
                String sqlQuery = "UPDATE animal SET aage = \'";
                sqlQuery += a.getNewValue() + "\' WHERE aid = '"
                        + animalTable.getSelectionModel().getSelectedItem().aID + "'";

                sendDBCommand(sqlQuery);
            }
        });

        tblcAGender.setCellFactory((TextFieldTableCell.forTableColumn()));
        tblcAGender.setOnEditCommit(
                new EventHandler<CellEditEvent<animal, String>>() {
            @Override
            public void handle(CellEditEvent<animal, String> a) {
                a.getTableView().getItems().get(
                        a.getTablePosition().getRow()).setAGender(a.getNewValue());

                //DATABASE HERE 
                String sqlQuery = "UPDATE animal SET agender = \'";
                sqlQuery += a.getNewValue() + "\' WHERE aid = '"
                        + animalTable.getSelectionModel().getSelectedItem().aID + "'";

                sendDBCommand(sqlQuery);
            }
        });

        tblcADescription.setCellFactory((TextFieldTableCell.forTableColumn()));
        tblcADescription.setOnEditCommit(
                new EventHandler<CellEditEvent<animal, String>>() {
            @Override
            public void handle(CellEditEvent<animal, String> a) {
                a.getTableView().getItems().get(
                        a.getTablePosition().getRow()).setADescription(a.getNewValue());

                //DATABASE HERE 
                String sqlQuery = "UPDATE animal SET adesc = \'";
                sqlQuery += a.getNewValue() + "\' WHERE aid = '"
                        + animalTable.getSelectionModel().getSelectedItem().aID + "'";

                sendDBCommand(sqlQuery);
            }
        });

        cboGender.getItems().addAll("Male", "Female");
        cboGender.setValue("Male");
        // set size for the controls
        txtAName.setPrefWidth(97);
        txtASpecies.setPrefWidth(97);
        txtAAge.setPrefWidth(57);
        cboGender.setPrefWidth(77);
        txtADesc.setPrefWidth(220);
        title.setFont(new Font(20));
        // click on the btnAdd
        btnAdd.setOnAction(e -> {
            createanimal();
            viewanimal();
            imagePane.getChildren().remove(imgView);
            lblDrag.setText("Drag Animal Picture Here");
            txtASpecies.clear();
            txtAName.clear();
            txtAAge.clear();
            txtADesc.clear();
        });
        // click on the btnDelete
        btnDelete.setOnAction(deleteAnimal -> {
//            deleteanimal();
            viewanimal();
        });

        // set the imagepane drag over
        imagePane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasFiles() || db.hasUrl()) {
                e.acceptTransferModes(TransferMode.ANY);
            }
        });
        // set the imagepane drag dropped
        imagePane.setOnDragDropped(e -> {
            lblDrag.setText("");
            imagePane.getChildren().remove(imgView);
            Dragboard db = e.getDragboard();
            fileLocation = db.getFiles().get(0).getName();
            System.out.println(fileLocation);
            e.consume();
            File file = db.getFiles().get(0);
            try {
                Image image = new Image(new FileInputStream(file.getAbsolutePath()));
                imgView.setImage(image);
            } catch (FileNotFoundException fex) {
                System.out.print(fex.toString());
            }
            // add imgView to imagepane
            imagePane.add(imgView, 0, 0);
            imgView.setFitHeight(50);
            imgView.setFitWidth(50);
        });
        // add controls to pane
        imagePane.add(lblDrag, 0, 0);
        imagePane.setAlignment(Pos.CENTER);
        imagePane.setPrefSize(100, 100);
        topPane.add(lblName, 0, 0);
        topPane.add(lblSpecies, 1, 0);
        topPane.add(lblAge, 2, 0);
        topPane.add(lblGender, 3, 0);
        topPane.add(lblDescription, 4, 0);
        topPane.add(txtAName, 0, 1);
        topPane.add(txtASpecies, 1, 1);
        topPane.add(txtAAge, 2, 1);
        topPane.add(cboGender, 3, 1);
        topPane.add(txtADesc, 4, 1);
        topPane.add(btnAdd, 5, 1);

        // set the padding, alignment, vgap, hgap for the panes
        topPane.setPadding(new Insets(10, 10, 10, 10));
        topPane.setAlignment(Pos.TOP_LEFT);
        topPane.setVgap(5.5);
        topPane.setHgap(5.5);
        // add controls to midpane
        midPane.add(title, 0, 0);
        midPane.add(animalTable, 0, 1);
        midPane.add(btnDelete, 0, 2);
        // add all panes to the main pane
        mainVolunteerWindow.vAnimalPane.add(topPane, 0, 0);
        mainVolunteerWindow.vAnimalPane.add(imagePane, 0, 1);
        mainVolunteerWindow.vAnimalPane.add(midPane, 0, 2);
        mainVolunteerWindow.vAnimalPane.setAlignment(Pos.CENTER);
        animalTable.setMaxSize(600, 240);
        animalTable.setEditable(true);

        mainVolunteerWindow.vAnimalPane.setMaxSize(1000, 1000);

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
    // create the createanimal method

    public void createanimal() {
        String aid = UUID.randomUUID().toString().substring(0, 8);
        // create sql statement for the animal table
        String sqlQuery = "";
        sqlQuery += "INSERT INTO animal(AID, ASPECIES, ANAME, AAGE, AGENDER, ADESC, APIC, adoptedstatus) VALUES (";
        sqlQuery += "\'" + aid + "\',";
        sqlQuery += "\'" + txtASpecies.getText() + "\',";
        sqlQuery += "\'" + txtAName.getText() + "\',";
        sqlQuery += "\'" + txtAAge.getText() + "\',";
        sqlQuery += "\'" + cboGender.getSelectionModel().getSelectedItem() + "\',";
        sqlQuery += "\'" + txtADesc.getText() + "\',";
        sqlQuery += "\'" + fileLocation + "\',";
        sqlQuery += "\'" + 0 + "\')";

        sendDBCommand(sqlQuery);
    }

    // Enter values into animal sql table
    public void viewanimal() {
        String query = "Select * from animal where adoptedStatus = " + 0;

        try {
            sendDBCommand(query);
            animaltableData.clear();
            while (dbResults.next()) {
                // store information to animaltableData
                animaltableData.add(new animal(
                        dbResults.getString("AID"),
                        dbResults.getString("ANAME"),
                        dbResults.getString("ASPECIES"),
                        dbResults.getString("AAGE"),
                        dbResults.getString("AGENDER"),
                        dbResults.getString("ADESC")
                ));
                adminAdoption.animalNamesData.add(dbResults.getString("ANAME"));

                adminAdoption.cbAnimal.setItems(adminAdoption.animalNamesData);

                animalTable.setItems(viewAdminAnimal.animaltableData);
            }

            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print("Catch");
        }
    }

}
