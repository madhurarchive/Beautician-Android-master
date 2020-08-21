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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.provider.beautician.CommonBean.JsonObjectResponse;
import com.provider.beautician.CommonBean.JsonObjectResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.DailySalesCashSummaryAdapter;
import com.provider.beautician.adapter.DailySalesTransactionAdapter;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.AppDialogHelper;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.listners.BottomSheetListener;
import com.provider.beautician.model.BottomSheetModel;
import com.provider.beautician.model.DailySalesTransactionModel;
import com.provider.beautician.model.SalesSummaryModel;
import com.provider.beautician.model.DailySalesModel;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragDailySales extends Fragment implements View.OnClickListener {

    private Context mContext;
    private boolean mLoaded;
    private View rootView;
    private ImageView imgBack, imgDots, imgFilter;
    private RelativeLayout layoutDate;
    private RecyclerView mRecyclerTransaction, mRecyclerSummary;
    private ArrayList<DailySalesTransactionModel> mArrayTransaction = new ArrayList<>();
    private ArrayList<SalesSummaryModel> mArraySummary = new ArrayList<>();
    private DailySalesTransactionAdapter mAdapterTransaction;
    private DailySalesCashSummaryAdapter mAdapterSummary;
    private Date selectDate;
    private int selectPosition = 0;
    private static FragDailySales mInstance;
    private TextView txtDate;
    private String TAG = FragDailySales.class.getSimpleName();

    public FragDailySales() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null)
            rootView = inflater.inflate(R.layout.frag_daily_sales, container, false);
        mInstance = this;
        return rootView;
    }

    public static FragDailySales getInstance() {
        return mInstance;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded) {
            mLoaded = true;
            mContext = getActivity();
            initView();
            setRecyclerStaffAdapter();
            setRecyclerCashSummaryAdapter();
            getDailySales(CommonUtils.formatDate(selectDate, "yyyy-MM-dd"));
        }
    }

    private void initView() {
        mRecyclerTransaction = rootView.findViewById(R.id.frag_daily_sales_recyclerViewTransaction);
        mRecyclerSummary = rootView.findViewById(R.id.frag_daily_sales_recyclerViewCashSummary);
        imgBack = rootView.findViewById(R.id.frag_daily_sales_imgBack);
        imgFilter = rootView.findViewById(R.id.frag_daily_sales_imgFilter);
        imgDots = rootView.findViewById(R.id.frag_daily_sales_imgMenu);
        layoutDate = rootView.findViewById(R.id.frag_daily_sales_layoutDate);
        txtDate = rootView.findViewById(R.id.frag_daily_sales_txtDate);

        imgBack.setOnClickListener(this);
        imgFilter.setOnClickListener(this);
        imgDots.setOnClickListener(this);
        layoutDate.setOnClickListener(this);
        selectDate = new Date();
        txtDate.setText(CommonUtils.formatDate(selectDate, "dd MMM, yyyy"));
    }

    private void setRecyclerStaffAdapter() {
        mAdapterTransaction = new DailySalesTransactionAdapter(mContext, mArrayTransaction);
        mRecyclerTransaction.setAdapter(mAdapterTransaction);
        mRecyclerTransaction.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerTransaction.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }

    private void setRecyclerCashSummaryAdapter() {
        mAdapterSummary = new DailySalesCashSummaryAdapter(mContext, mArraySummary);
        mRecyclerSummary.setAdapter(mAdapterSummary);
        mRecyclerSummary.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerSummary.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_daily_sales_imgBack:
                ((ActHome) mContext).onBackPressed();
                break;
            case R.id.frag_daily_sales_imgFilter:
                Toast.makeText(mContext, "Coming soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.frag_daily_sales_imgMenu:
                ArrayList<BottomSheetModel> modelArrayList = new ArrayList<>();
                modelArrayList.add(new BottomSheetModel(CommonUtils.gettingString(R.string.pdf, mContext)));
                modelArrayList.add(new BottomSheetModel(CommonUtils.gettingString(R.string.excel, mContext)));
                modelArrayList.add(new BottomSheetModel(CommonUtils.gettingString(R.string.csv, mContext)));
                AppDialogHelper.showBottomSheet(mContext, modelArrayList, new BottomSheetListener() {
                    @Override
                    public void onSelect(int position) {
                        Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon, mContext), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.frag_daily_sales_layoutDate:
                FragCalendar fragCalendar = new FragCalendar();
                fragCalendar.date = selectDate;
                fragCalendar.position = selectPosition;
                ((ActHome) mContext).switchContent(fragCalendar, FragCalendar.class.getSimpleName(), true);
                break;
        }
    }

    public void setDate(Date date, int position) {
        selectDate = date;
        selectPosition = position;
        txtDate.setText(CommonUtils.formatDate(selectDate, "dd MMM, yyyy"));
        getDailySales(CommonUtils.formatDate(selectDate, "yyyy-MM-dd"));
    }

    private void getDailySales(String date) {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);
            String saloonId = CommonUtils.getSaloonId(mContext);
            Log.e(TAG, "saloonId         ===" + saloonId);
            Log.e(TAG, "date             ===" + date);
            ApiClient.getClient().getDailySales(saloonId, date).enqueue(new Callback<JsonObjectResponse<DailySalesModel>>() {
                @Override
                public void onResponse(Call<JsonObjectResponse<DailySalesModel>> call, Response<JsonObjectResponse<DailySalesModel>> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            mArrayTransaction.clear();
                            mArraySummary.clear();
                            mArrayTransaction.addAll(response.body().getObject().getTransactionSummary());
                            mArraySummary.addAll(response.body().getObject().getCashmovement());
                            mAdapterTransaction.notifyDataSetChanged();
                            mAdapterSummary.notifyDataSetChanged();
                        } else {
                            String statusCode = response.body().getMessage();
                            MessageStatusCode.showErrorMessageByStatusCode(statusCode, mContext);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObjectResponse<DailySalesModel>> call, Throwable t) {
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
