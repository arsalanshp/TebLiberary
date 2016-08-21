package library.tebyan.com.teblibrary.model;

import java.util.ArrayList;

/**
 * Created by root on 8/10/16.
 */
public class BookDetails {
    private String Title;
    private String Author;
    private String Publisher;
    private String ImageUrl;
    private int MetadataID;
    private String Description;
    private String Link;

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    private ArrayList<BitStreamGroups> BitStreamGroups;

    public ArrayList<BitStreamGroups> getBitStreamGroups() {return BitStreamGroups;}
    public void setBitStreamGroups(ArrayList<BitStreamGroups> bitStreamGroups) {BitStreamGroups = bitStreamGroups;}

    public String getTitle() {return Title;}
    public void setTitle(String title) {Title = title;}
    public String getAuthor() {return Author;}
    public void setAuthor(String author) {Author = author;}
    public String getPublisher() {return Publisher;}
    public void setPublisher(String publisher) {Publisher = publisher;}
    public String getImageUrl() {return ImageUrl;}
    public void setImageUrl(String imageUrl) {ImageUrl = imageUrl;}
    public int getMetadataID() {return MetadataID;}
    public void setMetadataID(int metadataID) {MetadataID = metadataID;}
    public String getDescription() {return Description;}
    public void setDescription(String description) {Description = description;}


}
