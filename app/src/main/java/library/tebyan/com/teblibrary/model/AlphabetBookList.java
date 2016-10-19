package library.tebyan.com.teblibrary.model;

import java.util.ArrayList;

/**
 * Created by v.karimi on 8/10/2016.
 */
public class AlphabetBookList {

//    private DataList Data;
    private int RowCount;
    private ArrayList<Data> Data;

    public ArrayList<library.tebyan.com.teblibrary.model.Data> getData() {
        return Data;
    }

    public void setData(ArrayList<library.tebyan.com.teblibrary.model.Data> data) {
        Data = data;
    }

//    public DataList getData() {
//        return Data;
//    }
//
//    public void setData(DataList data) {
//        Data = data;
//    }

    public int getRowCount() {
        return RowCount;
    }

    public void setRowCount(int rowCount) {
        RowCount = rowCount;
    }
}
