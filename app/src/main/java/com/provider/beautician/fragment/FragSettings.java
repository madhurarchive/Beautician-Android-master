package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.listners.RadioDialogListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragSettings extends Fragment implements View.OnClickListener {
    private Context         mContext;
    private View            rootView;
    private boolean         mLoaded;
    private LinearLayout    layoutTimeType,layoutTax,layoutVoucher;
    private TextView        txtTimeType,txtTax,txtVoucher;
    private ImageView       imgBack;
    private int             timeTypePosition=0,taxPosition=0,voucherPosition=0;

    public FragSettings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null)
        rootView = inflater.inflate(R.layout.frag_settings, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded){
            mLoaded = true;
            mContext = getActivity();
            initView();
        }
        if (mContext != null) {
            ((ActHome) mContext).setUpToolBar(this, "",true);
        }
    }

    private void initView() {
            imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
            layoutTimeType      =   rootView.findViewById(R.id.frag_setting_layoutTimeType);
            layoutTax           =   rootView.findViewById(R.id.frag_setting_layoutTax);
            layoutVoucher       =   rootView.findViewById(R.id.frag_setting_layoutVoucher);
            txtTimeType         =   rootView.findViewById(R.id.frag_setting_txtTimeType);
            txtTax              =   rootView.findViewById(R.id.frag_setting_txtTax);
            txtVoucher          =   rootView.findViewById(R.id.frag_setting_txtVoucher);

            imgBack.setOnClickListener(this);
            layoutTimeType.setOnClickListener(this);
            layoutTax.setOnClickListener(this);
            layoutVoucher.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar2_imgBack:
                ((ActHome)mContext).onBackPressed();
                break;
            case R.id.frag_setting_layoutTimeType:
                final String[] timeTypeItem = {"1h 30min","1h 35min","1h 40min","1h 45min","1h 50min","1h 55min", "2h 05min", "2h 30min"};
                CommonUtils.openRadioDialog(mContext, timeTypePosition, timeTypeItem, new RadioDialogListener() {
                    @Override
                    public void onClick(int position, String value) {
                        timeTypePosition = position;
                        txtTimeType.setText(value);
                    }
                });
                break;
            case R.id.frag_setting_layoutTax:
                final String[] timeTax = {"No tax","Default: No tax"};
                CommonUtils.openRadioDialog(mContext, taxPosition, timeTax, new RadioDialogListener() {
                    @Override
                    public void onClick(int position, String value) {
                        taxPosition = position;
                        txtTax.setText(value);
                    }
                });
                break;
            case R.id.frag_setting_layoutVoucher:
                final String[] voucherItem = {"Default (6 Months)","1 Month","2 Month","3 Month","4 Month","5 Month","6 Month","1 Years","3 Years","5 Years","No Expiry"};
                CommonUtils.openRadioDialog(mContext, voucherPosition, voucherItem, new RadioDialogListener() {
                    @Override
                    public void onClick(int position, String value) {
                        voucherPosition = position;
                        txtVoucher.setText(value);
                    }
                });
                break;
        }
    }
}
