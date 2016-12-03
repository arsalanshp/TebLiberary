package library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments;

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
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.AlphabetAdapter;
import library.tebyan.com.teblibrary.adapter.BookAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.classes.interfaces.AlphabetInterface;
import library.tebyan.com.teblibrary.classes.interfaces.BookDetailsInterface;
import library.tebyan.com.teblibrary.model.MetadataListRowCount;
import library.tebyan.com.teblibrary.model.Data;

public class AlphabetFragment extends Fragment implements AlphabetInterface {

    List<String> characters = Arrays.asList("ح","چ","ج","ث","ت","پ","ب","ا",
            "ص","ش","س","ز","ر","ذ","د","خ",
            "ک","ق","ف","غ","ع","ظ","ط","ض",
            ".", "ی","ه","و","ن","م","ل","گ"
    );

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_alphabet, container, false);
        context = getContext();
        initUI();
        return view;
    }


    private void initUI() {

        //alphabet part
        alphabetRecyclerView = (RecyclerView) view.findViewById(R.id.alphabet_recycler_view);
        alphabetAdapter = new AlphabetAdapter(characters,this);
        alphabetRecyclerView.setAdapter(alphabetAdapter);
        GridLayoutManager alphabetGridLayoutManager = new GridLayoutManager(context,8);
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
                    }
                }
            });
        }catch (Exception e){}

    }

    @Override
    public void callbackAlphabet(String character) {
        this.characterFilter = character;
        bookAdapter.items.clear();
        initData();
    }
}
