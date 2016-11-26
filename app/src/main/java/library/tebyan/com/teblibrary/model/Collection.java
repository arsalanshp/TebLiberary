package library.tebyan.com.teblibrary.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by v.karimi on 7/24/2016.
 */
public class Collection {

    private int ID;
    private String Title;
    private String ImageUrl;
    private int MetadataCount;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public int getMetadataCount() {
        return MetadataCount;
    }

    public void setMetadataCount(int metadataCount) {
        MetadataCount = metadataCount;
    }

}

//"Title": " بیانات رهبر درباره محرم و قیام امام حسین علیه السلام",
//        "ID": 306378,
//        "ImageUrl": "http://img1.tebyan.net/Big/1394/07/141569018961682031632019333202167223144.jpg",
//        "MetadataCount": 20


//"Title": " بیانات رهبر درباره محرم و قیام امام حسین علیه السلام",
//        "ID": 306378,
//        "ImageUrl": "http://img1.tebyan.net/Big/1394/07/141569018961682031632019333202167223144.jpg",
//        "MetadataCount": 20,
//        "MetadataList": [{
//        "Author": "سخنران: مقام معظم رهبری",
//        "MetadataID": 152859,
//        "Title": "[بیانات مقام معظم رهبری در ديدار با مردم قم به مناسبت قيام نوزدهم دى]‌ ",
//        "ImageUrl": "http://library.tebyan.net/images/DefaulImage/metadata.jpg",
//        "Link": "http://library.tebyan.net/fa/Viewer/Switcher/159755/0?Frame=true"
//        }