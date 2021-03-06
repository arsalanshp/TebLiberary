package library.tebyan.com.teblibrary.classes;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.webkit.MimeTypeMap;

import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.Database.Database;
import library.tebyan.com.teblibrary.classes.Network_User.NetworkUser;

public class Globals extends Application {

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


    public static String getMimeType(File file) {
        MimeTypeMap map = MimeTypeMap.getSingleton();
        String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
        String type = map.getMimeTypeFromExtension(ext);
        if (type == null)
            type = "*/*";
        return type;
    }

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
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = getResources().openRawResource(R.raw.cer_tebyan);
            Certificate ca = cf.generateCertificate(caInput);
            caInput.close();
            KeyStore keyStore = KeyStore.getInstance("BKS");
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, wrappedTrustManagers, null);
            ion= Ion.getDefault(this);
            ion.configure().createSSLContext("TLS");
            ion.getHttpClient().getSSLSocketMiddleware().setSSLContext(sslContext);
            ion.getHttpClient().getSSLSocketMiddleware().setTrustManagers(wrappedTrustManagers);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // cert file stored in \app\src\main\res\raw

    }



    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0){
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkClientTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkClientTrusted", e.toString());
                        }
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0){
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkServerTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkServerTrusted", e.toString());
                        }
                    }
                }
        };
    }
    }

