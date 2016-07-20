package library.tebyan.com.teblibrary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import library.tebyan.com.teblibrary.R;

/**
 * Created by root on 7/19/16.
 */
public class ProfileFragment extends Fragment {
    public View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.profile_layout,container,false);
        return view;
    }
}
