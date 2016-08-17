package library.tebyan.com.teblibrary.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import library.tebyan.com.teblibrary.MainActivity;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.shared.Shared;


public class LoginFragment extends Fragment implements View.OnClickListener {

    String username, password;
    EditText passwordEditText;
    EditText usernameEditText;
    private ProgressBar progressBar;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login, container, false);
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar_login);
        passwordEditText = (EditText) v.findViewById(R.id.login_password);
        usernameEditText = (EditText) v.findViewById(R.id.login_userName);
        CheckBox showPassword = (CheckBox) v.findViewById(R.id.showPassword);
        v.findViewById(R.id.btn_login).setOnClickListener(this);
        v.findViewById(R.id.forgetButton).setOnClickListener(this);
        v.findViewById(R.id.login_as_guest).setOnClickListener(this);

       /* Globals.userToken = Shared.getData(getContext(),Shared.TOKEN);
        if (!Globals.userToken.equals("")) {

            startActivity(new Intent(getActivity(),MainActivity.class));
        }*/
//        Button login = (Button) v.findViewById(R.id.btn_login);
//        final TextView forgetButton = (TextView) v.findViewById(R.id.forgetButton);
//        TextView login_as_guest = (TextView) v.findViewById(R.id.login_as_guest);
//        login_as_guest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startMainActivity(true);
//            }
//        });


//        forgetButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                try {
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
//                            .parse(WebserviceUrl.forgetPass));
//                    startActivity(browserIntent);
//                } catch (ActivityNotFoundException e) {
//                    Utils.showDefaultCustomizedToast(getActivity(), "No application can handle this request."
//                            + " Please install a webbrowser");
//
//                    e.printStackTrace();
//                }
//            }
//        });

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    passwordEditText
                            .setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    passwordEditText.setInputType(129);
                }
            }
        });
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                username = usernameEditText.getText().toString();
//                password = passwordEditText.getText().toString();
//                login(username, password);
//            }
//        });
        return v;
    }


    public void startMainActivity(boolean guest) {
        Intent i = new Intent(getActivity(), MainActivity.class);
        if (guest)
            i.putExtra("guest", true);
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        getActivity().finish();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void login(String username, String password) {
        if (Utils.isOnline(getActivity())) {
            if (!username.equals("") && !password.equals("") && localValidate(username, password)) {
                progressBar.setVisibility(View.VISIBLE);
                Ion.with(this)
                        .load(WebserviceUrl.LoginForMobile + username + "&password=" + password)
                        .setTimeout(1000000000)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (result != null && e == null) {
                                    Globals.networkUserId = result.get("UserID").getAsInt();
                                    Globals.userToken=result.get("Token").getAsString();
                                    Shared.setData(getContext(),Shared.TOKEN,result.get("Token").getAsString());
                                }
                                //Globals.networkUserId = 1035955;
                                parseResult(result);
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            } else
                Utils.showDefaultCustomizedToast(getActivity(), getString(R.string.wrong_user_pass));
        } else
            Utils.showDefaultCustomizedToast(getActivity(), getString(R.string.network_connection_fail));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forgetButton:
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
                            .parse(WebserviceUrl.forgetPass));
                    startActivity(browserIntent);
                } catch (ActivityNotFoundException e) {
                    Utils.showDefaultCustomizedToast(getActivity(), "No application can handle this request."
                            + " Please install a webbrowser");

                    e.printStackTrace();
                }
                break;
            case R.id.login_as_guest:
                startMainActivity(true);
                break;
            case R.id.btn_login:
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                login(username, password);
                break;

        }
    }

    private void parseResult(JsonObject result) {
        if (result != null && result.has("Token") && result.has("UserID")) {
            String Token = result.get("Token").getAsString();
            int NetworkUserId = result.get("UserID").getAsInt();
            saveUserPassToken(username, password, Token, NetworkUserId);
            startMainActivity(false);
        } else
            Utils.showDefaultCustomizedToast(getActivity(), getString(R.string.wrong_user_pass));
    }

    public void saveUserPassToken(String username, String password, String token, int NetworkUserId) {
        Globals.userToken = token;
        Globals.networkUserId = NetworkUserId;
        Globals.database.saveLogin(username, password,
                Globals.networkUserId);
    }
//
    public boolean localValidate(String username, String password) {
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
        } else if (password.length() < 6 || password.length() > 16) {
            text = Globals.context.getResources().getString(
                    R.string.len_password_error);
            passwordEditText.setError(text);
            result = false;
        }
        return result;
    }

}
