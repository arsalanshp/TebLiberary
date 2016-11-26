package library.tebyan.com.teblibrary.model;

import java.util.ArrayList;

/**
 * Created by root on 11/12/16.
 */
public class Navigate {
    private String BaseName;
    private ArrayList<SubSubjects> Items;

    public String getBaseName() {
        return BaseName;
    }

    public void setBaseName(String baseName) {
        BaseName = baseName;
    }

    public ArrayList<SubSubjects> getItems() {
        return Items;
    }

    public void setItems(ArrayList<SubSubjects> items) {
        Items = items;
    }
}
