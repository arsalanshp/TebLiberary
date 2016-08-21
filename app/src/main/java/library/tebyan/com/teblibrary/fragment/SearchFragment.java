package library.tebyan.com.teblibrary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.SearchAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.Data;
import library.tebyan.com.teblibrary.model.SearchList;

/**
 * Created by v.karimi on 8/10/2016.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    public View view;
    public Context context;
    public RecyclerView recyclerSearch;
    public String searchText;
    public SearchAdapter adapter;
    public LinearLayoutManager manager;
    public FloatingActionButton fab;
    public ArrayList<Data> data=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchText=getArguments().getString("searchText");
        if(searchText!=null){
            initData(searchText);
        }

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.search_fragment_layout,container,false);
        context=container.getContext();
        initUI();
        return view;
    }

    private void initUI() {
        recyclerSearch= (RecyclerView) view.findViewById(R.id.recycler_search);
        fab= (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        manager=new LinearLayoutManager(getContext());
        recyclerSearch.setLayoutManager(manager);
        recyclerSearch.setHasFixedSize(true);
        adapter=new SearchAdapter(getActivity(),data);
        recyclerSearch.setAdapter(adapter);

    }

    private void initData(String title) {

        Globals.ion.with(getContext()).load(WebserviceUrl.SEARCH+"Field1="+title).as(SearchList.class).setCallback(new FutureCallback<SearchList>() {
            @Override
            public void onCompleted(Exception e, SearchList searchList) {
                if (Utils.isOnline(getContext())) {
                    if (e == null & searchList.getResult().getData().getData().size() > 0)
                        Log.i("sdsd", searchList + "");
                    adapter.items.addAll(searchList.getResult().getData().getData());
                    //recyclerSearch.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
//                initAdvanceSearch()
                break;
        }
    }


}
