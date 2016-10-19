package library.tebyan.com.teblibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.DescriptionActivity;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.IonRoundedCornersTransformation;
import library.tebyan.com.teblibrary.model.Metadata;

/**
 * Created by v.karimi on 7/25/2016.
 */
public class TopBooksAdapter extends RecyclerView.Adapter<TopBooksAdapter.ViewHolder> implements View.OnClickListener {

    public ArrayList<Metadata> items;
    public Context context;

    public TopBooksAdapter(Context context, ArrayList<Metadata> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public TopBooksAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_book_category,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopBooksAdapter.ViewHolder viewHolder, int i) {
       Metadata metadata =items.get(i);
        viewHolder.txtTitle.setText(metadata.getTitle());
        viewHolder.txtBookAuthor.setText(metadata.getAuthor());
        viewHolder.imgBook.setOnClickListener(this);
        viewHolder.imgBook.setTag(i);
        Globals.ion.with(viewHolder.imgBook).centerCrop()
                .transform(new IonRoundedCornersTransformation(10,0))
                .load(metadata.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return (items!=null)?items.size():0;
    }

    @Override
    public void onClick(View view) {

        int index = (int) view.getTag();
        int id = items.get(index).getMetadataID();
        switch (view.getId()) {
            case (R.id.img_book_thumbnail):
                Intent bookProfileIntent = new Intent(context, DescriptionActivity.class);
                bookProfileIntent.putExtra("book_id", id);
                context.startActivity(bookProfileIntent);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtBookAuthor;
        public ImageView imgBook;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_book_title);
            txtBookAuthor = (TextView) itemView.findViewById(R.id.txt_book_author);
            imgBook = (ImageView) itemView.findViewById(R.id.img_book_thumbnail);
        }
    }
}
