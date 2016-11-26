package library.tebyan.com.teblibrary;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import library.tebyan.com.teblibrary.adapter.ScreenSlidePagerAdapter;
import library.tebyan.com.teblibrary.classes.CirclePageIndicator;
import library.tebyan.com.teblibrary.classes.DepthPageTransformer;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.PageIndicator;
import library.tebyan.com.teblibrary.fragment.BookListFragment;
import library.tebyan.com.teblibrary.fragment.HomeFragment;
import library.tebyan.com.teblibrary.fragment.SearchFragment;
import library.tebyan.com.teblibrary.model.AlbumMainResult;
import library.tebyan.com.teblibrary.shared.Shared;

public class MainActivity_old extends AppCompatActivity implements  View.OnTouchListener {
    public FragmentManager fragmentManager;
    public Fragment fragment;
    private CoordinatorLayout coordinatorLayout;
    public AHBottomNavigation bottomMenu;
    public ProgressBar progressBar;
    public Toolbar toolbar;
    public MenuItem searchItemMenu;
    public String searchFile;
    private static int NUM_PAGES = 5;
    PageIndicator mIndicator;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    AlbumMainResult slides;
    private Handler handler = new Handler();
    private Runnable animateViewPager;
    boolean stopSliding = false;
    private static final long ANIM_VIEWPAGER_DELAY = 5000;
    private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);
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
        slides = new AlbumMainResult();
        //initNavigation();
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mPager = (ViewPager) findViewById(R.id.slider_pager);
        initSlider();
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
        /*bottomMenu.setNotification("1", 1);*/

        bottomMenu.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                         startActivity(new Intent(getApplicationContext(),MainActivity_old.class));
                        break;
                    case 1:
                        initFavoriteFragment(1);
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        break;
                    case 3:
                        Globals.logout();
                        Shared.clearData(getApplicationContext());
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
    private void getSlides() {

        AlbumMainResult result = new AlbumMainResult();
        result.Result.add("http://img1.tebyan.net/Big/1395/05/39972162109162106512121860214164940234.jpg");
        result.Result.add("http://img1.tebyan.net/Big/1395/05/1632716224483129192781221549454324423431.jpg");
        result.Result.add("http://img1.tebyan.net/Big/1395/04/109119143180220124432031762502381719224513854.jpg");
        slides = result;
        NUM_PAGES = slides.Result.size();
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), slides.Result);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());
        mIndicator.setViewPager(mPager);
    }

    private void initSlider() {
        mIndicator.setOnPageChangeListener(new PageChangeListener());
        mPager.setOnPageChangeListener(new PageChangeListener());
        mPager.setOnTouchListener(this);

        getSlides();
    }


    public void runnable(final int size) {
        handler = new Handler();
        animateViewPager = new Runnable() {
            public void run() {
                if (!stopSliding) {
                    if (mPager.getCurrentItem() == size - 1) {
                        mPager.setCurrentItem(0);
                    } else {
                        mPager.setCurrentItem(
                                mPager.getCurrentItem() + 1, true);
                    }
                    handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
                }
            }
        };
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {

            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_UP:
                if (slides != null && slides.Result.size() != 0) {
                    stopSliding = false;
                    runnable(slides.Result.size());
                    handler.postDelayed(animateViewPager,
                            ANIM_VIEWPAGER_DELAY_USER_VIEW);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (handler != null && stopSliding == false) {
                    stopSliding = true;
                    handler.removeCallbacks(animateViewPager);
                }
                break;
        }
        return false;
    }


    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
               /* if (products != null) {
                    imgNameTxt.setText(""
                            + ((Product) products.get(mViewPager
                            .getCurrentItem())).getName());
                }*/
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
        }
    }
    @Override
    public void onPause() {
        if (handler != null) {
            //Remove callback
            handler.removeCallbacks(animateViewPager);
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (slides.Result.size() == 0) {
            getSlides();
        } else {
            mPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager(), slides.Result));

            mIndicator.setViewPager(mPager);
            runnable(slides.Result.size());
            //Re-run callback
            handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
        }
        super.onResume();
    }


//    it's for refreshing page after unFavorite book in onBackPressed DescitionActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                if (data.getStringExtra("result").equals("1")){
                    initFavoriteFragment(1);
                }
//                String result=data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}
