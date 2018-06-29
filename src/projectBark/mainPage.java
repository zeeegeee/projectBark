/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: This is the main page containing the main information page about the shelter. Here we have information about the volunteers, the address and registration forms
         This main page also serves as a gateway into signing in
 */
package projectBark;

import adminWindows.adminReports;
import chatApplication.basicChatApplication;
import java.awt.Desktop;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;
import java.util.UUID;
import oracle.jdbc.pool.OracleDataSource;

public class mainPage extends Application {

    Connection dbConn;
    Statement commStmt;
    ResultSet dbResults;
    //GridPane
    GridPane mainPaneOverall = new GridPane();
    GridPane videoPane = new GridPane();
    GridPane volunteerPane = new GridPane();
    GridPane contactPane = new GridPane();
    GridPane aboutPane = new GridPane();
    GridPane donationPane = new GridPane();
    //HBox
    HBox navHbox = new HBox();
    TextField txtDFName = new TextField();
    TextField txtDStreet = new TextField();
    TextField txtDZip = new TextField();
    TextField txtDState = new TextField();
    TextField txtDCity = new TextField();
    TextField txtDPhone = new TextField();
    TextField txtDEmail = new TextField();
    ComboBox cbCause = new ComboBox();
    ObservableList<String> causeList = FXCollections.observableArrayList();
    ToggleGroup group = new ToggleGroup();
    //Menu Bar
    MenuBar menu = new MenuBar();
    Menu menuDonation = new Menu("Donation");
    Menu menuHome = new Menu("Home");
    Menu menuVolunteer = new Menu("Volunteer");
    Menu menuAbout = new Menu("About");
    Menu menuContact = new Menu("Contact");
    Menu menuLogIn = new Menu("Sign In/Register");
    Menu img = new Menu("");
    Label lblsign = new Label("Make a Donation");
    Label lblamount = new Label("Amount:");
    Label lblCause = new Label("Donation Cause:");
    RadioButton rb1 = new RadioButton("$25");
    RadioButton rb2 = new RadioButton("$50");
    RadioButton rb3 = new RadioButton("$75");
    RadioButton rb4 = new RadioButton("$100");
    RadioButton rb5 = new RadioButton("$150");
    RadioButton rb6 = new RadioButton("$200");
    Media vidSource;
    MediaPlayer vidPlayer;
    MediaView vidViewer;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        updateStatus();
        updateSpecCombo();
        viewPurchaseReport();
        FileInputStream barkHeader = new FileInputStream("../Bark/barkImage.jpg");
        // create image
        Image imageHeader = new Image(barkHeader);
        ImageView barkView = new ImageView(imageHeader);

        // set the mp4 video for main page
        vidSource = new Media((new File("../Bark/Puppies.mp4")).toURI().toString());
        vidPlayer = new MediaPlayer(vidSource);
        vidViewer = new MediaView(vidPlayer);
        vidPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        vidPlayer.play();
        vidPlayer.setMute(true);

        vidViewer.setFitHeight(600);
        vidViewer.setFitWidth(600);
        // create the hbox
        HBox barkHbox = new HBox(barkView);
        barkHbox.setAlignment(Pos.CENTER);
        barkView.setFitHeight(150);
        barkView.setFitWidth(600);
        videoPane.setAlignment(Pos.CENTER);
        videoPane.setPadding(new Insets(50, 0, 0, 0));
        videoPane.add(vidViewer, 0, 0);
        mainPaneOverall.add(menu, 0, 0);
        mainPaneOverall.add(barkHbox, 0, 3);
        mainPaneOverall.add(videoPane, 0, 4);
        // set scene
        Scene primaryScene = new Scene(mainPaneOverall, 1000, 600);
        primaryStage.setTitle("Volunteer Login portal");
        primaryStage.setScene(primaryScene);
        primaryStage.show();
        // set menu
        menu.getMenus().addAll(menuLogIn, menuContact, menuAbout, menuVolunteer, menuDonation, menuHome);
        menuLogIn.getItems().add(new MenuItem("Sign In"));
        menuLogIn.getItems().add(new MenuItem("Register"));

        menuVolunteer.getItems().add(new MenuItem("Volunteer Info"));
        menuVolunteer.getItems().add(new MenuItem("Register as a Voluntter"));

