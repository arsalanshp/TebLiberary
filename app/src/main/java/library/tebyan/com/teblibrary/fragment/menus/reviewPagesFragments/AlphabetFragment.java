package library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.AlphabetAdapter;
import library.tebyan.com.teblibrary.adapter.BookAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.RtlGridLayoutManager;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.classes.interfaces.AlphabetInterface;
import library.tebyan.com.teblibrary.classes.interfaces.BookDetailsInterface;
import library.tebyan.com.teblibrary.model.MetadataListRowCount;
import library.tebyan.com.teblibrary.model.Data;

public class AlphabetFragment extends Fragment implements AlphabetInterface {

    List<String> characters8 = Arrays.asList("ا","ب","پ","ت","ث","ج","چ","ح",
            "خ","د","ذ","ر","ز","س","ش","ص",
            "ض","ط","ظ","ع","غ","ف","ق","ک",
            "گ","ل","م","ن","و","ه","ی"
    );

    List<String> characters5= Arrays.asList("ا","ب","پ","ت","ث",
            "ج","چ","ح","خ","د",
            "ذ","ر","ز","س","ش",
            "ص","ض","ط","ظ","ع",
            "غ","ف","ق","ک","گ",
            "ل","م","ن","و","ه",
            "ی"
            );


//    List<String> characters5= Arrays.asList("ث","ت","پ","ب","ا",
//            "د","خ","ح","چ","ج",
//            "ش","س","ز","ر","ذ",
//            "ع","ظ","ط","ض","ص",
//            "گ","ک","ق","ف","غ",
//            "ه","و","ن","م","ل",
//            "ی"
//    );


    List<String> characters;
    View view;
    RecyclerView alphabetRecyclerView;
    AlphabetAdapter alphabetAdapter;
    Context context;
    String characterFilter;
    TextView rowCount;
    public ArrayList<Data> items;

    RecyclerView alphabetDataRecyclerView;
    BookAdapter bookAdapter;
    public int visibleItemCount,pastVisiblesItems,pageIndex;
    private int totalItemCount;
    boolean loading=false;
    LinearLayoutManager alphabetlinearLayoutManager;
    public ArrayList<Data> data=new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isBack= false,bigWidth ;
    private int colCount;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_alphabet, container, false);
        context = getContext();
        initUI();
        if(!isBack) {
            initData();
            isBack = true;
        }
        else{
            bookAdapter.notifyDataSetChanged();
        }
        return view;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;

        switch (colCount){
            case 8:
                characters = characters8;
                break;
            case 5:
                characters = characters5;
                break;
        }
    }


    private void initUI() {

        //alphabet part
        alphabetRecyclerView = (RecyclerView) view.findViewById(R.id.alphabet_recycler_view);
        alphabetAdapter = new AlphabetAdapter(characters,this);
        alphabetRecyclerView.setAdapter(alphabetAdapter);
        RtlGridLayoutManager alphabetGridLayoutManager = new RtlGridLayoutManager(context,colCount);
        alphabetRecyclerView.setLayoutManager(alphabetGridLayoutManager);
        //alphabet part
        alphabetDataRecyclerView = (RecyclerView) view.findViewById(R.id.alphabet_data_recyclerView);
        alphabetDataRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    visibleItemCount = alphabetlinearLayoutManager.getChildCount();
                    totalItemCount = alphabetlinearLayoutManager.getItemCount();
                    pastVisiblesItems = alphabetlinearLayoutManager.findFirstVisibleItemPosition();
                    if (!loading && dy>0) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = true;
                            pageIndex++;
                            initData();
                        }
                    }
                }
        });
        alphabetlinearLayoutManager = new LinearLayoutManager(context);
        alphabetDataRecyclerView.setLayoutManager(alphabetlinearLayoutManager);
        alphabetDataRecyclerView.setHasFixedSize(true);

        bookAdapter = new BookAdapter(context, data,(BookDetailsInterface) getActivity(),false);
        alphabetDataRecyclerView.setAdapter(bookAdapter);
        rowCount = (TextView)view.findViewById(R.id.row_count);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                initData();
            }
        });
    }


    private void initData() {
        try {

            if (this.characterFilter == null) {
                this.characterFilter = "ا";
            }
//            this.characterFilter = URLEncoder.encode(this.characterFilter, "utf-8");
            Globals.ion.with(this).load(WebserviceUrl.BROWSE_ALPHABET + "alphabet=" + this.characterFilter + "&PageSize=10&PageIndex=" + pageIndex)
                    .as(MetadataListRowCount.class).setCallback(new FutureCallback<MetadataListRowCount>() {
                @Override
                public void onCompleted(Exception e, MetadataListRowCount bookList) {
                    if (Utils.isOnline(getContext())) {
                        if (e == null&bookList !=null & bookList.getResult().size() > 0)
                            Log.i("sdsd", bookList + "");
                        bookAdapter.items.addAll(bookList.getResult());
                        bookAdapter.notifyDataSetChanged();
                        rowCount.setText("نتایج :"+String.valueOf(bookList.getRowCount()));
                        loading=false;
                    }else {
                        Toast.makeText(context, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            swipeRefreshLayout.setRefreshing(false);
        }catch (Exception e){}

    }

    @Override
    public void callbackAlphabet(String character) {
        this.characterFilter = character;
        bookAdapter.items.clear();
        initData();
    }
}
