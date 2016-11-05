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
import library.tebyan.com.teblibrary.model.DataFirstPage;

/**
 * Created by Yahyapour on 7/18/2016.
 */
public class SpecialAdapter extends RecyclerView.Adapter<SpecialAdapter.ViewHolder> implements View.OnClickListener {

    public Context context;
    public ArrayList<DataFirstPage> items;

    public SpecialAdapter(Context context, ArrayList<DataFirstPage> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public SpecialAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.special_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SpecialAdapter.ViewHolder holder, final int position) {
        DataFirstPage metadata = items.get(position);
        holder.bookTitle.setText(metadata.getTitle());
//        holder.bookAuthor.setText(metadata.getTitr());
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
                break;

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView bookThumbnail;
        public TextView bookAuthor, bookTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            /*imgOverFlow = (ImageView) itemView.findViewById(R.id.overflow);*/
            bookThumbnail = (ImageView) itemView.findViewById(R.id.book_thumbnail);
//            bookAuthor= (TextView) itemView.findViewById(R.id.book_author);
            bookTitle = (TextView) itemView.findViewById(R.id.book_title);
        }

    }

//    private class MenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
//        public int pos;
//        public MenuItemClickListener(int pos){
//            this.pos=pos;
//        }
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_add_favourite:
//                   addToFavorite(pos);
//                    return true;
//                case R.id.action_reads_book:
//
//                    return true;
//                default:
//            }
//            return false;
//        }
//    }

//    private void addToFavorite(int pos) {
//
//    }
}