package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.provider.beautician.R;
import com.provider.beautician.adapter.StaffMemberServicesAdapter;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.model.ServicesListModel;

import java.util.ArrayList;


public class FragStaffMemberServices extends Fragment implements View.OnClickListener {
    private Context                             mContext;
    private View                                rootView;
    private boolean                             mLoaded;

    private RecyclerView                        rvServices;
    private Button                              btnSave,btnCancel;
    private StaffMemberServicesAdapter          mAdapter;
    private ArrayList<ServicesListModel>        mServicesListModelArrayList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView ==null){
            rootView = inflater.inflate(R.layout.frag_staff_member_services, container, false);
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
            addStaticData();
            setUpRecyclerView();
        }
    }

    private void addStaticData(){
        ServicesListModel servicesListModel = new ServicesListModel();
        servicesListModel.setId("all");
        servicesListModel.setName("Select All");
        servicesListModel.setChecked(false);
        mServicesListModelArrayList.add(servicesListModel);

        servicesListModel = new ServicesListModel();
        servicesListModel.setId("1");
        servicesListModel.setName("Blow Dry");
        servicesListModel.setChecked(false);
        mServicesListModelArrayList.add(servicesListModel);

        servicesListModel = new ServicesListModel();
        servicesListModel.setId("2");
        servicesListModel.setName("Hair Cut");
        servicesListModel.setChecked(false);
        mServicesListModelArrayList.add(servicesListModel);
    }

    private void setUpRecyclerView() {
        mAdapter = new StaffMemberServicesAdapter(mContext, mServicesListModelArrayList);
        rvServices.setAdapter(mAdapter);
        rvServices.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter.setOnRecyclerViewItemClickListener(new StaffMemberServicesAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {

            }
        });
    }

    private void initView() {
        rvServices          =   rootView.findViewById(R.id.sms_rv_services);
        btnSave             =   rootView.findViewById(R.id.sms_btn_save);
        btnCancel           =   rootView.findViewById(R.id.sms_btn_cancel);

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sms_btn_save:
                break;

            case R.id.sms_btn_cancel:
                break;
        }
    }
}
