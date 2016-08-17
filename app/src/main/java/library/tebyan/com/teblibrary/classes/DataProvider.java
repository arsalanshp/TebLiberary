package library.tebyan.com.teblibrary.classes;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import library.tebyan.com.teblibrary.classes.Login.Login;
import library.tebyan.com.teblibrary.classes.Login.LoginResult;

public class DataProvider {

    public static String readJSONFeed(ReadJSONTaskInput input) {

        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();

        StringEntity reqEntity = null;
        reqEntity = new StringEntity(input.data.toString(), HTTP.UTF_8);
        Log.e("DataProvider", input.data.toString());

        if (input.baseUrl == null) {
            input.baseUrl = Globals.servicesBaseUrl;
        }
        HttpPost httpPost = new HttpPost(input.baseUrl + input.url);
        httpPost.setEntity(reqEntity);
        try {
            httpPost.setHeader("content-type", "application/json");
            httpPost.setHeader("X-Device", "MOBILE");
            httpPost.setHeader("X-Device-Type", "ANDROID");
            httpPost.setHeader("X-Device-Id", Globals.deviceId);
            Log.e("DataProvider:DEVICEID", Globals.deviceId);
            if (input.needsLogin) {
                if (Globals.userToken != null && input.needsLogin) {
                    httpPost.setHeader("userToken", Globals.userToken);
                    Log.e("DataProvider:TOKEN", Globals.userToken);
                }
                if (Globals.userToken == null
                        || (Globals.userToken != null && Globals.userToken
                        .length() == 0)) {
                    Globals.userToken = refreshToken();
                    return readJSONFeed(input);
                }
                httpPost.setHeader("checkToken", "true");
                Log.e("checkToken", "TRUE");
            } else {
                httpPost.setHeader("checkToken", "false");
            }
            if (Globals.selectedNetworkUser != null) {
                httpPost.setHeader("X-Group-Id",
                        String.valueOf(Globals.selectedNetworkUser.Id));
            }
            HttpResponse response = httpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200/* ok */) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else if (statusCode == 403/* forbiden */) {
                if (input.needsLogin) {
                    Log.e("DataProvider", "403 ** Reresh Token");
                    Globals.userToken = refreshToken();
                    return readJSONFeed(input);
                }
            } else {
                Log.e("DataProvider", statusLine + " > " + response + " > "
                        + String.valueOf(statusCode)
                        + " > Failed to download file");
                return ""; // Failed to download file"
            }
        } catch (Exception e) {
            if (e != null && e.getLocalizedMessage() != null) {
                Log.e("DataProvider", e.getLocalizedMessage());
            }
            return "";

        }
        Log.e("DataProvider", stringBuilder.toString());
        return stringBuilder.toString();
    }

    public static String refreshToken() {
        ReadJSONTaskInput input = new ReadJSONTaskInput();
        try {
            Login lastLogin = Globals.database.getLogin();
            if (lastLogin != null) {
                input.data.put("username", lastLogin.username);
                input.data.put("password", lastLogin.password);
            }
        } catch (JSONException e) {

        }
        input.url = "/mobileapi.asmx/Login";
        String json = readJSONFeed(input);
        Gson gson = new Gson();
        LoginResult result = gson.fromJson(json, LoginResult.class);

        Globals.userToken_socialNetwork = result.d.Token;
        Globals.networkUserId_socialNetwork = result.d.NetworkUserId;

        return result.d.Token;
    }

    public static String uploadFile(File file, int mode) {

        StringBuilder stringBuilder = new StringBuilder();

        String url = Globals.uploadServerUri + mode;
        // File file = new File(sourceFileUri);
        HttpResponse response;
        try {
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost(url);

            FileEntity reqEntity = new FileEntity(file, "binary/octet-stream");
            // InputStreamEntity reqEntity = new InputStreamEntity(
            // fileInputStream, -1);
            reqEntity.setContentType("binary/octet-stream");
            reqEntity.setChunked(true); // Send in multiple parts if needed

            // String filenameArray[] = sourceFileUri.split("\\.");
            // String extension = filenameArray[filenameArray.length - 1];

            if (mode == 1 || mode == 6) {
                httppost.addHeader("X-File-Name", "android.jpg");
                httppost.addHeader("X-File-Extension", "." + "jpg");
            }
            if (mode == 2) {
                httppost.addHeader("X-File-Name", "android.mp3");
                httppost.addHeader("X-File-Extension", "." + "mp3");
            }
            if (mode == 3) {
                httppost.addHeader("X-File-Name", "android.mp4");
                httppost.addHeader("X-File-Extension", "." + "mp4");
            }
            httppost.addHeader("X-File-Size", String.valueOf(1024));

            httppost.addHeader("X-File-Mobile", "ANDROID");
            httppost.addHeader("X-Device", "MOBILE");
            httppost.addHeader("X-Device-Type", "ANDROID");
            httppost.addHeader("X-Device-Id", Globals.deviceId);
            httppost.addHeader("checkToken", "true");
            if (Globals.userToken_socialNetwork != null) {
                httppost.setHeader("userToken", Globals.userToken_socialNetwork);
            }
            if (Globals.selectedNetworkUser != null) {
                httppost.setHeader("X-Group-Id",
                        String.valueOf(Globals.selectedNetworkUser.Id));
            }
            httppost.setEntity(reqEntity);
            response = httpclient.execute(httppost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200/* ok */) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
                Log.e("DataProvider", statusLine + " > " + response + " > "
                        + String.valueOf(statusCode) + " > UPLOAD OK!");
            } else if (statusCode == 403/* forbiden */) {
                refreshToken();
                return uploadFile(file, mode);

            } else {
                Log.e("DataProvider", statusLine + " > " + response + " > "
                        + String.valueOf(statusCode)
                        + " > Failed to download file");
                return null; // Failed to download file"
            }

        } catch (Exception e) {
            Log.e("UPLOAD FILE", e.getMessage() + " ");
        }
        return stringBuilder.toString();
    }

//    public static String uploadFile(File file, String filename) {
//        StringBuilder stringBuilder = new StringBuilder();
//        String url = WebserviceUrl.MobileUpload + filename;
//        HttpResponse response;
//        try {
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpPost httppost = new HttpPost(url);
//            FileEntity reqEntity = new FileEntity(file, "binary/octet-stream");
//            httppost.addHeader("checkToken", "true");
//            if (Globals.userToken != null) {
//                httppost.setHeader("userToken", Globals.userToken);
//            }
//            httppost.setEntity(reqEntity);
//            response = httpclient.execute(httppost);
//            StatusLine statusLine = response.getStatusLine();
//            int statusCode = statusLine.getStatusCode();
//            if (statusCode == 200/* ok */) {
//                HttpEntity entity = response.getEntity();
//                InputStream inputStream = entity.getContent();
//                BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(inputStream));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    stringBuilder.append(line);
//                }
//                inputStream.close();
//            } else if (statusCode == 403/* forbiden */) {
//                //Globals.userToken = refreshToken();
//                Globals.refreshToken(Globals.database.getLogin().username, Globals.database.getLogin().password, Globals.context);
//                return uploadFile(file, filename);
//
//            } else {
//                return null; // Failed to download file"
//            }
//
//        } catch (Exception e) {
//        }
//        return stringBuilder.toString();
//    }
}