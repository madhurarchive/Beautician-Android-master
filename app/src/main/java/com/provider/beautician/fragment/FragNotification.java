package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragNotification extends Fragment {
    private Context                     mContext;
    private View                        rootView;
    private boolean                     mLoaded;
    private TabLayout                   mTabLayout;
    private CustomViewPager             tabViewPager;
    private ViewPagerAdapter            adapter;
    private TextView                    txtHeaderName;
    private ImageView                   imgBack;

    public FragNotification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView==null)
        rootView = inflater.inflate(R.layout.frag_notification, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded){
            mLoaded = true;
            mContext = getActivity();
            initView();
            setUpViewPager();
        }
        if (mContext!=null){
             ((ActHome)mContext).setUpToolBar(this,"",false);
        }
    }

    private void initView() {
        imgBack                 =   rootView.findViewById(R.id.toolbar2_imgBack);
        txtHeaderName           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);
        mTabLayout              =   rootView.findViewById(R.id.frag_notification_TabLayout);
        tabViewPager            =   rootView.findViewById(R.id.frag_notification_viewPager);

        imgBack.setVisibility(View.INVISIBLE);
        txtHeaderName.setText(CommonUtils.gettingString(R.string.notifications,mContext));
    }

    private void setUpViewPager(){

        adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new FragActivity(), CommonUtils.gettingString(R.string.activity,mContext));
        adapter.addFragment(new FragNews(), CommonUtils.gettingString(R.string.news,mContext));
        tabViewPager.setPagingEnabled(true);
        tabViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(tabViewPager);
        tabViewPager.addOnPageChangeListener(onPageChangeListener);
        tabViewPager.setCurrentItem(0);
    }

    static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

        public void addFragment(Fragment fragment, String title) {
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
     /*       Fragment fragment = adapter.getItem(position);
            ((Theme2_Frag_Review)fragment).callReviewApi();*/
        }

        @Override
        public void onPageScrollStateChanged(int position) {

        }
    };

}
