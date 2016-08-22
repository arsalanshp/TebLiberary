package library.tebyan.com.teblibrary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.navigationtabbar.ntb.NavigationTabBar;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.DialogSelectImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private DialogSelectImage dialog;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageButton profileImageView = (ImageButton) findViewById(R.id.profileImg);
        profileImageView.setOnClickListener(this);
        Button sendButton = (Button) findViewById(R.id.send_new_pass);
        sendButton.setOnClickListener(this);
//        profileImageView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                DialogSelectImage.imageType = "Profile";
//                AddImageForProfile(arg0);
//            }
//        });
//        initProfilePageView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == DialogSelectImage.PICK_IMAGE_FROM_GALLERY
                    || requestCode == DialogSelectImage.TAKE_IMAGE_CAMERA ||
                    requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                try {
                    dialog.userSelectImage(requestCode, resultCode, data);
                    String userSelectedImagePath = dialog.getSelectedImagePath();
                    Bitmap userSelectedImage = dialog.getSelectedImage();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(this,
                            getString(R.string.problem_on_selecting_image),
                            Toast.LENGTH_SHORT).show();
                }

                File file = new File(Environment.getExternalStorageDirectory()
                        + "/Tebyan/01.jpg");
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.send_new_pass:
                changePassword();
                
                break;
            case R.id.profileImg:
                DialogSelectImage.imageType = "Profile";
                AddImageForProfile(view);
                break;


        }
    }

    private void changePassword() {
        String txtOldPass = ((EditText)findViewById(R.id.old_password_etx)).getText().toString();
        String txtNewPass = ((EditText)findViewById(R.id.new_password_etx)).getText().toString();
        if (Utils.isOnline(this)) {
//            if (Globals.userToken_socialNetwork == null) {
//                refToken();
//            } else {
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("txtOldPas", txtOldPass);
//            jsonObject.addProperty("txtNewPas", txtNewPass);
            Globals.ion.with(this)
                    .load("GET",WebserviceUrl.CHANGE_PASSWORD)
                    .setHeader("userToken", Globals.userToken_socialNetwork)
                    .setHeader("checkToken", "true")
                    .setBodyParameter("txtNewPas", txtNewPass)
                    .setBodyParameter("txtOldPas", txtOldPass)
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            if (result != null && e == null) {
                                //Utils.showDefaultCustomizedToast(activity, result.get("d").toString());
//                                Globals.logout();
                                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                startActivity(intent);
        //                                    Globals.clearSharedPreferences(activity);
        //                                    Utils.goToLoginReg(activity);
                            }
                        }
                    });
        } else{
            String text = getResources().getString(
                    R.string.network_connection_fail);
            Toast.makeText(this, text + " ",
                    Toast.LENGTH_LONG).show();
        }
//            Utils.showDefaultCustomizedToast(activity, getString(R.string.network_connection_fail));
    }



    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.profile_tab_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.txt.setText(String.format("Navigation Item #%d", position));
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView txt;

            public ViewHolder(final View itemView) {
                super(itemView);
                txt = (TextView) itemView.findViewById(R.id.txt_vp_item_list);
            }
        }
    }

    public void AddImageForProfile(View v) {
        dialog = new DialogSelectImage(this);
        dialog.show();
    }
}