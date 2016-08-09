package library.tebyan.com.teblibrary.model;

import android.media.Image;

import java.util.Date;

/**
 * Created by root on 8/8/16.
 */

public class Comment {

    String commentText;
    String commentScore;
    String userName;
    Image userAvatar;
    Integer user_id;
    Date commentDate;

    Comment(){}

    public Comment(String userName , String commentText ){
        this .commentText = commentText;
        this.userName = userName;

    }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Image getUserAvatar() {return userAvatar;}
    public void setUserAvatar(Image userAvatar) {this.userAvatar = userAvatar;}

    public Date getCommentDate() { return commentDate; }
    public void setCommentDate(Date commentDate) { this.commentDate = commentDate; }

    public Integer getUser_id() { return user_id; }
    public void setUser_id(Integer user_id) { this.user_id = user_id; }

    public String getUser() { return userName; }
    public void setUser(String user) { this.userName = user; }

    public String getCommentScore() {
        return commentScore;
    }
    public void setCommentScore(String commentScore) {
        this.commentScore = commentScore;
    }

    public String getCommentText() {
        return commentText;
    }
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
