package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.provider.beautician.CommonBean.JsonArrayResponse;
import com.provider.beautician.CommonBean.JsonObjectResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.AppDialogHelper;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.listners.CustomDatePickerListener2;
import com.provider.beautician.listners.TwoButtonAlertDialogListener;
import com.provider.beautician.model.ClosedDateModel;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragAddCloseDates extends Fragment implements View.OnClickListener {

    private Context         mContext;
    private View            rootView;
    private boolean         mLoaded;
    private TextView        txtHeader,txtStartDate, txtEndDate,txtSave,txtCancel,txtDelete;
    private EditText        edtDescription;
    private LinearLayout    llStartDate, llEndDate;
    private Date            startDate,endDate,selectDate;
    private int             startDatePosition=0;
    private boolean         isSelected;
    private ImageView       imgBack,imgClose;


    private String salonId;
    public boolean isEdit = false;
    public ClosedDateModel closedDateModel;
    private String TAG = FragAddCloseDates.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null)
        rootView = inflater.inflate(R.layout.frag_add_close_dates, container, false);
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

            if (isEdit){
                setData();
                txtHeader.setText(CommonUtils.gettingString(R.string.edit_closed_date,mContext));
                txtDelete.setVisibility(View.VISIBLE);
            }else {
                txtHeader.setText(CommonUtils.gettingString(R.string.new_closed_date,mContext));
                txtDelete.setVisibility(View.GONE);
            }
        }
    }

    private void setData(){
        txtStartDate.setText(getDate(closedDateModel.getDatesArray(),true));
        txtEndDate.setText(getDate(closedDateModel.getDatesArray(),false));
        edtDescription.setText(closedDateModel.getDescription());
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(closedDateModel.getFromDate());
            endDate   = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(closedDateModel.getToData());
            isSelected = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        txtHeader       =   rootView.findViewById(R.id.toolbar2_txtHeaderName);
        imgBack         =   rootView.findViewById(R.id.toolbar2_imgBack);
        imgClose        =   rootView.findViewById(R.id.toolbar2_imgMenu);
        txtStartDate    =   rootView.findViewById(R.id.frag_add_close_dates_txtStartDate);
        txtEndDate      =   rootView.findViewById(R.id.frag_add_close_dates_txtEndDate);
        llStartDate     =   rootView.findViewById(R.id.frag_add_close_dates_layoutStartDate);
        llEndDate       =   rootView.findViewById(R.id.frag_add_close_dates_layoutEndDate);
        txtSave         =   rootView.findViewById(R.id.frag_add_close_dates_txtSave);
        txtCancel       =   rootView.findViewById(R.id.frag_add_close_dates_txtCancel);
        txtDelete       =   rootView.findViewById(R.id.frag_add_close_dates_txtDelete);
        edtDescription  =   rootView.findViewById(R.id.frag_add_close_dates_edt_desc);

        imgBack.setVisibility(View.INVISIBLE);
        imgClose.setVisibility(View.VISIBLE);
        imgClose.setImageResource(R.drawable.ic_close);

        imgClose.setOnClickListener(this);
        llStartDate.setOnClickListener(this);
        llEndDate.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        txtDelete.setOnClickListener(this);

        selectDate = new Date();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frag_add_close_dates_layoutStartDate:
                AppDialogHelper.showDatePickerWithCheck(mContext, view, startDate,selectDate, true,isSelected, startDatePosition, new CustomDatePickerListener2() {
                    @Override
                    public void onSelectDate(Date date, int selectedPosition) {
                        startDatePosition = selectedPosition;
                        startDate  = date;
                        selectDate = date;
                        //txtStartDate.setText(CommonUtils.getConvertDateTime(date.getDate() + "-" + (date.getMonth() + 1) + "-" + (date.getYear() + 1900), "d-MM-yyyy", "EEE,dd MMM yyyy"));
                        txtStartDate.setText(CommonUtils.formatDate(date, "EEE,dd MMM yyyy"));
                    }
                });
                break;

            case R.id.frag_add_close_dates_layoutEndDate:
                boolean isStart;
                isStart = txtStartDate.getText().toString().isEmpty();
                AppDialogHelper.showDatePickerWithCheck(mContext, view, endDate,selectDate, isStart,isSelected, startDatePosition, new CustomDatePickerListener2() {
                    @Override
                    public void onSelectDate(Date date, int selectedPosition) {
                        startDatePosition = selectedPosition;
                        endDate = date;
                        selectDate = date;
                        isSelected = true;
                        //txtEndDate.setText(CommonUtils.getConvertDateTime(date.getDate() + "-" + (date.getMonth() + 1) + "-" + (date.getYear() + 1990), "d-MM-yyyy", "EEE,dd MMM yyyy"));
                        txtEndDate.setText(CommonUtils.formatDate(date, "EEE,dd MMM yyyy"));

                    }
                });
                break;
                
            case R.id.toolbar2_imgMenu:
            case R.id.frag_add_close_dates_txtCancel:
                ((ActHome)mContext).onBackPressed();
                break;

            case R.id.frag_add_close_dates_txtSave:
                CommonUtils.hideKeyboard(mContext);
                onSaveBtnClick();
                break;

            case R.id.frag_add_close_dates_txtDelete:
                onDeleteBtnClick();
                break;
        }
    }

    private void onDeleteBtnClick(){
        AppDialogHelper.showDialogWithTwoButton(mContext, CommonUtils.gettingString(R.string.confimation, mContext),
                CommonUtils.gettingString(R.string.are_you_sure_to_delete, mContext),
                CommonUtils.gettingString(R.string.cancel, mContext),
                CommonUtils.gettingString(R.string.ok, mContext),
                new TwoButtonAlertDialogListener() {
                    @Override
                    public void onYesBtnClick() {
                        deleteCloseDate(closedDateModel.getCloseId());
                    }

                    @Override
                    public void onNoBtnClick() {

                    }
                });
    }

    private void onSaveBtnClick(){
        String description  =   edtDescription.getText().toString().trim();
        String locationId   =   "1";
        if (txtStartDate.getText().toString().isEmpty()){
            CommonUtils.showSnackbarWithoutView(mContext.getResources().getString(R.string.val_error_start_date),mContext,0);
        }else if (txtEndDate.getText().toString().isEmpty()){
            CommonUtils.showSnackbarWithoutView(mContext.getResources().getString(R.string.val_error_end_date),mContext,0);
        }else if (description.isEmpty()){
            CommonUtils.showSnackbarWithoutView(mContext.getResources().getString(R.string.val_error_description),mContext,0);
        }else {
            String fromDate = CommonUtils.formatDate(startDate,"yyyy-MM-dd");
            String toDate = CommonUtils.formatDate(endDate,"yyyy-MM-dd");
            if (isEdit){
                callEditCloseDateApi(closedDateModel.getCloseId(),fromDate, toDate, description, locationId);
            }else {
                callAddCloseDateApi(fromDate, toDate, description, locationId);
            }
        }
    }

    private void callAddCloseDateApi(String from_date,String to_data,String description,String location_id) {
        if (CommonUtils.isConnectingToInternet(mContext)){
            CommonUtils.showLoader(mContext);

            ApiClient.getClient().addCloseDate(salonId,from_date,to_data,description,location_id).enqueue(new Callback<JsonArrayResponse<ClosedDateModel>>() {
                @Override
                public void onResponse(Call<JsonArrayResponse<ClosedDateModel>> call, Response<JsonArrayResponse<ClosedDateModel>> response) {
                    CommonUtils.dismissLoader();

                    if (response.code() == 200 && response.body()!=null) {
                        Log.e(TAG,"url       ===== " + call.request().url());
                        Log.e(TAG,"response  ===== " + new Gson().toJson(response.body()));

                        if(response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)){
                            FragStaffCloseDate.getInstance().updateListData((ArrayList<ClosedDateModel>) response.body().getData());
                            ((ActHome)mContext).onBackPressed();
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

    private void callEditCloseDateApi(String closeId,String from_date,String to_data,String description,String location_id) {
        if (CommonUtils.isConnectingToInternet(mContext)){
            CommonUtils.showLoader(mContext);

            ApiClient.getClient().editCloseDate(closeId,salonId,from_date,to_data,description,location_id).enqueue(new Callback<JsonArrayResponse<ClosedDateModel>>() {
                @Override
                public void onResponse(Call<JsonArrayResponse<ClosedDateModel>> call, Response<JsonArrayResponse<ClosedDateModel>> response) {
                    CommonUtils.dismissLoader();

                    if (response.code() == 200 && response.body()!=null) {
                        Log.e(TAG,"url       ===== " + call.request().url());
                        Log.e(TAG,"response  ===== " + new Gson().toJson(response.body()));

                        if(response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)){
                            FragStaffCloseDate.getInstance().updateListData((ArrayList<ClosedDateModel>) response.body().getData());
                            ((ActHome)mContext).onBackPressed();
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

    private String getDate(String jsonStringArray,boolean isStart){
        Gson converter = new Gson();
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> list =  converter.fromJson(jsonStringArray, type );
        StringBuilder stringBuilder = new StringBuilder();
        if (list.size()>0){
            if (isStart){
                stringBuilder.append(CommonUtils.getFormattedDate(list.get(0),"yyyy-MM-dd","EEE, dd MMM yyyy"));
            }else {
                stringBuilder.append(CommonUtils.getFormattedDate(list.get(list.size()-1),"yyyy-MM-dd","EEE, dd MMM yyyy"));
            }
        }
        return stringBuilder.toString();
    }

    private void deleteCloseDate(String closeId){
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);

            ApiClient.getClient().deleteCloseDate(closeId).enqueue(new Callback<JsonObjectResponse>() {
                @Override
                public void onResponse(Call<JsonObjectResponse> call, Response<JsonObjectResponse> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200 && response.body()!=null) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            FragStaffCloseDate.getInstance().refreshData();
                            ((ActHome)mContext).onBackPressed();
                        } else {
                            String statusCode = response.body().getMessage();
                            MessageStatusCode.showErrorMessageByStatusCode(statusCode, mContext);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObjectResponse> call, Throwable t) {
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
