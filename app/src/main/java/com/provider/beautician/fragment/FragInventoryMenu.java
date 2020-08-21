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
import android.widget.ImageView;
import android.widget.TextView;

import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.AnalyticsListAdapter;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.model.ListDataModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragInventoryMenu extends Fragment {
    private Context                     mContext;
    private View                        rootView;
    private boolean                     mLoaded;
    private RecyclerView                mRecyclerView;
    private AnalyticsListAdapter        mAdapter;
    private ArrayList<ListDataModel>    mMenuList = new ArrayList<ListDataModel>();
    private ImageView                   imgBack;
    private TextView                    txtHeader;

    public FragInventoryMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView ==null)
        rootView = inflater.inflate(R.layout.frag_inventory_menu, container, false);
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
        if (mContext!=null){
            ((ActHome)mContext).setUpToolBar(this,"",true);
        }
    }

    private void initView() {
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        txtHeader           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);
        mRecyclerView       =   rootView.findViewById(R.id.frag_inventory_recyclerView);

        txtHeader.setText(CommonUtils.gettingString(R.string.inventory,mContext));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActHome)mContext).onBackPressed();
            }
        });
    }

    private void setUpRecycleView() {
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.products,mContext),"Manage the stock levels, pricing and details of your product inventory", R.mipmap.img_prodects_icon));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.orders,mContext),"Order stock from suppliers and transfer stock between your locations", R.mipmap.img_orders));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.brands,mContext),"Manage the barnd names accociated to your product types", R.mipmap.img_brands_icon));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.categories,mContext),"Manage the categories associated to your product type", R.mipmap.img_categories));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.suppliers,mContext),"Manage supplier information for use with your product stock order", R.mipmap.img_suppliers_icon));
        mAdapter = new AnalyticsListAdapter(mContext, mMenuList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mAdapter.setOnRecyclerViewItemClickListener(new AnalyticsListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                ListDataModel model = mMenuList.get(position);
                if (model.getHeader().equals(CommonUtils.gettingString(R.string.products,mContext))){
                    ((ActHome)mContext).switchContent(new FragInventoryProducts(),FragInventoryProducts.class.getSimpleName(),true);
                }else if (model.getHeader().equals(CommonUtils.gettingString(R.string.orders,mContext))){
                    ((ActHome)mContext).switchContent(new FragInventoryOrders(),FragInventoryOrders.class.getSimpleName(),true);
                }else if (model.getHeader().equals(CommonUtils.gettingString(R.string.brands,mContext))){
                    ((ActHome)mContext).switchContent(new FragInventoryBrands(),FragInventoryBrands.class.getSimpleName(),true);
                }else if (model.getHeader().equals(CommonUtils.gettingString(R.string.categories,mContext))){
                    ((ActHome)mContext).switchContent(new FragInventoryCategories(),FragInventoryCategories.class.getSimpleName(),true);
                }else if (model.getHeader().equals(CommonUtils.gettingString(R.string.suppliers,mContext))){
                    ((ActHome)mContext).switchContent(new FragInventorySuppliers(),FragInventorySuppliers.class.getSimpleName(),true);
                }
            }
        });
    }
}
