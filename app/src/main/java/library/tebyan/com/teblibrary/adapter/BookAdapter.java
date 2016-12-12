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
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> implements View.OnClickListener {

    public Context context;
    public ArrayList<Data> items;
    public BookDetailsInterface callBack;
    private boolean ownerFlag;

    public BookAdapter(Context context, ArrayList<Data> items,BookDetailsInterface callBack ,boolean ownerFlag) {
        this.items = items;
        this.context = context;
        this.callBack = callBack;
        this.ownerFlag = ownerFlag;
    }

    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BookAdapter.ViewHolder holder,final int position) {
        Data metadata = items.get(position);
        holder.bookTitle.setText(metadata.getTitle());
        holder.bookAuthor.setText(metadata.getAuthor());
        Globals.ion.with(holder.bookThumbnail).load(metadata.getImage());
        holder.itemView.setTag(metadata.getID());
        holder.itemView.setOnClickListener(this);
}


    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public void onClick(View v) {
                int book_id = (int) v.getTag();
                if(this.ownerFlag) {
                    this.callBack.StartOwnerBookDetailsInterfaces(book_id);
                }
                else{
                    this.callBack.StartBookDetailsInterfaces(book_id);
                }

        }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView bookThumbnail;
        public TextView bookAuthor, bookTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            bookThumbnail = (ImageView) itemView.findViewById(R.id.book_thumbnail);
            bookAuthor= (TextView) itemView.findViewById(R.id.book_author);
            bookTitle = (TextView) itemView.findViewById(R.id.book_title);


        }

    }
}