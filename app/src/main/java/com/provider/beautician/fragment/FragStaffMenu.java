package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.AnalyticsListAdapter;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.model.ListDataModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragStaffMenu extends Fragment {
    private Context                     mContext;
    private View                        rootView;
    private boolean                     mLoaded;
    private RecyclerView                mRecyclerView;
    private AnalyticsListAdapter        mAdapter;
    private ArrayList<ListDataModel>    mMenuList = new ArrayList<ListDataModel>();
    private ImageView                   imgBack;
    private TextView                    txtHeader;

    public FragStaffMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView==null)
        rootView = inflater.inflate(R.layout.frag_staff_menu, container, false);
        return rootView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded){
            mLoaded = true;
            mContext = getActivity();
            initView();
            setUpRecycleView();
        }
    }

    private void initView() {
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        txtHeader           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);
        mRecyclerView       =   rootView.findViewById(R.id.frag_staff_recyclerView);

        txtHeader.setText(CommonUtils.gettingString(R.string.staff,mContext));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActHome)mContext).onBackPressed();
            }
        });
    }

    private void setUpRecycleView() {
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.staff_working_hours,mContext),CommonUtils.gettingString(R.string.staff_working_hours_sub_heading,mContext), R.mipmap.img_working_hour));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.closed_dates,mContext),CommonUtils.gettingString(R.string.closed_dates_sub_heading,mContext), R.mipmap.img_calander));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.staff_members,mContext),CommonUtils.gettingString(R.string.staff_members_sub_heading,mContext), R.mipmap.img_staff_member));
        //mMenuList.add(new ListDataModel("Use permission","Set access permissions for sensitive features and information", R.drawable.ic_home));
        mAdapter = new AnalyticsListAdapter(mContext, mMenuList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mAdapter.setOnRecyclerViewItemClickListener(new AnalyticsListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                    if (mMenuList.get(position).getHeader().equals(CommonUtils.gettingString(R.string.staff_working_hours,mContext))){
                        ((ActHome)mContext).switchContent(new FragStaffWorkingHours(),FragStaffWorkingHours.class.getSimpleName(),true);
                    }else if (mMenuList.get(position).getHeader().equals(CommonUtils.gettingString(R.string.staff_members,mContext))){
                        ((ActHome)mContext).switchContent(new FragStaffMemberMain(),FragStaffMemberMain.class.getSimpleName(),true);
                    }else if (mMenuList.get(position).getHeader().equals(CommonUtils.gettingString(R.string.closed_dates,mContext))){
                        ((ActHome)mContext).switchContent(new FragStaffCloseDate(),FragStaffCloseDate.class.getSimpleName(),true);
                    }
            }
        });
    }
}
