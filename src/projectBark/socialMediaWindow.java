/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: Social media page allows user to post, comment, like and view the inbox
 */
package projectBark;

import classes.post;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import classes.Event;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import oracle.jdbc.pool.OracleDataSource;
import static projectBark.mainAdminWindow.eventData;
import static projectBark.mainAdminWindow.requestListOBV;

/*
    This social aspect of the application will allow:
        *users to post anything to a timeline
        *users to see events they can register for
        *users to read other users posts
        *users to receieve notifications from shelter or admin
        *users to comment or like other users posts  
 */
public class socialMediaWindow {

    public static int index = 0;

    //controls for the media window
    GridPane pane = new GridPane();

    //buttons for the window
    Button postButton = new Button("Post");
    Button cancelButton = new Button("Cancel");
    Button likeButton = new Button("Likes");
    Button inbox = new Button("Inbox");
    Button commentButton = new Button("Comment");
    Button postAboutEvent = new Button("Post About Event");

    //textfields for the window 
    TextField postTextArea = new TextField();
    TextField commentTextArea = new TextField();

    //Labels
    Label recentEventsLabel = new Label("Your Recent Events");
    Label commentsLabel = new Label("Comments on this Post");
    ObservableList<post> obvList = FXCollections.observableArrayList();
    ListView<post> list = new ListView(obvList);

    public static ObservableList<String> commentsOBV = FXCollections.observableArrayList();
    ListView<String> commentList = new ListView(commentsOBV);

    //needs to be done, use a sql query to pull events for the user
    //this is a string temp, this will change to event once db is done
    ObservableList<String> eventOBV = FXCollections.observableArrayList();
    ListView<String> eventList = new ListView(eventOBV);

    //thought process behind making the posts:
    /*
            The user/volunteer logs in, he types in the post content in the 
            posttextArea, a new post instance object is created and added to the 
            arrayList of posts, his/her id is also added to the instance object
    
            in the list area all the posts from all volunteers will show up, a simple way to 
            do this would be to call the array list of the posts 
    
            to allow pictures to show up, we will need to add to the contructor the 
            address of each volunteer profile picture
    
            COMMENTS AND LIKES: this is simple code
                        each comment added under a post would be attached to the
                           post. this could be done in a similar way we did 
                            attachCoBit().
    
                
     */
    public static Image profilePicture;

