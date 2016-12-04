package library.tebyan.com.teblibrary.fragment.menus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.koushikdutta.async.future.FutureCallback;

import java.net.URLEncoder;
import java.util.ArrayList;

import library.tebyan.com.teblibrary.MainActivity;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.QuestionAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.classes.interfaces.AskQuestionInterface;
import library.tebyan.com.teblibrary.classes.interfaces.BookerAnswerInterface;
import library.tebyan.com.teblibrary.model.BookerQuestion;
import library.tebyan.com.teblibrary.model.QuestionList;

public class AskFragment extends Fragment implements View.OnClickListener {

    private RecyclerView questionRecyclerView ;
    private QuestionAdapter questionAdapter;
    private View view ;
    private Context context;
    private LinearLayoutManager questionlinearLayoutManager;
    private ArrayList<BookerQuestion> data=new ArrayList<>();
    private int visibleItemCount,pastVisiblesItems,pageIndex;
    private int totalItemCount;
    private boolean loading=false;
    private ImageButton clearBtnSearch;
    private ImageButton goBtnSearch;
    private EditText searchTxt;
    private Button askQuestionBtn;
    private AskQuestionInterface callBack;
//    private PopupWindow ask_question_popUpWindown;
//    private LayoutInflater layoutInflater;
//    private LinearLayout linearLayout;
//    private Button sendQuestionBtn,cancelQuestionBtn;
//    private ImageButton closeBtn;
//    private EditText userQuestionTxt,userName,userAge,userEmail;
//    private RadioButton manRadioButton,womanRadioButton;
//    private CheckBox userUnKnow;
//    private Spinner userEducationSpinner;

    public AskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ask, container, false);
        context = view.getContext();
        this.callBack = (AskQuestionInterface)getActivity();
        initUI();
        initData();
        return view;
    }
    private void initUI() {

//        linearLayout = (LinearLayout)view.findViewById(R.id.ask_fragment_layout);
        searchTxt = (EditText)view.findViewById(R.id.search_txt);
        askQuestionBtn = (Button)view.findViewById(R.id.ask_question_btn);
        askQuestionBtn.setOnClickListener(this);
        clearBtnSearch = (ImageButton)view.findViewById(R.id.clear_btn_search);
        clearBtnSearch.setOnClickListener(this);
        goBtnSearch = (ImageButton)view.findViewById(R.id.go_btn_search );
        goBtnSearch.setOnClickListener(this);

        questionRecyclerView = (RecyclerView) view.findViewById(R.id.question_recyclerView);
        questionlinearLayoutManager = new LinearLayoutManager(context);
        questionRecyclerView.setLayoutManager(questionlinearLayoutManager);
        questionRecyclerView.setHasFixedSize(true);

        questionRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = questionlinearLayoutManager.getChildCount();
                totalItemCount = questionlinearLayoutManager.getItemCount();
                pastVisiblesItems = questionlinearLayoutManager.findFirstVisibleItemPosition();
                if (!loading && dy>0) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        pageIndex++;
                        initData();
                    }
                }
            }
        });
//        questionAdapter = new QuestionAdapter(context,data,(BookerAnswerInterface)getActivity());
        questionAdapter = new QuestionAdapter(context,data);
        questionRecyclerView.setAdapter(questionAdapter);
    }


    private void initData() {
        try {
            String filter = URLEncoder.encode(searchTxt.getText().toString(), "utf-8");
            Globals.ion.with(this).load(WebserviceUrl.SEARCHQUESTIONS + "PageSize=10&PageIndex=" + pageIndex+"&keyword="+filter)
                    .as(QuestionList.class).setCallback(new FutureCallback<QuestionList>() {
                @Override
                public void onCompleted(Exception e, QuestionList questionList) {
                    if (Utils.isOnline(getContext())) {
                        if (e == null &questionList != null& questionList.getResult().size() > 0)
                            questionAdapter.items.addAll(questionList.getResult());
                            questionAdapter.notifyDataSetChanged();
                            loading=false;
                    }
                }
            });
        }catch (Exception e){}
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clear_btn_search:
                searchTxt.setText("");
                initData();
                break;

            case R.id.go_btn_search:
                questionAdapter.items.clear();
                initData();
                break;

            case R.id.ask_question_btn:
                this.callBack.callbackAskQuestion();
                break;
        }
    }
}
