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
public class FragNewPricing extends Fragment implements View.OnClickListener{
    private Context                     mContext;
    private View                        rootView;
    private boolean                     mLoaded;
    private ImageView                   imgBack,imgClose;
    private LinearLayout                layoutDuration,layoutPriceType;
    private TextView                    txtDuration,txtPriceType;
    private int                         durationPosition=0,priceTypePosition=0;

    public FragNewPricing() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView==null)
        rootView = inflater.inflate(R.layout.frag_new_pricing, container, false);
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
        layoutDuration      =   rootView.findViewById(R.id.frag_new_price_layoutDuration);
        layoutPriceType     =   rootView.findViewById(R.id.frag_new_price_layoutPriceType);
        txtDuration         =   rootView.findViewById(R.id.frag_new_price_txtDuration);
        txtPriceType        =   rootView.findViewById(R.id.frag_new_price_txtPriceType);
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        imgClose            =   rootView.findViewById(R.id.toolbar2_imgMenu);
        imgClose.setImageResource(R.drawable.ic_close);
        imgClose.setVisibility(View.VISIBLE);
        imgBack.setVisibility(View.INVISIBLE);
        
        imgClose.setOnClickListener(this);
        layoutDuration.setOnClickListener(this);
        layoutPriceType.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar2_imgMenu:
                ((ActHome)mContext).onBackPressed();
                break;

            case R.id.frag_new_price_layoutDuration:
                final String[] items = {"1h 30min","1h 35min","1h 40min","1h 45min","1h 50min","1h 55min", "2h 05min", "2h 30min"};
                CommonUtils.openRadioDialog(mContext, durationPosition, items, new RadioDialogListener() {
                    @Override
                    public void onClick(int position, String value) {
                        durationPosition = position;
                        txtDuration.setText(value);
                    }
                });
                break;
            case R.id.frag_new_price_layoutPriceType:
                final String[] priceType = {"Fixed","Free", "From"};
                CommonUtils.openRadioDialog(mContext, priceTypePosition, priceType, new RadioDialogListener() {
                    @Override
                    public void onClick(int position, String value) {
                        priceTypePosition = position;
                        txtPriceType.setText(value);
                    }
                });

                break;
        }
    }
}
