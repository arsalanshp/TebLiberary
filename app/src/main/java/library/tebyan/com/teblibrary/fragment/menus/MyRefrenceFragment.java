package library.tebyan.com.teblibrary.fragment.menus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import library.tebyan.com.teblibrary.MainActivity;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.classes.interfaces.UploadBookInterface;

public class MyRefrenceFragment extends Fragment implements View.OnClickListener{

    private FragmentManager fragmentManager;
    private View view;
    private RadioButton radioReaded;
    private RadioButton radioWillRead;
    private RadioButton radioReading;
    private RadioButton radioNew;
    private String fragmentClassName;
    private ImageButton uploadBTN;
    private FragmentTransaction fragmentTransaction;
    private UploadBookInterface callBack;
    private ImageButton listStateBTN;
    private boolean listState;
    private String fragmentTag;
//    private Spinner refrenceFilterSpinner;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        fragmentClassName = "library.tebyan.com.teblibrary.fragment.menus.myRefrencePagesFraments.ReadFragment";
        super.onCreate(savedInstanceState);
        fragmentTag ="NewFragment";
        fragmentManager = getFragmentManager();
        openFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_refrence, container, false);
        context = getContext();
        radioReaded= (RadioButton)view.findViewById(R.id.radioReaded);
        radioWillRead= (RadioButton)view.findViewById(R.id.radioWillRead);
        radioReading= (RadioButton)view.findViewById(R.id.radioReading);
        radioNew= (RadioButton)view.findViewById(R.id.radioNew);
        uploadBTN = (ImageButton)view.findViewById(R.id.upload_btn);
        radioReaded.setOnClickListener(this);
        radioWillRead.setOnClickListener(this);
        radioReading.setOnClickListener(this);
        radioNew.setOnClickListener(this);
        uploadBTN.setOnClickListener(this);
        listStateBTN = (ImageButton)view.findViewById(R.id.list_state_btn);
        listStateBTN.setOnClickListener(this);
        listStateBTN.setTag(true);
//        refrenceFilterSpinner = (Spinner) view.findViewById(R.id.refrence_filter_spinner);
//        ArrayAdapter<CharSequence> refrence_type_adapter = ArrayAdapter.createFromResource(context,
//                R.array.my_refrence_filters, android.R.layout.simple_spinner_item);
//        refrenceFilterSpinner.setAdapter(refrence_type_adapter);

        this.callBack = (UploadBookInterface)getActivity();

        return view;
    }

    public void openFragment() {
        try{

            Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
            if (fragment == null){
                fragmentManager.findFragmentByTag(fragmentTag);
                Class fName = Class.forName(fragmentClassName);
                fragment = (Fragment)fName.newInstance();

                Bundle bundle = new Bundle();
                bundle.putString("fragmentTag", fragmentTag);
                bundle.putBoolean("listState",listState);
                fragment.setArguments(bundle);

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_placeholder, fragment, fragmentTag).commit();
//                fragmentTransaction.replace(R.id.fragment_placeholder, fragment, fragmentTag).addToBackStack(fragmentTag).commit();
            }
            else{
               fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_placeholder, fragment, fragmentTag).commit();
            }
        }
        catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e){}

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.upload_btn:
                this.callBack.UploadBookInterfaces();
                return;
            case R.id.radioReaded:
                fragmentTag = "ReadedFragment";
                break;
            case R.id.radioReading:
                fragmentTag ="ReadingFragment";
                break;
            case R.id.radioWillRead:
                fragmentTag ="WillReadFragment";
                break;
            case R.id.radioNew:
                fragmentTag ="NewFragment";
                break;
            case R.id.list_state_btn:
                listState = ((boolean)listStateBTN.getTag());
                listStateBTN.setTag(!listState);
                break;
        }
        openFragment();
    }
}
