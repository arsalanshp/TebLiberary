package library.tebyan.com.teblibrary.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BitStreamGroupsModel{
	int GroupID;
	String Title;

	public int getGroupID() {
		return GroupID;
	}

	public void setGroupID(int groupID) {
		GroupID = groupID;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}
}

//"BitStreamGroups": [
//		{
//		"GroupID": 219661,
//		"Title": "نسخه PDF"
//		}
//		]
