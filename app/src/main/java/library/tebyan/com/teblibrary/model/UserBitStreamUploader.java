package library.tebyan.com.teblibrary.model;

/**
 * Created by root on 12/10/16.
 */
public class UserBitStreamUploader {
    private String Thumb;
    private int BitStreamID;
    private String FileName;

    public String getThumb() {
        return Thumb;
    }

    public void setThumb(String thumb) {
        Thumb = thumb;
    }

    public int getBitStreamID() {
        return BitStreamID;
    }

    public void setBitStreamID(int bitStreamID) {
        BitStreamID = bitStreamID;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }
}


//{
//        "Thumb":"/images/DefaulImage/pdf.jpg",
//        "BitStreamID":2867106,
//        "FileName":"app_lib%20(2).pdf"
//        }