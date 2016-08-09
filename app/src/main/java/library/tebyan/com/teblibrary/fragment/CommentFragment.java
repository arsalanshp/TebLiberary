package library.tebyan.com.teblibrary.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.CommentsAdapter;
import library.tebyan.com.teblibrary.model.Comment;

public class CommentFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private List<Comment> commentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CommentsAdapter commentsAdapter;

    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_comment, container, false);

        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.comment_recycler_view);

        commentsAdapter = new CommentsAdapter(commentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(fragmentView.getContext()); //getApplicationContext()
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(commentsAdapter);

        prepareMovieData();


        return fragmentView;
    }


    private void prepareMovieData() {
        Comment comment = new Comment("Mad Max: Fury Road", "Action & Adventure");
        commentList.add(comment);

        comment = new Comment("Inside Out", "Animation, Kids & Family");
        commentList.add(comment);
        commentsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
