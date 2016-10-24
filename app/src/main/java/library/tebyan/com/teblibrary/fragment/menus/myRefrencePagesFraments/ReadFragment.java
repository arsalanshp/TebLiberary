package library.tebyan.com.teblibrary.fragment.menus.myRefrencePagesFraments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.BookAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.Data;
import library.tebyan.com.teblibrary.model.DataList;

public class ReadFragment extends Fragment {

    View view;
    Context context;
    public ArrayList<Data> items;
    private String webServiceURL;

    RecyclerView recyclerView;
    BookAdapter bookAdapter;
    public int visibleItemCount,pastVisiblesItems,pageIndex;
    private int totalItemCount;
    boolean loading;
    LinearLayoutManager linearLayoutManager;
    public ArrayList<Data> data=new ArrayList<>();
    private String fragmentTag;
    private ImageButton emptyImageButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentTag = getArguments().getString("fragmentTag");
        initUrl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_read, container, false);
        context = getContext();
        initUI();
        initData();
        return view;
    }

    private void initUrl(){
        switch (fragmentTag) {
            case "ReadedFragment":
                webServiceURL = WebserviceUrl.READED + "&PageSize=3&PageIndex=";
                break;
            case "ReadingFragment":
                webServiceURL = WebserviceUrl.READING + "&PageSize=10&PageIndex=";
                break;
            case "WillReadFragment":
                webServiceURL = WebserviceUrl.FOR_READ + "&PageSize=10&PageIndex=";
                break;
            case "NewFragment":
                webServiceURL = WebserviceUrl.FOR_READ + "&PageSize=10&PageIndex=";
                break;
        }

    }

    private void initUI() {

        emptyImageButton = (ImageButton) view.findViewById(R.id.empty_image_button);
        recyclerView = (RecyclerView) view.findViewById(R.id.will_read_recycler_view);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    pageIndex++;
                    initData();
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
            Globals.ion.with(this).load(webServiceURL+ pageIndex)
                    .setHeader("userToken", Globals.userToken)
                    .as(DataList.class).setCallback(new FutureCallback<DataList>() {
                @Override
                public void onCompleted(Exception e, DataList bookList) {
                    if (Utils.isOnline(getContext())) {
                        if (e == null & bookList.getResult().size() > 0) {
                            Log.i("sdsd", bookList + "");
                            bookAdapter.items.addAll(bookList.getResult());
                            bookAdapter.notifyDataSetChanged();
                            emptyImageButton.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                        else if(bookList.getResult().size()==0){

                            emptyImageButton.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }catch (Exception e){}

    }

}
