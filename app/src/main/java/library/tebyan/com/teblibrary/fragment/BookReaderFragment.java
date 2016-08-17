package library.tebyan.com.teblibrary.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import library.tebyan.com.teblibrary.R;

public class BookReaderFragment extends Fragment {

    View view;
    WebView bookReaderWebView;
    String bookUrl;

    public BookReaderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_book_reader, container, false);

        bookReaderWebView=(WebView)view.findViewById(R.id.book_reader_webview);
        bookReaderWebView.setWebViewClient(new BookReaderBrowser());

        bookUrl = getArguments().getString("book_url");
        if (bookUrl!= null) {
            initWebView();
        }
        else{
            Toast.makeText(view.getContext(), "book is not available", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
        return view;
    }

    public void initWebView(){
        bookReaderWebView.getSettings().setLoadsImagesAutomatically(true);
        bookReaderWebView.getSettings().setJavaScriptEnabled(true);
        bookReaderWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        bookReaderWebView.loadUrl(bookUrl);
    }

    private class BookReaderBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

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
