package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.provider.beautician.CommonBean.JsonArrayResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.ClosedDatesAdapter;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.model.ClosedDateModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragStaffCloseDate extends Fragment implements View.OnClickListener{
    private Context                             mContext;
    private View                                rootView;
    private boolean                             mLoaded;
    private ImageView                           imgAdd;
    private ImageView                           imgBack;
    private TextView                            txtHeader,txtNoData;
    private RecyclerView                        mRecyclerView;
    private ClosedDatesAdapter                  mAdapter;
    private ArrayList<ClosedDateModel>          mArrayList = new ArrayList<>();
    private static FragStaffCloseDate           mInstance;
    private LinearLayout                        layoutRoot;

    private String TAG = FragStaffCloseDate.class.getSimpleName();
    private String salonId;

    public static FragStaffCloseDate getInstance() {
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.frag_staff_close_date, container, false);
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
            callGetCloseDatesBySaloonIdApi();
        }
    }

    private void initView() {

        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        txtHeader           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);
        layoutRoot          =   rootView.findViewById(R.id.frag_staff_close_date_root);
        txtNoData           =   rootView.findViewById(R.id.frag_staff_close_date_txtNoData);

        txtHeader.setText(CommonUtils.gettingString(R.string.closed_dates,mContext));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActHome)mContext).onBackPressed();
            }
        });

        imgAdd          =   rootView.findViewById(R.id.frag_staff_close_date_img_add);
        mRecyclerView   =   rootView.findViewById(R.id.frag_staff_close_date_recyclerView);
        imgAdd.setOnClickListener(this);
    }

    private void setUpRecycleView() {
        mAdapter = new ClosedDatesAdapter(mContext, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mAdapter.setOnRecyclerViewItemClickListener(new ClosedDatesAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                FragAddCloseDates fragAddCloseDates = new FragAddCloseDates();
                fragAddCloseDates.isEdit = true;
                fragAddCloseDates.closedDateModel = mArrayList.get(position);
                ((ActHome) mContext).switchContent(fragAddCloseDates, FragAddCloseDates.class.getSimpleName(), true);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.frag_staff_close_date_img_add) {
            ((ActHome) mContext).switchContent(new FragAddCloseDates(), FragAddCloseDates.class.getSimpleName(), true);
        }
    }

    private void callGetCloseDatesBySaloonIdApi() {
        if (CommonUtils.isConnectingToInternet(mContext)){
            CommonUtils.showLoader(mContext);

            ApiClient.getClient().getCloseDatesBySaloonId(salonId).enqueue(new Callback<JsonArrayResponse<ClosedDateModel>>() {
                @Override
                public void onResponse(Call<JsonArrayResponse<ClosedDateModel>> call, Response<JsonArrayResponse<ClosedDateModel>> response) {
                    CommonUtils.dismissLoader();
                    if (response.code() == 200 && response.body()!=null) {
                        Log.e(TAG,"url       ===== " + call.request().url());
                        Log.e(TAG,"response  ===== " + new Gson().toJson(response.body()));

                        if(response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)){
                            updateListData((ArrayList<ClosedDateModel>) response.body().getData());
                        } else {
                            String statusCode = response.body().getMessage();
                            MessageStatusCode.showErrorMessageByStatusCode(statusCode,mContext);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonArrayResponse<ClosedDateModel>> call, Throwable t) {
                    CommonUtils.showSnackbarWithoutView(getString(R.string.default_error),mContext, 0);
                    Log.e(TAG,"Throwable === " + t.getMessage());
                }
            });
        } else {
            CommonUtils.showSnackbarWithoutView(getString(R.string.no_network_error),mContext, 0);
        }
    }

    public void refreshData(){
        mArrayList.clear();
        callGetCloseDatesBySaloonIdApi();
    }

    public void updateListData(ArrayList<ClosedDateModel> closedDateModelArrayList){
        mArrayList.clear();
        mArrayList.addAll(closedDateModelArrayList);
        mAdapter.notifyDataSetChanged();

        if (mArrayList.size()<=0){
            layoutRoot.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }else {
            layoutRoot.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
        }
    }

}
