package library.tebyan.com.teblibrary.model;

import java.util.ArrayList;

/**
 * Created by v.karimi on 8/10/2016.
 */
public class Data {

    private int ID;
    private int MetadataID;
    private String Image;
    private String Publisher;
    private String LinkTitle;
    private String Link;
    private String Title;
    private ArrayList<String> Language;
    private ArrayList<String> Subjects;
    private String Author;

    public int getMetadataID() {
        return MetadataID;
    }

    public void setMetadataID(int metadataID) {
        MetadataID = metadataID;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public ArrayList<String> getSubjects() {
        return Subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        Subjects = subjects;
    }

    public ArrayList<String> getLanguage() {
        return Language;
    }

    public void setLanguage(ArrayList<String> language) {
        Language = language;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLinkTitle() {
        return LinkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        LinkTitle = linkTitle;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
