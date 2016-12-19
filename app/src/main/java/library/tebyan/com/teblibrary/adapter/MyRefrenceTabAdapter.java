package library.tebyan.com.teblibrary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import library.tebyan.com.teblibrary.fragment.menus.myRefrencePagesFraments.ReadFragment;
import library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments.AlphabetFragment;
import library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments.CollectionFragment;
import library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments.SubjectFragment;

/**
 * Created by root on 12/18/16.
 */
public class MyRefrenceTabAdapter extends FragmentStatePagerAdapter {
    int tabCount;
    boolean listState;

    public boolean isListState() {
        return listState;
    }

    public void setListState(boolean listState) {
        this.listState = listState;
    }

    //Constructor to the class
    public MyRefrenceTabAdapter(FragmentManager fm, int tabCount , boolean listState) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
        this.listState = listState;
    }

    public void updatePager(){
        notifyDataSetChanged();
    }


    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                ReadFragment tab1 = new ReadFragment();
                tab1.setFragmentTag("ReadedFragment");
                tab1.setListState(listState);
                return tab1;
            case 1:
                ReadFragment tab3 = new ReadFragment();
                tab3.setFragmentTag("WillReadFragment");
                tab3.setListState(listState);
                return tab3;
            case 2:
                ReadFragment tab2 = new ReadFragment();
                tab2.setFragmentTag("ReadingFragment");
                tab2.setListState(listState);
                return tab2;
            case 3:
                ReadFragment tab4 = new ReadFragment();
                tab4.setFragmentTag("NewFragment");
                tab4.setListState(listState);
                return tab4;


            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
