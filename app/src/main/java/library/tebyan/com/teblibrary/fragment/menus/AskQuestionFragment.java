package library.tebyan.com.teblibrary.fragment.menus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.Consultation;
import library.tebyan.com.teblibrary.model.ConsultationResult;

public class AskQuestionFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Context context;
    private EditText userQuestionTxt,userName,userAge,userEmail, password;
    private RadioGroup radioButtonGroup;
    private CheckBox userUnKnow;
    private Spinner userEducationSpinner;
    private Button sendQuestionBTN,cancelQuestionBTN;
    private String name, age,email,question;
    private String[] userEducationTag;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_ask_question, container, false);
        context = view.getContext();
        initUI();
        initData();
        return view;
    }


    private void initUI() {
        password = (EditText)view.findViewById(R.id.password);
        userQuestionTxt=(EditText)view.findViewById(R.id.user_question_txt);
        userName=(EditText)view.findViewById(R.id.user_name);
        userAge=(EditText)view.findViewById(R.id.user_age);
        userEmail=(EditText)view.findViewById(R.id.user_email);
        sendQuestionBTN =(Button)view.findViewById(R.id.send_question_btn);
        sendQuestionBTN.setOnClickListener(this);
        radioButtonGroup = (RadioGroup) view.findViewById(R.id.user_gender);
        userUnKnow =(CheckBox)view.findViewById(R.id.user_unknow);
        userEducationSpinner=(Spinner)view.findViewById(R.id.user_education_spinner);
        cancelQuestionBTN=(Button)view.findViewById(R.id.cancel_question_btn);
        cancelQuestionBTN.setOnClickListener(this);

    }

    private void initData() {
        ArrayAdapter<CharSequence> educationAdapter = ArrayAdapter.createFromResource(context,
                R.array.education_type, android.R.layout.simple_spinner_item);
        userEducationSpinner.setAdapter(educationAdapter);
        userEducationTag = getResources().getStringArray(R.array.education_type_tag);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.send_question_btn:
                if (validation()){

                    int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                    RadioButton radioButton =(RadioButton)radioButtonGroup.findViewById(radioButtonID);
//
                    String url = WebserviceUrl.CONSULTATION_CREATE_KNOWN+
                            "?Name="+name+
                            "&Email="+email+
                            "&DeviceID="+Globals.deviceId+
                            "&GroupID="+"1197"+
                            "&ConsultantID=274"+
                            "&Question="+ question+
                            "&Education="+userEducationTag[userEducationSpinner.getSelectedItemPosition()].toString()+
                            "&Age="+age+
                            "&Gender="+radioButton.getTag().toString()+
                            "&ShowInSite="+ Integer.toString(!(userUnKnow.isChecked())?1:0)+
                            "&OutputType=2";
//                    try {

                        Globals.ion.with(context).load(url)
                                .as(ConsultationResult.class)
                                .setCallback(new FutureCallback<ConsultationResult>(){
                                    @Override
                                    public void onCompleted(Exception e, ConsultationResult result) {
                                        if(result.getResult().getError()!= null) {
                                            userQuestionTxt.setText("");
                                            password.setText(result.getResult().getObject().getPassword());
                                            password.setVisibility(View.VISIBLE);
                                        }
                                        else {
                                            Toast.makeText(context,result.getResult().getError(),Toast.LENGTH_SHORT).show();
                                        }
                                    }});
//                    }catch (Exception e){}


                }
                break;
            case R.id.cancel_question_btn:
                getActivity().onBackPressed();
                break;
        }
    }

    protected boolean validation(){
        boolean continueFlag = true;
        name = userName.getText().toString();
        age = userAge.getText().toString();
        email = userEmail.getText().toString();
        question = userQuestionTxt.getText().toString();
        if(name == null){
            userName.setError("این فیلد ضروری است");
            continueFlag = false;
        }
        if(age == null || age.length() > 3){
            userAge.setError("لطفا سن خود را به درستی وارد کنید");
            continueFlag = false;
        }
        if(email== null){
            userEmail.setError("لطفا ایمیل معتبر وارد نمایید");
            continueFlag = false;
        }
        else{
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if(!email.matches(emailPattern)){
                continueFlag = false;
                userEmail.setError("لطفا ایمیل معتبر وارد نمایید");
            };
        }
        return continueFlag;
    }
}