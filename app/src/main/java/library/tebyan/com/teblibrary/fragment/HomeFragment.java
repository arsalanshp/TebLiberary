package library.tebyan.com.teblibrary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.MainActivity;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.BooksCategoryAdapter;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.Category;
import library.tebyan.com.teblibrary.model.Test;

/**
 * Created by v.karimi on 7/17/2016.
 */
public class HomeFragment extends Fragment {

    public View view;
    public RecyclerView recyclerCategory;
    public BooksCategoryAdapter adapter;
    public ArrayList<Category> data;
    public LinearLayoutManager manager;
    public int visibleItemCount,pastVisiblesItems,pageIndex;
    private int totalItemCount;
    boolean loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {



    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_layout,container,false);
        getCategory(0);
        initUI();
        return view;
    }


    private void initUI() {
        recyclerCategory= (RecyclerView) view.findViewById(R.id.recycler_category);
        manager=new LinearLayoutManager(getContext());
        recyclerCategory.setLayoutManager(manager);
        data=new ArrayList<>();
        adapter=new BooksCategoryAdapter(getContext(),data);
        recyclerCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerCategory.setHasFixedSize(true);
        recyclerCategory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = manager.getChildCount();
                totalItemCount = manager.getItemCount();
                pastVisiblesItems = manager.findFirstVisibleItemPosition();
                if (!loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        pageIndex++;
                        /*progressBar.setVisibility(View.VISIBLE);*/
                        getCategory(pageIndex);
                        Log.v("...", "Last Item Wow !");
                    }
                }
            }
        });
    }
    private void getCategory(int count) {
        if (Utils.isOnline((MainActivity) getActivity())) {
            ((MainActivity) getActivity()).progressBar.setVisibility(View.VISIBLE);
            Ion.with(getActivity()).load(WebserviceUrl.GET_COLLECTIONS+"PageIndex="+count).as(Test.class)
                    .setCallback(new FutureCallback<Test>() {
                        @Override
                        public void onCompleted(Exception e, Test test) {
                            Log.i("etgg", test + "");
                            data=test.getCategories();
                            adapter.items.addAll(test.getCategories());
                            adapter.notifyDataSetChanged();
                        }
                    });
        }else{
            Toast.makeText(getActivity(),getString(R.string.no_internet_connection),Toast.LENGTH_LONG).show();
        }

    }

}
