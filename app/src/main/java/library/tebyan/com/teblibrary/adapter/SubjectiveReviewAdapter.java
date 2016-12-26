package library.tebyan.com.teblibrary.adapter;

/**
 * Created by root on 10/17/16.
 */
//public class AlphabetAdapter {
//}

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.interfaces.SubSubjectiveReviewInterface;
import library.tebyan.com.teblibrary.model.TerminologyItem;

public class SubjectiveReviewAdapter extends RecyclerView.Adapter<SubjectiveReviewAdapter.ViewHolder> implements View.OnClickListener  {
    public ArrayList<TerminologyItem> terminologyItems;
    private SubSubjectiveReviewInterface callBack;
    private int[] collorArray={0,1,7,8,12,13};


    public SubjectiveReviewAdapter(ArrayList<TerminologyItem> terminologyArray, SubSubjectiveReviewInterface callBack) {
        this.terminologyItems = terminologyArray;
        this.callBack = callBack;
    }

    @Override
    public SubjectiveReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        if (i==0) {
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.terminology_box, viewGroup, false);
        }
        else{
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.terminology_box_orang, viewGroup, false);

        }
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SubjectiveReviewAdapter.ViewHolder viewHolder, int i) {
        TerminologyItem item = terminologyItems.get(i);
        viewHolder.terminology_topic.setText(item.getTopic());
        viewHolder.terminology_topic.setTag(item.getSubjectID());
        viewHolder.terminology_topic.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return terminologyItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView terminology_topic;
        public ViewHolder(View view) {
            super(view);
            terminology_topic = (TextView)view.findViewById(R.id.terminology_topic);
        }
    }

    @Override
    public int getItemViewType(int position) {
        for (int i : collorArray){
            if (position ==i)
                return 1;
        }
        return 0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.terminology_topic:
                int terminoloty_id = (int) view.getTag();
                this.callBack.StartSubSubjectiveInterfaces(terminoloty_id);
                break;


        }
    }




}
