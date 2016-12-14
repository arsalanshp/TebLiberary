package library.tebyan.com.teblibrary.fragment.menus;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.SpecialAdapter;
import library.tebyan.com.teblibrary.adapter.SpecialSubjectiveAdapter;
import library.tebyan.com.teblibrary.classes.Globals;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.DataFirstPage;
import library.tebyan.com.teblibrary.model.SpecialBookList;
import library.tebyan.com.teblibrary.model.SpecialList;


public class SpecialsFragment extends Fragment {


    private View view;
    private Context context;
    private RecyclerView subjective_recyler_view;
    private RecyclerView specials_recyler_view;
    private RecyclerView favoriouts_recyler_view;
    private SpecialSubjectiveAdapter subjectiveAdapter;
    private SpecialAdapter specialsAdapter;
    private SpecialAdapter favorioutsAdapter;
    private LinearLayoutManager subjectiveLinearLayoutManager;
    private LinearLayoutManager specialLinearLayoutManager;
    private LinearLayoutManager favorioutLinearLayoutManager;
    private ImageButton naghdBTN;
    private boolean isBack= false;


    public SpecialsFragment() {
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
        view = inflater.inflate(R.layout.fragment_specials, container, false);
        context = view.getContext();
        initUI();
        if(!isBack) {
            initData();
            isBack = true;
        }
        else{
            subjective_recyler_view.setAdapter(subjectiveAdapter);
            specials_recyler_view.setAdapter(specialsAdapter);
            favoriouts_recyler_view.setAdapter(favorioutsAdapter);
            subjectiveAdapter.notifyDataSetChanged();
            specialsAdapter.notifyDataSetChanged();
            favorioutsAdapter.notifyDataSetChanged();
        }
        return view;
    }


    private void initUI() {

        naghdBTN = (ImageButton) view.findViewById(R.id.naghd_img_btn);
        subjective_recyler_view = (RecyclerView) view.findViewById(R.id.subjective_recyler_view);
        specials_recyler_view = (RecyclerView) view.findViewById(R.id.specials_recyler_view);
        favoriouts_recyler_view = (RecyclerView) view.findViewById(R.id.favoriouts_recyler_view);

        subjectiveLinearLayoutManager = new LinearLayoutManager(context);
        subjectiveLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        specialLinearLayoutManager = new LinearLayoutManager(context);
        specialLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        favorioutLinearLayoutManager = new LinearLayoutManager(context);
        favorioutLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        subjective_recyler_view.setLayoutManager(subjectiveLinearLayoutManager);
        specials_recyler_view.setLayoutManager(specialLinearLayoutManager);
        favoriouts_recyler_view.setLayoutManager(favorioutLinearLayoutManager);

        subjective_recyler_view.setHasFixedSize(true);
        specials_recyler_view.setHasFixedSize(true);
        favoriouts_recyler_view.setHasFixedSize(true);

    }

    private void initData() {
        try {
            Globals.ion.with(this).load(WebserviceUrl.GETFIRSTPAGE)
                    .as(SpecialList.class).setCallback(new FutureCallback<SpecialList>() {
                @Override
                public void onCompleted(Exception e, SpecialList specialList) {
                    if (Utils.isOnline(getContext())) {
                        SpecialBookList specialBookList = specialList.getResult();
                        if (e == null & specialBookList.getFarhangi().size() > 0) {
                            subjectiveAdapter = new SpecialSubjectiveAdapter(context, specialBookList.getFarhangi());
                            specialsAdapter = new SpecialAdapter(context, specialBookList.getSpecial());
                            favorioutsAdapter = new SpecialAdapter(context, specialBookList.getMahboob());

                            subjective_recyler_view.setAdapter(subjectiveAdapter);
                            specials_recyler_view.setAdapter(specialsAdapter);
                            favoriouts_recyler_view.setAdapter(favorioutsAdapter);
                            subjectiveAdapter.notifyDataSetChanged();
                            specialsAdapter.notifyDataSetChanged();
                            favorioutsAdapter.notifyDataSetChanged();

                            Globals.ion.with(naghdBTN).load(specialBookList.getNaghd().getImage());


                        }
                    } else {
                        Toast.makeText(context, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }

            }
            });
        }catch (Exception e){}

    }
}