        menuContact.getItems().add(new MenuItem("Contact Us"));

        menuAbout.getItems().add(new MenuItem("About Us"));
        menuDonation.getItems().add(new MenuItem("Make a Donation"));

        menu.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        menu.setPrefWidth(primaryScene.getWidth());
        menu.setMaxHeight(Double.MAX_VALUE);

        // menu home
        menuHome.getItems().add(new MenuItem("Back to HomePage"));
        menuHome.getItems().get(0).setOnAction(home -> {
            videoPane.setVisible(true);
            volunteerPane.setVisible(false);
            contactPane.setVisible(false);
            aboutPane.setVisible(false);
            donationPane.setVisible(false);

        });
        // select from menuLogin 
        menuLogIn.getItems().get(0).setOnAction(e -> {
            loginPage ml = new loginPage();
            ml.start();

        });
        // select from menuLogin
        menuLogIn.getItems().get(1).setOnAction(t -> {
            try {
                signUpWindow n = new signUpWindow();
                n.signUpWindow();

            } catch (Exception e) {
                System.out.println(e.toString());
            }

        });

        //code for contact information
        mainPaneOverall.add(contactPane, 0, 4);
        Text titleC = new Text("Contact Us");
        titleC.setFont(Font.font("Verdana", FontWeight.BOLD, 23));
        contactPane.setPadding(new Insets(30, 10, 10, 80));
        contactPane.setVgap(10);
        contactPane.setHgap(20);
        contactPane.add(titleC, 0, 0);

        // set the map image
        ImageView ivMap = new ImageView(new Image("file:map.png"));
        ivMap.setFitHeight(280);
        ivMap.setFitWidth(400);
        //needs to be replaced with valid information later
        contactPane.add(ivMap, 1, 1);
        contactPane.add(new Text("Hours of Operation: Mon-Sun, 9-5\n"
                + "\nPhone: (540)123-4567\n"
                + "\nFax: (540)123-4566\n"
                + "\nMailing Address: P.O. Box 666, Harrisonburg VA 22801\n"
                + "\nLocation: 1443 Hillside Ave, Harrisonburg, VA 22801\n"
                + "\nEmail Address: Barkcenter@gmail.com"), 0, 1);

        contactPane.setVisible(false);
        // select option from contact menu 
        menuContact.getItems().get(0).setOnAction(selected -> {
            videoPane.setVisible(false);
            volunteerPane.setVisible(false);
            contactPane.setVisible(true);
            aboutPane.setVisible(false);
            donationPane.setVisible(false);
        });

        //code for about us information
        mainPaneOverall.add(aboutPane, 0, 4);
        Text titleA = new Text("About Us");
        titleA.setFont(Font.font("Verdana", FontWeight.BOLD, 23));
        aboutPane.setPadding(new Insets(30, 10, 10, 55));
        aboutPane.setAlignment(Pos.CENTER);
        aboutPane.setVgap(10);
        aboutPane.setHgap(20);
        aboutPane.add(titleA, 0, 0);

        //needs to be replaced with valid information later
        aboutPane.add(new Text("BARK Animal Shelter is an open admission animal shelter serving Harrisonburg and Rockingham County.\n"
                + "We accept pets from our jurisdiction regardless of temperament, health, age, breed, size, color, or ability to pay for their care. \n"
                + "We are all volunteer-based. No experience? No worries, we will train you!\n"
                + "If you are passionate with animals, welcome to join us!"), 0, 1);
        // images about us
        ImageView sakshi = new ImageView(new Image("file:sakshi.png"));
        sakshi.setFitHeight(200);
        sakshi.setFitWidth(140);
        ImageView wenqian = new ImageView(new Image("file:wenqian.png"));
        wenqian.setFitHeight(200);
        wenqian.setFitWidth(140);
        ImageView moe = new ImageView(new Image("file:moe.png"));
        moe.setFitHeight(200);
        moe.setFitWidth(130);

        ImageView caity = new ImageView(new Image("file:caity.png"));
        caity.setFitHeight(200);
        caity.setFitWidth(160);
        ImageView yui = new ImageView(new Image("file:yui.png"));
        yui.setFitHeight(200);
        yui.setFitWidth(150);

