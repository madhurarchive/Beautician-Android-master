package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.ServiceMenuAdapter;
import com.provider.beautician.helpers.AppDialogHelper;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.listners.BottomSheetListener;
import com.provider.beautician.model.BottomSheetModel;
import com.provider.beautician.model.CategoryModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragServicesMenu extends Fragment implements View.OnClickListener{

    private Context                     mContext;
    private View                        rootView;
    private boolean                     mLoaded;
    private BottomSheetDialog           mBottom;
    private ImageView                   imgBack,imgMenu,imgCategoryMenu;
    private TextView                    txtHeader;
    private RecyclerView                mRecyclerView;
    private ServiceMenuAdapter          mAdapter;
    private ArrayList<CategoryModel>    mMenuList = new ArrayList<>();

    public FragServicesMenu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView==null)
            rootView = inflater.inflate(R.layout.frag_services_menu, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded){
            mLoaded = true;
            mContext = getActivity();
            initView();
            setUpRecycleView();
        }
        if (mContext != null) {
            ((ActHome) mContext).setUpToolBar(this, "",false);
        }
    }

    private void initView() {
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        imgMenu             =   rootView.findViewById(R.id.toolbar2_imgMenu);
        imgCategoryMenu     =   rootView.findViewById(R.id.frag_services_imgMenu);
        txtHeader           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);
        mRecyclerView       =   rootView.findViewById(R.id.frag_services_recyclerView);

        imgMenu.setImageResource(R.drawable.ic_there_dots);
        imgMenu.setVisibility(View.VISIBLE);

        txtHeader.setText(CommonUtils.gettingString(R.string.services,mContext));
        imgBack.setOnClickListener(this);
        imgCategoryMenu.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar2_imgBack:
                ((ActHome)mContext).onBackPressed();
                break;
            case R.id.toolbar2_imgMenu:
                ArrayList<BottomSheetModel> modelArrayList = new ArrayList<>();
                modelArrayList.add(new BottomSheetModel(CommonUtils.gettingString(R.string.download_pdf,mContext)));
                modelArrayList.add(new BottomSheetModel(CommonUtils.gettingString(R.string.download_excel,mContext)));
                modelArrayList.add(new BottomSheetModel(CommonUtils.gettingString(R.string.download_csv,mContext)));
                modelArrayList.add(new BottomSheetModel(CommonUtils.gettingString(R.string.manage_service_order,mContext)));
                AppDialogHelper.showBottomSheet(mContext, modelArrayList, new BottomSheetListener() {
                    @Override
                    public void onSelect(int position) {
                        Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.frag_services_imgMenu:
                ArrayList<BottomSheetModel> modelArrayList2 = new ArrayList<>();
                modelArrayList2.add(new BottomSheetModel(CommonUtils.gettingString(R.string.add_new_service,mContext)));
                AppDialogHelper.showBottomSheet(mContext, modelArrayList2, new BottomSheetListener() {
                    @Override
                    public void onSelect(int position) {
                        Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }


    private void setUpRecycleView() {
        mMenuList.add(new CategoryModel("Blow Dry","25","1h 30min"));
        mMenuList.add(new CategoryModel("Haircut","25","1h 30min"));
        mAdapter = new ServiceMenuAdapter(mContext, mMenuList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mAdapter.setOnRecyclerViewItemClickListener(new ServiceMenuAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                ((ActHome)mContext).switchContent(new FragEditService(),FragEditService.class.getSimpleName(),true);
            }
        });
    }
}
