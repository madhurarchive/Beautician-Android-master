package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.provider.beautician.CommonBean.JsonObjectResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.model.AccountSettingModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragSetupAccountSetting extends Fragment implements View.OnClickListener {
    private Context         mContext;
    private View            rootView;
    private boolean         mLoaded;

    private EditText        edtBusinessName,edtWebsite,
                            edtFbPage,edtInstaPage;
    private Button          btnSave;
    private ImageView       imgBack;
    private TextView        txtHeader;
    private String          TAG = FragSetupAccountSetting.class.getSimpleName();
    private String          saloonId = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView ==null){
            rootView = inflater.inflate(R.layout.frag_account_setting, container, false);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded){
            mLoaded     =   true;
            mContext    =   getActivity();
            saloonId    =   CommonUtils.getSaloonId(mContext);
            initView();
            getAccountSetting();
        }
    }

    private void initView() {
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        txtHeader           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);

        edtBusinessName     =   rootView.findViewById(R.id.as_edt_business_name);
        edtWebsite          =   rootView.findViewById(R.id.as_edt_website);
        edtFbPage           =   rootView.findViewById(R.id.as_edt_fb_page);
        edtInstaPage        =   rootView.findViewById(R.id.as_edt_insta_page);
        btnSave             =   rootView.findViewById(R.id.as_btn_save);

        btnSave.setOnClickListener(this);
        txtHeader.setText(CommonUtils.gettingString(R.string.setup_menu_1,mContext));
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.as_btn_save) {
                String businessName  = edtBusinessName.getText().toString();
                String webSite       = edtWebsite.getText().toString();
                String fbPage        = edtFbPage.getText().toString();
                String instaPage     = edtInstaPage.getText().toString();

                if (businessName.isEmpty()){
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_business_name,mContext),0,mContext);
                }else {
                    updateAccountSetting(businessName,webSite,fbPage,instaPage);
                }

        }else if (view.getId() == R.id.toolbar2_imgBack) {
            ((ActHome)mContext).onBackPressed();
        }
    }

    private void updateAccountSetting(String businessName, String webSite, String fbPage, String instaPage) {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);

            ApiClient.getClient().updateAccountSetting(saloonId,businessName,webSite,instaPage,fbPage).enqueue(new Callback<JsonObjectResponse<AccountSettingModel>>() {
                @Override
                public void onResponse(Call<JsonObjectResponse<AccountSettingModel>> call, Response<JsonObjectResponse<AccountSettingModel>> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200 && response.body()!=null) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.account_setting_update_successfully,mContext),2,mContext);
                        } else {
                            String statusCode = response.body().getMessage();
                            MessageStatusCode.showErrorMessageByStatusCode(statusCode, mContext);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObjectResponse<AccountSettingModel>> call, Throwable t) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "OnFailure === " + t.getMessage());
                    CommonUtils.showSnackbarWithoutView(getString(R.string.default_error), mContext, 0);
                }
            });
        } else {
            CommonUtils.showSnackbarWithoutView(getString(R.string.no_network_error), mContext, 0);
        }
    }

    private void getAccountSetting(){
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);
            ApiClient.getClient().getAccountSetting(saloonId).enqueue(new Callback<JsonObjectResponse<AccountSettingModel>>() {
                @Override
                public void onResponse(Call<JsonObjectResponse<AccountSettingModel>> call, Response<JsonObjectResponse<AccountSettingModel>> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200 && response.body()!=null) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            AccountSettingModel model = response.body().getObject();
                            edtBusinessName.setText(model.getBusineessName());
                            edtWebsite.setText(model.getWebsite());
                            edtFbPage.setText(model.getFacebook());
                            edtInstaPage.setText(model.getInstagram());
                        } else {
                            String statusCode = response.body().getMessage();
                            MessageStatusCode.showErrorMessageByStatusCode(statusCode, mContext);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObjectResponse<AccountSettingModel>> call, Throwable t) {
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
