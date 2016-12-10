package library.tebyan.com.teblibrary.fragment.menus;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.koushikdutta.async.future.FutureCallback;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.BookAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.classes.interfaces.BookDetailsInterface;
import library.tebyan.com.teblibrary.model.Data;
import library.tebyan.com.teblibrary.model.DataList;
import library.tebyan.com.teblibrary.model.SearchList;

public class SearchFragment extends Fragment implements View.OnClickListener ,OnItemSelectedListener {

    private EditText txtField1;
    private EditText txtField2;
    private EditText txtField3;
    private Spinner spinner_filter2;
    private Spinner spinner_filter3;
    private Spinner spinner_filter1;
    private Spinner logic_spinner1;
    private Spinner logic_spinner2;
    private Spinner sort_base_spinner;
    private Spinner sort_type_spinner;
    private Button drop_down_filtes_btn;
    private LinearLayout linearLayout_filters;
    private ImageButton search_box_btn;
    private ImageButton send_with_filters_btn;
    private EditText search_box_txt;
    private RecyclerView search_recycler_view;
    private LinearLayoutManager linearLayoutManager;
    private BookAdapter bookAdapter;
    private int visibleItemCount,pastVisiblesItems,pageIndex;
    private int totalItemCount;
    private ArrayList<Data> data=new ArrayList<>();
    private String filter_query;
    private String[] search_filters_tag;
    private String[] sort_base_search_tag;
    private String[] logical_search_filters_tag;
    private String[] sort_type_tag;
    private boolean loading=false;

    View view ;
    List<String> filtersList;
    List<String> logicsList;

    Context context;

