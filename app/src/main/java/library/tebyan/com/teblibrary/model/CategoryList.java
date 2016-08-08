package library.tebyan.com.teblibrary.model;

import java.util.ArrayList;

/**
 * Created by v.karimi on 7/25/2016.
 */
public class CategoryList {
    public ArrayList<Category> Result;

    public ArrayList<Category> getCategories() {
        return Result;
    }

    public void setCategories(ArrayList<Category> Result) {
        this.Result = Result;
    }
}
