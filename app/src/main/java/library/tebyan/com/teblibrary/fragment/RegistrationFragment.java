package library.tebyan.com.teblibrary.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import library.tebyan.com.teblibrary.MainActivity;
import library.tebyan.com.teblibrary.MobileNoActivity;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.DataProvider;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Login.LoginResult;
import library.tebyan.com.teblibrary.classes.ReadJSONTaskInput;
import library.tebyan.com.teblibrary.classes.Sms.StringResult;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.model.BoolResult;
import library.tebyan.com.teblibrary.shared.Shared;


//public class RegistrationFragment extends Fragment implements View.OnClickListener {
public class RegistrationFragment extends Fragment {

    Button regButton;
    EditText usernameEditText;
    EditText nicknameEditText;
    EditText passwordEditText;
    ProgressDialog progressDialog;
    CheckBox checkBox1;
    Activity current;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registration, container, false);
        current = getActivity();
        Globals.context = getActivity();
        regButton = (Button) v.findViewById(R.id.regButton);
        usernameEditText = (EditText) v.findViewById(R.id.usernameEditText);
        nicknameEditText = (EditText) v.findViewById(R.id.nicknameEditText);
        passwordEditText = (EditText) v.findViewById(R.id.passwordEditText_reg);
        checkBox1 = (CheckBox) v.findViewById(R.id.checkBox_reg);
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    passwordEditText
                            .setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    passwordEditText.setInputType(129);
                }
            }
        });
        regButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();
                String nickname = nicknameEditText.getText().toString().trim();

                if (localValidate(nickname, username, password)) {
                    checkUsername(username);
                }
            }
        });
        return v;
    }


    public boolean localValidate(String nickname, String username,
                                 String password/* , String rePassword */) {
        boolean result = true;
        String text = "";

        if (username.length() < 6) {
            text = Globals.context.getResources().getString(
                    R.string.min_username_error);
            usernameEditText.setError(text);
            result = false;
        } else if (!Utils.startsWithEn(username)) {
            text = Globals.context.getResources().getString(
                    R.string.format_begin_username_error);
            usernameEditText.setError(text);
            result = false;
        } else if (!Utils.validateUsernameChars(username)) {
            text = Globals.context.getResources().getString(
                    R.string.format_username_error);
            usernameEditText.setError(text);
            result = false;
        } else if (nickname.length() < 6) {
            text = Globals.context.getResources().getString(
                    R.string.min_nickname_error);
            nicknameEditText.setError(text);
            result = false;
        } else if (!Utils.startsWithEnOrFa(nickname)) {
            text = Globals.context.getResources().getString(
                    R.string.format_begin_nickname_error);
            nicknameEditText.setError(text);
            result = false;
        } else if (!Utils.validateNicknameChars(nickname)) {
            text = Globals.context.getResources().getString(
                    R.string.format_nickname_error);
            nicknameEditText.setError(text);
            result = false;
        } else if (password.length() < 6 || password.length() > 16) {
            text = Globals.context.getResources().getString(
                    R.string.len_password_error);
            passwordEditText.setError(text);
            result = false;
        }
        return result;
    }

    public void checkUsername(String username) {

        String text = Globals.context.getResources().getString(
                R.string.plz_wait);
        progressDialog = ProgressDialog.show(current, "", text);

        JSONObject data = new JSONObject();
        try {
            data.put("Value", username);
        } catch (JSONException e) {

        }
        ReadJSONTaskInput input = new ReadJSONTaskInput();
        input.url = "/CheckUserName";
        input.baseUrl = Globals.regServicesBaseUrl;
        input.data = data;
        new ReadJSONTask().execute(input);
       /* Application.ion.with(getContext()).load(Globals.regServicesBaseUrl+"/CheckUserName")
                .setBodyParameter("Value", username).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                Log.v("sdfsd",result);
            }
        });*/
    }

    public void checkNickname(String nickname) {

        String text = Globals.context.getResources().getString(
                R.string.plz_wait);
        progressDialog = ProgressDialog.show(current, "", text);

        JSONObject data = new JSONObject();
        try {
            data.put("Value", nickname);
        } catch (JSONException e) {

        }
        ReadJSONTaskInput input = new ReadJSONTaskInput();
        input.url = "/CheckNickName";
        input.baseUrl = Globals.regServicesBaseUrl;
        input.data = data;
        new ReadJSONTask1().execute(input);
/*Application.ion.with(getContext()).load(WebserviceUrl.)*/
    }

    public void submit(String username, String nickname, String password) {

        String text = Globals.context.getResources().getString(
                R.string.plz_wait);
        progressDialog = ProgressDialog.show(current, "", text);

        JSONObject data = new JSONObject();
        try {
            data.put("UserName", username);
            data.put("NickName", nickname);
            data.put("PassWord", password);
            data.put("CaptchaText", "");
            data.put("Sessions", "");
        } catch (JSONException e) {
        }

        ReadJSONTaskInput input = new ReadJSONTaskInput();
        input.url = "/SaveMemberInf_Avalieh";
        input.baseUrl = Globals.regServicesBaseUrl;
        input.data = data;
        new ReadJSONTask2().execute(input);
    }

    public void startMainActivity() {
        Intent intent = new Intent(current, MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void startMobileNoActivity() {
        Intent intent = new Intent(current, MobileNoActivity.class);
        startActivity(intent);
        // finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getActivity().finish();
        return super.onOptionsItemSelected(item);
    }

    public void login(String username, String password) {

        String text = Globals.context.getResources().getString(
                R.string.plz_wait);
        progressDialog = ProgressDialog.show(current, "", text);

        JSONObject data = new JSONObject();
        try {
            data.put("username", username);
            data.put("password", password);
        } catch (JSONException e) {

        }
        ReadJSONTaskInput input = new ReadJSONTaskInput();
        input.url = "/mobileapi.asmx/Login";
        input.data = data;
        new ReadJSONTask3().execute(input);

    }

    private class ReadJSONTask extends
            AsyncTask<ReadJSONTaskInput, Void, String> {

        @Override
        protected String doInBackground(ReadJSONTaskInput... inputs) {
            return DataProvider.readJSONFeed(inputs[0]);
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                progressDialog.dismiss();
                Gson gson = new Gson();
                if (json != null & json.length() > 0) {
                    BoolResult result = gson.fromJson(json, BoolResult.class);
                    if (result != null) {
                        if (result.d) {
                            String nickname = nicknameEditText.getText()
                                    .toString().trim();
                            checkNickname(nickname);
                        } else {
                            String text = Globals.context
                                    .getResources()
                                    .getString(R.string.repeated_username_error);
                            Utils.showDefaultCustomizedToast(getActivity(), text + " ");
                        }
                    } else {
                        String text = Globals.context.getResources().getString(
                                R.string.repeated_username_error);
                        Utils.showDefaultCustomizedToast(getActivity(), text + " ");
                    }
                } else {
                    String text = Globals.context.getResources().getString(
                            R.string.network_connection_fail);
                    Utils.showDefaultCustomizedToast(getActivity(), text + " ");
                }
                super.onPostExecute(json);
            } catch (Exception e) {
            }
        }
    }

    private class ReadJSONTask1 extends
            AsyncTask<ReadJSONTaskInput, Void, String> {

        @Override
        protected String doInBackground(ReadJSONTaskInput... inputs) {
            return DataProvider.readJSONFeed(inputs[0]);
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                progressDialog.dismiss();
                Gson gson = new Gson();
                if (json != null & json.length() > 0) {
                    StringResult result = gson.fromJson(json,
                            StringResult.class);
                    if (result != null) {
                        if (result.d != null) {
                            String msg = Jsoup.parse(result.d).text();
                            Utils.showDefaultCustomizedToast(getActivity(), msg);
                        } else {
                            String username = usernameEditText.getText()
                                    .toString().trim();
                            String password = passwordEditText.getText()
                                    .toString();
                            String nickname = nicknameEditText.getText()
                                    .toString().trim();
                            submit(username, nickname, password);
                        }
                    }
                } else {
                    String text = Globals.context.getResources().getString(
                            R.string.network_connection_fail);
                    Utils.showDefaultCustomizedToast(getActivity(), text + " ");
                }
                super.onPostExecute(json);
            } catch (Exception e) {
            }
        }
    }

    private class ReadJSONTask2 extends
            AsyncTask<ReadJSONTaskInput, Void, String> {

        @Override
        protected String doInBackground(ReadJSONTaskInput... inputs) {
            return DataProvider.readJSONFeed(inputs[0]);
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                progressDialog.dismiss();
                Gson gson = new Gson();
                if (json != null & json.length() > 0) {
                    StringResult result = gson.fromJson(json,
                            StringResult.class);
                    if (result != null) {
                        if (result.d != null) {
                            String msg = Jsoup.parse(result.d).text();
                            Utils.showDefaultCustomizedToast(getActivity(), msg + " ");
                        } else {
                            String username = usernameEditText.getText()
                                    .toString().trim();
                            String password = passwordEditText.getText()
                                    .toString();

                            login(username, password);
                        }
                    }
                } else {
                    String text = Globals.context.getResources().getString(
                            R.string.network_connection_fail);
                    Utils.showDefaultCustomizedToast(getActivity(), text + " " + " ");
                }
                super.onPostExecute(json);
            } catch (Exception e) {
            }
        }
    }

    private class ReadJSONTask3 extends
            AsyncTask<ReadJSONTaskInput, Void, String> {

        @Override
        protected String doInBackground(ReadJSONTaskInput... inputs) {
            return DataProvider.readJSONFeed(inputs[0]);
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                progressDialog.dismiss();
                Gson gson = new Gson();
                if (json != null & json.length() > 0) {
                    LoginResult result = gson.fromJson(json, LoginResult.class);
                    if (result != null && result.d != null
                            && result.d.Token.length() > 0) {
                        /*Globals.userToken_socialNetwork = result.d.Token;*/
                        Shared.setData(getContext(),Shared.SOCIAL_TOKEN,result.d.Token);

                        Globals.networkUserId_socialNetwork = result.d.NetworkUserId;

                        String username = usernameEditText.getText().toString()
                                .trim();
                        String password = passwordEditText.getText().toString();

                        Globals.database.saveLogin(username, password,
                                Globals.networkUserId_socialNetwork);
                        //Globals.refreshToken(username, password, getActivity());
                        //DataProvider.refreshToken();
                        if (result.d.NetworkUserId > 0) {
                            startMainActivity();
                        } else {
                            startMobileNoActivity();
                        }

                    } else {
                        Utils.showDefaultCustomizedToast(getActivity(), getString(R.string.wrong_user_pass));
                    }
                } else {
                    String text = Globals.context.getResources().getString(
                            R.string.network_connection_fail);
                    Utils.showDefaultCustomizedToast(getActivity(), text + " ");
                }
                super.onPostExecute(json);
            } catch (Exception e) {
                Exception x = e;
            }
        }
    }
}

