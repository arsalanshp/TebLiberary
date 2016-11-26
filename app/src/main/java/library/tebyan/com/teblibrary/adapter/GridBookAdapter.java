package library.tebyan.com.teblibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.interfaces.BookDetailsInterface;
import library.tebyan.com.teblibrary.model.Data;

/**
 * Created by Yahyapour on 7/18/2016.
 */
public class GridBookAdapter extends RecyclerView.Adapter<GridBookAdapter.ViewHolder> implements View.OnClickListener {

    public Context context;
    public ArrayList<Data> items;
    public BookDetailsInterface callBack;

    public GridBookAdapter(Context context, ArrayList<Data> items, BookDetailsInterface callBack) {
        this.items = items;
        this.context = context;
        this.callBack = callBack;
    }

    @Override
    public GridBookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_book_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GridBookAdapter.ViewHolder holder, final int position) {
        Data metadata = items.get(position);
        String bookTitle = metadata.getTitle();
        if (bookTitle.length()>30)
        {
            bookTitle = bookTitle.substring(0,27)+"...";
        }
        holder.bookTitle.setText(bookTitle);
//        holder.bookAuthor.setText(metadata.getAuthor());
        Globals.ion.with(holder.bookThumbnail).load(metadata.getImage());
        holder.bookThumbnail.setOnClickListener(this);
        holder.bookThumbnail.setTag(metadata.getID());
}


    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.book_thumbnail:
                int book_id = (int) v.getTag();
                this.callBack.StartBookDetailsInterfaces(book_id);
                break;

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView bookThumbnail;
        public TextView bookTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            /*imgOverFlow = (ImageView) itemView.findViewById(R.id.overflow);*/
            bookThumbnail = (ImageView) itemView.findViewById(R.id.book_thumbnail);
//            bookAuthor= (TextView) itemView.findViewById(R.id.book_author);
            bookTitle = (TextView) itemView.findViewById(R.id.book_title);


        }

    }
}