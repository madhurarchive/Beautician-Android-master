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
import android.widget.TextView;

import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.AppointmentTabAdapter;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.model.ClientProfileTabModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragClientTabAppointment extends Fragment {
    private Context                             mContext;
    private View                                rootView;
    private boolean                             mLoaded;
    private RecyclerView                        mRecyclerView;
    private AppointmentTabAdapter               mAdapter;
    private ArrayList<ClientProfileTabModel>    mDataList = new ArrayList<ClientProfileTabModel>();
    private TextView                            txtTotalCount;

    public FragClientTabAppointment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView ==null)
        rootView = inflater.inflate(R.layout.frag_client_tab_appointment, container, false);
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
        if (mContext!=null){
            ((ActHome)mContext).setUpToolBar(this,"",true);
        }
    }

    private void initView() {
        mRecyclerView       =   rootView.findViewById(R.id.frag_client_tab_appointment_recyclerView);
        txtTotalCount       =   rootView.findViewById(R.id.frag_client_tab_appointment_txtTotalCount);
    }

    private void setUpRecycleView() {
        mDataList.add(new ClientProfileTabModel());
        mDataList.add(new ClientProfileTabModel());

        txtTotalCount.setText("Past ("+mDataList.size()+")");

        mAdapter = new AppointmentTabAdapter(mContext, mDataList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mAdapter.setOnRecyclerViewItemClickListener(new AppointmentTabAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                ((ActHome)mContext).switchContent(new FragClientProfile(),FragClientProfile.class.getSimpleName(),true);
            }
        });
    }
}
