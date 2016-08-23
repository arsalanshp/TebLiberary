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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.MainActivity;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.BookAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.Metadata;
import library.tebyan.com.teblibrary.model.MetadataList;

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
    public int visibleItemCount, pastVisiblesItems, pageIndex, categoryId;
    private int totalItemCount;
    boolean loading;
    public ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
        categoryId = getArguments().getInt("id");
        initData(type);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_book_fragment, container, false);
        initUI();
        return view;
    }

    private void initData(int type) {
        switch (type) {
            case 0:
                getBooks(0);
                break;
            case (1):
                getBooks(1);
                break;
        }
    }

    private void getBooks(int count) {
        ((MainActivity) getActivity()).progressBar.setVisibility(View.VISIBLE);
        String url = null;
        if (Utils.isOnline((MainActivity) getActivity())) {
            if (type == 0) {
                url = WebserviceUrl.GET_METADATA_LIST + "PageIndex=" + count + "&ID=" + categoryId;
            } else {
                url = WebserviceUrl.GET_FAVORITE_LIST;
            }
            Globals.ion.with(getActivity()).load(url).setHeader("userToken", Globals.userToken).as(MetadataList.class)
                    .setCallback(new FutureCallback<MetadataList>() {
                        @Override
                        public void onCompleted(Exception e, MetadataList metaData) {
                            ((MainActivity) getActivity()).progressBar.setVisibility(View.GONE);
                            if (e == null && metaData.getResult().size() > 0) {
                                data = metaData.getResult();
                                adapter.items.addAll(data);
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getActivity(), getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
        }

    }

    private void initUI() {
        recyclerBookList = (RecyclerView) view.findViewById(R.id.recycler_book_list);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        if (Utils.isTablet(getContext())) {
            manager = new GridLayoutManager(getContext(), 3);
        } else {
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
                        if(type==0) {
                            getBooks(pageIndex);
                        }
                    }
                }
            }
        });
    }
}

