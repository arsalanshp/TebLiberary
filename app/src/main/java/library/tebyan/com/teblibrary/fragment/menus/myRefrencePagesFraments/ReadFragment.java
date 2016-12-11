package library.tebyan.com.teblibrary.fragment.menus.myRefrencePagesFraments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.BookAdapter;
import library.tebyan.com.teblibrary.adapter.GridBookAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.classes.interfaces.BookDetailsInterface;
import library.tebyan.com.teblibrary.model.Data;
import library.tebyan.com.teblibrary.model.DataList;

public class ReadFragment extends Fragment {

    private View view;
    private Context context;
    private String webServiceURL;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter bookAdapter;
    private int visibleItemCount,pastVisiblesItems,pageIndex;
    private int totalItemCount , rowCount;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Data> data=new ArrayList<>();
    private String fragmentTag;
    private ImageButton emptyImageButton;
    private boolean loading=false;
    private boolean listState = true;  // list state , Grid : False , List : Ture


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentTag = getArguments().getString("fragmentTag");
        listState = getArguments().getBoolean("listState");
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
                webServiceURL = WebserviceUrl.GETMYMETADATA + "&PageSize=10&PageIndex=";
                break;
        }
    }

    private void initUI() {

        emptyImageButton = (ImageButton) view.findViewById(R.id.empty_image_button);
        recyclerView = (RecyclerView) view.findViewById(R.id.will_read_recycler_view);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = 10;//layoutManager.;//.findFirstVisibleItemPosition();
                if (!loading && dy>0) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount && rowCount%10 ==0) {
                        loading = true;
                        pageIndex++;
                        initData();
                    }
                }
            }
        });

        if (listState){
            layoutManager = new LinearLayoutManager(context);
            bookAdapter = new BookAdapter(context, data,(BookDetailsInterface) getActivity() , true);

        }


        else{
            layoutManager = new GridLayoutManager(context,4);
            bookAdapter = new GridBookAdapter(context, data,(BookDetailsInterface) getActivity(),true);
        }

//        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
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
                        rowCount = bookList.getResult().size();
                        if (e == null & rowCount > 0) {
                            Log.i("sdsd", bookList + "");
                            if(listState){
                                ((BookAdapter)bookAdapter).items.addAll(bookList.getResult());
                            }
                            else{
                                ((GridBookAdapter)bookAdapter).items.addAll(bookList.getResult());
                            }

                            bookAdapter.notifyDataSetChanged();
                            emptyImageButton.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                        else if(bookList.getResult().size()==0 && pageIndex==0){
                            emptyImageButton.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                        loading=false;
                    }
                    else {
                        Toast.makeText(context, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){}

    }

}
