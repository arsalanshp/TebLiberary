package library.tebyan.com.teblibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Pattern;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.img_commitProfile).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_commitProfile:
                //validation
               if(checkValidation()) {
                   //Sen Changes TO DB

                   Intent editProfileIntent = new Intent(EditProfileActivity.this, ProfileActivity.class);
//                editProfile_Intent.putExtra()
                   startActivity(editProfileIntent);
               }
                break;
        }
    }

    private boolean checkValidation(){
        EditText familyET = (EditText)findViewById(R.id.family);
        String validationText = familyET.getText().toString().trim();

        boolean result = true;

        result = isValidPhonNumber();
        if(validationText.isEmpty() || validationText.length() == 0 || validationText.equals("") || validationText == null)
        {
            familyET.setError("لطفا نام ونام خانوادگی خود را پر نمایید");
            result = false;
        }

        EditText userName = (EditText)findViewById(R.id.userName);
        validationText = userName.getText().toString().trim();

        if(validationText.isEmpty() || validationText.length() == 0 || validationText.equals("") || validationText == null)
        {
            userName.setError("لطفا نام کاربری را پر نمایید");
            result = false;
        }
        return result;

    }

    private boolean isValidPhonNumber(){

        EditText editText = (EditText)findViewById(R.id.phonNumber);
        String PHONE_PATTERN ="^(0)[\\d]{10}";
        boolean isvalid  = Pattern.matches(PHONE_PATTERN,editText.getText());
        if (!isvalid) {
            editText.setError("شماره تلفن نامعتبر");
            return false;
        }

        return true;

    }
}
