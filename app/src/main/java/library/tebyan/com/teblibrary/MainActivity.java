package library.tebyan.com.teblibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import library.tebyan.com.teblibrary.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {
    public FragmentManager fragmentManager;
    public Fragment fragment;
    private CoordinatorLayout coordinatorLayout;
    public AHBottomNavigation bottomMenu;
    public ProgressBar progressBar;

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
        /*getMenuInflater().inflate(R.menu.menu_main, menu);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
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
        initMenu();
    }


    private void initMenu() {
        bottomMenu = (AHBottomNavigation) findViewById(R.id.bottom_menu);
        AHBottomNavigationItem actionHome = new AHBottomNavigationItem(R.string.action_home, R.drawable.ic_launcher, R.color.menu_background);
        AHBottomNavigationItem actionFavorite = new AHBottomNavigationItem(R.string.action_favorite, R.drawable.ic_launcher, R.color.menu_background);
        AHBottomNavigationItem actionMore = new AHBottomNavigationItem(R.string.action_add_favourite, R.drawable.ic_launcher, R.color.menu_background);
        AHBottomNavigationItem actionMostVisited = new AHBottomNavigationItem(R.string.action_most_visited, R.drawable.ic_launcher, R.color.menu_background);
        AHBottomNavigationItem actionAccount = new AHBottomNavigationItem(R.string.action_account, R.drawable.ic_launcher, R.color.menu_background);
        bottomMenu.addItem(actionHome);
        bottomMenu.addItem(actionFavorite);
        bottomMenu.addItem(actionMore);
        bottomMenu.addItem(actionMostVisited);
        bottomMenu.addItem(actionAccount);
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

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
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
    public interface InitFragment{
        void initFragment(int id,int type,String tag);
    }
}
