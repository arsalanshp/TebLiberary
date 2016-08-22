package library.tebyan.com.teblibrary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.fragment.ImageSliderFragment;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> slides;
    public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<String> slides) {
        super(fm);
        this.slides=slides;
    }

    @Override
    public Fragment getItem(int position) {
        return ImageSliderFragment.newInstance(slides.get(position));
    }

    @Override
    public int getCount() {
        return slides.size();
    }
}
