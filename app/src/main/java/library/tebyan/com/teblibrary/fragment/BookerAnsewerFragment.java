package library.tebyan.com.teblibrary.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;

import java.net.URLEncoder;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.QuestionList;

public class BookerAnsewerFragment extends Fragment {

    private View view ;
    private Context context;
    private String question;
    private int questionId;
    private TextView questionTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        question = getArguments().getString("bookerQestion");
        questionId = getArguments().getInt("bookerQestionId");
        view = inflater.inflate(R.layout.fragment_booker_ansewer, container, false);
        context = getContext();
        initUI();
        initData();
        return view;
    }

    private void initUI() {

        questionTxt = (TextView)view.findViewById(R.id.question_txt);
        questionTxt.setText(question);
    }

    private void initData() {
//        try {
//
//            String filter = URLEncoder.encode(searchTxt.getText().toString(), "utf-8");
//            Globals.ion.with(this).load(WebserviceUrl.GETQUESTIONANSWER + "ID=" + questionId)
//                    .as(QuestionList.class).setCallback(new FutureCallback<QuestionList>() {
//                @Override
//                public void onCompleted(Exception e, QuestionList questionList) {
//                    if (Utils.isOnline(getContext())) {
//                        if (e == null & questionList.getResult().size() > 0)
//                            questionAdapter.items.addAll(questionList.getResult());
//                        questionAdapter.notifyDataSetChanged();
//                        loading=false;
//                    }
//                }
//            });
//        }catch (Exception e){}

    }
}
