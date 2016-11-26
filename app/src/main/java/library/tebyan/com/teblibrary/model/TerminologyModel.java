package library.tebyan.com.teblibrary.model;

import java.util.ArrayList;

public class TerminologyModel {

	private String Title;
	private ArrayList<TerminologyItem> Items;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public ArrayList<TerminologyItem> getItems() {
		return Items;
	}

	public void setItems(ArrayList<TerminologyItem> items) {
		Items = items;
	}
}


//
//"Result": [{
//		"Title": "اصطلاحنامه فرهنگی فارسی",
//		"Items": [{
//		"Topic": "آموزش و پرورش",
//		"SubjectID": 51
//		}]
