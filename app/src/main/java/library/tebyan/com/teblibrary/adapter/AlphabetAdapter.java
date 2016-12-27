package library.tebyan.com.teblibrary.adapter;

/**
 * Created by root on 10/17/16.
 */
//public class AlphabetAdapter {
//}

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.interfaces.AlphabetInterface;

public class AlphabetAdapter extends RecyclerView.Adapter<AlphabetAdapter.ViewHolder> implements View.OnClickListener  {
//    private ArrayList<String> alphabetArray;
    List<String> alphabetArray;
    Button character_btn;

    AlphabetInterface callback;

    public AlphabetAdapter(List<String> alphabetArray,AlphabetInterface callback) {
        this.alphabetArray = alphabetArray;
        this.callback=callback;
    }

    @Override
    public AlphabetAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        if (i==1)
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.char_view, viewGroup, false);
        else
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.light_char_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlphabetAdapter.ViewHolder viewHolder, int i) {

        viewHolder.char_img.setText(alphabetArray.get(i));
        viewHolder.char_img.setTag(alphabetArray.get(i));
        viewHolder.char_img.setOnClickListener(this);
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

    @Override
    public int getItemViewType(int position) {
     if (position%2==0)
         return 1;
     return 0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.character_btn:
                Button charBtn = (Button)view.findViewById(R.id.character_btn);
                callback.callbackAlphabet(charBtn.getText().toString());
                Log.i("sdsd","  " );
                break;


        }
    }




}
