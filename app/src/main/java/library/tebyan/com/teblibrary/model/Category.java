package library.tebyan.com.teblibrary.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by v.karimi on 7/24/2016.
 */
public class Category implements Parcelable {

    public String Title;
    public String ImageUrl;
    public int ID;

    public ArrayList<Metadata> MetadataList;

    public ArrayList<Metadata> getMetadata() {
        return MetadataList;
    }

    public void setMetadata(ArrayList<Metadata> MetadataList) {
        MetadataList = MetadataList;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }



    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
