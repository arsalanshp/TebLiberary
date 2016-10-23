package library.tebyan.com.teblibrary.fragment.menus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import library.tebyan.com.teblibrary.R;

public class MyRefrenceFragment extends Fragment implements View.OnClickListener{

    private FragmentManager fragmentManager;
    private View view;
    private RadioButton radioReaded;
    private RadioButton radioWillRead;
    private RadioButton radioReading;
    private RadioButton radioNew;

    public MyRefrenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_refrence, container, false);
        radioReaded= (RadioButton)view.findViewById(R.id.radioReaded);
        radioWillRead= (RadioButton)view.findViewById(R.id.radioWillRead);
        radioReading= (RadioButton)view.findViewById(R.id.radioReading);
        radioNew= (RadioButton)view.findViewById(R.id.radioNew);
        radioReaded.setOnClickListener(this);
        radioWillRead.setOnClickListener(this);
        radioReading.setOnClickListener(this);
        radioNew.setOnClickListener(this);
        return view;
    }

    public void openFragment(String fragmentName,String fragmentTag) {
        try{
            Class fName = Class.forName(fragmentName); //"com.duke.MyLocaleServiceProvider"
            Fragment fragment = (Fragment)fName.newInstance();

            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_placeholder, fragment, fragmentTag);
            fragmentTransaction.addToBackStack(fragmentTag);
            fragmentTransaction.commit();
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
            case R.id.radioReaded:
                openFragment("library.tebyan.com.teblibrary.fragment.menus.myRefrencePagesFraments.ReadedFragment","ReadedFragment");
                return;

            case R.id.radioReading:
                openFragment("library.tebyan.com.teblibrary.fragment.menus.myRefrencePagesFraments.ReadingFragment","ReadingFragment");
                return;
            case R.id.radioWillRead:
                openFragment("library.tebyan.com.teblibrary.fragment.menus.myRefrencePagesFraments.WillReadFragment","WillReadFragment");
                return;
            case R.id.radioNew:
                openFragment("library.tebyan.com.teblibrary.fragment.menus.myRefrencePagesFraments.NewFragment","NewFragment");
                return;
        }
    }






}
