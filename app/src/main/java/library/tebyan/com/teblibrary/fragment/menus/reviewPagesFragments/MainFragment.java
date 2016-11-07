package library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.AlphabetBookList;
import library.tebyan.com.teblibrary.model.Data;

public class MainFragment<A,M> extends Fragment {

    protected View view;
    protected RecyclerView recyclerView;
    protected Context context;
    protected RecyclerView.Adapter adapter;
    protected int visibleItemCount,pastVisiblesItems,pageIndex;
    protected int totalItemCount;
    protected boolean loading = false;
    protected LinearLayoutManager linearLayoutManager;
    protected ArrayList<Data> data=new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initData("ا",pageIndex);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_alphabet, container, false);
        context = getContext();
        initUI();
        return view;
    }


    protected void initUI() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                if (!loading && dy>0) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        pageIndex++;
//                        initData();
                    }
                }
            }
        });
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        setAdapter();

    }

    protected void setAdapter(){}


//    protected void initData(String alphabet, int pageIndex) {
//
//        switch (alphabet){
//            case "1":
//                try {
//                    alphabet = URLEncoder.encode(alphabet, "utf-8");
//                    Globals.ion.with(this).load(WebserviceUrl.BROWSE_ALPHABET+"alphabet="+alphabet+"&PageSize=10&PageIndex="+pageIndex)
//                            .setBodyParameter("PageSize", "10")
//                            .setBodyParameter("PageIndex",String.valueOf(pageIndex))
//                            .as(AlphabetBookList.class).setCallback(new FutureCallback<AlphabetBookList>() {
//                        @Override
//                        public void onCompleted(Exception e, AlphabetBookList bookList) {
//                            if (Utils.isOnline(getContext())) {
//                                if (e == null & bookList.getData().size() > 0)
//                                    Log.i("sdsd", bookList + "");
//
//                                bookAdapter.items.addAll(bookList.getData());
//                                bookAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    });
//                }catch (UnsupportedEncodingException ex){}
//
//                return;
//
//            case "2":
//                return;
//
//        }
//        try {
//            alphabet = URLEncoder.encode(alphabet, "utf-8");
//            Globals.ion.with(this).load(WebserviceUrl.BROWSE_ALPHABET+"alphabet="+alphabet+"&PageSize=10&PageIndex="+pageIndex)
//                    .setBodyParameter("PageSize", "10")
//                    .setBodyParameter("PageIndex",String.valueOf(pageIndex))
//                    .as(M.class).setCallback(new FutureCallback<M>() {
//                @Override
//                public void onCompleted(Exception e, M dataList) {
//                    if (Utils.isOnline(getContext())) {
//                        if (e == null) {
//                            refreshAdapter(dataList);
//                        }
//
////                        if (e == null & bookList.getData().size() > 0)
////                            Log.i("sdsd", bookList + "");
//
////                        refreshAdapter(bookList.getData());
//                    }
//                }
//            });
//        }catch (UnsupportedEncodingException ex){}

//    }

    protected void refreshAdapter(M dataList){}
}
