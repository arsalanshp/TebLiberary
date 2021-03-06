package library.tebyan.com.teblibrary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments.AlphabetFragment;
import library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments.CollectionFragment;
import library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments.SubjectFragment;

/**
 * Created by root on 12/18/16.
 */
public class ReviewPagerTabAdapter extends FragmentStatePagerAdapter {
    int tabCount,colCount,alphabetColCount;

    //Constructor to the class
    public ReviewPagerTabAdapter(FragmentManager fm, int tabCount, int colCount, int alphabetColCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
        this.colCount = colCount;
        this.alphabetColCount =alphabetColCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                CollectionFragment tab1 = new CollectionFragment();
                return tab1;
            case 1:
                AlphabetFragment  tab2 = new  AlphabetFragment ();
                tab2.setColCount(alphabetColCount);
//                tab2.setColCount(8);
                return tab2;
            case 2:
                SubjectFragment tab3 = new SubjectFragment();
                tab3.setColCount(colCount);

                return tab3;
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
