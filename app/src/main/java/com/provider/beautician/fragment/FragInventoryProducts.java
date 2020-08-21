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
public class FragInventoryProducts extends Fragment implements View.OnClickListener{

    private Context         mContext;
    private View            rootView;
    private boolean         mLoaded;
    private TextView        txtHeader,txtNewProduct;
    private ImageView       imgBack;

    public FragInventoryProducts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView ==null)
        rootView = inflater.inflate(R.layout.frag_inventory_products, container, false);
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
        txtNewProduct       =   rootView.findViewById(R.id.frag_inventory_products_txtNewProduct);
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        txtHeader           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);

        txtHeader.setText(CommonUtils.gettingString(R.string.products,mContext));

        txtNewProduct.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frag_inventory_products_txtNewProduct:
                ((ActHome)mContext).switchContent(new FragInventoryAddNewProduct(),FragInventoryAddNewProduct.class.getSimpleName(),true);
                break;
            case R.id.toolbar2_imgBack:
                ((ActHome)mContext).onBackPressed();
                break;
        }
    }
}
