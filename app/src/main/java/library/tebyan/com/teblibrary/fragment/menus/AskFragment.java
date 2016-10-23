package library.tebyan.com.teblibrary.fragment.menus;

import android.content.Context;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.QuestionAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.Question;
import library.tebyan.com.teblibrary.model.QuestionList;

public class AskFragment extends Fragment implements View.OnClickListener {

    private RecyclerView questionRecyclerView ;
    private QuestionAdapter questionAdapter;
    private View view ;
    private Context context;
    private LinearLayoutManager questionlinearLayoutManager;
    private ArrayList<Question> data=new ArrayList<>();
    private int visibleItemCount,pastVisiblesItems,pageIndex;
    private int totalItemCount;
    private boolean loading;
    private ImageButton clear_btn_search;
    private ImageButton go_btn_search;
    private EditText searchTxt;
    private Button askQuestionBtn;
    private PopupWindow ask_question_popUpWindown;
    private LayoutInflater layoutInflater;
    private LinearLayout linearLayout;
    private Button send_question_btn;
    private EditText user_question_txt;

    public AskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ask, container, false);
        context = view.getContext();
        initUI();
        return view;
    }
    private void initUI() {
        linearLayout = (LinearLayout)view.findViewById(R.id.ask_fragment_layout);
        searchTxt = (EditText)view.findViewById(R.id.search_txt);
        askQuestionBtn = (Button)view.findViewById(R.id.ask_question_btn);
        askQuestionBtn.setOnClickListener(this);
        clear_btn_search = (ImageButton)view.findViewById(R.id.clear_btn_search);
        clear_btn_search.setOnClickListener(this);
        go_btn_search = (ImageButton)view.findViewById(R.id.go_btn_search );
        go_btn_search.setOnClickListener(this);

        questionRecyclerView = (RecyclerView) view.findViewById(R.id.question_recyclerView);
        questionlinearLayoutManager = new LinearLayoutManager(context);
        questionRecyclerView.setLayoutManager(questionlinearLayoutManager);
        questionRecyclerView.setHasFixedSize(true);
        //alphabet part
        questionRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = questionlinearLayoutManager.getChildCount();
                totalItemCount = questionlinearLayoutManager.getItemCount();
                pastVisiblesItems = questionlinearLayoutManager.findFirstVisibleItemPosition();
                if (!loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        pageIndex++;
                        initData();
                    }
                }
            }
        });
        questionAdapter = new QuestionAdapter(context,data);
        questionRecyclerView.setAdapter(questionAdapter);
    }


    private void initData() {
        try {
            searchTxt.getText();
            Globals.ion.with(this).load(WebserviceUrl.BROWSE_ALPHABET + "&PageSize=10&PageIndex=" + pageIndex)
                    .as(QuestionList.class).setCallback(new FutureCallback<QuestionList>() {
                @Override
                public void onCompleted(Exception e, QuestionList questionList) {
                    if (Utils.isOnline(getContext())) {
                        if (e == null & questionList.getData().size() > 0)
                            Log.i("sdsd", questionList + "");
                        questionAdapter.items.addAll(questionList.getData());
                        questionAdapter.notifyDataSetChanged();
                    }
                }
            });
        }catch (Exception e){}
//        }catch (UnsupportedEncodingException ex){}

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.clear_btn_search:
                searchTxt.setText("");
                break;

            case R.id.go_btn_search:
                initData();
                break;

            case R.id.ask_question_btn:

                layoutInflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup)layoutInflater.inflate(R.layout.ask_pop_up_window,null);
                ask_question_popUpWindown = new PopupWindow(container,400,400,true);
                ask_question_popUpWindown.showAtLocation(linearLayout, Gravity.CENTER,0,0);

                send_question_btn = (Button)container.findViewById(R.id.send_question_btn);
                user_question_txt = (EditText) container.findViewById(R.id.user_question_txt);
                send_question_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)
                    {

                        String qesiton = user_question_txt.getText().toString();
                        searchTxt.setText(qesiton);
                        ask_question_popUpWindown.dismiss();
                    }
                });

                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        ask_question_popUpWindown.dismiss();
                        return false;
                    }
                });
                break;
        }

    }


}
