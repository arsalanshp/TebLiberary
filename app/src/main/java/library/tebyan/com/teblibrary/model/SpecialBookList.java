package library.tebyan.com.teblibrary.model;

import java.util.ArrayList;

/**
 * Created by v.karimi on 8/10/2016.
 */
public class SpecialBookList {

    private ArrayList<DataFirstPage> Farhangi;
    private DataFirstPage Naghd;
    private ArrayList<DataFirstPage> Special;
    private ArrayList<DataFirstPage> Mahboob;

    public ArrayList<DataFirstPage> getFarhangi() {
        return Farhangi;
    }

    public void setFarhangi(ArrayList<DataFirstPage> farhangi) {
        Farhangi = farhangi;
    }

    public DataFirstPage getNaghd() {return Naghd;}

    public void setNaghd(DataFirstPage naghd) {Naghd = naghd;}

    public ArrayList<DataFirstPage> getSpecial() {return Special;}

    public void setSpecial(ArrayList<DataFirstPage> special) {Special = special;}

    public ArrayList<DataFirstPage> getMahboob() {
        return Mahboob;
    }

    public void setMahboob(ArrayList<DataFirstPage> mahboob) {
        Mahboob = mahboob;
    }
}
