package library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.BookAdapter;
import library.tebyan.com.teblibrary.adapter.NavigationAdapter;
import library.tebyan.com.teblibrary.adapter.SubSubjectiveAdapter;
import library.tebyan.com.teblibrary.adapter.SubjectiveReviewAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.RtlGridLayoutManager;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.classes.interfaces.BookDetailsInterface;
import library.tebyan.com.teblibrary.classes.interfaces.SubSubjectiveReviewInterface;
import library.tebyan.com.teblibrary.model.Data;
import library.tebyan.com.teblibrary.model.MetadataListRowCount;
import library.tebyan.com.teblibrary.model.SubSubjects;
import library.tebyan.com.teblibrary.model.ThesaurusResult;

public class SubSubjectiveFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Context context;
    private Button showSubBTN;
    private int subSubjectID;
    private RelativeLayout subRelativeLayout;
    private RecyclerView bookItemRecyclerView;
    private RecyclerView subSubjectRecyclerView;
    private RecyclerView navigationRecyclerView;
    private int visibleItemCount,pastVisiblesItems,pageIndex=0;
    private int totalItemCount;
    private boolean loading=false;
    private BookAdapter bookAdapter;
    private SubSubjectiveAdapter subSubjectiveAdapter;
    private NavigationAdapter navigationAdapter;
    private LinearLayoutManager linearLayoutManager;
//    private LinearLayoutManager subLinearLayoutManager;
    private RtlGridLayoutManager  gridLayoutManager;
    private RtlGridLayoutManager  navigationGridLayoutManager;
    private TextView rowCount;
    private ArrayList<Data> data=new ArrayList<>();
    private ArrayList<SubSubjects> subSubjectData=new ArrayList<>();
    private int hasSub;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sub_subjective, container, false);
        context = getContext();
        this.subSubjectID = getArguments().getInt("subSubjectID");

        initUI();
       if(subSubjectiveAdapter==null) {
           initData();
       }
        else{
           subSubjectRecyclerView.setAdapter(subSubjectiveAdapter);
           subSubjectiveAdapter.notifyDataSetChanged();

           navigationRecyclerView.setAdapter(navigationAdapter);
           navigationAdapter.notifyDataSetChanged();
       }
        return view;
    }

    private void initUI() {
        showSubBTN = (Button)view.findViewById(R.id.show_sub_btn);
        showSubBTN.setOnClickListener(this);
        rowCount = (TextView)view.findViewById(R.id.row_count_sub_subjective);
        subSubjectRecyclerView = (RecyclerView)view.findViewById(R.id.sub_subjective_recyclerView);
//        subLinearLayoutManager = new LinearLayoutManager(context);
//        subLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        subSubjectRecyclerView.setLayoutManager(subLinearLayoutManager);
        gridLayoutManager = new RtlGridLayoutManager(context,3);
        subSubjectRecyclerView.setLayoutManager((gridLayoutManager));

        navigationRecyclerView = (RecyclerView)view.findViewById(R.id.navigation_recyclerView);
        navigationGridLayoutManager = new RtlGridLayoutManager(context,3);
        navigationRecyclerView.setLayoutManager(navigationGridLayoutManager);


        bookItemRecyclerView = (RecyclerView) view.findViewById(R.id.book_item_recyclerView);
        bookItemRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        bookItemRecyclerView.setLayoutManager(linearLayoutManager);
        bookItemRecyclerView.setHasFixedSize(true);

        bookAdapter = new BookAdapter(context, data,(BookDetailsInterface) getActivity() );
        bookItemRecyclerView.setAdapter(bookAdapter);
    }


    private void initData() {

        try{
            Globals.ion.with(this).load(WebserviceUrl.GETTHESAURUS +"ID="+subSubjectID+ "&PageSize=10&PageIndex=" + pageIndex)
                    .as(ThesaurusResult.class).setCallback(new FutureCallback<ThesaurusResult>() {
                @Override
                public void onCompleted(Exception e, ThesaurusResult thesaurusResult){
                    if (Utils.isOnline(getContext())) {
                        if (e == null & thesaurusResult != null){
//                            subSubjectiveAdapter.notifyItemRangeRemoved(0,subSubjectiveAdapter.getItemCount()-1);
                            hasSub = thesaurusResult.getResult().getSubSubjects().size();
                            if(hasSub==0){
                                showSubBTN.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                showSubBTN.setOnClickListener(null);
                            }

                            subSubjectiveAdapter = new SubSubjectiveAdapter(thesaurusResult.getResult().getSubSubjects() , (SubSubjectiveReviewInterface)getActivity());
                            subSubjectRecyclerView.setAdapter(subSubjectiveAdapter);
                            //subSubjectiveAdapter.subSubjectiveArray.addAll();
                            subSubjectiveAdapter.notifyDataSetChanged();


                            navigationAdapter = new NavigationAdapter(thesaurusResult.getResult().getNavigate().getItems(),thesaurusResult.getResult().getNavigate().getBaseName() , (SubSubjectiveReviewInterface)getActivity());
                            navigationRecyclerView.setAdapter((navigationAdapter));
                            navigationAdapter.notifyDataSetChanged();

                            int count = thesaurusResult.getResult().getMetadataList().getRowCount();
                            if (count >0) {
                                bookAdapter.items.addAll(thesaurusResult.getResult().getMetadataList().getResult());
                                bookAdapter.notifyDataSetChanged();
                            }
                            rowCount.setText("نتایج :" + String.valueOf(count));
                            loading = false;
                        }
                    }
                }
            });
        }
        catch (Exception e){}
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_sub_btn:
                if (subSubjectRecyclerView.getVisibility() == View.GONE) {
                        subSubjectRecyclerView.setVisibility(View.VISIBLE);
                        showSubBTN.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_black_24dp, 0, 0, 0);
                } else {
                        subSubjectRecyclerView.setVisibility(View.GONE);
                        showSubBTN.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_drop_down_black_24dp, 0, 0, 0);
                }
                break;
        }
    }
}
