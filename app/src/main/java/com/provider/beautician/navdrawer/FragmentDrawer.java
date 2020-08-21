package com.provider.beautician.navdrawer;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.fragment.FragHome;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
public class FragmentDrawer extends Fragment implements View.OnClickListener{

    private static String TAG = FragmentDrawer.class.getSimpleName();

    private static RecyclerView recyclerView;
    public ActionBarDrawerToggle            mDrawerToggle;
    public DrawerLayout mDrawerLayout;
    private static NavigationDrawerAdapter  adapter;
    private View                            containerView;

    private static int[] titles_new = {R.string.nav_menu_1, R.string.nav_menu_2};
    private static int[] titles_new_LOGIN = {R.string.nav_menu_1, R.string.nav_menu_2};
    private static int[] arrSliderImgs = {R.mipmap.app_icon, R.mipmap.app_icon};
    private static int[] arrSliderSelImgs = {R.mipmap.app_icon, R.mipmap.app_icon};

    private FragmentDrawerListener drawerListener;
    private Toolbar toolbar;
    private static Context context;
    private static String StrTitlePreference;
    private ImageView ImgReedemDrawer;
    private TextView textViewProfrofileName, txtRewardsPointValue;

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();
        for (int i = 0; i < titles_new.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(context.getResources().getString(titles_new[i]));
            navItem.setImageId(arrSliderImgs[i]);
            navItem.setSelImageId(arrSliderSelImgs[i]);
            navItem.setIsHeader(false);
            if (i == 0) {
                navItem.setIsHeader(false);
                navItem.setSelected(true);
            }

            data.add(navItem);
        }
        return data;
    }



    public static List<NavDrawerItem> getDataOnLogin() {
        List<NavDrawerItem> data = new ArrayList<>();
        for (int i = 0; i < titles_new_LOGIN.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(context.getResources().getString(titles_new_LOGIN[i]));
            navItem.setImageId(arrSliderImgs[i]);
            navItem.setSelImageId(arrSliderSelImgs[i]);
            navItem.setIsHeader(false);
            if (i == 0) {
                navItem.setIsHeader(false);
                navItem.setSelected(true);
            }

            data.add(navItem);
        }
        return data;
    }


    public static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // drawer labels
        //titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        context = getActivity();
        View layout               =   inflater.inflate(R.layout.frag_profile_drawer, container, false);

        recyclerView              =   (RecyclerView) layout.findViewById(R.id.drawerList);

        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                mDrawerLayout.closeDrawer(containerView);
                System.out.println(position+"=====================Hello");
                drawerListener.onDrawerItemSelected(view, position);
                for (int i = 0; i < adapter.data.size(); i++) {
                    NavDrawerItem item = adapter.data.get(i);
                    item.setSelected(false);
                }
                NavDrawerItem item = adapter.data.get(position);


                //mDrawerLayout.closeDrawer(containerView);
                item.setSelected(true);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return layout;

    }

    public void refreshDataOnLogout(Context context){
        Log.i("RefreshDrawerCalled","Checked") ;
        adapter = new NavigationDrawerAdapter(context, getData());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActHome actHome = (ActHome)getActivity();
                if (actHome.getVisibleFragment() != null && actHome.getVisibleFragment() instanceof FragHome){
                    if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    } else {
                        mDrawerLayout.openDrawer(GravityCompat.START);
                    }
                }else{
                    actHome.onBackPressed();
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }


}

