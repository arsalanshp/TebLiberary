package library.tebyan.com.teblibrary.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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
//    private BookerAnswerInterface callBack;

    public QuestionAdapter(Context context, ArrayList<BookerQuestion> items) {
        this.items = items;
        this.context = context;
//        this.callBack = callBack;

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
        holder.questionTxt.setTag(question.getQuestionID());
//        holder.questionTxt.setOnClickListener(this);
        holder.answerTxt.setText(question.getAnswer());
        holder.showAnswer.setOnClickListener(this);
        holder.showAnswer.setTag(R.string.answer,holder.answerLayout);
        holder.showAnswer.setTag(R.string.show,"1"); // for hiding answer
}


    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public void onClick(View view) {
//        int pos= (int) view.getTag();
        LinearLayout answerLayout = (LinearLayout) view.getTag(R.string.answer);
        if (view.getTag(R.string.show)=="1") {
//            view.setVisibility(View.GONE);
            answerLayout.setVisibility(View.VISIBLE);
            view.setTag(R.string.show,"0");
            ((ImageButton)view).setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
        }else {
            answerLayout.setVisibility(View.GONE);
            view.setTag(R.string.show,"1");
            ((ImageButton)view).setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView questionTxt,answerTxt;
        public ImageButton showAnswer;
        public LinearLayout answerLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            questionTxt= (TextView) itemView.findViewById(R.id.question_txt);
            answerTxt = (TextView)itemView.findViewById(R.id.answer_txt);
            showAnswer = (ImageButton)itemView.findViewById(R.id.show_answer);
            answerLayout = (LinearLayout)itemView.findViewById(R.id.answer_layout);
        }
    }
}