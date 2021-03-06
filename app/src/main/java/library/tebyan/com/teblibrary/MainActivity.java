package library.tebyan.com.teblibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.interfaces.AskQuestionInterface;
import library.tebyan.com.teblibrary.classes.interfaces.BookDetailsInterface;
import library.tebyan.com.teblibrary.classes.interfaces.BookReaderInterface;
import library.tebyan.com.teblibrary.classes.interfaces.BookerAnswerInterface;
import library.tebyan.com.teblibrary.classes.interfaces.CollectionsInterface;
import library.tebyan.com.teblibrary.classes.interfaces.SubSubjectiveReviewInterface;
import library.tebyan.com.teblibrary.classes.interfaces.UploadBookInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , BookDetailsInterface,UploadBookInterface,SubSubjectiveReviewInterface,CollectionsInterface, BookerAnswerInterface,AskQuestionInterface,BookReaderInterface {

    public FragmentManager fragmentManager;
    private LinearLayout menuLinearLayout,mainContent;
    private FloatingActionButton fab;
    private ImageButton my_refrence_menu,specials_menu,search_menu,review_menu,ask_menu;
    private int bookID;
    private String bookUrl;
    private boolean ownerFlag;
    private int subSubjectID;
    private int collectionID;
    private String collectionTitle;
    private String collectionThumbnail;
    private String bookerQestion;
    private int bookerQestionId;
    private ProgressBar progress_bar;
    DisplayMetrics displaymetrics;
    private int windowHeight;
    private int windowWidth;
    private boolean bigWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bookID = 0;
        subSubjectID = 0;
        collectionID =0;
        bookerQestionId=0;

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.ic_launcher);

        getSupportActionBar().setTitle("");
        // start of defining menu attribut //
        menuLinearLayout = (LinearLayout) this.findViewById(R.id.menuLinearLayout);
//        mainContent = (LinearLayout) this.findViewById(R.id.mainContent);
//        mainContent.setOnClickListener(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.plus));
        fab.setTag("hide");
        fab.setOnClickListener(this);
        my_refrence_menu = (ImageButton) findViewById(R.id.my_refrence_menu);
        my_refrence_menu.setOnClickListener(this);
        specials_menu = (ImageButton) findViewById(R.id.specials_menu);
        specials_menu.setOnClickListener(this);
        search_menu = (ImageButton) findViewById(R.id.search_menu);
        search_menu.setOnClickListener(this);
        review_menu = (ImageButton) findViewById(R.id.review_menu);
        review_menu.setOnClickListener(this);
        ask_menu = (ImageButton) findViewById(R.id.ask_menu);
        ask_menu.setOnClickListener(this);
        /////// get size
        displaymetrics = new DisplayMetrics();
        getMetrics();

        // end of defining menu attribut //
        my_refrence_menu.performClick();
