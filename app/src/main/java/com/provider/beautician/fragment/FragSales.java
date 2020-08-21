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
import android.widget.Toast;

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
public class FragSales extends Fragment {
    private Context                     mContext;
    private View                        rootView;
    private boolean                     mLoaded;
    private RecyclerView                mRecyclerView;
    private AnalyticsListAdapter        mAdapter;
    private ArrayList<ListDataModel>    mMenuList = new ArrayList<ListDataModel>();
    private ImageView                   imgBack;
    private TextView txtHeader;


    public FragSales() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView ==null)
        rootView = inflater.inflate(R.layout.frag_sales, container, false);
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
    }

    private void initView() {
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        txtHeader           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);
        mRecyclerView       =   rootView.findViewById(R.id.frag_sales_recyclerView);

        txtHeader.setText(CommonUtils.gettingString(R.string.sales,mContext));
        imgBack.setVisibility(View.INVISIBLE);
    }

    private void setUpRecycleView() {
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.daily_sales,mContext),"See daily totals of sales made and payments collected", R.mipmap.img_money_icon));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.appointments,mContext),"List of all appointments booked, with filter and export options", R.mipmap.img_file_icon_v2));
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.invoices,mContext),"List of all sales made, with filter and export options", R.mipmap.img_list_icon));
        mAdapter = new AnalyticsListAdapter(mContext, mMenuList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mAdapter.setOnRecyclerViewItemClickListener(new AnalyticsListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                if (mMenuList.get(position).getHeader().equals(CommonUtils.gettingString(R.string.daily_sales,mContext))){
                    //((ActHome)mContext).switchContent(new FragDailySales(),FragDailySales.class.getSimpleName(),true);
                    Toast.makeText(mContext, "Coming soon", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext, "Coming soon", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
