package library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.BookAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.ImageConverter;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.classes.interfaces.BookDetailsInterface;
import library.tebyan.com.teblibrary.model.Data;
import library.tebyan.com.teblibrary.model.MetadataList;
import library.tebyan.com.teblibrary.model.MetadataListRowCount;

public class SubCollectionFragment extends Fragment {

    private View view;
    private Context context;
    private RecyclerView subCollectionRecyclerView;
    private BookAdapter bookAdapter;
    private ArrayList<Data> data=new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private int visibleItemCount,pastVisiblesItems,pageIndex;
    private int totalItemCount;
    private boolean loading=false;
    private int collectionID;
    private String collectionTitle;
    private String collectionThumbnail;
    private TextView collectionNameTxtView;
    private ImageView collectionThumbnailImgView;


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
        this.collectionID = getArguments().getInt("collectionID");
        collectionTitle = getArguments().getString("collectionTitle");
        collectionThumbnail= getArguments().getString("collectionThumbnail");
        initUI();
        initData();
        return view;
    }

    private void initUI() {

        collectionNameTxtView = (TextView)view.findViewById(R.id.collection_name);
        collectionThumbnailImgView = (ImageView)view.findViewById(R.id.collection_thumbnail);

        //alphabet part
        subCollectionRecyclerView = (RecyclerView) view.findViewById(R.id.sub_collection_recyclerView);
        bookAdapter = new BookAdapter(context, data,(BookDetailsInterface) getActivity() );
        subCollectionRecyclerView.setAdapter(bookAdapter);
        linearLayoutManager = new LinearLayoutManager(context);
        subCollectionRecyclerView.setLayoutManager(linearLayoutManager);
        subCollectionRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        subCollectionRecyclerView.setHasFixedSize(true);
    }


    private void initData() {
        try {
            collectionNameTxtView.setText(collectionTitle);

            Globals.ion.with(context).load(collectionThumbnail).withBitmap().asBitmap()
                    .setCallback(new FutureCallback<Bitmap>() {
                        @Override
                        public void onCompleted(Exception e, Bitmap result) {
                            collectionThumbnailImgView.setImageBitmap(ImageConverter.getRoundedCornerBitmap(result));
                            collectionThumbnailImgView.setLayoutParams(
                                    new LinearLayout.LayoutParams(
                                            200,
                                            78));
                        }
                    });

            Globals.ion.with(this).load(WebserviceUrl.GET_METADATA_LIST + "ID="+collectionID + "&PageSize=10&PageIndex=" + pageIndex)
                    .as(MetadataList.class).setCallback(new FutureCallback<MetadataList>() {
                @Override
                public void onCompleted(Exception e, MetadataList bookList) {
                    if (Utils.isOnline(getContext())) {
                        if (e == null& bookList!=null & bookList.getResult().size() > 0) {
                            bookAdapter.items.addAll(bookList.getResult());
                            bookAdapter.notifyDataSetChanged();
//                            rowCount.setText("نتایج :" + String.valueOf(bookList.getRowCount()));
                            loading = false;
                        }
                    }
                }
            });
        }catch (Exception e){
            System.out.print(e.toString());
        }

    }
}
