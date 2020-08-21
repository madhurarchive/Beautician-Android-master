package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.provider.beautician.CommonBean.JsonArrayResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.StaffMemberMainAdapter;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.helpers.SimpleItemTouchHelperCallback;
import com.provider.beautician.listners.OnStartDragListener;
import com.provider.beautician.model.StaffMemberDetailModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragStaffMemberMain extends Fragment implements View.OnClickListener, OnStartDragListener {
    private Context                             mContext;
    private View                                rootView;
    private boolean                             mLoaded;
    private ImageView                           imgAdd;
    private ImageView                           imgBack;
    private TextView                            txtHeader,txtNoData;
    private LinearLayout                        llMemberContainer;
    private RecyclerView                        mRecyclerView;
    private StaffMemberMainAdapter              mAdapter;
    private ArrayList<StaffMemberDetailModel>   mArrayList = new ArrayList<>();
    private ItemTouchHelper mItemTouchHelper;
    private String salonId;
    private static FragStaffMemberMain mInstance;
    private String TAG = FragStaffMemberMain.class.getSimpleName();

    public static FragStaffMemberMain getInstance() {
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView ==null){
            rootView = inflater.inflate(R.layout.frag_staff_member_main, container, false);
        }
        mInstance = this;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded){
            mLoaded     =   true;
            mContext    =   getActivity();
            salonId     =   CommonUtils.getSaloonId(mContext);
            initView();
            setUpRecycleView();
            callGetMyStaffApi();
        }
    }

    private void initView() {

        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        txtHeader           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);

        txtHeader.setText(CommonUtils.gettingString(R.string.staff_members,mContext));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActHome)mContext).onBackPressed();
            }
        });

        imgAdd              =   rootView.findViewById(R.id.sm_img_add);
        mRecyclerView       =   rootView.findViewById(R.id.frag_staff_member_main_recyclerView);
        llMemberContainer   =   rootView.findViewById(R.id.sm_member_container);
        txtNoData           =   rootView.findViewById(R.id.sm_txtNoData);

        imgAdd.setOnClickListener(this);
    }

    private void setUpRecycleView() {
        mAdapter = new StaffMemberMainAdapter(mContext, mArrayList,this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mAdapter.setOnRecyclerViewItemClickListener(new StaffMemberMainAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                FragStaffMember fragStaffMember = new FragStaffMember();
                fragStaffMember.isEdit = true;
                fragStaffMember.memberDetailModel = mArrayList.get(position);
                ((ActHome)mContext).switchContent(fragStaffMember,FragStaffMember.class.getSimpleName(),true);
            }
        });
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sm_img_add) {
            ((ActHome) mContext).switchContent(new FragStaffMember(), FragStaffMember.class.getSimpleName(), true);
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public void callGetMyStaffApi() {
        if (CommonUtils.isConnectingToInternet(mContext)){
            CommonUtils.showLoader(mContext);

            ApiClient.getClient().getMyStaff(salonId).enqueue(new Callback<JsonArrayResponse<StaffMemberDetailModel>>() {
                @Override
                public void onResponse(Call<JsonArrayResponse<StaffMemberDetailModel>> call, Response<JsonArrayResponse<StaffMemberDetailModel>> response) {
                    CommonUtils.dismissLoader();
                    if (response.code() == 200 && response.body()!=null) {
                        Log.e(TAG,"url       ===== " + call.request().url());
                        Log.e(TAG,"response  ===== " + new Gson().toJson(response.body()));

                        if(response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)){
                            updateListData((ArrayList<StaffMemberDetailModel>) response.body().getData());
                        } else {
                            String statusCode = response.body().getMessage();
                            MessageStatusCode.showErrorMessageByStatusCode(statusCode,mContext);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonArrayResponse<StaffMemberDetailModel>> call, Throwable t) {
                    CommonUtils.showSnackbarWithoutView(getString(R.string.default_error),mContext, 0);
                    Log.e(TAG,"Throwable === " + t.getMessage());
                }
            });
        } else {
            CommonUtils.showSnackbarWithoutView(getString(R.string.no_network_error),mContext, 0);
        }
    }

    private void updateListData(ArrayList<StaffMemberDetailModel> staffMemberDetailModelArrayList){
        mArrayList.clear();
        mArrayList.addAll(staffMemberDetailModelArrayList);
        mAdapter.notifyDataSetChanged();
        if (mArrayList.size()>0){
            llMemberContainer.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
        }else {
            llMemberContainer.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

}
