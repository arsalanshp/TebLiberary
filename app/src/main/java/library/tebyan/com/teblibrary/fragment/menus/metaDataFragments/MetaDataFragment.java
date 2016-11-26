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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.metadata_fragment, container, false);
//        bookId =getArguments().getInt("book_id");
        args = getArguments();
        initUI();
        return view;
    }


    private void initUI() {
        authorName = (TextView)view.findViewById(R.id.author_name);
        authorName.setText(args.getString("authorName"));
        language = (TextView)view.findViewById(R.id.language);
        language.setText(args.getString("language"));
        refrenceType = (TextView)view.findViewById(R.id.refrence_type);
        refrenceType.setText(args.getString("refrenceType"));
        digitalRefrences = (TextView)view.findViewById(R.id.digital_refrences);
        digitalRefrences.setText(args.getString("digitalRefrences"));
        publishState = (TextView)view.findViewById(R.id.publish_state);
        publishState.setText(args.getString("publishState"));
        subjects = (TextView)view.findViewById(R.id.subjects);
        subjects.setText(args.getString("subjects"));
        creator = (TextView)view.findViewById(R.id.creator);
        creator.setText(args.getString("creator"));
        note = (TextView)view.findViewById(R.id.note);
        note.setText(args.getString("note"));
    }
}


