package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.helpers.CommonUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragInventoryAddNewProduct extends Fragment implements View.OnClickListener {

    private Context         mContext;
    private View            rootView;
    private boolean         mLoaded;
    private TextView        txtHeader;
    private ImageView       imgBack,imgClose;

    public FragInventoryAddNewProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView ==null)
        rootView = inflater.inflate(R.layout.frag_inventory_add_new_product, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded){
            mContext = getActivity();
            mLoaded  = true;
            initView();
        }
        if (mContext!=null){
            ((ActHome)mContext).setUpToolBar(this,"",true);
        }
    }

    private void initView() {
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        imgClose            =   rootView.findViewById(R.id.toolbar2_imgMenu);
        txtHeader           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);

        imgBack.setVisibility(View.INVISIBLE);
        imgClose.setVisibility(View.VISIBLE);

        txtHeader.setText(CommonUtils.gettingString(R.string.create_product,mContext));
        imgClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar2_imgMenu:
                ((ActHome)mContext).onBackPressed();
                break;
        }
    }
}
