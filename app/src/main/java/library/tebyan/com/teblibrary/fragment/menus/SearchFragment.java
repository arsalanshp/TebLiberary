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

    Spinner spinnerFilter1;
    Spinner spinnerFilter2;
    Spinner spinnerFilter3;
    Spinner spinnerFilter4;
    Spinner logical_spinner1;
    Spinner logical_spinner2;

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
        initData();
        return view;
    }


    private void initData(){

        initSpinners();

    }

    private void initSpinners(){

        spinnerFilter1 = (Spinner) view.findViewById(R.id.spinner_filter1);
        spinnerFilter1.setOnItemSelectedListener(this);

        spinnerFilter2 = (Spinner) view.findViewById(R.id.spinner_filter2);
        spinnerFilter2.setOnItemSelectedListener(this);

        spinnerFilter3 = (Spinner) view.findViewById(R.id.spinner_filter3);
        spinnerFilter3.setOnItemSelectedListener(this);
//
//        spinnerFilter4 = (Spinner) view.findViewById(R.id.spinner_filter4);
//        spinnerFilter4.setOnItemSelectedListener(this);

//        logical_spinner1 = (Spinner) view.findViewById(R.id.logical_spinner1);
//        logical_spinner1.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        filtersList = new ArrayList<String>();
        filtersList.add("عنوان");
        filtersList.add("شرح پدیدآور");
        filtersList.add("موضوع");
        filtersList.add("ناشر");
        filtersList.add("یادداشت");
        filtersList.add("زبان");
        filtersList.add("نوع منبع");
        filtersList.add("تمام متن");
        filtersList.add("تمام موارد");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, filtersList);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerFilter1.setAdapter(dataAdapter);
        spinnerFilter2.setAdapter(dataAdapter);
        spinnerFilter3.setAdapter(dataAdapter);


        ///////logical spinner /////////

        logical_spinner1 = (Spinner) view.findViewById(R.id.logical_spinner1);
        logical_spinner1.setOnItemSelectedListener(this);

        logicsList = new ArrayList<String>();
        logicsList.add("و");
        logicsList.add("یا");
        logicsList.add("بجز");

        ArrayAdapter<String> logicalDataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, logicsList);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        logical_spinner1.setAdapter(logicalDataAdapter);
    }

    @Override
    public void onClick(View v) {

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
