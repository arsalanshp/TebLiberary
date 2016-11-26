package library.tebyan.com.teblibrary.model;

import java.util.ArrayList;

/**
 * Created by v.karimi on 8/10/2016.
 */
public class QuestionList {

    private ArrayList<BookerQuestion> Result;

    public ArrayList<BookerQuestion> getResult() {
        return Result;
    }

    public void setResult(ArrayList<BookerQuestion> result) {
        Result = result;
    }
}

//
//[{
//        "Question": "با سلام خدمت \nمن در کتابخانه کتاب \"ششصد و شصت و پنج پرسش و پاسخ در محضر علاّمه طباطبائى قدس سره \" را وارد کتابخانه خودم کرده ام ولی نمی توانم آنرا باز کنم و بخوانم . فقط فهرست موجود است. لطفا راهنماییم کنید.\nمتشکرم",
//        "Link": "http://www.tebyan.net/newindex.aspx?pid=78685\u0026consultationid=626286"
//        },]