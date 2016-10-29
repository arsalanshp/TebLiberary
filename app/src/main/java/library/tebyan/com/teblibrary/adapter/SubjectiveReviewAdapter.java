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

import java.util.ArrayList;


import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.model.TerminologyItem;

public class SubjectiveReviewAdapter extends RecyclerView.Adapter<SubjectiveReviewAdapter.ViewHolder> implements View.OnClickListener  {
    public ArrayList<TerminologyItem> terminologyItems;


    public SubjectiveReviewAdapter(ArrayList<TerminologyItem> terminologyArray) {
        this.terminologyItems = terminologyArray;
    }

    @Override
    public SubjectiveReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.terminology_box, viewGroup, false);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.character_btn:
//                Button charBtn = (Button)view.findViewById(R.id.character_btn);
                break;


        }
    }




}
