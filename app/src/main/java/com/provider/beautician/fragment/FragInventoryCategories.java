package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.helpers.CommonUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragInventoryCategories extends Fragment implements View.OnClickListener {

    private Context                     mContext;
    private View                        rootView;
    private boolean                     mLoaded;
    private TextView                    txtHeader,txtNewProduct,txtSave,txtSliderTitle,txtEditTitle;
    private EditText                    edtSlider;
    private ImageView                   imgBack,imgClose;
    private RelativeLayout              slideRoot;
    private BottomSheetBehavior         behavior;

    public FragInventoryCategories() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null)
        rootView = inflater.inflate(R.layout.frag_inventory_categories, container, false);
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
        txtNewProduct       =   rootView.findViewById(R.id.frag_inventory_category_txtNewCategory);
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        txtHeader           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);
        slideRoot           = rootView.findViewById(R.id.containt_slider_layoutRoot);
        imgClose            = rootView.findViewById(R.id.containt_slider_imgClose);
        txtSave             = rootView.findViewById(R.id.containt_slider_txtSave);
        txtSliderTitle      = rootView.findViewById(R.id.containt_slider_txtTitle);
        txtEditTitle        = rootView.findViewById(R.id.containt_slider_txtEditTitle);
        edtSlider           = rootView.findViewById(R.id.containt_slider_txtEdit);

        behavior            = BottomSheetBehavior.from(slideRoot);

        txtHeader.setText(CommonUtils.gettingString(R.string.categories,mContext));
        txtSliderTitle.setText(CommonUtils.gettingString(R.string.add_category,mContext));
        txtEditTitle.setText(CommonUtils.gettingString(R.string.category_name,mContext));
        edtSlider.setHint(CommonUtils.gettingString(R.string.e_g_hair_products,mContext));

        txtNewProduct.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        txtSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frag_inventory_category_txtNewCategory:
                if (slideRoot.getVisibility() == View.GONE)
                    slideRoot.setVisibility(View.VISIBLE);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.toolbar2_imgBack:
                ((ActHome)mContext).onBackPressed();
                break;

            case R.id.containt_slider_imgClose:
                CommonUtils.hideKeyboard(mContext);
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.containt_slider_txtSave:
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
        }
    }
}
