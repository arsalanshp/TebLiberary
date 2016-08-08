package library.tebyan.com.teblibrary.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.MainActivity;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.IonRoundedCornersTransformation;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.model.Category;

/**
 * Created by v.karimi on 7/24/2016.
 */
public class BooksCategoryAdapter extends RecyclerView.Adapter<BooksCategoryAdapter.ViewHolder> implements View.OnClickListener {

    public ArrayList<Category> items;
    public Context context;
    public GridLayoutManager manager;
    public TopBooksAdapter booksAdapter;
    public MainActivity.InitFragment handler;
    public BooksCategoryAdapter(Context context, ArrayList<Category> items) {
        this.context = context;
        this.items = items;
    }

    public void setHandler(MainActivity.InitFragment handler){
        this.handler=handler;
    }
    @Override
    public BooksCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item_row, viewGroup, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(BooksCategoryAdapter.ViewHolder viewHolder, int i) {
        Category category = items.get(i);
        viewHolder.txtTitle.setText(category.getTitle());
        viewHolder.txtSeeMore.setTag(i);
        viewHolder.txtSeeMore.setOnClickListener(this);
        Ion.with(viewHolder.imgThumbnail).transform(new IonRoundedCornersTransformation(5,0)).load(category.getImageUrl());
        booksAdapter = new TopBooksAdapter(context, category.getMetadata());
        booksAdapter.notifyDataSetChanged();
        viewHolder.recycler.setAdapter(booksAdapter);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(View view) {
        int pos= (int) view.getTag();
        handler.initFragment(items.get(pos).ID,0,"bookList");

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle, txtSeeMore;
        public ImageView imgThumbnail;
        public RecyclerView recycler;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title_category);
            txtSeeMore = (TextView) itemView.findViewById(R.id.txt_see_more);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.img_category_thumbnail);
            recycler = (RecyclerView) itemView.findViewById(R.id.recycler_top_category);
            initRecycler(recycler);
        }


        private void initRecycler(RecyclerView recyclerView) {
            if(Utils.isTablet(context)){
                manager = new GridLayoutManager(context, 4);
            }else{
                manager = new GridLayoutManager(context, 3);
            }

            recyclerView.setLayoutManager(manager);
            recyclerView.setHasFixedSize(true);
        }
    }
}
