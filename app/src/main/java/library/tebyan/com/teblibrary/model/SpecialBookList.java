package library.tebyan.com.teblibrary.model;

import java.util.ArrayList;

/**
 * Created by v.karimi on 8/10/2016.
 */
public class SpecialBookList {

    private ArrayList<DataFirstPage> Farhangi;
    private ArrayList<DataFirstPage> Naghd;
    private ArrayList<DataFirstPage> Tazeha;
    private ArrayList<DataFirstPage> Mahboob;

    public ArrayList<DataFirstPage> getFarhangi() {
        return Farhangi;
    }

    public void setFarhangi(ArrayList<DataFirstPage> farhangi) {
        Farhangi = farhangi;
    }

    public ArrayList<DataFirstPage> getNaghd() {
        return Naghd;
    }

    public void setNaghd(ArrayList<DataFirstPage> naghd) {
        Naghd = naghd;
    }

    public ArrayList<DataFirstPage> getTazeha() {
        return Tazeha;
    }

    public void setTazeha(ArrayList<DataFirstPage> tazeha) {
        Tazeha = tazeha;
    }

    public ArrayList<DataFirstPage> getMahboob() {
        return Mahboob;
    }

    public void setMahboob(ArrayList<DataFirstPage> mahboob) {
        Mahboob = mahboob;
    }
}
