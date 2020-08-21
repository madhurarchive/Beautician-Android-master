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
public class FragOnlineBooking extends Fragment implements View.OnClickListener {

    private Context                 mContext;
    private View                    rootView;
    private boolean                 mLoaded;
    private ImageView               imgBack;
    private LinearLayout            layoutService;
    private int                     selPosition=0;
    private TextView                txtService;
    public FragOnlineBooking() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView==null)
        rootView = inflater.inflate(R.layout.frag_online_booking, container, false);
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

    private void initView(){
        layoutService       =   rootView.findViewById(R.id.frag_online_booking_layoutService);
        txtService          =   rootView.findViewById(R.id.frag_online_booking_txtService);
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);

        layoutService.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar2_imgBack:
                ((ActHome)mContext).onBackPressed();
                break;

            case R.id.frag_online_booking_layoutService:
                final String[] items = {"Everyone","Females only","Males only"};
                CommonUtils.openRadioDialog(mContext, selPosition, items, new RadioDialogListener() {
                    @Override
                    public void onClick(int position, String value) {
                        selPosition = position;
                        txtService.setText(value);
                    }
                });
                break;
        }
    }
}
