package library.tebyan.com.teblibrary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.BookAdapter;

/**
 * Created by v.karimi on 7/18/2016.
 */
public class BookListFragment extends Fragment {
    public View view;
    public BookAdapter adapter;
    public RecyclerView recyclerBookList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.list_book_fragment,container,false);
        initUI();
        return view;
    }

    private void initUI() {
        recyclerBookList= (RecyclerView) view.findViewById(R.id.recycler_book_list);
    }
}
