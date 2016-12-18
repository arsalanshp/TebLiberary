package library.tebyan.com.teblibrary.fragment.menus.metaDataFragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import library.tebyan.com.teblibrary.R;


/**
 * Created by m.mohamadzadeh on 10/26/2016.
 */

public class MetaDataFragment extends Fragment {


    private View view;
    private Bundle args;
    private TextView authorName,language,refrenceType,digitalRefrences,publishState,subjects,creator,note;
    private String s_authorName,s_language,s_refrenceType,s_digitalRefrences,s_publishState,s_subjects,s_creator,s_note;

    public String getS_authorName() {
        return s_authorName;
    }

    public void setS_authorName(String s_authorName) {
        this.s_authorName = s_authorName;
    }

    public String getS_language() {
        return s_language;
    }

    public void setS_language(String s_language) {
        this.s_language = s_language;
    }

    public String getS_refrenceType() {
        return s_refrenceType;
    }

    public void setS_refrenceType(String s_refrenceType) {
        this.s_refrenceType = s_refrenceType;
    }

    public String getS_digitalRefrences() {
        return s_digitalRefrences;
    }

    public void setS_digitalRefrences(String s_digitalRefrences) {
        this.s_digitalRefrences = s_digitalRefrences;
    }

    public String getS_publishState() {
        return s_publishState;
    }

    public void setS_publishState(String s_publishState) {
        this.s_publishState = s_publishState;
    }

    public String getS_subjects() {
        return s_subjects;
    }

    public void setS_subjects(String s_subjects) {
        this.s_subjects = s_subjects;
    }

    public String getS_creator() {
        return s_creator;
    }

    public void setS_creator(String s_creator) {
        this.s_creator = s_creator;
    }

    public String getS_note() {
        return s_note;
    }

    public void setS_note(String s_note) {
        this.s_note = s_note;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.metadata_fragment, container, false);
//        bookId =getArguments().getInt("book_id");
//        args = getArguments();
        initUI();
        return view;
    }


    private void initUI() {
        authorName = (TextView)view.findViewById(R.id.author_name);
        authorName.setText(s_authorName);
        language = (TextView)view.findViewById(R.id.language);
        language.setText(s_language);
        refrenceType = (TextView)view.findViewById(R.id.refrence_type);
        String refrenceTypetxt = (s_refrenceType);
        if (refrenceTypetxt != null) {
            refrenceType.setText(refrenceTypetxt);
        }
        digitalRefrences = (TextView)view.findViewById(R.id.digital_refrences);
        digitalRefrences.setText(s_digitalRefrences);
//        publishState = (TextView)view.findViewById(R.id.publish_state);
//        publishState.setText(args.getString("publishState"));
        subjects = (TextView)view.findViewById(R.id.subjects);
        subjects.setText(s_subjects);
//        creator = (TextView)view.findViewById(R.id.creator);
//        creator.setText(args.getString("creator"));
        note = (TextView)view.findViewById(R.id.note);
        note.setText(s_note);
    }

//    private void initUI() {
//        authorName = (TextView)view.findViewById(R.id.author_name);
//        authorName.setText(args.getString("authorName"));
//        language = (TextView)view.findViewById(R.id.language);
//        language.setText(args.getString("language"));
//        refrenceType = (TextView)view.findViewById(R.id.refrence_type);
//        String refrenceTypetxt = args.getString("refrenceType");
//        if (refrenceTypetxt != null) {
//            refrenceType.setText(refrenceTypetxt);
//        }
//        digitalRefrences = (TextView)view.findViewById(R.id.digital_refrences);
//        digitalRefrences.setText(args.getString("digitalRefrences"));
////        publishState = (TextView)view.findViewById(R.id.publish_state);
////        publishState.setText(args.getString("publishState"));
//        subjects = (TextView)view.findViewById(R.id.subjects);
//        subjects.setText(args.getString("subjects"));
////        creator = (TextView)view.findViewById(R.id.creator);
////        creator.setText(args.getString("creator"));
//        note = (TextView)view.findViewById(R.id.note);
//        note.setText(args.getString("note"));
//    }
}


