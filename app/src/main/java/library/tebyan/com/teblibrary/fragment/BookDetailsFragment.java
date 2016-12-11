package library.tebyan.com.teblibrary.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import library.tebyan.com.teblibrary.MainActivity;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.AddRefResult;
import library.tebyan.com.teblibrary.model.BookDetails;
import library.tebyan.com.teblibrary.model.BookDetailsResults;
import library.tebyan.com.teblibrary.model.Data;

public class BookDetailsFragment extends Fragment implements View.OnClickListener {


    private FragmentManager fragmentManager;
    private View view;
    private RadioButton radio_metadata;
    private RadioButton radio_analyze;
    private RadioButton radio_relative_resource;
    private FragmentTransaction fragmentTransaction;
    private Context context;
    private int bookId;
    private boolean ownerFlag;
    private TextView txtAuthor;
    private TextView txtBookName;//book_name
    private ImageView imgViewBook; //book_thumbnail
    private String webUrl ,authorName,language,genre,detailsRef,topic,note;;
    private Bundle bundle;
    private Button addMyReBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_book_details, container, false);
        bookId =getArguments().getInt("book_id");
        ownerFlag = getArguments().getBoolean("ownerFlag");

        if(ownerFlag){
            webUrl =WebserviceUrl.Get_BOOK_DETAILS_OWNER;
        }
        else{
            webUrl =WebserviceUrl.Get_BOOK_DETAILS;
        }
        bundle = new Bundle();
        initUI();
        return view;
    }


    private void initUI() {
        context = getContext();
        radio_analyze = (RadioButton)view.findViewById(R.id.radio_analyze);
        radio_analyze.setOnClickListener(this);

        radio_metadata = (RadioButton)view.findViewById(R.id.radio_metadata);
        radio_metadata.setOnClickListener(this);

        radio_relative_resource=(RadioButton)view.findViewById(R.id.radio_relative_resource);
        radio_relative_resource.setOnClickListener(this);

        txtAuthor = (TextView) view.findViewById(R.id.author_name);
        txtBookName = (TextView) view.findViewById(R.id.book_name);
        imgViewBook = (ImageView) view.findViewById(R.id.book_thumbnail);
        imgViewBook.setOnClickListener(this);

        addMyReBtn = (Button)view.findViewById(R.id.add_myRe_button);
        addMyReBtn.setOnClickListener(this);
        initData();
    }

    private void initData() {
        if (Utils.isOnline(context)) {
            Globals.ion.with(this).load(webUrl + "?ID=" + bookId)
                    .setHeader("userToken", Globals.userToken)
                    .as(BookDetailsResults.class)
                    .setCallback(new FutureCallback<BookDetailsResults>() {
                        @Override
                        public void onCompleted(Exception e, BookDetailsResults result) {
                            if (e == null &result != null & result.getResult() !=null) {
                                Log.i("etgg", result + "");
                                initFragmentHeader(result.getResult());
                                openFragment("library.tebyan.com.teblibrary.fragment.menus.metaDataFragments.MetaDataFragment","MetaDataFragment");
                            }
                            else{
                                getActivity().finish();
                            }
                        }
                    });
        }
        else {
            Toast.makeText(context, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void initFragmentHeader(BookDetails details) {
        txtBookName.setText(details.getTitle());
        txtAuthor.setText(details.getAuthor());
        String url = details.getImageUrl();

        Globals.ion.with(imgViewBook)
                .load(details.getImageUrl());
        imgViewBook.setTag(details.getLink());
        authorName = details.getAuthor();
        language= details.getLanguage();
        genre = details.getGenre();
        detailsRef = details.getDigitalRefrence();
        topic = details.getTopics();
        note = details.getDescription();

    }

    public void openFragment(String fragmentName,String fragmentTag) {
        try {
            Class fName = Class.forName(fragmentName); //"com.duke.MyLocaleServiceProvider"
            Fragment fragment = (Fragment) fName.newInstance();

            if (fragmentTag =="MetaDataFragment") {
                bundle.clear();
                bundle.putString("authorName",authorName);
                bundle.putString("language",language);
                bundle.putString("refrenceType",genre);
                bundle.putString("digitalRefrences",detailsRef);
                bundle.putString("subjects",topic);
                bundle.putString("creator",authorName);
                bundle.putString("note",note);
                fragment.setArguments(bundle);
            }
            else if (fragmentTag == "AnalyzeFragment"){
                bundle.clear();
                bundle.putInt("book_id",bookId);
                fragment.setArguments(bundle);
            }
            else if (fragmentTag == "RelativeResourceFragment") {
                bundle.clear();
                bundle.putString("authorName",authorName);
                fragment.setArguments(bundle);
            }
            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_placeholder, fragment, fragmentTag);
            fragmentTransaction.addToBackStack(fragmentTag);
            fragmentTransaction.commit();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
        }
    }

        @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.radio_metadata:
                openFragment("library.tebyan.com.teblibrary.fragment.menus.metaDataFragments.MetaDataFragment","MetaDataFragment");
                break;
            case R.id.radio_analyze:
                openFragment("library.tebyan.com.teblibrary.fragment.menus.metaDataFragments.AnalyzeFragment","AnalyzeFragment");
                break;
            case R.id.radio_relative_resource:
                openFragment("library.tebyan.com.teblibrary.fragment.menus.metaDataFragments.RelativeResourceFragment","RelativeResourceFragment");
                break;
            case R.id.add_myRe_button:
                // -1 : will read , -2 is reading -3 readed
                if (Utils.isOnline(context)) {
                    Globals.ion.with(context).load(WebserviceUrl.ADDTO)
                            .setHeader("userToken", Globals.userToken)
                            .setBodyParameter("ID",String.valueOf(bookId))
                            .setBodyParameter("CategoryID","-1")
                            .as(AddRefResult.class)
                            .setCallback(new FutureCallback<AddRefResult>() {
                                @Override
                                public void onCompleted(Exception e, AddRefResult result) {
                                    if (e == null &result.getD()!= null ) {
                                        Toast.makeText(context,result.getD().getMessage(),Toast.LENGTH_SHORT).show();
//                                    }else {
//                                        Toast.makeText(context,"لطفا مجددا تست نمایید",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(context, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
