package library.tebyan.com.teblibrary.model;

import java.util.ArrayList;

/**
 * Created by root on 10/19/16.
 */
public class BaseModel<T> {

    private ArrayList<T> Data;

    public ArrayList<T> getData() {
        return Data;
    }

    public void setData(ArrayList<T> data) {
        Data = data;
    }
}
