package library.tebyan.com.teblibrary.fragment.menus.metaDataFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.CommentsAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.Comment;
import library.tebyan.com.teblibrary.model.CommentsList;


/**
 * Created by m.mohamadzadeh on 10/26/2016.
 */

public class AnalyzeFragment extends Fragment implements View.OnClickListener  {

    private Context context;
    private View view;
    private RecyclerView commentRecyclerView ;
    private List<Comment> commentList = new ArrayList<>();
    private CommentsAdapter commentsAdapter;
    private int visibleItemCount,pastVisiblesItems,pageIndex=0;
    private int totalItemCount;
    private boolean loading=false;
    private LinearLayoutManager commentlinearLayoutManager;
    private int bookId;
    private EditText comment;
    private ImageButton send_comment_btn;
    private LinearLayout noCommentLayout;
    private boolean isBack= false;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookId =getArguments().getInt("book_id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.analyze_fragment,container,false);
        context = view.getContext();
        initUI();
        if(!isBack) {
            initData();
            isBack = true;
        }
        else{
            commentRecyclerView.setAdapter(commentsAdapter);
            commentsAdapter.notifyDataSetChanged();
        }
        return view;
    }


    private void initUI() {

        noCommentLayout = (LinearLayout)view.findViewById(R.id.no_comment_layout);
        comment = (EditText) view.findViewById(R.id.comment_text);
        send_comment_btn = (ImageButton) view.findViewById(R.id.send_comment);
        send_comment_btn.setOnClickListener(this);

        commentRecyclerView = (RecyclerView) view.findViewById(R.id.comment_recycler_view);
        commentlinearLayoutManager = new LinearLayoutManager(context);
        commentRecyclerView.setLayoutManager(commentlinearLayoutManager);
        commentRecyclerView.setHasFixedSize(true);
        commentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = commentlinearLayoutManager.getChildCount();
                totalItemCount = commentlinearLayoutManager.getItemCount();
                pastVisiblesItems = commentlinearLayoutManager.findFirstVisibleItemPosition();
                if (!loading && dy>0) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        pageIndex++;
                        initData();
                    }
                }
            }
        });
        commentsAdapter = new CommentsAdapter(commentList);
        commentRecyclerView.setAdapter(commentsAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                initData();
            }
        });
    }


    private void initData() {
        try {

            Globals.ion.with(this).load(WebserviceUrl.GET_COMMENT+"ID="+bookId+"&PageIndex="+pageIndex+"&pageSize=10")
                    .setHeader("token_id", Globals.userToken)
                    .as(CommentsList.class).setCallback(new FutureCallback<CommentsList>() {
                @Override
                public void onCompleted(Exception e, CommentsList commentsList) {
                    if (Utils.isOnline(getContext())) {
                        if (e == null &commentsList != null& commentsList.getComments().size() > 0) {
                            noCommentLayout.setVisibility(View.GONE);
                            commentsAdapter.commentsList.addAll(commentsList.getComments());
                            commentsAdapter.notifyDataSetChanged();
                            loading = false;
                        }
                        else{

                            noCommentLayout.setVisibility(View.VISIBLE);

                        }
                    }
                    else {
                        Toast.makeText(context, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            swipeRefreshLayout.setRefreshing(false);
        }catch (Exception e){}
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_comment:
                String new_comment = comment.getText().toString();
                if (new_comment != null && Utils.isOnline(context)) {
                    Globals.ion.with(this).load(WebserviceUrl.INSERT_COMMENT)
                            .setHeader("userToken", Globals.userToken)
                            .setBodyParameter("MetadataID", String.valueOf(bookId))
                            .setBodyParameter("Comment", new_comment)
                            .setBodyParameter("ParentCommentID", "0")
                            .asString().setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            if (result == "0") {
                                comment.setError(getString(R.string.no_internet_connection));
                            } else {
                                comment.setText("");
                                Toast.makeText(context, "باتشکر - نظر شما پس از تایید نمایش داده خواهد شد", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(context, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;
            }
    }
}
