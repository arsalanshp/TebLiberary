package library.tebyan.com.teblibrary.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.model.Comment;

/**
 * Created by root on 8/8/16.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    public List<Comment> commentsList;

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView commentText,commentDate;


        public CommentViewHolder(View view) {
            super(view);
            commentText = (TextView) view.findViewById(R.id.comment_text);
            commentDate = (TextView) view.findViewById(R.id.comment_date);
//            userName = (TextView) view.findViewById(R.id.user_name);
//            view.findViewById(R.id.user_avatar);
//            view.findViewById(R.id.comment_score);

        }
    }


    public CommentsAdapter(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list_row, parent, false);

        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = commentsList.get(position);
        holder.commentText.setText(comment.getCommentText());
        holder.commentDate.setText(comment.getDate());
//        holder.userName.setText(comment.getUserName());
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }
}
