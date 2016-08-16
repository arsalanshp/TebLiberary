package library.tebyan.com.teblibrary;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import library.tebyan.com.teblibrary.adapter.CommentsAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.IonRoundedCornersTransformation;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.BookDetails;
import library.tebyan.com.teblibrary.model.BookDetailsResults;
import library.tebyan.com.teblibrary.model.Comment;
import library.tebyan.com.teblibrary.model.CommentsList;

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    
    public int bookId;
    public static Toolbar toolbar;
    public TextView txtAuthor;
    public TextView txtBookName;//book_name
    public ImageView imgViewBook; //book_thumbnail
    public TextView txtAuthorMore;
    public TextView publisher;
    public TextView description;
    private ArrayList<Comment> commentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CommentsAdapter commentsAdapter;
    private EditText comment;
    private ImageButton send_comment_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        comment = (EditText)findViewById(R.id.comment_text);
        send_comment_btn = (ImageButton) findViewById(R.id.send_comment);
        send_comment_btn.setOnClickListener(this);

        bookId=getIntent().getIntExtra("book_id",0);
        initUI();
        sendRequest();
    }

    private void initUI() {
        txtAuthor= (TextView) findViewById(R.id.author_name);
        txtBookName = (TextView) findViewById(R.id.book_name);
        imgViewBook = (ImageView) findViewById(R.id.book_thumbnail);
//        morePart
        txtAuthorMore = (TextView) findViewById(R.id.book_author_more);
        publisher = (TextView) findViewById(R.id.publisher);
        description =(TextView) findViewById(R.id.book_description);

    }
    private void sendRequest() {
        Log.i("etgg" , WebserviceUrl.Get_BOOK_DETAILS+"?ID="+bookId);
        if (Utils.isOnline(this)) {
//            ((DescriptionActivity) getActivity()).progressBar.setVisibility(View.VISIBLE);
            Ion.with(this).load(WebserviceUrl.Get_BOOK_DETAILS+"?ID="+bookId).as(BookDetailsResults.class)
                    .setCallback(new FutureCallback<BookDetailsResults>() {
                        @Override
                        public void onCompleted(Exception e, BookDetailsResults result) {
                            if (e == null) {
                                Log.i("etgg", result + "");
                                initData(result.getResult());
                            }
                        }
                    });

        } else {
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    private void initData(BookDetails details) {
        txtBookName.setText(details.getTitle());
        txtAuthor.setText(details.getAuthor());
        details.getAuthor();
        Ion.with(imgViewBook)
                .transform(new IonRoundedCornersTransformation(5, 0))
                .load(details.getImageUrl());
        txtAuthorMore.setText(details.getAuthor());
        publisher.setText(details.getPublisher());
        description.setText(details.getDescription());

        init_commentView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    private void init_commentView(){
        recyclerView = (RecyclerView) findViewById(R.id.comment_recycler_view);
        commentsAdapter = new CommentsAdapter(commentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this); //getApplicationContext()
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(commentsAdapter);

        if (Utils.isOnline(this)) {
            Ion.with(this).load(WebserviceUrl.GET_COMMENT)
                    .setHeader("token_id",Globals.userToken)
                    .setBodyParameter("ID",String.valueOf(bookId))
                    .setBodyParameter("PageSize","10")
                    .setBodyParameter("PageIndex","0")
                    .as(CommentsList.class)
                    .setCallback(new FutureCallback<CommentsList>() {
                        @Override
                        public void onCompleted(Exception e, CommentsList result) {
                            if (e == null) {
                                Log.i("etgg", result + "");
                                commentList = result.getComments();
                                commentsAdapter.commentsList.addAll(result.getComments());
                                commentsAdapter.notifyDataSetChanged();
//                                prepareMovieData();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
        }

//        commentsAdapter.notifyDataSetChanged();
//        prepareMovieData();
    }


//    private void prepareMovieData() {
//        Comment comment = new Comment("Mad Max: Fury Road", "Action & Adventure");
//        commentList.add(comment);
//
//        comment = new Comment("Inside Out", "Animation, Kids & Family");
//        commentList.add(comment);
//        commentsAdapter.notifyDataSetChanged();

//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_comment:
                String new_comment = comment.getText().toString();
                String token = Globals.userToken;
                String url = WebserviceUrl.INSERT_COMMENT+"MetadataID="+bookId+"&Comment="+new_comment+"&ParentCommentID="+bookId;
                if (new_comment!=null && Utils.isOnline(this)) {
                    Ion.with(this).load(WebserviceUrl.INSERT_COMMENT)
                            .setHeader("token_id",Globals.userToken)
                            .setBodyParameter("MetadataID",String.valueOf(bookId))
                            .setBodyParameter("Comment",new_comment)
                            .setBodyParameter("ParentCommentID",String.valueOf(0))
                            .asString().setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            if (result=="0"){
                                comment.setError(getString(R.string.no_internet_connection));
                            }
                            else{
                                comment.setText("");
                            }
                           Log.i("result",result);
                        }
                    });
                } else {
                    Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
                }
        }
    }


}
