package library.tebyan.com.teblibrary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.bitmap.Transform;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.DialogSelectImage;

import java.io.File;
import java.io.FileNotFoundException;

import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.shared.Shared;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private DialogSelectImage dialog;
    public Toolbar toolbar;
    public ImageButton profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity_old.class));
            }
        });

         profileImageView = (ImageButton) findViewById(R.id.profileImg);
        if(!Shared.getData(getApplicationContext(), Shared.AVATAR_PATH).equals("")){
            chageAvatar(Shared.getData(getApplicationContext(), Shared.AVATAR_PATH));
        }else{
            profileImageView.setImageResource(R.drawable.user_def);
        }
        profileImageView.setOnClickListener(this);
        Button sendButton = (Button) findViewById(R.id.send_new_pass);
        sendButton.setOnClickListener(this);
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
                    Shared.setData(getApplicationContext(),Shared.AVATAR_PATH,userSelectedImagePath);
                    chageAvatar(userSelectedImagePath);
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

    private void chageAvatar(String userSelectedImagePath) {
        Globals.ion.with(profileImageView)
                .transform(new Transform() {
                    @Override
                    public Bitmap transform(Bitmap b) {
                        return Utils.createCircleBitmap(b);
                    }

                    @Override
                    public String key() {
                        return null;
                    }
                })
                .load(userSelectedImagePath);
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
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("txtOldPas", txtOldPass);
            jsonObject.addProperty("txtNewPas", txtNewPass);
            Globals.ion.with(this)
                    .load(WebserviceUrl.CHANGE_PASSWORD)
                    .setHeader("userToken",Shared.getData(getApplicationContext(),Shared.SOCIAL_TOKEN))
                    .setHeader("checkToken", "true")
                    .setTimeout(1000000000)
                    .setJsonObjectBody(jsonObject)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (result != null && e == null) {
                                finish();
                                Intent intent = new Intent(ProfileActivity.this, MainActivity_old.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }
                    });
        } else{
            String text = getResources().getString(
                    R.string.network_connection_fail);
            Toast.makeText(this, text + " ",
                    Toast.LENGTH_LONG).show();
        }
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