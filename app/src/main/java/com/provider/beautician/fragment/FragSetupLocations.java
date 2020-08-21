package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.provider.beautician.CommonBean.JsonObjectResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.BusinessListAdapter;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.helpers.SimpleDividerItemDecoration;
import com.provider.beautician.model.StaffLocationModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragSetupLocations extends Fragment {
    private Context                     mContext;
    private View                        rootView;
    private boolean                     mLoaded;
    private FloatingActionButton        mFabBtn;
    private RecyclerView                mRecyclerView;
    private BusinessListAdapter         mAdapter;
    private ArrayList<StaffLocationModel>    mMenuList = new ArrayList<>();
    private BottomSheetDialog           mBottom;
    private ImageView imgBack;
    private TextView                    txtHeader,txtNoData;
    private String                      TAG = FragSetupLocations.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView==null){
            rootView = inflater.inflate(R.layout.frag_locations, container, false);
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
            setUpRecycleView();
            getSaloonLocation();
        }
    }

    private void initView() {
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        txtHeader           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);
        mFabBtn             =   rootView.findViewById(R.id.location_fab_add_new);
        mRecyclerView       =   rootView.findViewById(R.id.location_rv_business);
        txtNoData           =   rootView.findViewById(R.id.frag_locations_txtNoData);

        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActHome)mContext).switchContent(new FragAddLocations(),FragAddLocations.class.getSimpleName(),true);
            }
        });
        txtHeader.setText(CommonUtils.gettingString(R.string.setup_menu_2,mContext));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActHome)mContext).onBackPressed();
            }
        });
    }

    private void setUpRecycleView() {
        mAdapter = new BusinessListAdapter(mContext, mMenuList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        mAdapter.setOnRecyclerViewItemClickListener(new BusinessListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                ((ActHome)mContext).switchContent(new FragAddLocations(),FragAddLocations.class.getSimpleName(),true);
            }
        });
        mAdapter.setOnRecyclerViewMenuOptionClickListener(new BusinessListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                showMenuOptionDialog();
            }
        });
    }

    private void showMenuOptionDialog() {
        try {
            mBottom = new BottomSheetDialog(mContext);
            View sheetView = ((ActHome)mContext).getLayoutInflater().inflate(R.layout.dialog_location_list_menu_options, null);
            TextView btnEdit = sheetView.findViewById(R.id.dialog_btn_edit);
            TextView btnDelete = sheetView.findViewById(R.id.dialog_btn_delete);
            TextView btnClose = sheetView.findViewById(R.id.dialog_btn_close);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"Edit btn clicked",Toast.LENGTH_SHORT).show();
                    mBottom.dismiss();
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"Delete btn clicked",Toast.LENGTH_SHORT).show();
                    mBottom.dismiss();
                }
            });

            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"Close btn clicked",Toast.LENGTH_SHORT).show();
                    mBottom.dismiss();
                }
            });

            mBottom.setContentView(sheetView);
            mBottom.show();
            FrameLayout bottomSheet = mBottom.findViewById(R.id.design_bottom_sheet);
            bottomSheet.setBackground(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSaloonLocation(){
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);
            ApiClient.getClient().getSaloonLocation(CommonUtils.getSaloonId(mContext)).enqueue(new Callback<JsonObjectResponse<StaffLocationModel>>() {
                @Override
                public void onResponse(Call<JsonObjectResponse<StaffLocationModel>> call, Response<JsonObjectResponse<StaffLocationModel>> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200 && response.body()!=null) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            mMenuList.add(response.body().getObject());
                            mAdapter.notifyDataSetChanged();
                            if (mMenuList.size()<=0){
                                mRecyclerView.setVisibility(View.GONE);
                                txtNoData.setVisibility(View.VISIBLE);
                            }else {
                                mRecyclerView.setVisibility(View.VISIBLE);
                                txtNoData.setVisibility(View.GONE);
                            }
                        } else {
                            String statusCode = response.body().getMessage();
                            MessageStatusCode.showErrorMessageByStatusCode(statusCode, mContext);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObjectResponse<StaffLocationModel>> call, Throwable t) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "OnFailure === " + t.getMessage());
                    CommonUtils.showSnackbarWithoutView(getString(R.string.default_error), mContext, 0);
                }
            });
        } else {
            CommonUtils.showSnackbarWithoutView(getString(R.string.no_network_error), mContext, 0);
        }
    }
}
