package library.tebyan.com.teblibrary.fragment.menus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    private EditText userQuestionTxt,userName,userAge,userEmail;
    private TextView password;
    private RadioGroup radioButtonGroup;
    private CheckBox userUnKnow;
    private Spinner userEducationSpinner;
    private Button sendQuestionBTN,cancelQuestionBTN ,closeQuestionBTN;
    private String name, age,email,question;
    private String[] userEducationTag;
    private LinearLayout passwordLayout,sendLineareLayout;


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
        sendLineareLayout =(LinearLayout)view.findViewById(R.id.send_lineare_layout);
        passwordLayout =(LinearLayout)view.findViewById(R.id.password_layout);
        password = (TextView)view.findViewById(R.id.password);
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
        closeQuestionBTN = (Button)view.findViewById(R.id.close_question_btn);
        closeQuestionBTN.setOnClickListener(this);

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
                    String url="";
                    try{
                         url = WebserviceUrl.CONSULTATION_CREATE_KNOWN+
                                "?Name="+URLEncoder.encode(name,"utf-8")+
                                "&Email="+email+
                                "&DeviceID="+2+
                                "&GroupID="+"1197"+
                                "&ConsultantID=274"+
                                "&Question="+URLEncoder.encode(question,"utf-8")+
                                "&Education="+userEducationTag[userEducationSpinner.getSelectedItemPosition()].toString()+
                                "&Age="+age+
                                "&Gender="+radioButton.getTag().toString()+
                                "&ShowInSite="+ Integer.toString(!(userUnKnow.isChecked())?1:0)+
                                "&OutputType=2";}
                    catch (UnsupportedEncodingException e){
                                    Log.e("",e.getMessage().toString());
                                }
                    Globals.ion.with(context).load(url)
    //                                .asString()
    //                                .setCallback(new FutureCallback<String>(){
    //                                    @Override
    //                                    public void onCompleted(Exception e, String result) {
    //                                        Toast.makeText(context,result, Toast.LENGTH_LONG).show();
    //                                        }
    //                                    });
                            .as(ConsultationResult.class)
                            .setCallback(new FutureCallback<ConsultationResult>(){
                                @Override
                                public void onCompleted(Exception e, ConsultationResult result) {
                                    if(result.getResult().getError()== null) {
                                        userQuestionTxt.setText("");
                                        String text = "کاربر گرامی پسورد شما"+
                                                result.getResult().getObject().getPassword()+
                                                " می باشد"+
                                                "\n"+
                                                "شما میتوانید از این پسورد جهت پیگیری سوالات خود در سایت استفاده نمایید"+
                                                "\n"+
                                                "با تشکر از شما"+
                                                "موسسه فرهنگی اطلاع رسانی تبیان "
                                                ;
//                                        String text = "کاربر گرامی پسورد شما " +result.getResult().getObject().getPassword() + "می باشد. شما میتوانید از این پسورد جهت پیگیری پرسش های خود در آینده استفاده نمایید. با تشکر از شما ";
                                        password.setText(text);
                                        passwordLayout.setVisibility(View.VISIBLE);
                                        sendLineareLayout.setVisibility(View.GONE);
                                    }
                                    else {
                                        Toast.makeText(context,result.getResult().getError(),Toast.LENGTH_SHORT).show();
                                    }
                                }});
                }

                break;
            case R.id.cancel_question_btn:
                getActivity().onBackPressed();
                break;
            case R.id.close_question_btn:
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
        if(name == null || name.isEmpty()){
            userName.setError("این فیلد ضروری است");
            continueFlag = false;
        }
        if(age == null || age.length() > 3 || age.isEmpty()){
            userAge.setError("لطفا سن خود را به درستی وارد کنید");
            continueFlag = false;
        }
        if(email== null || email.isEmpty()){
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