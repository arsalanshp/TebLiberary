package library.tebyan.com.teblibrary.classes.Network_User;

import com.google.gson.Gson;

public class NetworkUser {

	public int Id;
	public String Name;
	public String ImageUrl;
	public String WallId;
	public String UserType;
	public Boolean IsPage;
	public Boolean IsVerified;
	public String CoverImageUrl;
	public String Status = "";
	public Boolean IsBlocked;
	public Boolean IsCurrentUserLike;
	public int LikeCount;

	public String toJSON() {
		if (this == null) {
			return "";
		}
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}

	public static NetworkUser fromJSON(String json) {
		if (json == null)
			return null;
		Gson gson = new Gson();
		NetworkUser item = gson.fromJson(json, NetworkUser.class);
		return item;
	}
}