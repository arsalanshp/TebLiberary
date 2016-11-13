package library.tebyan.com.teblibrary.adapter;

/**
 * Created by yahyapour on 10/17/16.
 * http://robusttechhouse.com/tutorial-how-to-add-header-to-recyclerview-in-android/
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.interfaces.SubSubjectiveReviewInterface;
import library.tebyan.com.teblibrary.model.SubSubjects;

public class NavigationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener  {
    public ArrayList<SubSubjects> subSubjectiveArray;
    private SubSubjectiveReviewInterface callback;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private String headerName;

    public NavigationAdapter(ArrayList<SubSubjects> subSubjectiveArray,String headerName, SubSubjectiveReviewInterface callback) {
        this.subSubjectiveArray = subSubjectiveArray;
        this.headerName = headerName;
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_HEADER)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_view, parent, false);
            return  new VHHeader(v);
        }
        else if(viewType == TYPE_ITEM)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_view, parent, false);
            return new VHItem(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof VHHeader)
        {
            ((VHHeader) viewHolder).subHeaderBTN.setText(headerName);
        }
        else if(viewHolder instanceof VHItem)
        {
            ((VHItem) viewHolder).subBTN.setText(subSubjectiveArray.get(i-1).getTitle());
            ((VHItem) viewHolder).subBTN.setTag(subSubjectiveArray.get(i-1).getID());
            ((VHItem) viewHolder).subBTN.setOnClickListener(this);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {return position == 0;}

    @Override
    public int getItemCount() {
        return subSubjectiveArray.size()+1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sub_btn:
                callback.StartSubSubjectiveInterfaces(view.getId());
                break;
        }
    }

    class VHHeader extends RecyclerView.ViewHolder{
        private Button subHeaderBTN;
        public VHHeader(View view) {
            super(view);
            subHeaderBTN = (Button)view.findViewById(R.id.sub_btn);
        }
    }

    class VHItem extends RecyclerView.ViewHolder{

        private Button subBTN;
        public VHItem(View view) {
            super(view);
            subBTN = (Button)view.findViewById(R.id.sub_btn);
        }
    }
}
