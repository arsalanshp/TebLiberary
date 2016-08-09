package library.tebyan.com.teblibrary.classes;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import library.tebyan.com.teblibrary.R;

/**
 * Created by v.karimi on 7/25/2016.
 */
public class Utils {

    static String enChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String faChars = "اآبویتجدرشنمسزهکحگلصفطقعضخظپچغثژاًهٔةءذ";
    static String numChars = "1234567890";

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public static void showDefaultCustomizedToast(Activity activity, String toastMsg) {
        LayoutInflater inflater = (LayoutInflater)
                activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup)
                activity.findViewById(R.id.like_popup_layout));
        ImageView imageView = (ImageView) layout.findViewById(R.id.like_popup_iv);
        TextView text = (TextView) layout.findViewById(R.id.like_popup_tv);
        text.setText(toastMsg);
        Toast toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    public static boolean validateUsernameChars(String p) {
        if (p.length() > 0) {
            for (int i = 0; i < p.length(); i++) {
                char s = p.charAt(i);
                if (enChars.indexOf(s) < 0 && numChars.indexOf(s) < 0
                        && s != '_') {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public static boolean startsWithEn(String p) {
        if (p.length() > 0) {
            char s = p.charAt(0);
            if (enChars.indexOf(s) >= 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean startsWithEnOrFa(String p) {
        if (p.length() > 0) {
            char s = p.charAt(0);
            if (enChars.indexOf(s) >= 0 || faChars.indexOf(s) >= 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    public static boolean validateNicknameChars(String p) {
        if (p.length() > 0) {
            for (int i = 0; i < p.length(); i++) {
                char s = p.charAt(i);
                if (enChars.indexOf(s) < 0 && faChars.indexOf(s) < 0
                        && numChars.indexOf(s) < 0 && s != '_' && s != ' ') {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public static Boolean isTablet(Context context) {

        if ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return true;
        }
        return false;
    }

    public static void addToFavorite(){

    }
    public static void addToReads(){

    }
}
