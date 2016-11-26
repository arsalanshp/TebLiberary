package library.tebyan.com.teblibrary.model;

import java.util.ArrayList;

/**
 * Created by root on 8/10/16.
 */

public class BookDetailsResults{


//    public BookDetailsResults(BookDetails bookDetails){
//        Result =  bookDetails;
//    }

    public BookDetails Result;
    public BookDetails getResult() {return Result;}
    public void setResult(BookDetails result) {
        Result = result;
    }
}

//  SAMPLE :
//{
//        "Result": {
//        "Title": "معارف قرآن در المیزان"
//        "Author": "علامه سید محمدحسین طباطبایی؛ تألیف: سید مهدی (حبیبی) امین"
//        "Publisher": "موسسه فرهنگی و اطلاع رسانی تبیان "
//        "ImageUrl": "http://img.tebyan.net/DL_BitStream//196816/thumb/2822919.jpg"
//        "MetadataID": 170091
//        "Description": "صفحات نمایش محتوا مطابق با صفحات کاغذی اثر می باشد. تجدید نظر: 1394"
//        "BitStreamGroups": [3]
//        0:  {
//        "GroupID": 196816
//        "Title": "تصویر جلد"
//        }-
//        1:  {
//        "GroupID": 196817
//        "Title": "نسخه PDF"
//        }-
//        2:  {
//        "GroupID": 204418
//        "Title": "نسخه متنی"
//        }-
//        -
//        }-
//        }