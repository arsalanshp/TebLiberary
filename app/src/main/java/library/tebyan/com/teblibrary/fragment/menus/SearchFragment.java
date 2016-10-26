package library.tebyan.com.teblibrary.fragment.menus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import library.tebyan.com.teblibrary.R;

public class SearchFragment extends Fragment implements View.OnClickListener ,OnItemSelectedListener {

    Spinner spinner_filter2;
    Spinner spinner_filter3;
    Spinner spinner_filter1;
    Spinner logic_spinner1;
    Spinner logic_spinner2;
    Spinner sort_base_spinner;
    Spinner sort_type_spinner;
    Button drop_down_filtes_btn;
    LinearLayout linearLayout_filters;

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
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_search, container, false);
        context = view.getContext();
        initUI();
        return view;
    }

    private void initUI(){
        initSpinners();

        drop_down_filtes_btn = (Button)view.findViewById(R.id.drop_down_filtes_btn);
        linearLayout_filters = (LinearLayout)view.findViewById(R.id.linearLayout_filters);
        drop_down_filtes_btn.setOnClickListener(this);

    }

    private void initData(){



    }

    private void initSpinners(){
        spinner_filter2 = (Spinner) view.findViewById(R.id.spinner_filter2);
        spinner_filter3 = (Spinner) view.findViewById(R.id.spinner_filter3);
        spinner_filter1 = (Spinner) view.findViewById(R.id.spinner_filter1);
        logic_spinner1 = (Spinner) view.findViewById(R.id.logic_spinner1);
        logic_spinner2 = (Spinner) view.findViewById(R.id.logic_spinner2);
        sort_base_spinner = (Spinner) view.findViewById(R.id.sort_base_spinner);
        sort_type_spinner = (Spinner) view.findViewById(R.id.sort_type_spinner);
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
