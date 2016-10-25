package library.tebyan.com.teblibrary;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import library.tebyan.com.teblibrary.fragment.menus.MyRefrenceFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public FragmentManager fragmentManager;
    private LinearLayout menuLinearLayout,mainContent;
    private FloatingActionButton fab;
    private boolean menuIsOpen;
    private ImageButton my_refrence_menu,specials_menu,search_menu,review_menu,ask_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // for uplaodFile : getIntent().getExtras().getString("uploadFile");
        if (getIntent().getExtras() != null) {
            openFragment("library.tebyan.com.teblibrary.fragment.UploadBookFragment", "UploadBookFragment");
        }


        // start of defining menu attribut //
        menuIsOpen = false;
        menuLinearLayout = (LinearLayout) this.findViewById(R.id.menuLinearLayout);
        mainContent = (LinearLayout) this.findViewById(R.id.mainContent);
        mainContent.setOnClickListener(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.plus));
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
        // end of defining menu attribut //

    }
    @Override
    public void onClick(View view)  {

        switch (view.getId()) {
            case R.id.fab:
                menuLinearLayout.setVisibility(LinearLayout.VISIBLE);
                fab.hide();
                menuIsOpen = true;
                return;
            case R.id.mainContent:
                if (menuIsOpen) {
                    menuLinearLayout.setVisibility(LinearLayout.GONE);
                    fab.show();
                    menuIsOpen = false;
                }
                return;
            case R.id.my_refrence_menu:
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
                openFragment("library.tebyan.com.teblibrary.fragment.menus.AskFragment","ReviewFragment");
                return;


        }
    }


    public void openFragment(String fragmentName, String fragmentTag) {

//        fragmentName
//        MyRefrenceFragment myRefrenceFragment = new MyRefrenceFragment();
//        BookReaderFragment bookReaderFragment = new BookReaderFragment();
//        bookReaderFragment.setArguments(bundle);
        try{
            Class fName = Class.forName(fragmentName); //"com.duke.MyLocaleServiceProvider"
            Fragment fragment = (Fragment)fName.newInstance();
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, fragmentTag);
            if (fragmentTag!="UploadBookFragment")
                fragmentTransaction.addToBackStack(fragmentTag);
            fragmentTransaction.commit();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e){}



//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new MyRefrenceFragment()).commit();

    }
}
