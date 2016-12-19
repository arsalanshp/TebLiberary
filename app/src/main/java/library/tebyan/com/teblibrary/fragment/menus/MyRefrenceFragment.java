package library.tebyan.com.teblibrary.fragment.menus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import library.tebyan.com.teblibrary.MainActivity;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.MyRefrenceTabAdapter;
import library.tebyan.com.teblibrary.classes.interfaces.UploadBookInterface;

public class MyRefrenceFragment extends Fragment implements View.OnClickListener,TabLayout.OnTabSelectedListener {

    private FragmentManager fragmentManager;
    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyRefrenceTabAdapter adapter;
    private boolean isBack= false;
    private ImageButton uploadBTN;
    private UploadBookInterface callBack;
    private ImageButton listStateBTN;
    private boolean listState;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        fragmentManager = getChildFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_refrence, container, false);
        uploadBTN = (ImageButton)view.findViewById(R.id.upload_btn);
        uploadBTN.setOnClickListener(this);
        listStateBTN = (ImageButton)view.findViewById(R.id.list_state_btn);
        listStateBTN.setOnClickListener(this);
        listStateBTN.setTag(true);
        this.callBack = (UploadBookInterface)getActivity();
        initTab();
        return view;
    }

    private void initTab(){

        //Initializing the tablayout
        tabLayout = (TabLayout) view.findViewById(R.id.my_ref_tab_layout);

        //Adding the tabs using addTab() method

        tabLayout.addTab(tabLayout.newTab().setText("خوانده شده"));
        tabLayout.addTab(tabLayout.newTab().setText("خواهم خواند"));
        tabLayout.addTab(tabLayout.newTab().setText("درحال خواندن"));
        tabLayout.addTab(tabLayout.newTab().setText("جدید"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //Initializing viewPager
        viewPager = (ViewPager) view.findViewById(R.id.my_ref_view_pager);
        viewPager.setOffscreenPageLimit(4);
        //Creating our pager adapter
        if(!isBack) {
            adapter = new MyRefrenceTabAdapter(fragmentManager, tabLayout.getTabCount(),listState);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.upload_btn:
                this.callBack.UploadBookInterfaces();
                return;
            case R.id.list_state_btn:
                listState = ((boolean)listStateBTN.getTag());
                listStateBTN.setTag(!listState);
                adapter = new MyRefrenceTabAdapter(fragmentManager, tabLayout.getTabCount(),listState);
                isBack = true;
                viewPager.setAdapter(adapter);
//                adapter.setListState(listState);
//                adapter.updatePager();
                break;
        }
    }
}
