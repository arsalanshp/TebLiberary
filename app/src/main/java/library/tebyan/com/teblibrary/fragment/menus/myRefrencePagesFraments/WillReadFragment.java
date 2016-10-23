package library.tebyan.com.teblibrary.fragment.menus.myRefrencePagesFraments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.BookAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.Data;
import library.tebyan.com.teblibrary.model.DataList;
import library.tebyan.com.teblibrary.model.MetadataList;

public class WillReadFragment extends Fragment {

    View view;
    Context context;
    public ArrayList<Data> items;

    RecyclerView recyclerView;
    BookAdapter bookAdapter;
    public int visibleItemCount,pastVisiblesItems,pageIndex;
    private int totalItemCount;
    boolean loading;
    LinearLayoutManager linearLayoutManager;
    public ArrayList<Data> data=new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_will_read, container, false);
        context = getContext();
        initUI();
        return view;
    }

    private void initUI() {

        recyclerView = (RecyclerView) view.findViewById(R.id.will_read_recycler_view);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                if (!loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        pageIndex++;
                        initData();
                    }
                }
            }
        });
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        bookAdapter = new BookAdapter(context, data);
        recyclerView.setAdapter(bookAdapter);
    }


    private void initData() {
        try {

            Globals.ion.with(this).load(WebserviceUrl.FOR_READ + "&PageSize=10&PageIndex=" + pageIndex)
                    .setHeader("userToken", Globals.userToken)
                    .as(DataList.class).setCallback(new FutureCallback<DataList>() {
                @Override
                public void onCompleted(Exception e, DataList bookList) {
                    if (Utils.isOnline(getContext())) {
                        if (e == null & bookList.getResult().size() > 0)
                            Log.i("sdsd", bookList + "");
                        bookAdapter.items.addAll(bookList.getResult());
                        bookAdapter.notifyDataSetChanged();
                    }
                }
            });
        }catch (Exception e){}

    }

}
