/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: shows all the admin animals
 */
package adminWindows;

import static adminWindows.adminReports.obAnimalType;
import classes.animal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import projectBark.mainAdminWindow;
import java.sql.*;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import oracle.jdbc.pool.OracleDataSource;
import static projectBark.VolunteerReports.vObAnimalType;

public class viewAdminAnimal {

    Connection dbConn;
    Statement commStmt;
    ResultSet dbResults;
    GridPane topPane = new GridPane();
    GridPane midPane = new GridPane();
    GridPane imagePane = new GridPane();
    ArrayList<animal> animalData = new ArrayList<>();
    TableView<animal> animalTable = new TableView<>();
    public static ObservableList<animal> animaltableData = FXCollections.observableArrayList();

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
    ImageView animalPic = new ImageView();
    Label lblDrag = new Label("Drag Animal Picture Here");
    String fileLocation;

    public void windowAnimal() {

        viewanimal();
        countSpecies();
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
        // set cell value for tableview
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

        animalTable.setItems(animaltableData);
        animalTable.getColumns().addAll(tblcAName, tblcASpecies, tblcAAge, tblcAGender, tblcADescription);

        animal oldSelectedRow = animalTable.getSelectionModel().getSelectedItem();
        animalTable.setEditable(true);
        animalTable.setRowFactory(tableView -> {
            TableRow<animal> row = new TableRow<>();

            row.hoverProperty().addListener((observable) -> {
                animal selectedAnimal = row.getItem();

                if (row.isHover() && !row.isEmpty()) {
                    animalPic = new ImageView(new Image("file:Animals/" + hover(selectedAnimal)));
                    animalPic.setFitHeight(200);
                    animalPic.setFitWidth(250);
                    midPane.add(animalPic, 1, 1);
                } else {
                    animalPic.setVisible(false);
                }

            });

            return row;
        });
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
        txtAName.setPrefWidth(97);
        txtASpecies.setPrefWidth(97);
        txtAAge.setPrefWidth(57);
        cboGender.setPrefWidth(77);
        txtADesc.setPrefWidth(220);
        title.setFont(new Font(20));

        btnAdd.setOnAction(e -> {
            createanimal();
            viewanimal();

            txtASpecies.clear();
            txtAName.clear();
            txtAAge.clear();
            txtADesc.clear();

        });

        btnDelete.setOnAction(deleteAnimal -> {
            deleteanimal();
            viewanimal();
        });

        imagePane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasFiles() || db.hasUrl()) {
                e.acceptTransferModes(TransferMode.ANY);
            }
        });

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

            imagePane.add(imgView, 0, 0);
            imgView.setFitHeight(50);
            imgView.setFitWidth(50);
        });
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

        topPane.setPadding(new Insets(10, 10, 10, 10));
        topPane.setAlignment(Pos.TOP_LEFT);
        topPane.setVgap(5.5);
        topPane.setHgap(5.5);

        midPane.add(title, 0, 0);
        midPane.add(animalTable, 0, 1);
        midPane.add(btnDelete, 0, 2);

        mainAdminWindow.animalPane.add(topPane, 0, 0);
        mainAdminWindow.animalPane.add(imagePane, 0, 1);
        mainAdminWindow.animalPane.add(midPane, 0, 2);
        mainAdminWindow.animalPane.setAlignment(Pos.CENTER);
        animalTable.setMaxSize(600, 240);
        animalTable.setEditable(true);

        mainAdminWindow.animalPane.setMaxSize(1000, 1000);

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

    // Add animals to animal sql table
    public void createanimal() {
        String aid = UUID.randomUUID().toString().substring(0, 8);
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
        String query = "Select * from animal where adoptedstatus = 0";
//        String label;
        try {
            sendDBCommand(query);
            animaltableData.clear();
            while (dbResults.next()) {

                animaltableData.add(new animal(
                        dbResults.getString("AID"),
                        dbResults.getString("ANAME"),
                        dbResults.getString("ASPECIES"),
                        dbResults.getString("AAGE"),
                        dbResults.getString("AGENDER"),
                        dbResults.getString("ADESC")
                ));

                animalTable.setItems(viewAdminAnimal.animaltableData);

            }
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print("Catch");
        }
    }

    // Counting # of animals based on species
    public void countSpecies() {

        try {
            String query = "Select count(aspecies), aspecies from animal group by aspecies";
            sendDBCommand(query);
            while (dbResults.next()) {
                obAnimalType.add(new PieChart.Data(dbResults.getString("ASPECIES"), dbResults.getInt("count(ASPECIES)")));
                vObAnimalType.add(new PieChart.Data(dbResults.getString("ASPECIES"), dbResults.getInt("count(ASPECIES)")));

            }
        } catch (SQLException sqle) {
            System.out.print(sqle.toString());
        }
    }

    // Delete animal selected
    public void deleteanimal() {
        String sqlQuery = "DELETE FROM animal WHERE aid = '"
                + animalTable.getSelectionModel().getSelectedItem().aID + "'";
        sendDBCommand(sqlQuery);

    }

    // show up animal picture by hovering
    public String hover(animal selectedAnimal) {
        String sqlQuery = "select apic FROM animal WHERE aid = '"
                + selectedAnimal.aID + "'";
        sendDBCommand(sqlQuery);
        String url = "";
        try {
            while (dbResults.next()) {
                url = dbResults.getString(1);

            }
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            System.out.print("Catch");
        }
        return url;
    }

}
