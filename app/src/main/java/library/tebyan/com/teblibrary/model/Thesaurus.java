package library.tebyan.com.teblibrary.model;

import java.util.ArrayList;

/**
 * Created by root on 11/12/16.
 */
public class Thesaurus {

    private Navigate Navigate;
    private ArrayList<SubSubjects> SubSubjects;
    private MetadataListRowCount MetadataList;

    public library.tebyan.com.teblibrary.model.Navigate getNavigate() {
        return Navigate;
    }

    public void setNavigate(library.tebyan.com.teblibrary.model.Navigate navigate) {
        Navigate = navigate;
    }

    public ArrayList<library.tebyan.com.teblibrary.model.SubSubjects> getSubSubjects() {
        return SubSubjects;
    }

    public void setSubSubjects(ArrayList<library.tebyan.com.teblibrary.model.SubSubjects> subSubjects) {
        SubSubjects = subSubjects;
    }

    public MetadataListRowCount getMetadataList() {
        return MetadataList;
    }

    public void setMetadataList(MetadataListRowCount metadataList) {
        MetadataList = metadataList;
    }
}

//sample
//{
//        "Result": {
//                "Navigate": {
//                "BaseName": "کتابخانه ملی",
//                "Items": [{
//                "ID": 49,
//                "Title": "حقوق"
//                },]
//            },
//            "SubSubjects": [{
//                "ID": 269625,
//                "Title": "حقوق‌ بين‌ الملل‌ خصوصي‌"
//                }],
//            "MetadataList": {
//                "Result": [],
//                "RowCount": 0
//            }
//        }
//}