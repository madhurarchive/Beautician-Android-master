package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.provider.beautician.R;
import com.provider.beautician.helpers.CustomViewPagerWithScroll;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragHomeSlider extends Fragment implements View.OnClickListener{

    private Context                     mContext;
    private View                        rootView;
    private boolean                     mAlreadyLoaded;
    private ViewPagerAdapter            adapter;
    private CustomViewPagerWithScroll   customViewPager;
    private LinearLayout                linearDotsAdItem,layoutContinue;
    private int                         count=0;
    private ImageView                   imgBack;

    public FragHomeSlider() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView==null)
        rootView = inflater.inflate(R.layout.frag_home_slider, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null && !mAlreadyLoaded) {
            mContext         =   getActivity();
            mAlreadyLoaded   =    true;
            initViews();
            setCustomeViewPager();
        }
    }

    private void initViews() {
        customViewPager     =  rootView.findViewById(R.id.frag_home_slider_viewPager);
        linearDotsAdItem    =   rootView.findViewById(R.id.frag_home_slider_dots_item);
        layoutContinue      =   rootView.findViewById(R.id.frag_home_slider_layoutContinue);
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);

        imgBack.setVisibility(View.GONE);

        layoutContinue.setOnClickListener(this);
        imgBack.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            customViewPager.setNestedScrollingEnabled(false);
        }
    }


    public void ChangePage(int count){
        customViewPager.setCurrentItem(count);
        addBottomDots(count);
    }

    private void setCustomeViewPager() {

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragSlider1(), "FragSlider1");
        adapter.addFragment(new FragSlider2(), "FragSlider2");
        adapter.addFragment(new FragSlider3(), "FragSlider3");
        adapter.addFragment(new FragSliderFinal(), "FragSliderFinal");

        customViewPager.setPagingEnabled(true);
        customViewPager.setAdapter(adapter);
        customViewPager.addOnPageChangeListener(onPageChangeListener);
        customViewPager.setOffscreenPageLimit(1);
        addBottomDots(0);
    }

    private void addBottomDots(int currentPage) {
        ImageView[] dots = new ImageView[adapter.getCount()];
        linearDotsAdItem.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(mContext);
            dots[i].setImageResource(R.drawable.default_dot);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            layoutParams.setMarginEnd(10);
            layoutParams.setMarginStart(10);
            dots[i].setLayoutParams(layoutParams);
            linearDotsAdItem.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setImageResource(R.drawable.selected_dot);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frag_home_slider_layoutContinue:
                if (count<3){
                    count++;
                    ChangePage(count);
                }
                break;
            case R.id.toolbar2_imgBack:
                if (count>0){
                    count--;
                    ChangePage(count);
                }
                break;
        }
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            Fragment fragment = adapter.getItem(position);
            addBottomDots(position);
            count=position;
            imgBack.setVisibility(View.VISIBLE);
            layoutContinue.setVisibility(View.VISIBLE);
            Log.e(FragHomeSlider.class.getSimpleName(),"Position === "+position);
            if (position==0){
                imgBack.setVisibility(View.GONE);
            }if (position ==3){
                layoutContinue.setVisibility(View.GONE);
            }
            if (fragment != null) {
                /*if (position == 0 && PreferenceConnector.readBoolean(mContext,PreferenceConnector.KEY_IS_PENDING_ORDER_LIST_MODIFIED,false)){
                    ((FragClientOrderDetails)fragment).refreshData();
                    PreferenceConnector.writeBoolean(mContext,PreferenceConnector.KEY_IS_PENDING_ORDER_LIST_MODIFIED,false);
                } else if (position == 1 && PreferenceConnector.readBoolean(mContext,PreferenceConnector.KEY_IS_CONFIRM_ORDER_LIST_MODIFIED,false)){
                    ((FragClientOrderDetails)fragment).refreshData();
                    PreferenceConnector.writeBoolean(mContext,PreferenceConnector.KEY_IS_CONFIRM_ORDER_LIST_MODIFIED,false);
                } else if (position == 2 && PreferenceConnector.readBoolean(mContext,PreferenceConnector.KEY_IS_COMPLETE_ORDER_LIST_MODIFIED,false)){
                    ((FragClientOrderDetails)fragment).refreshData();
                    PreferenceConnector.writeBoolean(mContext,PreferenceConnector.KEY_IS_COMPLETE_ORDER_LIST_MODIFIED,false);
                }*/
            }
        }

        @Override
        public void onPageScrollStateChanged(int position) {

        }
    };
}