    public SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_search, container, false);
        context = view.getContext();
        initUI();
        initData();
        return view;
    }

    private void initUI(){
        initSpinners();

        txtField1 = (EditText)view.findViewById(R.id.txtField1);
        txtField2 = (EditText)view.findViewById(R.id.txtField2);
        txtField3 = (EditText)view.findViewById(R.id.txtField3);

        drop_down_filtes_btn = (Button)view.findViewById(R.id.drop_down_filtes_btn);
        linearLayout_filters = (LinearLayout)view.findViewById(R.id.linearLayout_filters);
        drop_down_filtes_btn.setOnClickListener(this);
        search_box_btn = (ImageButton)view.findViewById(R.id.search_box_btn);
        search_box_btn.setOnClickListener(this);
        search_box_txt = (EditText)view.findViewById(R.id.search_box_txt);
        send_with_filters_btn = (ImageButton)view.findViewById(R.id.send_with_filters_btn);
        send_with_filters_btn.setOnClickListener(this);

        search_recycler_view = (RecyclerView)view.findViewById(R.id.search_recycler_view);
        search_recycler_view .addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();//layoutManager.;//.findFirstVisibleItemPosition();
                if (!loading && dy>0) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        pageIndex++;
                        initData();
                    }
                }
            }
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                visibleItemCount = linearLayoutManager.getChildCount();
//                totalItemCount = linearLayoutManager.getItemCount();
//                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
//                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
//                    pageIndex++;
//                    initData();
//                }
//
//            }
        });
        linearLayoutManager = new LinearLayoutManager(context);
        search_recycler_view .setLayoutManager(linearLayoutManager);
        search_recycler_view .setHasFixedSize(true);

        bookAdapter = new BookAdapter(context, data,(BookDetailsInterface) getActivity(), false);
        search_recycler_view .setAdapter(bookAdapter);
    }

    private void initData(){

        try {
            Globals.ion.with(this).load(WebserviceUrl.SEARCH+filter_query)
                    .setHeader("userToken", Globals.userToken)
                    .as(DataList.class).setCallback(new FutureCallback<DataList>() {
                @Override
                public void onCompleted(Exception e, DataList bookList) {
                    if (Utils.isOnline(getContext())) {
                        if (e == null & bookList != null& bookList.getResult().size() > 0) {
                            bookAdapter.items.addAll(bookList.getResult());
                            bookAdapter.notifyDataSetChanged();
//                            emptyImageButton.setVisibility(View.GONE);
                            search_recycler_view.setVisibility(View.VISIBLE);
                        }
                        else if(bookList.getResult().size()==0){

//                            emptyImageButton.setVisibility(View.VISIBLE);
                            search_recycler_view.setVisibility(View.GONE);
                        }
                    }
                    else {
                        Toast.makeText(context, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                    loading=false;
                }
            });
        }catch (Exception e){}



//
//        try {
//            title = URLEncoder.encode(search_box_txt.getText().toString(), "utf-8");
//        Globals.ion.with(getContext()).load(WebserviceUrl.SEARCH+filter_query) //"Field1=title&Value1="+title
//                .as(SearchList.class).setCallback(new FutureCallback<SearchList>() {
//            @Override
//            public void onCompleted(Exception e, SearchList searchList) {
//                if (Utils.isOnline(getContext())) {
//                    if (e == null & searchList.getResult().getData().getResult().size() > 0)
//                        Log.i("sdsd", searchList + "");
//                    bookAdapter.items.addAll(searchList.getResult().getData().getResult());
//                    //recyclerSearch.setAdapter(adapter);
//                    bookAdapter.notifyDataSetChanged();
//                }
//            }
//        });
//        }catch (UnsupportedEncodingException ex){}


    }

    private void initSpinners(){
        spinner_filter2 = (Spinner) view.findViewById(R.id.spinner_filter2);
        spinner_filter3 = (Spinner) view.findViewById(R.id.spinner_filter3);
        spinner_filter1 = (Spinner) view.findViewById(R.id.spinner_filter1);
        logic_spinner1 = (Spinner) view.findViewById(R.id.logic_spinner1);
        logic_spinner2 = (Spinner) view.findViewById(R.id.logic_spinner2);
        sort_base_spinner = (Spinner) view.findViewById(R.id.sort_base_spinner);
        sort_type_spinner = (Spinner) view.findViewById(R.id.sort_type_spinner);

        // get tag value of spinners for query
        search_filters_tag = getResources().getStringArray(R.array.search_filters_tag);
        sort_base_search_tag=getResources().getStringArray(R.array.sort_base_search_tag);
        logical_search_filters_tag=getResources().getStringArray(R.array.logical_search_filters_tag);
        sort_type_tag=getResources().getStringArray(R.array.sort_type_tag);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.search_filters, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> logic_adapter = ArrayAdapter.createFromResource(context,
                R.array.logical_search_filters, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> sort_base_adapter = ArrayAdapter.createFromResource(context,
                R.array.sort_base_search, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence>  sort_type_adapter = ArrayAdapter.createFromResource(context,
                R.array.sort_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_filter1.setAdapter(adapter);
        spinner_filter2.setAdapter(adapter);
        spinner_filter3.setAdapter(adapter);
        logic_spinner1.setAdapter(logic_adapter);
        logic_spinner2.setAdapter(logic_adapter);
        sort_base_spinner.setAdapter(sort_base_adapter);
        sort_type_spinner.setAdapter(sort_type_adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drop_down_filtes_btn:
                if (drop_down_filtes_btn.getTag()=="close"){
                    linearLayout_filters.setVisibility(View.VISIBLE);
                    drop_down_filtes_btn.setTag("open");
                    drop_down_filtes_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_black_24dp, 0, 0, 0);
                }
                else{
                    linearLayout_filters.setVisibility(View.GONE);
                    drop_down_filtes_btn.setTag("close");
                    drop_down_filtes_btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_drop_down_black_24dp, 0, 0, 0);
                }
                break;
            case R.id.search_box_btn:
                initData();
//                search_box_txt.setText("");
                break;
            case R.id.send_with_filters_btn:

                spinner_filter1.getSelectedItem().toString();
                //                https://library.tebyan.net/fa/Browse/Search#

                try {
//                    filter_query =
//                            "lstOrder=" + sort_type_tag[sort_type_spinner.getSelectedItemPosition()] +
//                                    "&lstOrderBy=" + sort_base_search_tag[sort_base_spinner.getSelectedItemPosition()] +
//                                    "&lstField1=" + search_filters_tag[spinner_filter1.getSelectedItemPosition()] +
//                                    "&txtField1=" + URLEncoder.encode(txtField1.getText().toString(), "utf-8") +
//                                    "&lstActor1=" + logical_search_filters_tag[logic_spinner1.getSelectedItemPosition()] +
//                                    "&lstField2=" + search_filters_tag[spinner_filter2.getSelectedItemPosition()] +
//                                    "&txtField2=" + URLEncoder.encode(txtField2.getText().toString(), "utf-8") +
//                                    "&lstActor2=" + logical_search_filters_tag[logic_spinner2.getSelectedItemPosition()] +
//                                    "&lstField3=" + search_filters_tag[spinner_filter3.getSelectedItemPosition()] +
//                                    "&txtField3=" + URLEncoder.encode(txtField3.getText().toString(), "utf-8") +
//                                    "&PageSize=10";
                    filter_query =
                            "lstOrder=" + sort_type_tag[sort_type_spinner.getSelectedItemPosition()] +
                                    "&lstOrderBy=" + sort_base_search_tag[sort_base_spinner.getSelectedItemPosition()] +
                                    "&Field1=" + search_filters_tag[spinner_filter1.getSelectedItemPosition()] +
                                    "&Value1=" + URLEncoder.encode(txtField1.getText().toString(), "utf-8") +
                                    "&Op1=" + logical_search_filters_tag[logic_spinner1.getSelectedItemPosition()] +
                                    "&Field2=" + search_filters_tag[spinner_filter2.getSelectedItemPosition()] +
                                    "&Value2=" + URLEncoder.encode(txtField2.getText().toString(), "utf-8") +
                                    "&OP2=" + logical_search_filters_tag[logic_spinner2.getSelectedItemPosition()] +
                                    "&Field3=" + search_filters_tag[spinner_filter3.getSelectedItemPosition()] +
                                    "&Value3=" + URLEncoder.encode(txtField3.getText().toString(), "utf-8") +
                                    "&PageSize=10";
                    initData();
                }
                catch (UnsupportedEncodingException e){

                }
                break;


        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }





}
