package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.provider.beautician.CommonBean.JsonArrayResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.ClientsListAdapter;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.model.ClientModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragClients extends Fragment {

    private Context mContext;
    private View rootView;
    private boolean mLoaded;
    private RecyclerView mRecyclerView;
    private LinearLayout layoutNoClientFound;
    private ClientsListAdapter mAdapter;
    private ArrayList<ClientModel> mMenuList = new ArrayList<>();
    private ImageView imgBack, imgMenu;
    private TextView txtHeader,txtNoData;
    private BottomSheetDialog mBottom;
    private FloatingActionButton fabBtn;
    private String TAG = FragClients.class.getSimpleName();
    private EditText    edtSearch;
    public static FragClients       mInstance;

    public FragClients() {
        // Required empty public constructor
    }

    public static FragClients getInstance() {
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null)
            rootView = inflater.inflate(R.layout.frag_clients, container, false);
            mInstance = this;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded) {
            mLoaded = true;
            mContext = getActivity();
            initView();
            setUpRecycleView();
            getClientList();
        }
        if (mContext != null) {
            ((ActHome) mContext).setUpToolBar(this, "", false);
        }
    }

    private void initView() {
        imgBack = rootView.findViewById(R.id.toolbar2_imgBack);
        imgMenu = rootView.findViewById(R.id.toolbar2_imgMenu);
        txtHeader = rootView.findViewById(R.id.toolbar2_txtHeaderName);
        mRecyclerView = rootView.findViewById(R.id.frag_clients_recyclerView);
        txtNoData = rootView.findViewById(R.id.frag_client_txtNoData);
        fabBtn = rootView.findViewById(R.id.frag_clients_fabBtn);
        edtSearch = rootView.findViewById(R.id.frag_clients_editSearch);
        layoutNoClientFound = rootView.findViewById(R.id.layoutNoClientFound);

        txtHeader.setText(CommonUtils.gettingString(R.string.clients, mContext));
        imgBack.setVisibility(View.INVISIBLE);
        imgMenu.setVisibility(View.VISIBLE);
        imgMenu.setImageResource(R.drawable.ic_there_dots);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogHeaderAction();
            }
        });

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActHome) mContext).switchContent(new FragAddNewClient(), FragAddNewClient.class.getSimpleName(), true);
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (mAdapter == null) {
                    return;
                }
                // When user changed the Text
                mAdapter.getFilter().filter(cs);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

    }

    private void showDialogHeaderAction() {
        try {
            View sheetView = ((ActHome) mContext).getLayoutInflater().inflate(R.layout.bottom_clients_menu, null);
            mBottom = new BottomSheetDialog(mContext);
            mBottom.setContentView(sheetView);
            mBottom.show();
            FrameLayout bottomSheet = (FrameLayout) mBottom.findViewById(R.id.design_bottom_sheet);
            bottomSheet.setBackground(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpRecycleView() {
        mAdapter = new ClientsListAdapter(mContext, mMenuList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mAdapter.setOnRecyclerViewItemClickListener(new ClientsListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(ClientModel model) {
                FragClientProfile fragClientProfile = new FragClientProfile();
                fragClientProfile.model = model;
                ((ActHome) mContext).switchContent(fragClientProfile, FragClientProfile.class.getSimpleName(), true);
            }
        });
        mAdapter.setOnRecyclerViewFilterListener(new ClientsListAdapter.OnRecyclerViewFilterListener() {
            @Override
            public void onFilter() {
                if (mAdapter.getItemCount()>0){
                    layoutNoClientFound.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }else {
                    layoutNoClientFound.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
                Log.e(TAG,"getItemCount :: "+mAdapter.getItemCount());
            }
        });
    }

    public void updateList(){
        mMenuList.clear();
        getClientList();
    }

    private void getClientList() {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);
            ApiClient.getClient().getClientList(CommonUtils.getSaloonId(mContext)).enqueue(new Callback<JsonArrayResponse<ClientModel>>() {
                @Override
                public void onResponse(Call<JsonArrayResponse<ClientModel>> call, Response<JsonArrayResponse<ClientModel>> response) {
                    CommonUtils.dismissLoader();
                    if (response.code() == 200 && response.body() != null) {
                        Log.e(TAG, "url       ===== " + call.request().url());
                        Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            mMenuList.addAll(response.body().getData());
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
                public void onFailure(Call<JsonArrayResponse<ClientModel>> call, Throwable t) {
                    CommonUtils.showSnackbarWithoutView(getString(R.string.default_error), mContext, 0);
                    Log.e(TAG, "Throwable === " + t.getMessage());
                }
            });
        } else {
            CommonUtils.showSnackbarWithoutView(getString(R.string.no_network_error), mContext, 0);
        }
    }
}
