package library.tebyan.com.teblibrary.classes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import com.koushikdutta.ion.Ion;

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

/**
 * Created by F.piri on 1/17/2016.
 */


public class Application extends android.app.Application {
    public static Ion ion;
    public static String regServicesBaseUrl = "http://tebyan.net/WebServices/Membership/Membership.asmx";
    public static String servicesBaseUrl = "http://tebyan.net/WebServices/SocialNetwork_v2";
    public static String uploadServerUri = "http://tebyan.net/Registeration/SocialNetwork_v2/Common/MultiUpload.aspx?Mode=";
    public static Context context;
    public static LayoutInflater inflater;
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
    @Override
    public void onCreate() {
        super.onCreate();
        /*TypefaceUtil.overrideFont(getApplicationContext(), "SERIF",
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