        HBox imgHbox = new HBox(sakshi, wenqian, caity, yui, moe);
        aboutPane.add(imgHbox, 0, 2);

        mainPaneOverall.add(donationPane, 0, 4);

        // set the toggleGroup
        rb1.setToggleGroup(group);
        rb2.setToggleGroup(group);
        rb3.setToggleGroup(group);
        rb4.setToggleGroup(group);
        rb5.setToggleGroup(group);
        rb6.setToggleGroup(group);

        // create the controls
        Label lblContact = new Label("Contact Information:");
        Label lblDName = new Label("Name/Organization");
        Label lblDEmail = new Label("Email");
        Label lblDStreet = new Label("Address");
        Label lblDZip = new Label("Zip");
        Label lblDState = new Label("State");
        Label lblDCity = new Label("City");
        Label lblDPhone = new Label("Phone Number");
        //set the text for controls
        txtDFName.setPromptText("Name/Organization");
        txtDStreet.setPromptText("Street Address");
        txtDZip.setPromptText("12345");
        txtDState.setPromptText("VA");
        txtDCity.setPromptText("City");
        txtDPhone.setPromptText("123-456-8910");
        txtDEmail.setPromptText("bark@mail.com");

        Button btndonate = new Button("Donate");
        causeList.add("Spay/Neuter Fund");
        causeList.add("Adoption Fees");
        causeList.add("Food");
        causeList.add("Where Needed Most");
        cbCause.setItems(causeList);
        //setting css ids 
        txtDFName.setId("text-field");
        txtDStreet.setId("text-field");
        txtDZip.setId("text-field");
        txtDState.setId("text-field");
        txtDCity.setId("text-field");
        txtDPhone.setId("text-field");
        txtDEmail.setId("text-field");

        btndonate.setDisable(false);
        // css for donation pane
        donationPane.add(lblsign, 0, 0, 2, 1);
        lblsign.setStyle("-fx-font-weight: bold;");
        lblsign.setStyle("-fx-font: 30px \"Sans Serif\";");
        donationPane.add(lblamount, 0, 1, 2, 1);
        lblamount.setStyle("-fx-font-weight: bold;");
        lblamount.setStyle("-fx-font: 15px \"Sans Serif\";");
        donationPane.add(lblContact, 2, 1, 2, 1);
        lblContact.setStyle("-fx-font-weight: bold;");
        lblContact.setStyle("-fx-font: 15px \"Sans Serif\";");
        lblCause.setStyle("-fx-font-weight: bold;");
        lblCause.setStyle("-fx-font: 15px \"Sans Serif\";");
        // add controls to donation pane
        donationPane.add(rb1, 0, 2);
        donationPane.add(rb2, 0, 3);
        donationPane.add(rb3, 0, 4);
        donationPane.add(rb4, 1, 2);
        donationPane.add(rb5, 1, 3);
        donationPane.add(rb6, 1, 4);
        donationPane.add(lblCause, 0, 5);
        donationPane.add(cbCause, 0, 6, 2, 1);
        donationPane.add(lblDName, 2, 2);
        donationPane.add(lblDPhone, 2, 3);
        donationPane.add(lblDEmail, 2, 4);
        donationPane.add(lblDStreet, 2, 5);
        donationPane.add(lblDCity, 2, 6);
        donationPane.add(lblDState, 2, 7);
        donationPane.add(lblDZip, 2, 8);

        donationPane.add(txtDFName, 3, 2);
        donationPane.add(txtDPhone, 3, 3);
        donationPane.add(txtDEmail, 3, 4);
        donationPane.add(txtDStreet, 3, 5);
        donationPane.add(txtDCity, 3, 6);
        donationPane.add(txtDState, 3, 7);
        donationPane.add(txtDZip, 3, 8);
        donationPane.add(btndonate, 2, 10);

        btndonate.setMinWidth(175);

