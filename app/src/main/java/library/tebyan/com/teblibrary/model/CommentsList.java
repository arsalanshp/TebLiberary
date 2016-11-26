package library.tebyan.com.teblibrary.model;

import java.util.ArrayList;

/**
 * Created by v.karimi on 7/25/2016.
 */
public class CommentsList {
    public ArrayList<Comment> Result;

    public ArrayList<Comment> getComments() {
        return Result;
    }

    public void setComments(ArrayList<Comment> Result) {
        this.Result = Result;
    }
}
