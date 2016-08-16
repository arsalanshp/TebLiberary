package library.tebyan.com.teblibrary.model;

import android.media.Image;

import java.util.Date;

/**
 * Created by root on 8/8/16.
 */

public class Comment {

    Integer ID;
    String CommentText;
    Integer Score;
    String Date;
    String Avatar;


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getCommentText() {
        return CommentText;
    }

    public void setCommentText(String commentText) {
        CommentText = commentText;
    }

    public Integer getScore() {
        return Score;
    }

    public void setScore(Integer score) {
        Score = score;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }
}
