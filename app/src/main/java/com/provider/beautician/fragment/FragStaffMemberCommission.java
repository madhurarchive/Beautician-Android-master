package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.helpers.AppDialogHelper;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.listners.TwoButtonAlertDialogListener;
import com.provider.beautician.model.StaffMemberDetailModel;

import java.util.Locale;


public class FragStaffMemberCommission extends Fragment implements View.OnClickListener {
    private Context                             mContext;
    private View                                rootView;
    private boolean                             mLoaded;

    private EditText                            edtCommission;
    private Button                              btnSave,btnCancel,btnDelete;

    public boolean isEdit = false;
    public StaffMemberDetailModel memberDetailModel;
    private static FragStaffMemberCommission    mInstance;

    public static FragStaffMemberCommission getInstance() {
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView ==null){
            rootView = inflater.inflate(R.layout.frag_staff_member_commission, container, false);
            mInstance = this;
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

            if (isEdit){
                float commission = Float.parseFloat(memberDetailModel.getServiceCommission());
                edtCommission.setText(String.format(Locale.ENGLISH,"%.1f",commission));
                btnDelete.setVisibility(View.VISIBLE);
            }else {
                btnDelete.setVisibility(View.GONE);
            }
        }
    }

    private void initView() {
        edtCommission       =   rootView.findViewById(R.id.smc_edt_commission);
        btnSave             =   rootView.findViewById(R.id.smc_btn_save);
        btnCancel           =   rootView.findViewById(R.id.smc_btn_cancel);
        btnDelete           =   rootView.findViewById(R.id.smc_btn_delete);

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.smc_btn_save:
                FragStaffMemberDetail.getInstance().onSaveBtnClick();
                break;

            case R.id.smc_btn_cancel:
                ((ActHome)mContext).onBackPressed();
                break;

            case R.id.smc_btn_delete:
                AppDialogHelper.showDialogWithTwoButton(mContext, CommonUtils.gettingString(R.string.confimation, mContext),
                        CommonUtils.gettingString(R.string.are_you_sure_to_delete, mContext),
                        CommonUtils.gettingString(R.string.cancel, mContext),
                        CommonUtils.gettingString(R.string.ok, mContext),
                        new TwoButtonAlertDialogListener() {
                            @Override
                            public void onYesBtnClick() {
                                FragStaffMemberDetail.getInstance().callDeleteStaffByIdApi(getStaffId());
                            }

                            @Override
                            public void onNoBtnClick() {

                            }
                        });
                break;
        }
    }

    public String getCommission(){
        return edtCommission.getText().toString().trim();
    }

    private String getStaffId(){
        if (isEdit){
            return memberDetailModel.getStaffId();
        }else {
            return "";
        }
    }
}
