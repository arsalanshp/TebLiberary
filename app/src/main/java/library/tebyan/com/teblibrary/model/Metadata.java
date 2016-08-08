package library.tebyan.com.teblibrary.model;

/**
 * Created by v.karimi on 7/24/2016.
 */
public class Metadata {
    public int MetadataID;
    public String Title;
    public String ImageUrl;
    private String Author;
    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public int getMetadataID() {
        return MetadataID;
    }

    public void setMetadataID(int metadataID) {
        MetadataID = metadataID;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
