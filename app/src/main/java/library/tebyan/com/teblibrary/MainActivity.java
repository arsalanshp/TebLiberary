package library.tebyan.com.teblibrary;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import library.tebyan.com.teblibrary.fragment.BookListFragment;
import library.tebyan.com.teblibrary.fragment.HomeFragment;
import library.tebyan.com.teblibrary.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {
    public FragmentManager fragmentManager;
    public Fragment fragment;
    private CoordinatorLayout coordinatorLayout;
    public AHBottomNavigation bottomMenu;
    public ProgressBar progressBar;
    public Toolbar toolbar;
    public MenuItem searchItemMenu;
    public String searchFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        Fragment fragment = new HomeFragment();
        openMenuFragment("home", fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchItemMenu = menu.findItem(R.id.action_search);
        final SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(searchItemMenu);
        searchView.setQueryHint(getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String text) {
                searchFile = text;
                Fragment fragment = new SearchFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putString("searchText", text);
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame, fragment, "search");
                fragmentTransaction.addToBackStack("search");
                fragmentTransaction.commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    private void openMenuFragment(String tag, Fragment topFragment) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,
        //android.R.animator.fade_out);
        Bundle bundle = new Bundle();
        topFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame, topFragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void initUI() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        toolbar= (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        initMenu();
    }

/*    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onPrepareOptionsMenu(menu);
    }*/

    private void initMenu() {
        bottomMenu = (AHBottomNavigation) findViewById(R.id.bottom_menu);
        AHBottomNavigationItem actionHome = new AHBottomNavigationItem(R.string.action_home, R.drawable.ic_launcher, R.color.menu_background);
        AHBottomNavigationItem actionFavorite = new AHBottomNavigationItem(R.string.action_favorite, R.drawable.ic_launcher, R.color.menu_background);
        AHBottomNavigationItem actionMostVisited = new AHBottomNavigationItem(R.string.action_exit, R.drawable.ic_launcher, R.color.menu_background);
        AHBottomNavigationItem actionAccount = new AHBottomNavigationItem(R.string.action_account, R.drawable.ic_launcher, R.color.menu_background);
        bottomMenu.addItem(actionHome);
        bottomMenu.addItem(actionFavorite);
        bottomMenu.addItem(actionAccount);
        bottomMenu.addItem(actionMostVisited);
        //bottomMenu.setAccentColor(getResources().getColor(R.color.primary_light));
        //bottomMenu.setInactiveColor(getResources().getColor(R.color.primaryColor));
        //bottomMenu.setForceTint(true);
        bottomMenu.setForceTitlesDisplay(true);
        bottomMenu.setColored(true);
        bottomMenu.setCurrentItem(0);
        bottomMenu.setBackgroundColor(getResources().getColor(R.color.menu_background));
        /*bottomMenu.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));*/
        bottomMenu.setNotification("1", 1);

        bottomMenu.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                         startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        break;
                    case 1:
                        initFavoriteFragment(1);
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        break;
                    case 3:
                           finish();
                        break;
                    case 4:

                        break;
                }
                return true;
            }
        });
        bottomMenu.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                // Manage the new y position
            }
        });
    }

    private void initFavoriteFragment(int type) {
        Fragment fragment = new BookListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,
        //android.R.animator.fade_out);
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame, fragment, "bookList");
        fragmentTransaction.addToBackStack("bookList");
        fragmentTransaction.commitAllowingStateLoss();
    }

    public interface InitFragment{
        void initFragment(int id,int type,String tag);
    }
}
