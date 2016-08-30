package library.tebyan.com.teblibrary.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.MainActivity;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.BooksCategoryAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.Category;
import library.tebyan.com.teblibrary.model.CategoryList;

/**
 * Created by v.karimi on 7/17/2016.
 */
public class HomeFragment extends Fragment implements MainActivity.InitFragment {

    public View view;
    public RecyclerView recyclerCategory;
    public BooksCategoryAdapter adapter;
    public ArrayList<Category> data;
    public LinearLayoutManager manager;
    public int visibleItemCount,pastVisiblesItems,pageIndex;
    private int totalItemCount;
    public MenuItem searchItemMenu;
    private String searchFile;
    boolean loading;
/*    private static int NUM_PAGES = 5;
    PageIndicator mIndicator;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    AlbumMainResult slides;
    public Toolbar toolbar;
    private Handler handler = new Handler();
    private Runnable animateViewPager;
    boolean stopSliding = false;
    private static final long ANIM_VIEWPAGER_DELAY = 5000;
    private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {



    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_layout,container,false);

        initUI();
        if(Utils.isTablet(getContext())) {
            getCategory(0,8);
        }else{
            getCategory(0,6);
        }
        return view;
    }


    private void initUI() {
        recyclerCategory= (RecyclerView) view.findViewById(R.id.recycler_category);
        manager=new LinearLayoutManager(getContext());
        recyclerCategory.setLayoutManager(manager);
        data=new ArrayList<>();
        adapter=new BooksCategoryAdapter(getContext(),data);
        recyclerCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerCategory.setHasFixedSize(true);
        adapter.setHandler(this);
       /* slides = new AlbumMainResult();
        //initNavigation();
        mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        mPager = (ViewPager) view.findViewById(R.id.slider_pager);
        initSlider();*/
        recyclerCategory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = manager.getChildCount();
                totalItemCount = manager.getItemCount();
                pastVisiblesItems = manager.findFirstVisibleItemPosition();
                if (!loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        pageIndex++;
                        /*progressBar.setVisibility(View.VISIBLE);*/
                        if(Utils.isTablet(getContext())) {
                            getCategory(pageIndex, 8);
                        }else{
                            getCategory(pageIndex, 6);
                        }
                        Log.v("...", "Last Item Wow !");
                    }
                }
            }
        });
    }
    private void getCategory(int count,int pageSize) {
        if (Utils.isOnline((MainActivity) getActivity())) {
            ((MainActivity) getActivity()).progressBar.setVisibility(View.VISIBLE);
            Globals.ion.with(getActivity()).load(WebserviceUrl.GET_COLLECTIONS+"PageIndex="+count+"&PageSize2="+pageSize).as(CategoryList.class)
                    .setCallback(new FutureCallback<CategoryList>() {
                        @Override
                        public void onCompleted(Exception e, CategoryList test) {
                            ((MainActivity) getActivity()).progressBar.setVisibility(View.GONE);
                            if (e == null && test.getCategories().size() > 0) {
                                data = test.getCategories();
                                adapter.items.addAll(test.getCategories());
                                adapter.notifyDataSetChanged();
                            } else if (e!= null){
                                Toast.makeText(getContext(), getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(getActivity(),getString(R.string.no_internet_connection),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void initFragment(int id, int type, String tag) {
        Fragment fragment = new BookListFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,
        //android.R.animator.fade_out);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        searchItemMenu = menu.findItem(R.id.action_search);
        final SearchManager searchManager = (SearchManager)getContext().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(searchItemMenu);
        searchView.setQueryHint(getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String text) {
                searchFile = text;
                Fragment fragment = new HomeFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
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
        super.onCreateOptionsMenu(menu, inflater);
    }
    /*private void getSlides() {

        AlbumMainResult result = new AlbumMainResult();
        result.Result.add("http://www.niazmandiha.net/img/1382812728_4946234.jpg");
        result.Result.add("http://iteraket.ir/img/users_files/16-1450198688.png");
        result.Result.add("http://8pic.ir/images/othh32lxzyrvo5nseaje.jpg");
        slides = result;
        NUM_PAGES = slides.Result.size();
        mPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager(), slides.Result);
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
               *//* if (products != null) {
                    imgNameTxt.setText(""
                            + ((Product) products.get(mViewPager
                            .getCurrentItem())).getName());
                }*//*
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
            mPager.setAdapter(new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager(), slides.Result));

            mIndicator.setViewPager(mPager);
            runnable(slides.Result.size());
            //Re-run callback
            handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
        }
        super.onResume();
    }*/
}
