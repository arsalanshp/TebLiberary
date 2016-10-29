package library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.SubjectiveReviewAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.RtlGridLayoutManager;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.TerminologyItem;
import library.tebyan.com.teblibrary.model.TerminologyResult;

public class SubjectFragment extends Fragment {


    private View view;
    private Context context;
    private RecyclerView persianRecyclerView;
    private RecyclerView dataRecyclerView;
    private ArrayList<TerminologyItem> persian_cultural_terminology_list;
    private ArrayList<TerminologyItem> data_center_terminolory_list;
    private SubjectiveReviewAdapter persianAdapter;
    private SubjectiveReviewAdapter dataAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_subject, container, false);
        context = getContext();
        initUI();
        initData();
        return view;

    }

    private void initUI() {

        persian_cultural_terminology_list = new ArrayList<>();
        data_center_terminolory_list = new ArrayList<>();
        persianRecyclerView = (RecyclerView)view.findViewById(R.id.persian_cultural_terminology_recycle_view);
        persianAdapter = new SubjectiveReviewAdapter(persian_cultural_terminology_list);
        persianRecyclerView.setAdapter(persianAdapter);
        RtlGridLayoutManager persianGridLayoutManager = new RtlGridLayoutManager(context,3);
        persianRecyclerView.setLayoutManager(persianGridLayoutManager);


        dataRecyclerView = (RecyclerView)view.findViewById(R.id.data_center_terminolory_recycle_view);
        dataAdapter = new SubjectiveReviewAdapter(data_center_terminolory_list);
        dataRecyclerView.setAdapter(dataAdapter);
        RtlGridLayoutManager dataGridLayoutManager = new RtlGridLayoutManager(context,3);
        dataRecyclerView.setLayoutManager(dataGridLayoutManager);
        //alphabet part

//        alphabetDataRecyclerView.setHasFixedSize(true);
    }


    private void initData() {
        try {
            Globals.ion.with(this).load(WebserviceUrl.THESAURUSROOT)
                    .as(TerminologyResult.class).setCallback(new FutureCallback<TerminologyResult>() {
                @Override
                public void onCompleted(Exception e, TerminologyResult terminologyResult) {
                    if (Utils.isOnline(getContext())) {
                        persian_cultural_terminology_list = ((terminologyResult.getResult()).get(0)).getItems();
                        persianAdapter.terminologyItems.addAll(persian_cultural_terminology_list);
                        persianAdapter.notifyDataSetChanged();

                        data_center_terminolory_list = ((terminologyResult.getResult()).get(1)).getItems();
                        dataAdapter.terminologyItems.addAll(data_center_terminolory_list);
                        dataAdapter.notifyDataSetChanged();
//                        rowCount.setText("نتایج :"+String.valueOf(bookList.getRowCount()));
                    }
                }
            });
        }catch (Exception e){}
    }
}
