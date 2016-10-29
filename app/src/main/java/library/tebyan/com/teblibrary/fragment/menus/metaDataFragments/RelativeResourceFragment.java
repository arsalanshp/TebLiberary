package library.tebyan.com.teblibrary.fragment.menus.metaDataFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import library.tebyan.com.teblibrary.R;

//import com.a7learn.mahdieh.myfragment.R;
//import com.a7learn.mahdieh.myfragment.adapter.AdapterRelativeCardView;


/**
 * Created by m.mohamadzadeh on 10/26/2016.
 */

public class RelativeResourceFragment extends Fragment {
    RecyclerView recyclerView ;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.relative_resource_fragment,container,false);

        // in 4 line
//        recyclerView = (RecyclerView) view.findViewById(R.id.realtive_recyclerView);
//        AdapterRelativeCardView adapter = new AdapterRelativeCardView(this.getContext());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }
}






