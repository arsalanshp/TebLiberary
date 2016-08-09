package library.tebyan.com.teblibrary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import library.tebyan.com.teblibrary.adapter.BookAdapter;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.MetadataList;
import library.tebyan.com.teblibrary.model.Metadata;

/**
 * Created by v.karimi on 7/18/2016.
 */
public class BookListFragment extends Fragment {
    public View view;
    public RecyclerView recyclerBookList;
    public BookAdapter adapter;
    public ArrayList<Metadata> data;
    public int type;
    public GridLayoutManager manager;
    public int visibleItemCount, pastVisiblesItems, pageIndex,categoryId;
    private int totalItemCount;
    boolean loading;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=getArguments().getInt("type");
        categoryId=getArguments().getInt("id");
        initData(type);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.list_book_fragment,container,false);
        initUI();
        getBooks(0);
        return view;
    }
    private void initData(int type) {
        switch (type){
            case 0:
                getBooks(0);
        }
    }

    private void getBooks(int count) {
        if (Utils.isOnline((MainActivity) getActivity())) {
            ((MainActivity) getActivity()).progressBar.setVisibility(View.VISIBLE);
            Ion.with(getActivity()).load(WebserviceUrl.GET_METADATA_LIST + "PageIndex=" + count+"&ID="+categoryId).as(MetadataList.class)
                    .setCallback(new FutureCallback<MetadataList>() {
                        @Override
                        public void onCompleted(Exception e, MetadataList metaData) {
                            if (e == null) {
                                Log.i("etgg", metaData + "");
                                data = metaData.getResult();
                                adapter.items.addAll(data);
                                adapter.notifyDataSetChanged();
                            }

                        }
                    });
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
        }

    }
    private void initUI() {
        recyclerBookList= (RecyclerView) view.findViewById(R.id.recycler_book_list);
        if(Utils.isTablet(getContext())) {
            manager = new GridLayoutManager(getContext(), 3);
        }else {
            manager = new GridLayoutManager(getContext(), 3);
        }
        recyclerBookList.setLayoutManager(manager);
        data = new ArrayList<>();
        adapter = new BookAdapter(getContext(), data);
        recyclerBookList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerBookList.setHasFixedSize(true);
        recyclerBookList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = manager.getChildCount();
                totalItemCount = manager.getItemCount();
                pastVisiblesItems = manager.findFirstVisibleItemPosition();
                if (!loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        pageIndex++;
                        /*progressBar.setVisibility(View.VISIBLE);*/
                        getBooks(pageIndex);
                        Log.v("...", "Last Item Wow !");
                    }
                }
            }
        });
    }
    }

