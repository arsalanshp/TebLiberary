package library.tebyan.com.teblibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.DescriptionActivity;
import library.tebyan.com.teblibrary.MainActivity;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.model.Metadata;

/**
 * Created by v.karimi on 7/18/2016.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> implements View.OnClickListener {

    public Context context;
    public ArrayList<Metadata> items;

    public BookAdapter(Context context, ArrayList<Metadata> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_book_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BookAdapter.ViewHolder holder, int position) {
        Metadata metadata = items.get(position);
        holder.txtTitle.setText(metadata.getTitle());

        /*holder.txtAuthor.setText(book.getAuthor());*/
        Ion.with(holder.imgThumbnail).load(metadata.getImageUrl());
        holder.imgThumbnail.setTag(position);
        holder.imgOverFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.imgOverFlow);
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_book, popup.getMenu());
        popup.setOnMenuItemClickListener(new MenuItemClickListener());
        popup.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_thumbnail:

                int pos= (int) view.getTag();
                int id = items.get(pos).getMetadataID();

//                Intent bookProfileIntent = new Intent(MainActivity.this,
//                        DescriptionActivity.class);
//
//                bookProfileIntent.putExtra("book_id",id);
//                startActivity(bookProfileIntent);



        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgThumbnail, imgOverFlow;
        public TextView txtAuthor, txtTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            imgOverFlow = (ImageView) itemView.findViewById(R.id.overflow);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            /*txtAuthor= (TextView) itemView.findViewById(R.id.txt_author);*/
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);


        }

    }

    private class MenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:

                    return true;
                case R.id.action_reads_book:

                    return true;
                default:
            }
            return false;
        }
    }
}