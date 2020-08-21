package com.provider.beautician.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.provider.beautician.CommonBean.JsonArrayResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.ListDataAdapter;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.app.MyAndroidApp;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.model.ListDataModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragList extends Fragment {
    private Context                         mContext;
    private View                            rootView;
    private boolean                         mAlreadyLoaded;

    private RecyclerView mRecyclerViewProduct;
    private TextView                        txtProductListNoRecord;
    private ShimmerFrameLayout              mShimmerViewContainer;
    private SwipeRefreshLayout mPullToRefresh;

    private NpaLinearLayoutManager          mLayoutManager;
    private ListDataAdapter                 mAdapter;
    private ArrayList<ListDataModel>        mDataModelArrayList = new ArrayList<ListDataModel>();

    private boolean                         loading = true;
    private int                             firstVisibleItems, visibleItemCount, totalItemCount;
    private String                          TAG = FragList.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.frag_list, container, false);
        }
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!mAlreadyLoaded) {
            mContext        =   getActivity();
            mAlreadyLoaded  =   true;

            initViews();
            setUpRecycleView();
            setUpLoadMoreListener();
        }

        if (mContext != null){
            ((ActHome)mContext).setUpToolBar(new FragList(),"List",false);
        }
    }


    private void initViews() {
        mRecyclerViewProduct    =   (RecyclerView) rootView.findViewById(R.id.recyclerViewOrderList);
        mShimmerViewContainer   =   (ShimmerFrameLayout) rootView.findViewById(R.id.shimmer_view_container_product);
        txtProductListNoRecord  =   (TextView) rootView.findViewById(R.id.txtProductListNoRecord);
        mPullToRefresh          =   (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshOrderList);

        txtProductListNoRecord.setTypeface(MyAndroidApp.getInstance().getBoldFont());

        mPullToRefresh.setOnRefreshListener(onRefreshListener);
    }

    private void setUpRecycleView(){
        mLayoutManager   =   new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
        mAdapter         =   new ListDataAdapter (mContext, mDataModelArrayList);
        mRecyclerViewProduct.setAdapter(mAdapter);
        mRecyclerViewProduct.setLayoutManager(mLayoutManager);

        mAdapter.setOnRecyclerViewItemClickListener(new ListDataAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {

            }
        });
    }

    private void setUpLoadMoreListener(){
        mRecyclerViewProduct.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    firstVisibleItems   =   mLayoutManager.findFirstVisibleItemPosition();
                    visibleItemCount    =   mLayoutManager.getChildCount();
                    totalItemCount      =   mLayoutManager.getItemCount();

                    if(firstVisibleItems +visibleItemCount == totalItemCount && totalItemCount!=0)
                    {
                        if(!loading)
                        {
                            loading = true;
                            mDataModelArrayList.add(null);
                            recyclerView.post(new Runnable() {
                                public void run() {
                                    mAdapter.notifyItemInserted(mDataModelArrayList.size() - 1);
                                }
                            });
                            callGetProductListApiApi(String.valueOf(mDataModelArrayList.size()));
                        }
                    }
                }
            }
        });
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            mDataModelArrayList.clear();
            callGetProductListApiApi("0");
        }
    };

    private void callGetProductListApiApi(String startLimit) {
        if (CommonUtils.isConnectingToInternet(mContext)){
            mShimmerViewContainer.startShimmer();

            if (mRecyclerViewProduct.getVisibility() == View.GONE){
                mRecyclerViewProduct.setVisibility(View.VISIBLE);
                txtProductListNoRecord.setVisibility(View.GONE);
            }

            ApiClient.getClient().getListApi(startLimit).enqueue(new Callback<JsonArrayResponse<ListDataModel>>() {
                @Override
                public void onResponse(Call<JsonArrayResponse<ListDataModel>> call, Response<JsonArrayResponse<ListDataModel>> response) {
                    CommonUtils.dismissLoader();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    mPullToRefresh.setRefreshing(false);

                    if (mDataModelArrayList != null && mDataModelArrayList.size() > 0) {
                        mDataModelArrayList.remove(mDataModelArrayList.size() - 1);
                        mAdapter.notifyItemRemoved(mDataModelArrayList.size());
                    }

                    if (response.code() == 200) {
                        Log.e(TAG,"url       ===== " + call.request().url());
                        Log.e(TAG,"response  ===== " + new Gson().toJson(response.body()));

                        if(response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)){
                            mDataModelArrayList.addAll(response.body().getData());
                            loading = false;
                            mAdapter.notifyDataSetChanged();
                        } else {
                            String statusCode = response.body().getMessage();
                            if (statusCode.equals("no_record_found")){
                                mRecyclerViewProduct.setVisibility(View.GONE);
                                txtProductListNoRecord.setVisibility(View.VISIBLE);
                            } else {
                                MessageStatusCode.showErrorMessageByStatusCode(statusCode,mContext);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonArrayResponse<ListDataModel>> call, Throwable t) {
                    mShimmerViewContainer.setVisibility(View.GONE);
                    mPullToRefresh.setRefreshing(false);
                    CommonUtils.showSnackbarWithoutView(getString(R.string.default_error),mContext, 0);
                }
            });
        } else {
            CommonUtils.showSnackbarWithoutView(getString(R.string.no_network_error),mContext, 0);
        }
    }

}
