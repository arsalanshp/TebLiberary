package library.tebyan.com.teblibrary.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.IonRoundedCornersTransformation;
import library.tebyan.com.teblibrary.model.Data;

/**
 * Created by v.karimi on 8/10/2016.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    public ArrayList<Data> items;
    public Activity activity;

    public SearchAdapter(Activity activity,ArrayList<Data> items){
        this.activity=activity;
        this.items=items;
    }
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_row, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder viewHolder, int i) {

        Data data=items.get(i);
        viewHolder.txtTitle.setText(data.getTitle());
        viewHolder.txtAuthor.setText(data.getAuthor());
        viewHolder.txtSubject.setText(initArray(data.getSubjects()));
        viewHolder.txtDetails.setText(data.getPublisher()+"-"+initArray(data.getLanguage())+data.getLinkTitle());
        Globals.ion.with(viewHolder.imgThumbnail).transform(new IonRoundedCornersTransformation(10,0))
                .load(data.getImage());

    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    public ArrayList<Data> getItems() {
        return items;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgThumbnail;
        public TextView txtTitle,txtAuthor,txtSubject,txtDetails;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle= (TextView) itemView.findViewById(R.id.txt_search_title);
            txtAuthor= (TextView) itemView.findViewById(R.id.txt_search_author);
            txtSubject= (TextView) itemView.findViewById(R.id.txt_subject);
            txtDetails= (TextView) itemView.findViewById(R.id.txt_search_details);
            imgThumbnail= (ImageView) itemView.findViewById(R.id.img_search_book_thumbnail);
        }
    }
    public String initArray(ArrayList<String> arrayStr){
        StringBuilder builder=new StringBuilder();
        for (int i=0;i<arrayStr.size();i++){
            builder.append(arrayStr.get(i));
            builder.append("-");
        }
        return builder.toString();
    }
}
