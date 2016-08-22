package library.tebyan.com.teblibrary.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by macbookpro on 1/13/16.
 */
public class Shared {
    public SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public static String TOKEN="token";
    public static String SOCIAL_TOKEN="social_token";
    public static String AVATAR_PATH="avatarPath";

    public static void setData(Context context,String key,String value) {
        /*PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key,value);*/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public static String getData(Context context,String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = preferences.getString(key, "");
        if(!name.equalsIgnoreCase("")) {
            return name;
        }else
            return "";
    }

}
