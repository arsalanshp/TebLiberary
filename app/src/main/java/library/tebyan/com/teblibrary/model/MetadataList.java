package library.tebyan.com.teblibrary.model;

/**
 * Created by v.karimi on 7/24/2016.
 */
public class MetadataList {
    public int MetadataID;
    public String Title;
    public String ImageUrl;

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