    public void window() {
        //grabbing posts from the database first
        grabPosts();
        viewEvent();
        likeButton.setId("likeButton");

        //setting sizes
        Scene primaryMediaWindowScene = new Scene(pane, 1200, 700);
        pane.setPadding(new Insets(25, 10, 10, 10));
        postTextArea.setMinSize(460, 100);
        list.setMinSize(450, 400);
        commentList.setMinSize(200, 200);
        eventList.setMinSize(200, 100);

        postTextArea.setPromptText("Welcome Back, share something with others...");
        postTextArea.setOnMouseClicked(u -> {
            likeButton.setText("Likes 0");

        });
        postTextArea.setFocusTraversable(false);

        Button a = new Button("HOME");

        //set profile picture 
        try {
            FileInputStream barkHeader = new FileInputStream(grabIconPicture(loginPage.txtuserName.getText()));
            Circle c1 = new Circle();
            c1.setStroke(Color.DARKOLIVEGREEN);
            c1.setStrokeWidth(5);

            profilePicture = new Image(barkHeader);
            c1.setFill(new ImagePattern(profilePicture));
            c1.setRadius(40);

            pane.add(c1, 1, 0);
        } catch (Exception e) {
            System.out.print("pic not found");
        }

        //col row, add controls to pane
        pane.add(inbox, 0, 0);
        pane.add(postTextArea, 2, 0, 3, 1);
        pane.add(postButton, 2, 1);
        pane.add(cancelButton, 3, 1);
        pane.add(recentEventsLabel, 0, 2);
        pane.add(eventList, 0, 3, 2, 1);
        pane.add(postAboutEvent, 0, 4);
        pane.add(list, 2, 3, 3, 1);
        pane.add(commentsLabel, 5, 2, 2, 1);
        pane.add(commentList, 5, 3, 2, 1);
        pane.add(commentTextArea, 5, 4, 2, 1);
        pane.add(likeButton, 6, 5);
        pane.add(commentButton, 5, 5);

        //action event to add a post 
        postButton.setOnAction(postNow -> {
            String pID = UUID.randomUUID().toString().substring(0, 8);
            post newPost = new post(loginPage.txtuserName.getText(), postTextArea.getText(), 0, pID);

            obvList.add(newPost);

            // sql statement to add post 
            String sqlQuery = "";
            sqlQuery += "insert into javauser.POSTS (vusername, postcontent, likes, postid) values (";
            sqlQuery += "'" + loginPage.txtuserName.getText() + "',";
            sqlQuery += "'" + postTextArea.getText() + "',";
            sqlQuery += "'" + 0 + "',";
            sqlQuery += "'" + pID + "')";
            sendDBCommand(sqlQuery);

            postTextArea.clear();
            index++;

        });

        list.setOnMouseClicked(clicked -> {

            // calls the grab comments method which calls the comment database and sets the comments to the tble  
            grabComments(list.getSelectionModel().getSelectedItem().pID);
            //grabing the likes for the post
            likeButton.setText("Likes 0");
            likeButton.setText("Likes " + grabLikes(list.getSelectionModel().getSelectedItem().pID));
            // when click on the commentButton 
            commentButton.setOnAction(com -> {
                //sql to add to the comment table
                String sqlQuery = "";
                sqlQuery += "insert into comments (vusername,postid,commenttext) values (";

                sqlQuery += "'" + loginPage.txtuserName.getText() + "',";
                sqlQuery += "'" + list.getSelectionModel().getSelectedItem().getpID() + "',";
                sqlQuery += "'" + commentTextArea.getText() + "')";

                sendDBCommand(sqlQuery);

                commentsOBV.add("@" + loginPage.txtuserName.getText() + ": " + commentTextArea.getText());
                commentTextArea.clear();
            });

            likeButton.setOnMouseClicked(i -> {

                int likes = grabLikes(list.getSelectionModel().getSelectedItem().pID);
                likes++;
                likeButton.setText("Likes " + likes);

                String sqlQuery = "";
                sqlQuery = "Update posts set likes = " + likes + "where postid='" + list.getSelectionModel().getSelectedItem().pID + "'";
                sendDBCommand(sqlQuery);
            });

        });
        // when click on the eventList button 
        eventList.setOnMouseClicked(events -> {

            //once the user clicks on the event and post button the post text area will populate 
            // this will be what goes inside the post text area 
            postAboutEvent.setOnAction(p -> {
                // once db is username will populate depending on user 
                postTextArea.setText("\n Attended: " + eventList.getSelectionModel().getSelectedItem());

            });

        });
        // when click on the cancelButton 
        cancelButton.setOnAction(cancel -> {
            postTextArea.clear();
        });
        // when click on the inbox button 
        inbox.setOnAction(in -> {
            chatApplication.basicChatApplication n = new chatApplication.basicChatApplication();

            n.window();
        });

        list.setItems(obvList);
        commentList.setItems(commentsOBV);
        eventList.setItems(eventOBV);
        //adding arraylist items 
        for (post s : post.posts) {
            obvList.add(s);
        }
        pane.setVgap(10);
        pane.setHgap(10);

        // this will have functionaity to retreieve the name of the user  
        //css code 
        pane.getStylesheets().add("file:../Bark/adminWindowCss.css");

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

    // create grabComments method
    public void grabComments(String postID) {
        try {
            // write sql statement for the comments table
            String sqlQuery = "SELECT * FROM JAVAUSER.COMMENTS WHERE postid ='" + postID + "'";
            sendDBCommand(sqlQuery);
            try {
                commentsOBV.clear();
                while (dbResults.next()) {
                    commentsOBV.add("@" + dbResults.getString("vusername") + ": " + dbResults.getString("commenttext"
                    ));
                    commentList.setItems(commentsOBV);
                }
            } catch (SQLException sqle) {
                System.out.print(sqle.toString());
            }

        } catch (Exception m) {
            System.out.print(m.toString());
        }
    }

    // create grablikes method
    public int grabLikes(String postID) {
        int numberOfLikes = 0;
        // write sql statements for post table
        try {
            String num = "SELECT postid, likes FROM JAVAUSER.POSTS WHERE postid= '" + list.getSelectionModel().getSelectedItem().pID + "\'";
            sendDBCommand(num);

            try {
                while (dbResults.next()) {
                    if (dbResults.getString(1).matches(postID)) {
                        numberOfLikes = Integer.parseInt(dbResults.getString(2));
                    }

                }
                commStmt.close();
                dbResults.close();
            } catch (SQLException sqle) {
                System.out.print(sqle.toString());
            }

        } catch (Exception m) {
            System.out.print(m.toString());
        }
        return numberOfLikes;

    }

    // crate grabPosts method
    public void grabPosts() {
        try {
            post.posts.clear();
            String sql = "select * from posts";
            sendDBCommand(sql);

            try {
                while (dbResults.next()) {

                    post newPost = new post(dbResults.getString(1), dbResults.getString(2), Integer.parseInt(dbResults.getString(3)), dbResults.getString(4));
                    post.posts.add(newPost);
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

    // create grabIconPicture method
    public String grabIconPicture(String username) {
        String url = "";
        try {
            // write sql statement for the volunteer
            String sql = "select vprofilepic from volunteer where vusername = '" + username + "'";
            sendDBCommand(sql);
            try {

                while (dbResults.next()) {
                    url = dbResults.getString(1);
                }
                commStmt.close();
                dbResults.close();
            } catch (SQLException sqle) {
                System.out.print(sqle.toString());
            }

        } catch (Exception m) {
            System.out.print(m.toString());
        }
        return url;
    }

    // create viewEvent method
    public void viewEvent() {
        // write sql statement for the event table
        String query = "Select event.ename,  event.edate, event.etime, event.elocation"
                + " from event inner join vattendance on event.eid = vattendance.eid inner join volunteer on vattendance.vusername = volunteer.vusername where volunteer.vusername = '" + loginPage.txtuserName.getText() + "'";

        try {
            sendDBCommand(query);
            while (dbResults.next()) {

                eventOBV.add(
                        dbResults.getString("ENAME") + " "
                        + (dbResults.getString("EDATE")).substring(6)
                        + " at: "
                        + dbResults.getString("ELOCATION")
                );
                eventList.setItems(eventOBV);

            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

}
