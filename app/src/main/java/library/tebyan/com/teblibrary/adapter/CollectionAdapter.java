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
import library.tebyan.com.teblibrary.classes.interfaces.CollectionsInterface;
import library.tebyan.com.teblibrary.model.Collection;
import library.tebyan.com.teblibrary.model.CollectionsResult;
import library.tebyan.com.teblibrary.model.Data;

/**
 * Created by Yahyapour on 7/18/2016.
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> implements View.OnClickListener {

    public Context context;
    public ArrayList<Collection> items;
    public CollectionsInterface callBack;

    public CollectionAdapter(Context context, ArrayList<Collection> items, CollectionsInterface callBack) {
        this.items = items;
        this.context = context;
        this.callBack = callBack;
    }

    @Override
    public CollectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CollectionAdapter.ViewHolder holder, final int position) {
        Collection collection = items.get(position);
        int id = collection.getID();
        holder.collectionName.setText(collection.getTitle());
//        holder.collectionName.setTag(id);
        holder.collectionName.setTag(position);
        holder.collectionName.setOnClickListener(this);
        holder.collectionThumbnail.setOnClickListener(this);
        holder.subCollectionCount.setText(String.valueOf(collection.getMetadataCount()));
        Globals.ion.with(holder.collectionThumbnail).load(collection.getImageUrl());
        holder.collectionThumbnail.setOnClickListener(this);
        holder.collectionThumbnail.setTag(position);
//        holder.collectionThumbnail.setTag(id);
}


    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public void onClick(View v) {
        int position =(int) v.getTag();
        Collection collection = items.get(position);
        this.callBack.StartSubCollectionsInterface(collection.getID(),collection.getTitle(),collection.getImageUrl());
//        this.callBack.StartSubCollectionsInterface(items.get(position));

//        switch (v.getId()){
//            case R.id.collection_thumbnail:
//                int book_id = (int) v.getTag();
//
//                break;
//        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView collectionThumbnail;
        public TextView collectionName,subCollectionCount ;

        public ViewHolder(View itemView) {
            super(itemView);
            collectionThumbnail = (ImageView) itemView.findViewById(R.id.collection_thumbnail);
            subCollectionCount = (TextView) itemView.findViewById(R.id.sub_collection_count);
            collectionName = (TextView) itemView.findViewById(R.id.collection_name);
        }
    }
}