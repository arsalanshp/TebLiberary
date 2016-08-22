package library.tebyan.com.teblibrary.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;

import library.tebyan.com.teblibrary.R;


public class ImageSliderFragment extends Fragment {

    String url;

    public ImageSliderFragment() {

    }

    public static ImageSliderFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString("url", url);
        ImageSliderFragment fragment = new ImageSliderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString("url");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_slider, container, false);
        ImageView sliderImage = (ImageView) view.findViewById(R.id.slider_image);
        Ion.with(sliderImage).centerCrop().load(url);
        sliderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;

    }

}
