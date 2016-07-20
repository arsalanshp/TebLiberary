package library.tebyan.com.teblibrary.fragment;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import library.tebyan.com.teblibrary.R;

/**
 * Created by v.karimi on 7/17/2016.
 */
public class HomeFragment extends Fragment {

    public View view;
    public RecyclerView recyclerCategory;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_layout,container,false);
        initUI();
        return view;
    }

    @TargetApi(23)
    private void initUI() {
        recyclerCategory= (RecyclerView) view.findViewById(R.id.recycler_category);

    }

}
