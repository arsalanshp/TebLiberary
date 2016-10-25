package library.tebyan.com.teblibrary.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import library.tebyan.com.teblibrary.R;

public class UploadBookFragment extends Fragment implements View.OnClickListener {

    View view;
    Context context;
    Button cancel_btn;
    Button send_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_upload_book, container, false);
        cancel_btn = (Button)view.findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(this);

        send_btn = (Button)view.findViewById(R.id.send_btn);
        send_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_btn:
                getActivity().onBackPressed();
//                getActivity().getSupportFragmentManager().popBackStackImmediate();
                break;
            case R.id.send_btn:
                break;
        }

    }
}
