package library.tebyan.com.teblibrary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.model.BookDetailsResults;


public class DetailsFragment extends Fragment {

    public View view ;
    public int book_id;
    public BookDetailsResults book_data;
    public TextView book_name;
    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle extras = getActivity().getIntent().getExtras();
            if(extras == null) {
                book_id= 0;
            } else {
                book_id = extras.getInt("book_id");

            }
        } else {
            book_id= (Integer)savedInstanceState.getSerializable("book_id");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details, container, false);
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