        donationPane.setPadding(new Insets(30, 10, 10, 10));
        donationPane.setAlignment(Pos.CENTER);
        donationPane.setVgap(10);
        donationPane.setHgap(20);
        donationPane.setVisible(false);
        // when click on the btndonate
        btndonate.setOnAction(b -> {
            Alert userPrompt = new Alert(Alert.AlertType.ERROR,
                    "Please Input Correct Data",
                    ButtonType.OK);
            Alert userAmount = new Alert(Alert.AlertType.ERROR,
                    "Please Select the Amount to Donate",
                    ButtonType.OK);
            Alert userCause = new Alert(Alert.AlertType.ERROR,
                    "Please Select the Donation Cause",
                    ButtonType.OK);
            // if else statement 
            if ((txtDFName.getText().isEmpty())
                    | (txtDPhone.getText().isEmpty())
                    | (txtDEmail.getText().isEmpty())
                    | (txtDStreet.getText().isEmpty())
                    | (txtDCity.getText().isEmpty())
                    | (txtDStreet.getText().isEmpty())
                    | (txtDState.getText().isEmpty())
                    | (txtDZip.getText().isEmpty())) {
                userPrompt.show();
            } else if (txtDFName.getText().matches("[0-9]+")) {
                userPrompt.show();
                txtDFName.clear();
            } else if (txtDPhone.getText().matches("[a-zA-Z_]+")) {
                userPrompt.show();
                txtDPhone.clear();
            } else if (txtDPhone.getText().length() != 12) {
                userPrompt.show();
                txtDPhone.clear();
            } else if (txtDCity.getText().matches("[0-9]+")) {
                userPrompt.show();
                txtDCity.clear();
            } else if (txtDState.getText().matches("[0-9]+")) {
                userPrompt.show();
                txtDState.clear();
            } else if (txtDZip.getText().matches("[a-zA-Z_]+")) {
                userPrompt.show();
                txtDZip.clear();
            } else if (!rb1.isSelected() && !rb2.isSelected() && !rb3.isSelected()
                    && !rb4.isSelected() && !rb5.isSelected() && !rb6.isSelected()) {
                userAmount.show();
            } else if (cbCause.getSelectionModel().isEmpty()) {
                userCause.show();
            } else {
                createDonor();
                Desktop browser = Desktop.getDesktop();
                try {
                    browser.browse(new URI("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=K77UGH56NQYJ2"));
                } catch (URISyntaxException err) {
                    System.out.print(err.toString());
                } catch (IOException ex) {
                    Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // menuDonation select 
        menuDonation.getItems().get(0).setOnAction(selected -> {
            videoPane.setVisible(false);
            volunteerPane.setVisible(false);
            contactPane.setVisible(false);
            aboutPane.setVisible(false);
            donationPane.setVisible(true);
        });

        aboutPane.setVisible(false);
        // when select from menuAbout
        menuAbout.getItems().get(0).setOnAction(selected -> {
            videoPane.setVisible(false);
            volunteerPane.setVisible(false);
            contactPane.setVisible(false);
            aboutPane.setVisible(true);
            donationPane.setVisible(false);
        });

        //code if volunteer menu item is clicked
        //code for volunteer information
        mainPaneOverall.add(volunteerPane, 0, 4);
        Text title = new Text();
        title.setText("Different Ways to Volunteer");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 23));
        Text title2 = new Text();
        Text title3 = new Text();
        title2.setText("Pet Socializer");
        title3.setText("Event & More");
        title2.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        title3.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        Button btnRegister = new Button("Register as a Volunteer");
        volunteerPane.setPadding(new Insets(30, 10, 10, 10));
        volunteerPane.setAlignment(Pos.CENTER);
        volunteerPane.setVgap(10);
        volunteerPane.setHgap(20);
        // add titles to pane
        volunteerPane.add(title, 0, 0);
        volunteerPane.add(title2, 0, 1);
        // set text to pane
        volunteerPane.add(new Text("Work with dogs and cats, beginning with VIT(Volunteer in Training)\n"
                + "and working up from there.\n "
                + "When VIT serve more than 20 hours, would change status to Volunteer. \n"), 0, 2);
        volunteerPane.add(title3, 1, 1);
        volunteerPane.add(new Text("Volunteer for our special events! "
                + "Help us by volunteering your time and talents at speicial\n"
                + "events such as Summer Clean, Happy shave, and Cute Greeting, etc.\n"
                + "We can always use volunteers at these events!"), 1, 2);
        volunteerPane.add(btnRegister, 0, 5);
        // create dog image
        ImageView dogandcat = new ImageView(new Image("file:dogandcat.jpg"));
        dogandcat.setFitHeight(200);
        dogandcat.setFitWidth(400);
        volunteerPane.add(dogandcat, 1, 5);
        // when click on the btnRegister
        btnRegister.setOnAction(register -> {
            try {

                signUpWindow n = new signUpWindow();
                n.signUpWindow();

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        });

        volunteerPane.setVisible(false);

        menuVolunteer.getItems().get(0).setOnAction(selected -> {
            videoPane.setVisible(false);
            volunteerPane.setVisible(true);
            contactPane.setVisible(false);
            aboutPane.setVisible(false);
            donationPane.setVisible(false);
        });

        //code for volunteer regisration
        menuVolunteer.getItems().get(1).setOnAction(reg -> {
            try {

                signUpWindow n = new signUpWindow();
                n.signUpWindow();

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        });

        primaryScene.widthProperty().addListener(ov -> {
            mainPaneOverall.setMinWidth(primaryScene.getWidth() - 20);
            menu.setMinWidth(primaryScene.getWidth() - 20);
            videoPane.setMinWidth(primaryScene.getWidth() - 20);
            volunteerPane.setMinWidth(primaryScene.getWidth() - 20);
        });

        barkHbox.setStyle("-fx-background-color: #FFFFFF");
        primaryScene.getStylesheets().add("file:../Bark/adminWindowCss.css");

    }

    public static void main(String[] args) {
        launch(args);
    }

    // connect to database
    public void sendDBCommand(String sqlQuery) {
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        String userID = "javauser";
        String userPASS = "javapass";
        OracleDataSource ds;

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
    // create the createDonor method

    public void createDonor() {
        String did = UUID.randomUUID().toString().substring(0, 8);
        String sqlQuery = "";
        double amt = 0;
        if (rb1.isSelected()) {
            amt = 25;
        }
        if (rb2.isSelected()) {
            amt = 50;
        }
        if (rb3.isSelected()) {
            amt = 75;
        }
        if (rb4.isSelected()) {
            amt = 100;
        }
        if (rb5.isSelected()) {
            amt = 150;
        }
        if (rb6.isSelected()) {
            amt = 200;
        }
        // create sql statement for donor table
        sqlQuery += "INSERT INTO Donor(DID, DNAME, DAMOUNT, DCATEGORY, DPHONE, DEMAIL, DADDRESS, DCITY, DSTATE, DZIP) VALUES (";
        sqlQuery += "\'" + did + "\',";
        sqlQuery += "\'" + txtDFName.getText() + "\',";
        sqlQuery += amt + ",";
        sqlQuery += "\'" + cbCause.getSelectionModel().getSelectedItem() + "\',";
        sqlQuery += "\'" + txtDPhone.getText() + "\',";
        sqlQuery += "\'" + txtDEmail.getText() + "\',";
        sqlQuery += "\'" + txtDStreet.getText() + "\',";
        sqlQuery += "\'" + txtDCity.getText() + "\',";
        sqlQuery += "\'" + txtDState.getText() + "\',";
        sqlQuery += txtDZip.getText() + ")";

        sendDBCommand(sqlQuery);
    }

    // create updateStatus method
    public void updateStatus() {
        String query = "update volunteer set vstatus = 2 where vusername=(select vusername from volunteer where vtotalhours >= 20 and vstatus <> 3)";
        sendDBCommand(query);
    }

    // create updateSpecCombo method
    public void updateSpecCombo() {
        String query = "Select * from specialization";

        try {
            sendDBCommand(query);
            mainVolunteerWindow.specialization.clear();
            mainVolunteerWindow.specialization.add("Animal HealthCare");
            mainVolunteerWindow.specialization.add("Feeding");
            mainVolunteerWindow.specialization.add("Enclosure Care");
            mainVolunteerWindow.specialization.add("Adopter Relations");

            mainVolunteerWindow.specialization.add("Event Volunteer");
            while (dbResults.next()) {
                mainVolunteerWindow.specialization.add(dbResults.getString(1));

                mainVolunteerWindow.specializationCBO.setItems(mainVolunteerWindow.specialization);

            }
            commStmt.close();
            dbResults.close();
        } catch (SQLException sqle) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Specialization has been already added");
            alert.showAndWait();

        }
    }
    // create viewPurchaseReport method

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
