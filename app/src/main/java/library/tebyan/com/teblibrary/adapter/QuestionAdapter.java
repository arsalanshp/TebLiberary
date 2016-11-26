package library.tebyan.com.teblibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.interfaces.BookerAnswerInterface;
import library.tebyan.com.teblibrary.model.BookerQuestion;

/**
 * Created by yahyapour
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> implements View.OnClickListener {

    public Context context;
    public ArrayList<BookerQuestion> items;
    private BookerAnswerInterface callBack;

    public QuestionAdapter(Context context, ArrayList<BookerQuestion> items,BookerAnswerInterface callBack) {
        this.items = items;
        this.context = context;
        this.callBack = callBack;

    }

    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final QuestionAdapter.ViewHolder holder, final int position) {
        BookerQuestion question = items.get(position);
        holder.questionTxt.setText(question.getQuestion());
        holder.questionTxt.setTag(question.getLink());
        holder.questionTxt.setOnClickListener(this);
}


    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public void onClick(View view) {
        TextView question = (TextView)view;

        this.callBack.StartBookerAnswersInterface((int)question.getTag() , question.getText().toString());
//        this.callBack.StartBookerAnswersInterface(626286 , question.getText().toString());





//        switch (view.getId()){
//            case R.id.img_thumbnail:
//
//                int pos= (int) view.getTag();
//                int id = items.get(pos).getID();
//                Intent bookProfileIntent = new Intent(context, DescriptionActivity.class);
//                bookProfileIntent.putExtra("book_id",id);
////                context.startActivity(bookProfileIntent);
//                ((Activity)context).startActivityForResult(bookProfileIntent,1);
//
//        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView questionTxt;

        public ViewHolder(View itemView) {
            super(itemView);
            questionTxt= (TextView) itemView.findViewById(R.id.question_txt);
        }

    }
}