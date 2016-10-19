package library.tebyan.com.teblibrary.adapter;

/**
 * Created by root on 10/17/16.
 */
//public class AlphabetAdapter {
//}

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import library.tebyan.com.teblibrary.R;

public class DataAlphabetAdapter extends RecyclerView.Adapter<DataAlphabetAdapter.ViewHolder> {
//    private ArrayList<String> alphabetArray;
    List<String> alphabetArray;

    public DataAlphabetAdapter(List<String> alphabetArray) {
        this.alphabetArray = alphabetArray;
    }

    @Override
    public DataAlphabetAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.char_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAlphabetAdapter.ViewHolder viewHolder, int i) {

        viewHolder.char_img.setText(alphabetArray.get(i));
        viewHolder.char_img.setTag(alphabetArray.get(i));
//        Picasso.with(context).load(alphabetArray.get(i).getAndroid_image_url()).resize(240, 120).into(viewHolder.img_android);
    }

    @Override
    public int getItemCount() {
        return alphabetArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private Button char_img;
        public ViewHolder(View view) {
            super(view);
            char_img = (Button)view.findViewById(R.id.character_btn);
        }
    }

}
