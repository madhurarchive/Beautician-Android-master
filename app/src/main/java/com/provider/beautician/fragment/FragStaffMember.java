package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.CustomViewPager;
import com.provider.beautician.model.StaffMemberDetailModel;

import java.util.ArrayList;
import java.util.List;


public class FragStaffMember extends Fragment implements View.OnClickListener {
    private Context                             mContext;
    private View                                rootView;
    private boolean                             mLoaded;

    private TabLayout                           mTabLayout;
    private CustomViewPager                     tabViewPager;
    private ViewPagerAdapter                    adapter;
    private ImageView imgBack,imgClose;
    private TextView txtHeader;

    public boolean isEdit = false;
    public StaffMemberDetailModel memberDetailModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView ==null){
            rootView = inflater.inflate(R.layout.frag_staff_member, container, false);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded){
            mLoaded     =   true;
            mContext    =   getActivity();
            initView();
            setUpViewPager();
        }
    }

    private void initView() {
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        imgClose            =   rootView.findViewById(R.id.toolbar2_imgMenu);
        txtHeader           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);

        if (isEdit){
            txtHeader.setText(CommonUtils.gettingString(R.string.edit_staff,mContext));
        }else {
            txtHeader.setText(CommonUtils.gettingString(R.string.new_staff,mContext));
        }

        imgBack.setVisibility(View.INVISIBLE);
        imgClose.setVisibility(View.VISIBLE);
        imgClose.setImageResource(R.drawable.ic_close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActHome)mContext).onBackPressed();
            }
        });

        mTabLayout              =   rootView.findViewById(R.id.sm_tab_layout);
        tabViewPager            =   rootView.findViewById(R.id.sm_view_pager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.smc_btn_save:
                break;

            case R.id.smc_btn_cancel:
                break;
        }
    }

    private void setUpViewPager(){
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        FragStaffMemberDetail fragStaffMemberDetail = new FragStaffMemberDetail();
        fragStaffMemberDetail.isEdit = isEdit;
        fragStaffMemberDetail.memberDetailModel = memberDetailModel;

        FragStaffMemberCommission fragStaffMemberCommission = new FragStaffMemberCommission();
        fragStaffMemberCommission.isEdit = isEdit;
        fragStaffMemberCommission.memberDetailModel = memberDetailModel;

        adapter.addFragment(fragStaffMemberDetail, CommonUtils.gettingString(R.string.details,mContext));
        //adapter.addFragment(new FragStaffMemberServices(), CommonUtils.gettingString(R.string.services,mContext));
        adapter.addFragment(fragStaffMemberCommission, CommonUtils.gettingString(R.string.commission,mContext));
        tabViewPager.setAdapter(adapter);
        tabViewPager.setPagingEnabled(false);
        mTabLayout.setupWithViewPager(tabViewPager);
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

}
