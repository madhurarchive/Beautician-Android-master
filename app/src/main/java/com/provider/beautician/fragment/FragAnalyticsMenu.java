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
public class FragAnalyticsMenu extends Fragment {
    private Context                     mContext;
    private View                        rootView;
    private boolean                     mLoaded;
    private RecyclerView                mRecyclerView;
    private AnalyticsListAdapter        mAdapter;
    private ArrayList<ListDataModel>    mMenuList = new ArrayList<ListDataModel>();
    private ImageView                   imgBack;
    private TextView                    txtHeader;
    public FragAnalyticsMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView==null)
        rootView = inflater.inflate(R.layout.frag_analytics, container, false);
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
        if (mContext != null) {
            ((ActHome) mContext).setUpToolBar(this, "",true);
        }
    }

    private void initView() {
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        txtHeader           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);
        mRecyclerView       =   rootView.findViewById(R.id.frag_analytics_recyclerView);

        txtHeader.setText(CommonUtils.gettingString(R.string.analytics,mContext));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActHome)mContext).onBackPressed();
            }
        });
    }

    private void setUpRecycleView() {
        mMenuList.add(new ListDataModel(gettingString(R.string.dashboard),gettingString(R.string.dashboard_sub_heading), R.mipmap.img_analytics_dashboard));
        mMenuList.add(new ListDataModel(gettingString(R.string.reports),gettingString(R.string.reports_sub_heading), R.mipmap.img_analytics_reports));
        mAdapter = new AnalyticsListAdapter(mContext, mMenuList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mAdapter.setOnRecyclerViewItemClickListener(new AnalyticsListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {

            }
        });
    }

    private String gettingString(int resId){
        return mContext.getResources().getString(resId);
    }
}
