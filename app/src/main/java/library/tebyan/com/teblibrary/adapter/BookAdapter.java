package library.tebyan.com.teblibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.model.Book;

/**
 * Created by v.karimi on 7/18/2016.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    Context context;
    ArrayList<Book> items;
    public BookAdapter(Context context,ArrayList<Book> items){
        this.items=items;
        this.context=context;
    }
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_book_row,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookAdapter.ViewHolder holder, int position) {
        Book book = items.get(position);
        holder.txtTitle.setText(book.getTitle());
        holder.txtAuthor.setText(book.getAuthor());
        /*Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);*/
        holder.imgOverFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*showPopupMenu(holder.overflow);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgThumbnail,imgOverFlow;
        public TextView txtAuthor,txtTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            imgOverFlow= (ImageView) itemView.findViewById(R.id.overflow);
            imgThumbnail= (ImageView) itemView.findViewById(R.id.img_thumbnail);
            txtAuthor= (TextView) itemView.findViewById(R.id.txt_author);
            txtTitle= (TextView) itemView.findViewById(R.id.txt_title);

        }
    }
    class MenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            return false;
        }
    }
}
