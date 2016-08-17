package library.tebyan.com.teblibrary.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import library.tebyan.com.teblibrary.MainActivity;
import library.tebyan.com.teblibrary.R;
import library.tebyan.com.teblibrary.adapter.BooksCategoryAdapter;
import library.tebyan.com.teblibrary.classes.Utils;
import library.tebyan.com.teblibrary.classes.WebserviceUrl;
import library.tebyan.com.teblibrary.model.Category;
import library.tebyan.com.teblibrary.model.CategoryList;

/**
 * Created by v.karimi on 7/17/2016.
 */
public class HomeFragment extends Fragment implements MainActivity.InitFragment {

    public View view;
    public RecyclerView recyclerCategory;
    public BooksCategoryAdapter adapter;
    public ArrayList<Category> data;
    public LinearLayoutManager manager;
    public int visibleItemCount,pastVisiblesItems,pageIndex;
    private int totalItemCount;
    public MenuItem searchItemMenu;
    private String searchFile;
    boolean loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {



    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_layout,container,false);

        initUI();
        if(Utils.isTablet(getContext())) {
            getCategory(0,8);
        }else{
            getCategory(0,6);
        }
        return view;
    }


    private void initUI() {
        recyclerCategory= (RecyclerView) view.findViewById(R.id.recycler_category);
        manager=new LinearLayoutManager(getContext());
        recyclerCategory.setLayoutManager(manager);
        data=new ArrayList<>();
        adapter=new BooksCategoryAdapter(getContext(),data);
        recyclerCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerCategory.setHasFixedSize(true);
        adapter.setHandler(this);
        recyclerCategory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = manager.getChildCount();
                totalItemCount = manager.getItemCount();
                pastVisiblesItems = manager.findFirstVisibleItemPosition();
                if (!loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        pageIndex++;
                        /*progressBar.setVisibility(View.VISIBLE);*/
                        if(Utils.isTablet(getContext())) {
                            getCategory(pageIndex, 8);
                        }else{
                            getCategory(pageIndex, 6);
                        }
                        Log.v("...", "Last Item Wow !");
                    }
                }
            }
        });
    }
    private void getCategory(int count,int pageSize) {
        if (Utils.isOnline((MainActivity) getActivity())) {
            ((MainActivity) getActivity()).progressBar.setVisibility(View.VISIBLE);
            Ion.with(getActivity()).load(WebserviceUrl.GET_COLLECTIONS+"PageIndex="+count+"&PageSize2="+pageSize).as(CategoryList.class)
                    .setCallback(new FutureCallback<CategoryList>() {
                        @Override
                        public void onCompleted(Exception e, CategoryList test) {
                            Log.i("etgg", test + "");
                            data=test.getCategories();
                            adapter.items.addAll(test.getCategories());
                            adapter.notifyDataSetChanged();
                        }
                    });
        }else{
            Toast.makeText(getActivity(),getString(R.string.no_internet_connection),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void initFragment(int id, int type, String tag) {
        Fragment fragment = new BookListFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,
        //android.R.animator.fade_out);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        searchItemMenu = menu.findItem(R.id.action_search);
        final SearchManager searchManager = (SearchManager)getContext().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(searchItemMenu);
        searchView.setQueryHint(getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String text) {
                searchFile = text;
                Fragment fragment = new HomeFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("searchText", text);
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame, fragment, "search");
                fragmentTransaction.addToBackStack("search");
                fragmentTransaction.commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}
