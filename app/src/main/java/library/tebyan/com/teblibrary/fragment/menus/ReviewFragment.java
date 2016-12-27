package library.tebyan.com.teblibrary.fragment.menus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import library.tebyan.com.teblibrary.MainActivity;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.ReviewPagerTabAdapter;

public class ReviewFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private FragmentManager fragmentManager;
    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ReviewPagerTabAdapter adapter;
    private boolean isBack= false,bigWidth ;
    private int colCount,alphabetColCount;

    public ReviewFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getChildFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_review, container, false);
        if (getArguments() != null) {
            bigWidth = getArguments().getBoolean("bigWidth");
            if (bigWidth){
                colCount = 3;
                alphabetColCount = 8;
            }
            else {
                colCount = 2;
                alphabetColCount = 5;
            }
        }
        initTab();
        return view;
    }


    private void initTab(){

        //Initializing the tablayout
        tabLayout = (TabLayout) view.findViewById(R.id.review_tab_layout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("مرور موضوعی"));
        tabLayout.addTab(tabLayout.newTab().setText("مرور الفبایی"));
        tabLayout.addTab(tabLayout.newTab().setText("مرور مجموعه ها"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) view.findViewById(R.id.review_view_pager);
        viewPager.setOffscreenPageLimit(3);
        //Creating our pager adapter
        if(!isBack) {
            adapter = new ReviewPagerTabAdapter(fragmentManager, tabLayout.getTabCount(),colCount,alphabetColCount);
            isBack = true;
        }
        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        //Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(this);

    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}
    @Override
    public void onTabReselected(TabLayout.Tab tab) {}
}
