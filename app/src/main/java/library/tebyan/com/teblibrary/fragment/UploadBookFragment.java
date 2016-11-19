package library.tebyan.com.teblibrary.fragment;

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
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;

public class UploadBookFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Context context;
    private Button cancel_btn;
    private Button send_btn;
    private EditText titleEditText, authorsEditText,tagEditText,noteEditText;
    private CheckBox readyToOrder;
    private Spinner languageSpiner;
    private LinearLayout fileLinearLayour , metadataLinearLayout;
    private int groupID , metaDataID;


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
        cancel_btn = (Button)view.findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(this);

        send_btn = (Button)view.findViewById(R.id.send_btn);
        send_btn.setOnClickListener(this);

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
        }

    }

    private void sendData() {
        try {
            titleEditText = (EditText)view.findViewById(R.id.title);
            authorsEditText = (EditText)view.findViewById(R.id.authors);
            tagEditText = (EditText)view.findViewById(R.id.tag);
            noteEditText  = (EditText)view.findViewById(R.id.note);
            readyToOrder = (CheckBox)view.findViewById(R.id.ready_to_order);

//            {'Title':'android studio','Author':'www.tutorialspoint.com/','LanguageID':'801','Note':'www.tutorialspoint.com/','Import_Subjects':'andrioid','ReadyToOrder':true}
            JsonObject json = new JsonObject();
            json.addProperty("Title",titleEditText.getText().toString());
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
                        if (e == null & !result.getAsJsonObject("d").get("Data").isJsonNull()) {
                            groupID = result.getAsJsonObject("d").getAsJsonObject("Data").get("GroupID").getAsInt();
                            metaDataID = result.getAsJsonObject("d").getAsJsonObject("Data").get("MetaDataID").getAsInt();
                            fileLinearLayour.setVisibility(View.VISIBLE);
                            metadataLinearLayout.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }catch (Exception e){}
    }

    private void sendFile() {
        try{
            JsonObject json = new JsonObject();
            json.addProperty("GroupID",groupID);
            json.addProperty("MetaDataID",metaDataID);

            Globals.ion.with(this).load(WebserviceUrl.USERBITSTREAMUPLOADER)
                    .setHeader("userToken", Globals.userToken)
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (Utils.isOnline(getContext())) {
                                if (e == null & !result.getAsJsonObject("d").get("Data").isJsonNull()) {
//                            result.getAsJsonObject("d").getAsJsonObject("Data").get("GroupID")
//                            result.getAsJsonObject("d").getAsJsonObject("Data").get("MetaDataID")
                                    result.getAsJsonObject();
                                }
                            }
                        }
                    });
        }
        catch (Exception ex){}
    }

}
