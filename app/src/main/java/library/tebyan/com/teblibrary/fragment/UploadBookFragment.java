package library.tebyan.com.teblibrary.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.UserBitStreamUploader;

public class UploadBookFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Context context;
    private Button sendBTN ,selectFileBTN,cancelBTN;
    private EditText titleEditText, authorsEditText,tagEditText,noteEditText;
    private CheckBox readyToOrder;
    private Spinner languageSpiner;
    private LinearLayout fileLinearLayour , metadataLinearLayout;
    private int groupID , metaDataID;
    private String[] extList;
    private String filePath , fileName;
    private File uploadFile;
    private TextView fileNameTxt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_upload_book, container, false);
        context = getContext();
        initUI();
        return view;
    }

    private void initUI(){

        fileNameTxt = (TextView)view.findViewById(R.id.file_name_txt);
        extList = new String[]{"pdf"};
        cancelBTN = (Button)view.findViewById(R.id.cancel_btn);
        cancelBTN.setOnClickListener(this);

        sendBTN = (Button)view.findViewById(R.id.send_btn);
        sendBTN.setOnClickListener(this);

        selectFileBTN = (Button)view.findViewById(R.id.select_file_btn);
        selectFileBTN.setOnClickListener(this);

//        sendFileBTN = (Button)view.findViewById(R.id.send_file_btn);
//        sendFileBTN.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.language_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpiner = (Spinner) view.findViewById(R.id.language_spiner);
        languageSpiner.setAdapter(adapter);

        fileLinearLayour =(LinearLayout) view.findViewById(R.id.file_linear_layour);
        metadataLinearLayout = (LinearLayout) view.findViewById(R.id.metadata_linear_layout);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_btn:
                getActivity().onBackPressed();
//                getActivity().getSupportFragmentManager().popBackStackImmediate();
                break;
            case R.id.send_btn:
                sendData();
//                https://library.tebyan.net/fa/Account/CreateMetaDataSource
                break;
            case R.id.select_file_btn:
                initFilePicker();
                break;
//            case R.id.send_file_btn:
//                sendFile();
//                break;
        }

    }

    private void initFilePicker(){
        Intent i = new Intent(context, FilePickerActivity.class);
        // This works if you defined the intent filter
        // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

        // Set these depending on your use case. These are the defaults.
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

        // Configure initial directory by specifying a String.
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        startActivityForResult(i, 1);

    }

    private void sendData() {
        try {
            titleEditText = (EditText)view.findViewById(R.id.title);
            authorsEditText = (EditText)view.findViewById(R.id.authors);
            tagEditText = (EditText)view.findViewById(R.id.tag);
            noteEditText  = (EditText)view.findViewById(R.id.note);
            readyToOrder = (CheckBox)view.findViewById(R.id.ready_to_order);
            fileName = titleEditText.getText().toString();
//            {'Title':'android studio','Author':'www.tutorialspoint.com/','LanguageID':'801','Note':'www.tutorialspoint.com/','Import_Subjects':'andrioid','ReadyToOrder':true}
            JsonObject json = new JsonObject();
            json.addProperty("Title",fileName);

            json.addProperty("Author",authorsEditText.getText().toString());
//            json.addProperty("LanguageID",languageEditText.getTag().toString());
            json.addProperty("LanguageID",801);
            json.addProperty("Note", noteEditText.getText().toString());
            json.addProperty("Import_Subjects",tagEditText.getText().toString());
            json.addProperty("ReadyToOrder",readyToOrder.isChecked() );

            Globals.ion.with(this).load(WebserviceUrl.GREATMETADATASOURCE)
                    .setHeader("userToken", Globals.userToken)
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    if (Utils.isOnline(getContext())) {
                        if (e == null& result!=null & !result.getAsJsonObject("d").get("Data").isJsonNull()) {
                            groupID = result.getAsJsonObject("d").getAsJsonObject("Data").get("GroupID").getAsInt();
                            metaDataID = result.getAsJsonObject("d").getAsJsonObject("Data").get("MetaDataID").getAsInt();
                            fileLinearLayour.setVisibility(View.VISIBLE);
                            metadataLinearLayout.setVisibility(View.GONE);
                            sendFile(fileNameTxt.getText().toString(), uploadFile);

                        }
                    }else {
                        Toast.makeText(context, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){}
    }

//    public void getFile(String fileName){
//        Log.d("aaaaaaaaaaaaaaaaaaaaa",fileName);
//        String x = fileName;
//        x.substring(1);
//
//    }
    private void sendFile(String fileName , File uploadFile) {
        try{
//            JsonObject json = new JsonObject();
//            json.addProperty("GroupID",groupID);
//            json.addProperty("MetaDataID",metaDataID);
            String fileName_encoded = "";
            try {
                fileName_encoded = URLEncoder.encode(fileName, "utf-8").toString();
                if (fileName_encoded.contains("+")) {
                    fileName_encoded = URLEncoder.encode(fileName, "utf-8").toString().replace("+", "%20");
                }
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Globals.ion.with(this).load(WebserviceUrl.USERBITSTREAMUPLOADER+"?Group="+groupID+"&fileName="+fileName_encoded+"&MetaDataID="+metaDataID)
                    .setHeader("userToken",Globals.userToken).setTimeout(60 * 60 * 1000)
                    .setMultipartFile("file", Globals.getMimeType(uploadFile),uploadFile)
                    .as(UserBitStreamUploader.class)
                    .setCallback(new FutureCallback<UserBitStreamUploader>() {
                        @Override
                        public void onCompleted(Exception e, UserBitStreamUploader result) {
                            if (Utils.isOnline(getContext())) {
                                if (e == null & result != null & result.getBitStreamID()!= 0) {
                                    Toast.makeText(context,getString(R.string.success_uplad),Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(context,getString(R.string.server_errore),Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(context, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                            }
//                            getActivity().onBackPressed();
                        }
                    });
        }
        catch (Exception ex){}
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
                // For JellyBean and above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clip = data.getClipData();

                    if (clip != null) {
                        for (int i = 0; i < clip.getItemCount(); i++) {
                            Uri uri = clip.getItemAt(i).getUri();
                            // Do something with the URI
                        }
                    }
                    // For Ice Cream Sandwich
                } else {
                    ArrayList<String> paths = data.getStringArrayListExtra
                            (FilePickerActivity.EXTRA_PATHS);

                    if (paths != null) {
                        for (String path: paths) {
                            Uri uri = Uri.parse(path);
                            // Do something with the URI
                        }
                    }
                }

            } else {
                Uri uri = data.getData();
                filePath = data.getDataString();
                String ext = filePath.substring(filePath.indexOf(".")+1);
                for(String extName:extList){
                    if (extName.contains(ext)){
                        uploadFile = new File(uri.getPath());
                        fileNameTxt.setText(uploadFile.getName());
                        break;
                    }
                }
//                uploadFile = new File(filePath);
//                fileNameTxt.setText(uploadFile.getName());
            }
        }
    }

}
