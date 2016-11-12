package library.tebyan.com.teblibrary.adapter;

/**
 * Created by root on 10/17/16.
 */
//public class AlphabetAdapter {
//}

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.interfaces.AlphabetInterface;
import library.tebyan.com.teblibrary.classes.interfaces.SubSubjectiveReviewInterface;
import library.tebyan.com.teblibrary.model.SubSubjects;

public class SubSubjectiveAdapter extends RecyclerView.Adapter<SubSubjectiveAdapter.ViewHolder> implements View.OnClickListener  {
//    private ArrayList<String> alphabetArray;
    public ArrayList<SubSubjects> subSubjectiveArray;
    private SubSubjectiveReviewInterface callback;
    Button sub_btn;

    public SubSubjectiveAdapter(ArrayList<SubSubjects> subSubjectiveArray, SubSubjectiveReviewInterface callback) {
        this.subSubjectiveArray = subSubjectiveArray;
        this.callback = callback;
    }

    @Override
    public SubSubjectiveAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sub_subjective_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubSubjectiveAdapter.ViewHolder viewHolder, int i) {

        viewHolder.char_img.setText(subSubjectiveArray.get(i).getTitle());
        viewHolder.char_img.setTag(subSubjectiveArray.get(i).getID());
        viewHolder.char_img.setOnClickListener(this);
//        Picasso.with(context).load(alphabetArray.get(i).getAndroid_image_url()).resize(240, 120).into(viewHolder.img_android);
    }

    @Override
    public int getItemCount() {
        return subSubjectiveArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private Button char_img;
        public ViewHolder(View view) {
            super(view);
            char_img = (Button)view.findViewById(R.id.character_btn);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.character_btn:
                callback.StartSubSubjectiveInterfaces(view.getId());
//                Button charBtn = (Button)view.findViewById(R.id.character_btn);
                break;
        }
    }




}
