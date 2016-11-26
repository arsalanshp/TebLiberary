package library.tebyan.com.teblibrary.classes;

/**
 * Created by F.piri on 12/27/2015.
 */

import org.json.JSONObject;

public class ReadJSONTaskInput {

    public String url;
    public JSONObject data;
    public Boolean needsLogin;
    public String baseUrl;

    public ReadJSONTaskInput() {
        data = new JSONObject();
        needsLogin = false;
    }

    // public abstract void execute(String result);
}