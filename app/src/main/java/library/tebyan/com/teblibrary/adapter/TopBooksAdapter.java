package library.tebyan.com.teblibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.IonRoundedCornersTransformation;
import library.tebyan.com.teblibrary.model.MetadataList;

/**
 * Created by v.karimi on 7/25/2016.
 */
public class TopBooksAdapter extends RecyclerView.Adapter<TopBooksAdapter.ViewHolder> {

    public ArrayList<MetadataList> items;
    public Context context;
    public TopBooksAdapter(Context context,ArrayList<MetadataList> items){
        this.context=context;
        this.items=items;
    }

    @Override
    public TopBooksAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_book_category,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopBooksAdapter.ViewHolder viewHolder, int i) {
       MetadataList metadataList=items.get(i);
        viewHolder.txtTitle.setText(metadataList.getTitle());
        Ion.with(viewHolder.imgBook).centerInside()
                .transform(new IonRoundedCornersTransformation(10,0))
                .load(metadataList.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle;
        public ImageView imgBook;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle= (TextView) itemView.findViewById(R.id.txt_book_title);
            imgBook= (ImageView) itemView.findViewById(R.id.img_book_thumbnail);
        }
    }
}
