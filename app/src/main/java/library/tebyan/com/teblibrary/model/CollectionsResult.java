package library.tebyan.com.teblibrary.model;

import java.util.ArrayList;

/**
 * Created by root on 8/10/16.
 */

public class CollectionsResult {

    private ArrayList<Collection> Result;
    public ArrayList<Collection> getResult() {
        return Result;
    }
    public void setResult(ArrayList<Collection> result) {
        Result = result;
    }
}

//  SAMPLE :
//"Result": [{
//        "Title": " بیانات رهبر درباره محرم و قیام امام حسین علیه السلام",
//        "ID": 306378,
//        "ImageUrl": "http://img1.tebyan.net/Big/1394/07/141569018961682031632019333202167223144.jpg",
//        "MetadataCount": 20,
//        "MetadataList": [{
//        "Author": "سخنران: مقام معظم رهبری",
//        "MetadataID": 152859,
//        "Title": "[بیانات مقام معظم رهبری در ديدار با مردم قم به مناسبت قيام نوزدهم دى]‌ ",
//        "ImageUrl": "http://library.tebyan.net/images/DefaulImage/metadata.jpg",
//        "Link": "http://library.tebyan.net/fa/Viewer/Switcher/159755/0?Frame=true"
//        },]