package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.provider.beautician.CommonBean.JsonObjectResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActLogin;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.AppDialogHelper;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.listners.AlertDialogListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragForgetPwd extends Fragment implements View.OnClickListener {
    private Context                 mContext;
    private View                    rootView;
    private boolean                 mLoaded;
    private EditText                edtEmail;
    private TextView                btnResetPwd, txtHeader;
    private ImageView               imgClose;
    private String TAG = FragForgetPwd.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.frag_forget_pwd, container, false);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded) {
            mLoaded     =   true;
            mContext    =   getActivity();
            initView();
        }
    }

    private void initView() {
        imgClose    =   rootView.findViewById(R.id.toolbar2_imgMenu);
        txtHeader   =   rootView.findViewById(R.id.toolbar2_txtHeaderName);
        edtEmail    =   rootView.findViewById(R.id.forget_edt_email);
        btnResetPwd =   rootView.findViewById(R.id.btn_reset_pwd);

        rootView.findViewById(R.id.toolbar2_imgBack).setVisibility(View.INVISIBLE);
        txtHeader.setText(CommonUtils.gettingString(R.string.forgot_password, mContext));
        imgClose.setVisibility(View.VISIBLE);
        imgClose.setImageResource(R.drawable.ic_close);

        btnResetPwd.setOnClickListener(this);
        imgClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_reset_pwd:
                onResetPwdBtnClick();
                break;

            case R.id.toolbar2_imgMenu:
                ((ActLogin) mContext).onBackPressed();
                break;
        }

    }

    private void onResetPwdBtnClick(){
        String email = edtEmail.getText().toString().trim();

        if (email.isEmpty()) {
            CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_email, mContext), 0, mContext);
        } else if (!CommonUtils.isEmailValid(email)) {
            CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_valid_email, mContext), 0, mContext);
        } else {
            callForgotPasswordApi(email);
        }
    }

    private void callForgotPasswordApi(String email) {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);
            ApiClient.getClient().forgotPassword(email).enqueue(new Callback<JsonObjectResponse>() {
                @Override
                public void onResponse(Call<JsonObjectResponse> call, Response<JsonObjectResponse> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200 && response.body()!=null) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            AppDialogHelper.showAlertDialog(mContext, CommonUtils.gettingString(R.string.success,mContext), CommonUtils.gettingString(R.string.reset_password_link_sent,mContext), new AlertDialogListener() {
                                @Override
                                public void onCloseBtnClick() {
                                    ((ActLogin)mContext).onBackPressed();
                                }
                            });
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
