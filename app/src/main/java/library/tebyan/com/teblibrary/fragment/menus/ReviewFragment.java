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

public class ReviewFragment extends Fragment implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private View view;
    private RadioButton radioAlphabet;
    private RadioButton radioSubject;
    private RadioButton radioCollection;

    public ReviewFragment() {
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
        view = inflater.inflate(R.layout.fragment_review, container, false);
        radioAlphabet= (RadioButton)view.findViewById(R.id.radioAlphabet);
        radioSubject= (RadioButton)view.findViewById(R.id.radioSubject);
        radioCollection= (RadioButton)view.findViewById(R.id.radioCollection);
        radioAlphabet.setOnClickListener(this);
        radioSubject.setOnClickListener(this);
        radioCollection.setOnClickListener(this);
        openFragment("library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments.SubjectFragment","SubjectFragment");
        return view;
    }



    public void openFragment(String fragmentName,String fragmentTag) {
        try{
            Class fName = Class.forName(fragmentName); //"com.duke.MyLocaleServiceProvider"
            Fragment fragment = (Fragment)fName.newInstance();

            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.review_fragment_container, fragment, fragmentTag);
            fragmentTransaction.addToBackStack(fragmentTag);
            fragmentTransaction.commit();
        }
        catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e){}





//        if (rbtnView == view.findViewById(R.id.radioAlphabet)){
//            fragment = new AlphabetFragment();
//            fragmentTag = "radioAlphabet";
//
//        }else if(rbtnView == view.findViewById(R.id.radioCollection)){
//            fragment = new CollectionFragment();
//            fragmentTag = "radioCollection";
//
//        }else {     //view.findViewById(R.id.radioSubject))
//            fragment = new SubjectFragment();
//            fragmentTag = "radioSubject";
//        }

//        fragmentManager = getFragmentManager(); //getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.review_fragment_container, fragment, fragmentTag);
//        fragmentTransaction.addToBackStack(fragmentTag);
//        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.radioAlphabet:
                openFragment("library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments.AlphabetFragment","AlphabetFragment");
                return;

            case R.id.radioCollection:
                openFragment("library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments.CollectionFragment","CollectionFragment");
                return;
            case R.id.radioSubject:
                openFragment("library.tebyan.com.teblibrary.fragment.menus.reviewPagesFragments.SubjectFragment","SubjectFragment");
                return;



        }
    }
}
