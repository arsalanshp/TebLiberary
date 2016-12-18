package library.tebyan.com.teblibrary.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import library.tebyan.com.teblibrary.adapter.BookDetailsTabAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.classes.interfaces.BookReaderInterface;
import library.tebyan.com.teblibrary.model.AddRefResult;
import library.tebyan.com.teblibrary.model.BookDetails;
import library.tebyan.com.teblibrary.model.BookDetailsResults;
import library.tebyan.com.teblibrary.model.Data;

public class BookDetailsFragment extends Fragment implements View.OnClickListener,TabLayout.OnTabSelectedListener  {


    private FragmentManager fragmentManager;
    private View view;
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
    private BookReaderInterface callBack;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BookDetailsTabAdapter adapter;
    private boolean isBack= false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getChildFragmentManager();
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
        this.callBack = (BookReaderInterface) getActivity();
        return view;
    }


    private void initUI() {
        context = getContext();
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
                                initFragmentHeader(result.getResult());
                                initTab();
                            }
                            else{
                                Toast.makeText(context,"نتیجه ای برگردانده نشد",Toast.LENGTH_SHORT);
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

    private void initTab(){

        //Initializing the tablayout
        tabLayout = (TabLayout) view.findViewById(R.id.book_details_tab_layout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("منابع مرتبط"));
        tabLayout.addTab(tabLayout.newTab().setText("نقدوبررسی"));
        tabLayout.addTab(tabLayout.newTab().setText("فراداده"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) view.findViewById(R.id.book_details_view_pager);
        viewPager.setOffscreenPageLimit(3);
        //Creating our pager adapter
        if(!isBack) {
            adapter = new BookDetailsTabAdapter(fragmentManager, tabLayout.getTabCount(),bookId ,authorName, language, genre,detailsRef,topic,note);
            isBack = true;
        }
        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        //Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(this);

    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}
    @Override
    public void onTabReselected(TabLayout.Tab tab) {}


        @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.book_thumbnail:
                this.callBack.StartBookerReaderInterface(bookId,String.valueOf(v.getTag()));
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
