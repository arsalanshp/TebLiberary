package library.tebyan.com.teblibrary.model;

import java.util.ArrayList;

/**
 * Created by v.karimi on 7/26/2016.
 */
public class MetadataListRowCount {
    private ArrayList<Data> Result;
    private int RowCount;

    public int getRowCount() {
        return RowCount;
    }

    public void setRowCount(int rowCount) {
        RowCount = rowCount;
    }

    public ArrayList<Data> getResult() {
        return Result;
    }

    public void setResult(ArrayList<Data> result) {
        Result = result;
    }
}
