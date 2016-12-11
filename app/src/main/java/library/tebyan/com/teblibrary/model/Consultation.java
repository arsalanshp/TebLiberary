package library.tebyan.com.teblibrary.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by v.karimi on 7/24/2016.
 */
public class Consultation {

    public String Error;
    public ConsultationObject Object;

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public ConsultationObject getObject() {
        return Object;
    }

    public void setObject(ConsultationObject object) {
        Object = object;
    }
}

//
//{
//        "Result": {
//        "Error": null,
//        "Object": {
//        "ConsultationID": "0",
//        "password": "AKSMTT"
//        }
//        }
//        }
