/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: This is the Post class 
 */
package classes;

import java.util.ArrayList;

public class post {

    public String vID;
    public String pID;
    public String postContent;
    public post postComment;
    public int likes = 0;

    public static ArrayList<post> posts = new ArrayList<post>();
    public ArrayList<String> comments = new ArrayList<String>();

    // Constructors
    public post(String vID, String postContent, int likes, String pID) {
        this.vID = vID;
        this.postContent = postContent;
        this.comments = null;
        this.likes = likes;
        this.pID = pID;
    }
    
    public post(String postContent) {
        this.postContent = postContent;

    }
    // create getter
    public String getpID() {
        return pID;
    }
    // create setter
    public void setpID(String pID) {
        this.pID = pID;
    }
    // create add
    public void add(post newPost) {
        posts.add(newPost);
    }
    // create delete
    public void delete(post newPost) {
        posts.remove(newPost);
    }
    // create getter
    public String getvID() {
        return vID;
    }
    // create setter
    public void setvID(String vID) {
        this.vID = vID;
    }
    // create getter
    public String getPostContent() {
        return postContent;
    }
    // create setter
    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    @Override
    public String toString() {

        String output = "";
        if (this.vID == null) {
            output += "Comment: " + this.postContent;
        } else {
            output += "@" + this.vID + "\n" + this.postContent;
        }
        return output;

    }
    // create getter
    public int getLikes() {
        return likes;
    }

}