package library.tebyan.com.teblibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.adapter.CommentsAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.IonRoundedCornersTransformation;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.fragment.BookReaderFragment;
import library.tebyan.com.teblibrary.model.BookDetails;
import library.tebyan.com.teblibrary.model.BookDetailsResults;
import library.tebyan.com.teblibrary.model.Comment;
import library.tebyan.com.teblibrary.model.CommentsList;

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener {

    public int bookId;
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
    private FragmentManager fragmentManager;
    public Toolbar toolbar;
    public LinearLayoutManager mLayoutManager;
    public int visibleItemCount,pastVisiblesItems,pageIndex;
    private int totalItemCount;
    private boolean favoriout_status;
    private boolean loading=false;
    private Menu menu;



    @Override
    public void onBackPressed() {
        // for updating favoriout view
        if (!favoriout_status) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", "1");
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
        super.onBackPressed();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        bookId = getIntent().getIntExtra("book_id", 0);
        initUI();
        sendRequest();
    }

    private void initUI() {
        txtAuthor = (TextView) findViewById(R.id.author_name);
        txtBookName = (TextView) findViewById(R.id.book_name);
        imgViewBook = (ImageView) findViewById(R.id.book_thumbnail);
        imgViewBook.setOnClickListener(this);
//        morePart
        txtAuthorMore = (TextView) findViewById(R.id.book_author_more);
        publisher = (TextView) findViewById(R.id.publisher);
        description = (TextView) findViewById(R.id.book_description);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        comment = (EditText) findViewById(R.id.comment_text);
        send_comment_btn = (ImageButton) findViewById(R.id.send_comment);
        send_comment_btn.setOnClickListener(this);


    }

    private void sendRequest() {
        Log.i("etgg", WebserviceUrl.Get_BOOK_DETAILS + "?ID=" + bookId);
        if (Utils.isOnline(this)) {
//            ((DescriptionActivity) getActivity()).progressBar.setVisibility(View.VISIBLE);
            Globals.ion.with(this).load(WebserviceUrl.Get_BOOK_DETAILS + "?ID=" + bookId)
                    .setHeader("userToken", Globals.userToken)
                    .as(BookDetailsResults.class)
                    .setCallback(new FutureCallback<BookDetailsResults>() {
                        @Override
                        public void onCompleted(Exception e, BookDetailsResults result) {
                            if (e == null & result != null) {
                                Log.i("etgg", result + "");
                                initData(result.getResult());
                            }
                        }
                    });

        } else {
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void initData(BookDetails details) {
        txtBookName.setText(details.getTitle());
        txtAuthor.setText(details.getAuthor());
        String url = details.getImageUrl();

        Globals.ion.with(imgViewBook)
                .transform(new IonRoundedCornersTransformation(5, 0))
                .load(details.getImageUrl());
//        if (url.contains("DefaulImage/metadatabook.jpg")) {
//
//            imgViewBook.setImageResource(R.drawable.appicon);
//
//        } else {
//            Globals.ion.with(imgViewBook)
//                    .transform(new IonRoundedCornersTransformation(5, 0))
//                    .load(details.getImageUrl());
//        }
        imgViewBook.setTag(details.getLink());

        txtAuthorMore.setText(details.getAuthor());
        publisher.setText(details.getPublisher());
        description.setText(details.getDescription());

        if (details.getForRead()) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.favorite_selected));
            favoriout_status = true;
        }
        else{
            favoriout_status = false;
        }


        init_commentView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_profile_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.favorite_book:
                if (favoriout_status) {
                    removeFavorite();
                }else{
                    addFavorite();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean removeFavorite() {
        if (Utils.isOnline(getApplicationContext())) {

            Globals.ion.with(this).load(WebserviceUrl.UN_FAVORITE)
                    .setHeader("userToken", Globals.userToken)
                    .setBodyParameter("metadataID",String.valueOf(bookId))
                    .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject d) {
                    if (d!= null & d.getAsJsonObject("d").get("IsMessage").getAsBoolean()) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.favorite_unselected));
                        favoriout_status = false;
                    } else {
                        favoriout_status = true;
                    }

                }
            });
            return favoriout_status;
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean addFavorite() {
        if (Utils.isOnline(getApplicationContext())) {
            Globals.ion.with(this).load(WebserviceUrl.ADD_FAVORITE + bookId).setHeader("userToken", Globals.userToken).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject d) {
                    if (d!= null & d.getAsJsonObject("d").get("IsMessage").getAsBoolean()) {
                        Toast.makeText(getApplicationContext(), d.getAsJsonObject("d").get("Message").toString(), Toast.LENGTH_SHORT).show();
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.favorite_selected));
                        favoriout_status = true;
                    } else {
                        favoriout_status = false;
                    }

                }
            });
            return favoriout_status;
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void init_commentView() {

        recyclerView = (RecyclerView) findViewById(R.id.comment_recycler_view);
        commentsAdapter = new CommentsAdapter(commentList);
        mLayoutManager = new LinearLayoutManager(this); //getApplicationContext()
        mLayoutManager =new LinearLayoutManager(getApplicationContext());


        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(commentsAdapter);
        recyclerView.setHasFixedSize(true);
        //paging part
        getComments(pageIndex);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                if (!loading && dy>0) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        pageIndex++;
                        getComments(pageIndex);
                    }
                }
            }
        });
    }

    private void getComments(int pageIndex) {
        if (Utils.isOnline(this)) {
//            Ion.with(this).load(WebserviceUrl.GET_COMMENT)
//                    .setHeader("userToken",Globals.userToken)
//                    .setBodyParameter("ID",String.valueOf(bookId))
//                    .setBodyParameter("PageSize","10")
//                    .setBodyParameter("PageIndex","0")
            Globals.ion.with(this).load(WebserviceUrl.GET_COMMENT)
                    .setHeader("token_id", Globals.userToken)
                    .setBodyParameter("ID", String.valueOf(bookId))
                    .setBodyParameter("PageSize", "10")
                    .setBodyParameter("PageIndex",String.valueOf(pageIndex))
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
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
        loading=false;
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
        switch (view.getId()) {

            case R.id.book_thumbnail:
                String bookUrl = (String) imgViewBook.getTag();
                Bundle bundle = new Bundle();
//                bundle.putString("book_url", "https://library.tebyan.net/fa/Viewer/Pdf/"+bookId+"/1?frame=true&userToken="+Globals.userToken);
                bundle.putString("book_url", bookUrl + "&userToken=" + Globals.userToken);
                bundle.putString("book_url", "https://library.tebyan.net/fa/Viewer/Pdf/" + bookId + "/1?frame=true&userToken=" + Globals.userToken);
                BookReaderFragment bookReaderFragment = new BookReaderFragment();
                bookReaderFragment.setArguments(bundle);
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_fragment, bookReaderFragment, "bookReader");
                fragmentTransaction.addToBackStack("bookReader");
                fragmentTransaction.commit();
                break;

            case R.id.send_comment:
                String new_comment = comment.getText().toString();
                String token = Globals.userToken;
//                String url = WebserviceUrl.INSERT_COMMENT + "MetadataID=" + bookId + "&Comment=" + new_comment + "&ParentCommentID=" + bookId;
//                if (new_comment != null && Utils.isOnline(this)) {
//                    Globals.ion.with(this).load(WebserviceUrl.INSERT_COMMENT)
//                            .setHeader("userToken", Globals.userToken)
//                            .setBodyParameter("MetadataID", String.valueOf(bookId))
//                            .setBodyParameter("Comment", new_comment)
//                            .setBodyParameter("ParentCommentID", String.valueOf(0));
                String url = WebserviceUrl.INSERT_COMMENT + "MetadataID=" + bookId + "&Comment=" + new_comment + "&ParentCommentID=" + bookId;
                if (new_comment != null && Utils.isOnline(this)) {
                    Globals.ion.with(this).load(WebserviceUrl.INSERT_COMMENT)
                            .setHeader("token_id", Globals.userToken)
                            .setBodyParameter("MetadataID", String.valueOf(bookId))
                            .setBodyParameter("Comment", new_comment)
                            .setBodyParameter("ParentCommentID", String.valueOf(0))
                            .asString().setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            if (result == "0") {
                                comment.setError(getString(R.string.no_internet_connection));
                            } else {
                                comment.setText("");
                                Toast.makeText(getApplicationContext(), "باتشکر - نظر شما پس از تایید نمایش داده خواهد شد", Toast.LENGTH_SHORT).show();
                            }
                            Log.i("result", result);
                        }
                    });
                } else {
                    Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
