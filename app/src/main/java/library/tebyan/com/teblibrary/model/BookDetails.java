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
    private Boolean IsForRead;
    private ArrayList<BitStreamGroupsModel> BitStreamGroups;
    private ArrayList<SubjectsModel> Subjects;
    private String Genre;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getMetadataID() {
        return MetadataID;
    }

    public void setMetadataID(int metadataID) {
        MetadataID = metadataID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public Boolean getForRead() {
        return IsForRead;
    }

    public void setForRead(Boolean forRead) {
        IsForRead = forRead;
    }

    public ArrayList<BitStreamGroupsModel> getBitStreamGroups() {
        return BitStreamGroups;
    }

    public void setBitStreamGroups(ArrayList<BitStreamGroupsModel> bitStreamGroups) {
        BitStreamGroups = bitStreamGroups;
    }

    public ArrayList<SubjectsModel> getSubjects() {
        return Subjects;
    }

    public void setSubjects(ArrayList<SubjectsModel> subjects) {
        Subjects = subjects;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }


    public String getTopics() {

        String topics = "";
        for (SubjectsModel s : Subjects) {
            topics += s.getTopic() + ",";
        }
        return topics;
    }

    public String getRefrenceType(){
        String refsType="";
        for(BitStreamGroupsModel refType : BitStreamGroups ){
            refsType+=refType.getTitle()+",";
        }

        return refsType;
    }
}

    //    "Result": {
//        "IsForRead": false,
//                "Title": "ارتباط دست نویسی با حس حرکت در دانش آموزان کم توان ذهنی",
//                "Author": "فاطمه هداوندخانی، هادی بهرامی، فاطمه بهنیا، مژگان فرهبد، مسعود صالحی ",
//                "Publisher": "موسسه فرهنگی و اطلاع رسانی تبیان ",
//                "ImageUrl": "http://library.tebyan.net/images/DefaulImage/metadataarticle.jpg#1",
//                "MetadataID": 185699,
//                "Description": "منبع: فصلنامه پژوهش در حيطه كودكان استثنايي, بهار 1386, دوره 7, شماره 1 (پياپي 23)",
//                "BitStreamGroups": [
//        {
//            "GroupID": 219661,
//                "Title": "نسخه PDF"
//        }
//        ],
//        "Subjects": [
//        {
//            "Topic": "نوشتن‌",
//                "ID": 268091
//        },
//        {
//            "Topic": "آموزش‌ استثنايي‌",
//                "ID": 271659
//        },
//        {
//            "Topic": "آموزش‌و پرورش‌ کودکان‌ استثنايي‌",
//                "ID": 271718
//        },
//        {
//            "Topic": "دانش‌ آموزان‌ استثنايي‌ +",
//                "ID": 271857
//        },
//        {
//            "Topic": "دانش‌ آموزان‌ عقب‌ مانده‌",
//                "ID": 271861
//        },
//        {
//            "Topic": "اختلال‌هاي‌ يادگيري‌ +",
//                "ID": 273978
//        },
//        {
//            "Topic": "کودکان‌ عقب‌ مانده‌",
//                "ID": 274241
//        },
//        {
//            "Topic": "ناتوانايي‌ هاي‌ يادگيري‌ +",
//                "ID": 274275
//        }
//        ],
//        "Genre": null,
//                "Link": "http://library.tebyan.net/fa/Viewer/Switcher/219661/0?Frame=true"

