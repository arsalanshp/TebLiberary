package library.tebyan.com.teblibrary.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.model.DataFirstPage;

/**
 * Created by Yahyapour on 7/18/2016.
 */
public class SpecialSubjectiveAdapter extends RecyclerView.Adapter<SpecialSubjectiveAdapter.ViewHolder> implements View.OnClickListener {

    public Context context;
    public ArrayList<DataFirstPage> items;

    public SpecialSubjectiveAdapter(Context context, ArrayList<DataFirstPage> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public SpecialSubjectiveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.special_item_subjective, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SpecialSubjectiveAdapter.ViewHolder holder, final int position) {
        DataFirstPage metadata = items.get(position);
//        holder.bookTitle.setText(metadata.getTitle());
//        holder.bookAuthor.setText(metadata.getTitr());
//        Globals.ion.with(holder.bookThumbnail).load();


        Globals.ion.with(context).load(metadata.getImage()).withBitmap().asBitmap()
                .setCallback(new FutureCallback<Bitmap>() {
                    @Override
                    public void onCompleted(Exception e, Bitmap result) {
//                        result.getWidth();322*198
//                        result.getHeight();

                        Bitmap imageRounded = Bitmap.createBitmap(result.getWidth(), result.getHeight(), result.getConfig());
//                        Bitmap imageRounded = Bitmap.createBitmap(370, 270, result.getConfig());
//                        Bitmap imageRounded = Bitmap.createScaledBitmap(result,370,250 , false);
                        Canvas canvas = new Canvas(imageRounded);
                        Paint mpaint = new Paint();
                        mpaint.setAntiAlias(true);
                        mpaint.setShader(new BitmapShader(result, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                        canvas.drawRoundRect((new RectF(0, 0, result.getWidth(), result.getHeight())), 25, 25, mpaint);// Round Image Corner 100 100 100 100
//                        canvas.drawRoundRect((new RectF(0, 0, 370, 270)), 25, 25, mpaint);// Round Image Corner 100 100 100 100
                        holder.bookThumbnail.setImageBitmap(imageRounded);
                        // change layout param for not deform image with change it's size
                        holder.bookThumbnail.setLayoutParams(
                                new LinearLayout.LayoutParams(
                                        322,
                                        200));


                    }
                });

        holder.bookThumbnail.setOnClickListener(this);
        holder.bookThumbnail.setTag(metadata.getLink());
}


    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.book_thumbnail:
                String box_url = (String) v.getTag();
                break;

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView bookThumbnail;
//        public TextView bookAuthor, bookTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            /*imgOverFlow = (ImageView) itemView.findViewById(R.id.overflow);*/
            bookThumbnail = (ImageView) itemView.findViewById(R.id.book_thumbnail);
//            bookAuthor= (TextView) itemView.findViewById(R.id.book_author);
//            bookTitle = (TextView) itemView.findViewById(R.id.book_title);
        }

    }

}