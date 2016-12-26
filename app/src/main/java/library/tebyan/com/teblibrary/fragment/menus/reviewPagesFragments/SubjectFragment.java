package library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.SubjectiveReviewAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.RtlGridLayoutManager;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.classes.interfaces.SubSubjectiveReviewInterface;
import library.tebyan.com.teblibrary.model.TerminologyItem;
import library.tebyan.com.teblibrary.model.TerminologyResult;

public class SubjectFragment extends Fragment {


    private View view;
    private Context context;
    private RecyclerView persianRecyclerView;
    private RecyclerView dataRecyclerView;
    private SubjectiveReviewAdapter persianAdapter;
    private SubjectiveReviewAdapter dataAdapter;
    private boolean bigWidth;
    private int colCount;



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
        if(persianAdapter==null) {
            initData();
        }
        else{
            persianRecyclerView.setAdapter(persianAdapter);
            persianAdapter.notifyDataSetChanged();
            dataRecyclerView.setAdapter(dataAdapter);
            dataAdapter.notifyDataSetChanged();
        }

        return view;

    }

    public int getColCount() {
        return colCount;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;
    }

    private void initUI() {
        persianRecyclerView = (RecyclerView)view.findViewById(R.id.persian_cultural_terminology_recycle_view);
        RtlGridLayoutManager persianGridLayoutManager = new RtlGridLayoutManager(context,colCount);
        persianRecyclerView.setLayoutManager(persianGridLayoutManager);

        dataRecyclerView = (RecyclerView)view.findViewById(R.id.data_center_terminolory_recycle_view);
        RtlGridLayoutManager dataGridLayoutManager = new RtlGridLayoutManager(context,colCount);
        dataRecyclerView.setLayoutManager(dataGridLayoutManager);
    }

    private void initData() {
        try {
            Globals.ion.with(this).load(WebserviceUrl.THESAURUSROOT)
                    .as(TerminologyResult.class).setCallback(new FutureCallback<TerminologyResult>() {
                @Override
                public void onCompleted(Exception e, TerminologyResult terminologyResult) {
                    if (Utils.isOnline(getContext())) {
                        persianAdapter = new SubjectiveReviewAdapter(((terminologyResult.getResult()).get(0)).getItems(),(SubSubjectiveReviewInterface)getActivity());
                        persianRecyclerView.setAdapter(persianAdapter);
                        persianAdapter.notifyDataSetChanged();

                        dataAdapter = new SubjectiveReviewAdapter((((terminologyResult.getResult()).get(1)).getItems()),(SubSubjectiveReviewInterface)getActivity());
                        dataRecyclerView.setAdapter(dataAdapter);
                        dataAdapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(context, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){}
    }
}
