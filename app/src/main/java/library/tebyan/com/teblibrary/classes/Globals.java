package library.tebyan.com.teblibrary.classes;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;

import com.koushikdutta.ion.Ion;

import library.tebyan.com.teblibrary.classes.Database.Database;
import library.tebyan.com.teblibrary.classes.Network_User.NetworkUser;

public class Globals extends Application {

    public static String regServicesBaseUrl = "https://tebyan.net/WebServices/Membership/Membership.asmx";
    public static String servicesBaseUrl = "http://tebyan.net/WebServices/SocialNetwork_v2";
    public static String uploadServerUri = "http://tebyan.net/Registeration/SocialNetwork_v2/Common/MultiUpload.aspx?Mode=";
    public static Context context;
    public static LayoutInflater inflater;
    public static Ion ion;
    public static String deviceId;
    public static String userToken = null;
    public static String userToken_socialNetwork = null;
    public static int networkUserId = 0;
    public static int networkUserId_socialNetwork = 0;
    public static NetworkUser mainNetworkUser = null;
    public static NetworkUser selectedNetworkUser = null;
    public static Database database;
    public static boolean guest;


    public static void logout() {
        Globals.database.removeLogin();
        Globals.networkUserId = 0;
        Globals.userToken = null;
        Globals.mainNetworkUser = null;
        Globals.selectedNetworkUser = null;
    }

    public static boolean isGuest() {
        // if ( Globals.database.getLogin()==null
        if (Globals.mainNetworkUser == null) {
            guest = true;
            return guest;
        } else {
            guest = false;
            return guest;
        }
    }

//    public static void refreshToken(final String username, final String password, final Context context) {
//
//        if (InternetConnection.isOnline(context)) {
//            JsonObject json = new JsonObject();
//            json.addProperty("username", username);
//            json.addProperty("password", password);
//            Ion.with(context)
//                    .load(WebserviceUrl.LoginForMobile + username + "&password=" + password)
//                    .setTimeout(1000000000)
//                    .asJsonObject()
//                    .setCallback(new FutureCallback<JsonObject>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonObject result) {
//                            if (result != null && e == null)
//                                if (result != null && result.has("Token")) {
//                                    String Token = result.get("Token").getAsString();
//                                    int NetworkUserId = result.get("UserID").getAsInt();
//                                    Globals.networkUserId = NetworkUserId;
//                                    Globals.userToken = Token;
//                                    //saveUserPassToken(username, password, Token, NetworkUserId, context);
//                                }
//                        }
//                    });
//        } else
//            Toast.makeText(context, R.string.network_connection_fail, Toast.LENGTH_SHORT).show();
//    }

//    public static void saveNameAndImageUrl(String fullName, String imageUrl, Context context) {
//        SharedPreferences sp = context.getSharedPreferences("Login", 0);
//        SharedPreferences.Editor ed = sp.edit();
//        ed.putString("fullName", fullName);
//        ed.putString("imageUrl", imageUrl);
//        ed.commit();
//    }
//
//    public static void clearSharedPreferences(Context context) {
//        SharedPreferences preferences = context.getSharedPreferences("Login", 0);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.clear();
//        editor.commit();
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       database = new Database(context);
     TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
       deviceId = telephonyManager.getDeviceId();
    /*    TypefaceUtil.overrideFont(getApplicationContext(), "SERIF",
                "fonts/IRAN Sans Light.ttf");*/
    }
}
