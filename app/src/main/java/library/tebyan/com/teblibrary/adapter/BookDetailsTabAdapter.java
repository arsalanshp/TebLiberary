package library.tebyan.com.teblibrary.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import library.tebyan.com.teblibrary.fragment.menus.metaDataFragments.AnalyzeFragment;
import library.tebyan.com.teblibrary.fragment.menus.metaDataFragments.MetaDataFragment;
import library.tebyan.com.teblibrary.fragment.menus.metaDataFragments.RelativeResourceFragment;
import library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments.AlphabetFragment;
import library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments.CollectionFragment;
import library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments.SubjectFragment;

/**
 * Created by root on 12/18/16.
 */
public class BookDetailsTabAdapter extends FragmentStatePagerAdapter {
    int tabCount , bookId;
    Bundle bundle;
    String authorName, language, genre,detailsRef,topic,note;

    //Constructor to the class
    public BookDetailsTabAdapter(FragmentManager fm, int tabCount,int bookId,String authorName,String language,String genre ,String detailsRef,String topic,String note) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
        bundle = new Bundle();
        this.bookId=bookId;
        this.authorName=authorName;
        this.language=language;
        this.genre=genre;
        this.detailsRef=detailsRef;
        this.topic=topic;
        this.note=note;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                RelativeResourceFragment tab2 = new  RelativeResourceFragment();
                tab2.setAuthorName(authorName);
//                bundle.clear();
//                bundle.putString("authorName", authorName);
//                tab2.setArguments(bundle);
                return tab2;

            case 1:
                AnalyzeFragment tab3 = new AnalyzeFragment();
                tab3.setBookId(bookId);
                return tab3;
            case 2:
                MetaDataFragment tab1 = new MetaDataFragment();
                tab1.setS_authorName(authorName);
                tab1.setS_language(language);
                tab1.setS_digitalRefrences(detailsRef);
                tab1.setS_refrenceType(genre);
                tab1.setS_subjects(topic);
                tab1.setS_creator(authorName);
                tab1.setS_note(note);
                return tab1;
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
