package library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;

public class SubCollectionFragment extends Fragment {

    private View view;
    private Context context;
    private RecyclerView recyclerView;
//    private CollectionAdapter collectionAdapter;
    private int visibleItemCount,pastVisiblesItems,pageIndex;
    private int totalItemCount;
    private RecyclerView.LayoutManager layoutManager;
//    private ArrayList<Collection> data=new ArrayList<>();
    private boolean loading=false;
    private int subCollectionID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sub_collection, container, false);
        context = getContext();
        this.subCollectionID = getArguments().getInt("subCollectionID");
        return view;
    }
}
