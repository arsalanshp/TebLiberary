package library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments;

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

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.CollectionAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.classes.interfaces.CollectionsInterface;
import library.tebyan.com.teblibrary.model.Collection;
import library.tebyan.com.teblibrary.model.CollectionsResult;

public class CollectionFragment extends Fragment {

    private View view;
    private Context context;
    private RecyclerView recyclerView;
    private CollectionAdapter collectionAdapter;
    private int visibleItemCount,pastVisiblesItems,pageIndex;
    private int totalItemCount;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Collection> data=new ArrayList<>();
    private boolean loading=false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_collection, container, false);
        context = getContext();
        initUI();
        initData();
        return view;
    }

    private void initUI() {
        recyclerView = (RecyclerView) view.findViewById(R.id.collections_recyler_view);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = 10;//layoutManager.;//.findFirstVisibleItemPosition();
                if (!loading && dy>0) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        pageIndex++;
                        initData();
                    }
                }
            }
        });

        layoutManager = new LinearLayoutManager(context);
        collectionAdapter = new CollectionAdapter(context , data,(CollectionsInterface)getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(collectionAdapter);
    }


    private void initData() {
        try {
            Globals.ion.with(this).load(WebserviceUrl.GET_COLLECTIONS + "&PageIndex=" + pageIndex)
                    .as(CollectionsResult.class).setCallback(new FutureCallback<CollectionsResult>() {
                @Override
                public void onCompleted(Exception e, CollectionsResult collectionList) {
                    if (Utils.isOnline(getContext())) {
                        if (e == null & collectionList != null & collectionList.getResult().size() > 0) {
                            collectionAdapter.items.addAll(collectionList.getResult());
                            collectionAdapter.notifyDataSetChanged();
                            loading=false;
                        }
                    }else {
                        Toast.makeText(context, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){}

    }

}
