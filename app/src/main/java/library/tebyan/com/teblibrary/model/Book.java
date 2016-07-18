package library.tebyan.com.teblibrary.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
	int id;
	String title;
	float rating;
	String thumbnail;
	String author;
	String dateUpload;

	public Book() {

	}

	Book(Parcel in) {
		id = in.readInt();
		title = in.readString();
		rating = in.readFloat();
		dateUpload = in.readString();
        author=in.readString();
        thumbnail=in.readString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getDateUpload() {
		return dateUpload;
	}

	public void setDateUpload(String dateUpload) {
		this.dateUpload = dateUpload;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
	public void writeToParcel(Parcel parcel, int arg1) {
		parcel.writeInt(id);
		parcel.writeString(title);
		parcel.writeFloat(rating);
		parcel.writeString(dateUpload);
        parcel.writeString(author);
        parcel.writeString(thumbnail);

	}

}
