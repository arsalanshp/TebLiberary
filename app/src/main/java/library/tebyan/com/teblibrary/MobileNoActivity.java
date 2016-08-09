package library.tebyan.com.teblibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import library.tebyan.com.teblibrary.classes.DataProvider;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Login.Login;
import library.tebyan.com.teblibrary.classes.ReadJSONTaskInput;
import library.tebyan.com.teblibrary.classes.Sms.Sms;
import library.tebyan.com.teblibrary.classes.Sms.SmsListener;
import library.tebyan.com.teblibrary.classes.Sms.SmsRadar;
import library.tebyan.com.teblibrary.classes.Sms.StringResult;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.model.BoolResult;
import library.tebyan.com.teblibrary.model.IntResult;
import library.tebyan.com.teblibrary.model.MemberActivationResult;

public class MobileNoActivity extends AppCompatActivity {

    public MobileNoActivity current;
    public EditText mobileNoEditText;
    public EditText activationCodeEditText;
    public LinearLayout activationCodeLinearLayout;
    public Button sendButton;
    public Button resendButton;
    public Button editMobileButton;
    public Button goButton;
    // File file;
    ProgressDialog progressDialog;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove title bar
        // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Remove notification bar
        // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_mobile_no);
        current = this;

        mobileNoEditText = (EditText) findViewById(R.id.mobileNoEditText);
        sendButton = (Button) findViewById(R.id.sendButton);

        activationCodeLinearLayout = (LinearLayout) findViewById(R.id.activationCodeLinearLayout);
        activationCodeEditText = (EditText) findViewById(R.id.activationCodeEditText);
        goButton = (Button) findViewById(R.id.goButton);
        resendButton = (Button) findViewById(R.id.resendButton);
        editMobileButton = (Button) findViewById(R.id.editMobileButton);

