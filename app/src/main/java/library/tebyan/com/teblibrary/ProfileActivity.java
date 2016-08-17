package library.tebyan.com.teblibrary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.navigationtabbar.ntb.NavigationTabBar;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.DialogSelectImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private DialogSelectImage dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ImageButton profileImageView = (ImageButton) findViewById(R.id.profileImg);
        profileImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                DialogSelectImage.imageType = "Profile";
                AddImageForProfile(arg0);
            }
        });
        initProfilePageView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == DialogSelectImage.PICK_IMAGE_FROM_GALLERY
                    || requestCode == DialogSelectImage.TAKE_IMAGE_CAMERA ||
                    requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                try {
                    dialog.userSelectImage(requestCode, resultCode, data);
                    String userSelectedImagePath = dialog.getSelectedImagePath();
                    Bitmap userSelectedImage = dialog.getSelectedImage();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(this,
                            getString(R.string.problem_on_selecting_image),
                            Toast.LENGTH_SHORT).show();
                }

                File file = new File(Environment.getExternalStorageDirectory()
                        + "/Tebyan/01.jpg");
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initProfilePageView() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final View view = LayoutInflater.from(
                        getBaseContext()).inflate(R.layout.profile_tab_list, null, false);

                final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(
                                getBaseContext(), LinearLayoutManager.VERTICAL, false
                        )
                );
                if (position == 2) {
                    Toast.makeText(getApplicationContext(), "ggggg", Toast.LENGTH_LONG).show();
                    recyclerView.setAdapter(new RecycleAdapter());
                }
//                recyclerView.setAdapter();

                container.addView(view);
                return view;
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        int greenColor = getResources().getColor(R.color.profileTabColor);
        String tabColor = "#" + Integer.toHexString(greenColor);


        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_first),
                        Color.parseColor(colors[1]))
                        .title("علاقه مندی ها")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_second),
                        Color.parseColor(colors[1]))
                        .title(getString(R.string.book_readed))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_third),
                        Color.parseColor(tabColor))
                        .title(getString(R.string.book_mark))
                        .build()
        );
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 2);
        navigationTabBar.post(new Runnable() {
            @Override
            public void run() {
                final View viewPager = findViewById(R.id.vp_horizontal_ntb);
                ((ViewGroup.MarginLayoutParams) viewPager.getLayoutParams()).topMargin =
                        (int) -navigationTabBar.getBadgeMargin();
                viewPager.requestLayout();
            }
        });
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                if (index == 2) {
                    Toast.makeText(getApplicationContext(), "hhghg", Toast.LENGTH_LONG).show();
                }
                model.hideBadge();
            }
        });
        /*findViewById(R.id.mask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final String title = String.valueOf(new Random().nextInt(15));
                            if (!model.isBadgeShowed()) {
                                model.setBadgeTitle(title);
                                model.showBadge();
                            } else model.updateBadgeTitle(title);
                        }
                    }, i * 100);
                }
            }
        });*/
    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.profile_tab_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.txt.setText(String.format("Navigation Item #%d", position));
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView txt;

            public ViewHolder(final View itemView) {
                super(itemView);
                txt = (TextView) itemView.findViewById(R.id.txt_vp_item_list);
            }
        }
    }

    public void AddImageForProfile(View v) {
        dialog = new DialogSelectImage(this);
        dialog.show();
    }
}