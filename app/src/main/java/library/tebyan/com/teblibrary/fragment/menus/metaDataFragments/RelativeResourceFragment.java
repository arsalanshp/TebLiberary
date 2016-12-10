package library.tebyan.com.teblibrary.fragment.menus.metaDataFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.koushikdutta.async.future.FutureCallback;

import java.net.URLEncoder;
import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.BookAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.classes.interfaces.BookDetailsInterface;
import library.tebyan.com.teblibrary.model.Data;
import library.tebyan.com.teblibrary.model.DataList;

//import com.a7learn.mahdieh.myfragment.R;
//import com.a7learn.mahdieh.myfragment.adapter.AdapterRelativeCardView;


/**
 * Created by m.mohamadzadeh on 10/26/2016.
 */

public class RelativeResourceFragment extends Fragment {
    RecyclerView recyclerView ;
    View view;
    Context context;
    BookAdapter bookAdapter;
    public int visibleItemCount,pastVisiblesItems,pageIndex;
    private int totalItemCount;
    boolean loading=false;
    LinearLayoutManager linearLayoutManager;
    public ArrayList<Data> data=new ArrayList<>();
    private String authorName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authorName= getArguments().getString("authorName");
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.relative_resource_fragment, container, false);
        context = getContext();
        initUI();
        return view;
    }


    private void initUI() {
        recyclerView = (RecyclerView) view.findViewById(R.id.realtive_recyclerView);
        bookAdapter = new BookAdapter(context, data,(BookDetailsInterface) getActivity(), false);
        recyclerView.setAdapter(bookAdapter);
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
                        initData();
                    }
                }
            }
        });
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

    }

    private void initData() {
        try {
            Globals.ion.with(this).load(WebserviceUrl.SEARCH)
                    .setBodyParameter("Field1","Author")
                    .setBodyParameter("Value1", URLEncoder.encode(authorName, "utf-8"))
                    .setBodyParameter("pageIndex",String.valueOf(pageIndex))
                    .setBodyParameter("pageSize","10")
                    .as(DataList.class).setCallback(new FutureCallback<DataList>() {
                @Override
                public void onCompleted(Exception e, DataList bookList) {
                    if (Utils.isOnline(getContext())) {
                        if (e == null & bookList != null& bookList.getResult().size() > 0) {
                            bookAdapter.items.addAll(bookList.getResult());
                            bookAdapter.notifyDataSetChanged();
//                            emptyImageButton.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                        else if(bookList.getResult().size()==0){

//                            emptyImageButton.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(context, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                    loading=false;
                }
            });
        }catch (Exception e){}



    }


//
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//      view = inflater.inflate(R.layout.relative_resource_fragment,container,false);
//
//        // in 4 line
////        recyclerView = (RecyclerView) view.findViewById(R.id.realtive_recyclerView);
////        AdapterRelativeCardView adapter = new AdapterRelativeCardView(this.getContext());
////        recyclerView.setAdapter(adapter);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//
//        return view;
//    }
}