        activationCodeEditText.setVisibility(View.GONE);
        activationCodeLinearLayout.setVisibility(View.GONE);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNo = mobileNoEditText.getText().toString().trim();
                checkMobileNo(mobileNo);
            }
        });

        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNo = mobileNoEditText.getText().toString().trim();
                checkMobileNo(mobileNo);
            }
        });

        editMobileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activationCodeEditText.setVisibility(View.GONE);
                activationCodeLinearLayout.setVisibility(View.GONE);

                mobileNoEditText.setVisibility(View.VISIBLE);
                sendButton.setVisibility(View.VISIBLE);
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activationCode = activationCodeEditText.getText()
                        .toString().trim();
                checkActivationCode(activationCode);
            }
        });

    }

    public void checkMobileNo(String mobileNo) {

        String text = Globals.context.getResources().getString(
                R.string.plz_wait);
        progressDialog = ProgressDialog.show(current, "", text);

        JSONObject data = new JSONObject();
        try {
            data.put("Value", mobileNo);
        } catch (JSONException e) {

        }
        ReadJSONTaskInput input = new ReadJSONTaskInput();
        input.url = "/CheckMobile";
        input.baseUrl = Globals.regServicesBaseUrl;
        input.data = data;
        input.needsLogin = true;
        new ReadJSONTask().execute(input);
    }

    public void sendActivationCode(String mobileNo) {
        initializeSmsRadarService();
        String text = Globals.context.getResources().getString(
                R.string.plz_wait);
        progressDialog = ProgressDialog.show(current, "", text);

        JSONObject data = new JSONObject();
        try {
            data.put("Type", "2");
            data.put("Value", mobileNo);
            data.put("RetUrl", "");
        } catch (JSONException e) {

        }
        ReadJSONTaskInput input = new ReadJSONTaskInput();
        input.url = "/SendEmail_SMS";
        input.baseUrl = Globals.regServicesBaseUrl;
        input.data = data;
        input.needsLogin = true;
        new ReadJSONTask1().execute(input);
    }

    public void checkActivationCode(String activationCode) {

        String text = Globals.context.getResources().getString(
                R.string.plz_wait);
        progressDialog = ProgressDialog.show(current, "", text);

        JSONObject data = new JSONObject();
        try {
            data.put("Type", "2");
            data.put("q", activationCode);
        } catch (JSONException e) {

        }
        ReadJSONTaskInput input = new ReadJSONTaskInput();
        input.url = "/DoMemberAvalieh";
        input.baseUrl = Globals.regServicesBaseUrl;
        input.data = data;
        input.needsLogin = true;
        new ReadJSONTask2().execute(input);
    }

    public void createNetworkUser() {
        String text = Globals.context.getResources().getString(
                R.string.plz_wait);
        progressDialog = ProgressDialog.show(current, "", text);

        JSONObject data = new JSONObject();

        ReadJSONTaskInput input = new ReadJSONTaskInput();
        input.url = "/MobileApi.asmx/CreateNetworkUser";
        input.data = data;
        input.needsLogin = true;
        new ReadJSONTask3().execute(input);
    }

    private void initializeSmsRadarService() {
        SmsRadar.initializeSmsRadarService(current, new SmsListener() {
            @Override
            public void onSmsSent(Sms sms) {

            }

            @Override
            public void onSmsReceived(Sms sms) {
                if (sms.getAddress().contains("50004686")
                        || sms.getAddress().contains("30008130")) {
                    String activationCode = sms.getMsg().substring(15, 23);
                    activationCodeEditText.setText(activationCode);
                    // checkActivationCode(activationCode);
                } else {
                    // other sms
                }
                stopSmsRadarService();
            }

        });
    }

    private void stopSmsRadarService() {
        SmsRadar.stopSmsRadarService(this);
    }

    public void startMainActivity() {
        Intent intent = new Intent(current, MainActivity.class);
        intent.putExtra("imageType", "Profile");
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    public void startLoginActivity() {
        Intent intent = new Intent(current, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // finish();
        Globals.logout();
        startLoginActivity();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(current, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
                            String mobileNo = mobileNoEditText.getText()
                                    .toString().trim();
                            sendActivationCode(mobileNo);
                        } else {
                            String text = Globals.context
                                    .getResources()
                                    .getString(R.string.repeated_mobileno_error);
                            Utils.showDefaultCustomizedToast(current, text + " ");
                        }
                    } else {
                        Utils.showDefaultCustomizedToast(current, getString(R.string.repeated_mobileno_error));
                    }
                } else {
                    String text = Globals.context.getResources().getString(
                            R.string.network_connection_fail);
                    Utils.showDefaultCustomizedToast(current, text + " ");
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
                    if (result != null && result.d != null
                            && result.d.length() > 0) {
                        /*Toast.makeText(Globals.context, result.d + " ",
                                Toast.LENGTH_LONG).show();*/
                        Utils.showDefaultCustomizedToast(current, result.d + " ");
                    } else {
                        String text = Globals.context.getResources().getString(
                                R.string.activation_code_sent);
                        Utils.showDefaultCustomizedToast(current, text + " ");
                        mobileNoEditText.setVisibility(View.GONE);
                        sendButton.setVisibility(View.GONE);

                        activationCodeEditText.setVisibility(View.VISIBLE);
                        activationCodeLinearLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    String text = Globals.context.getResources().getString(
                            R.string.network_connection_fail);
                    Utils.showDefaultCustomizedToast(current, text + " ");
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
                    MemberActivationResult result = gson.fromJson(json,
                            MemberActivationResult.class);
                    if (result != null && result.d != null) {
                        if (result.d.Error != null && result.d.Error != "") {
                            Utils.showDefaultCustomizedToast(current, result.d.Error + " ");
                        } else {
                            createNetworkUser();
                        }
                    } else {
                        startLoginActivity();
                    }
                } else {
                    String text = Globals.context.getResources().getString(
                            R.string.network_connection_fail);
                    Utils.showDefaultCustomizedToast(current, text + " ");
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
                    IntResult result = gson.fromJson(json, IntResult.class);
                    if (result != null) {
                        if (result.d > 0) {
                            Globals.networkUserId = result.d;
                            Login lastLogin = Globals.database.getLogin();

                            if (lastLogin != null) {
                                Globals.database.saveLogin(lastLogin.username,
                                        lastLogin.password,
                                        Globals.networkUserId);
                                /*startMainActivity();
                            } else {*/
                                startLoginActivity();
                            }

                        } else {
                            String text = Globals.context
                                    .getResources()
                                    .getString(
                                            R.string.operation_failed_please_try_again);
                            Utils.showDefaultCustomizedToast(current, text + "");
                        }

                    }
                } else {
                    String text = Globals.context.getResources().getString(
                            R.string.network_connection_fail);
                    Utils.showDefaultCustomizedToast(current, text + "");
                }
                super.onPostExecute(json);
            } catch (Exception e) {
            }
        }
    }

}