//        }

    }

    private void getMetrics(){
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        windowHeight = displaymetrics.heightPixels;
        windowWidth = displaymetrics.widthPixels;
        if (windowWidth <500){
            bigWidth =false;
        }
        else
            bigWidth = true;

    }

    private void setFabMenu(){
        if (fab.getTag()=="visible") {
            menuLinearLayout.setVisibility(LinearLayout.VISIBLE);
            fab.setTag("hide");
            fab.hide();
        }else{
            menuLinearLayout.setVisibility(LinearLayout.GONE);
            fab.show();
            fab.setTag("visible");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onClick(View view)  {

        setFabMenu();
        switch (view.getId()) {
            case R.id.my_refrence_menu:
//                Intent intent = new Intent(this , ReviewActivity.class);
//                startActivity(intent);
                openFragment("library.tebyan.com.teblibrary.fragment.menus.MyRefrenceFragment","MyRefrenceFragment");
                return;
            case R.id.specials_menu:
                openFragment("library.tebyan.com.teblibrary.fragment.menus.SpecialsFragment","SpecialsFragment");
                return;
            case R.id.search_menu:
                openFragment("library.tebyan.com.teblibrary.fragment.menus.SearchFragment","SearchFragment");
                return;
            case R.id.review_menu:
                openFragment("library.tebyan.com.teblibrary.fragment.menus.ReviewFragment","ReviewFragment");
                return;
            case R.id.ask_menu:
                openFragment("library.tebyan.com.teblibrary.fragment.menus.AskFragment","AskFragment");
                return;
        }
    }


    public void openFragment(String fragmentName, String fragmentTag) {

        try{
            Class fName = Class.forName(fragmentName);
            Fragment fragment = (Fragment)fName.newInstance();



            if(bookUrl!=null){
                Bundle args = new Bundle();
                args.putString("book_url",bookUrl);
                fragment.setArguments(args);
            }
            else if (bookID!=0){
                Bundle args = new Bundle();
                args.putInt("book_id",bookID);
                args.putBoolean("ownerFlag",ownerFlag);
                fragment.setArguments(args);
            }
            else if(subSubjectID !=0){
                Bundle args = new Bundle();
                args.putInt("subSubjectID",subSubjectID);
                fragment.setArguments(args);
            }
            else if(collectionID !=0){
                Bundle args = new Bundle();
                args.putInt("collectionID",collectionID);
                args.putString("collectionTitle",collectionTitle);
                args.putString("collectionThumbnail",collectionThumbnail);
                fragment.setArguments(args);
            }
            else if (bookerQestionId!=0){
                Bundle args = new Bundle();
                args.putInt("bookerQestionId",bookerQestionId);
                args.putString("bookerQestion",bookerQestion);
                fragment.setArguments(args);
            }
            else {
                Bundle args = new Bundle();
                args.putBoolean("bigWidth", bigWidth);
                fragment.setArguments(args);
            }


            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, fragmentTag);
            fragmentTransaction.addToBackStack(fragmentTag);
            fragmentTransaction.commit();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e){}
    }






    @Override
    public void StartBookDetailsInterfaces(int bookID) {
        this.subSubjectID = 0;
        this.collectionID= 0;
        this.bookID =bookID;
        this.ownerFlag = false;
        openFragment("library.tebyan.com.teblibrary.fragment.BookDetailsFragment","BookDetailsFragment");
    }
    @Override
    public void StartOwnerBookDetailsInterfaces(int bookID) {
        this.subSubjectID = 0;
        this.collectionID= 0;
        this.bookID =bookID;
        this.ownerFlag = true;
        openFragment("library.tebyan.com.teblibrary.fragment.BookDetailsFragment","BookDetailsFragment");
    }



    @Override
    public void callbackAskQuestion() {
        this.subSubjectID = 0;
        this.collectionID= 0;
        this.bookID = 0;
        openFragment("library.tebyan.com.teblibrary.fragment.menus.AskQuestionFragment", "AskQuestionFragment");
    }


    @Override
    public void UploadBookInterfaces() {
        this.subSubjectID = 0;
        this.collectionID= 0;
        this.bookID = 0;
        openFragment("library.tebyan.com.teblibrary.fragment.UploadBookFragment", "UploadBookFragment");
    }

    @Override
    public void StartSubSubjectiveInterfaces(int subSubjectID) {
        this.bookID = 0;
        this.collectionID= 0;
        this.subSubjectID =subSubjectID;
        openFragment("library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments.SubSubjectiveFragment", "SubSubjectiveFragment");
    }

    @Override
    public void StartSubCollectionsInterface(int collectionID,String collectionTitle, String collectionThumbnail) {
        this.bookID = 0;
        this.subSubjectID = 0;
        this.collectionTitle = collectionTitle;
        this.collectionThumbnail = collectionThumbnail;
        this.collectionID = collectionID;
        openFragment("library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments.SubCollectionFragment", "SubCollectionFragment");

    }

    @Override
    public void StartBookerAnswersInterface(int questionID,String questionText) {
        this.bookerQestionId=questionID;
        this.bookerQestion = questionText;
        openFragment("library.tebyan.com.teblibrary.fragment.BookerAnsewerFragment", "BookerAnsewerFragment");

    }

    @Override
    public void StartBookerReaderInterface(int bookId , String bookUrl) {
//        this.bookerQestionId=questionID;
//        this.bookerQestion = questionText;
        this.bookUrl = "https://library.tebyan.net/fa/Viewer/Pdf/" + bookId + "/1?frame=true&userToken=" + Globals.userToken;
        openFragment("library.tebyan.com.teblibrary.fragment.BookReaderFragment", "BookReaderFragment");

    }


}
