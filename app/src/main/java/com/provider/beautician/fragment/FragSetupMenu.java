package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.SetupMenuListAdapter;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.helpers.SimpleDividerItemDecoration;
import com.provider.beautician.model.ListDataModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragSetupMenu extends Fragment {
    private Context                     mContext;
    private View                        rootView;
    private boolean                     mLoaded;
    private RecyclerView                mRecyclerView;
    private SetupMenuListAdapter        mAdapter;
    private ArrayList<ListDataModel>    mMenuList = new ArrayList<ListDataModel>();
    private String TAG = FragSetupMenu.class.getSimpleName();
    private ImageView                   imgBack;
    private TextView                    txtHeader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView==null){
            rootView = inflater.inflate(R.layout.frag_setup_menu, container, false);
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
            setUpRecycleView();

            Log.e(TAG,"Test commit");
        }
    }

    private void initView() {
        imgBack             =   rootView.findViewById(R.id.toolbar2_imgBack);
        txtHeader           =   rootView.findViewById(R.id.toolbar2_txtHeaderName);
        mRecyclerView   =   rootView.findViewById(R.id.setup_menu_rv_menu);

        txtHeader.setText(CommonUtils.gettingString(R.string.setup,mContext));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActHome)mContext).onBackPressed();
            }
        });
    }

    private void setUpRecycleView() {
        mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.setup_menu_1,mContext),CommonUtils.gettingString(R.string.setup_desc_1,mContext)));
        //mMenuList.add(new ListDataModel(CommonUtils.gettingString(R.string.setup_menu_2,mContext),CommonUtils.gettingString(R.string.setup_desc_2,mContext)));
        mAdapter = new SetupMenuListAdapter(mContext, mMenuList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        mAdapter.setOnRecyclerViewItemClickListener(new SetupMenuListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                switch (position){
                    case 0:
                        ((ActHome)mContext).switchContent(new FragSetupAccountSetting(),FragSetupAccountSetting.class.getSimpleName(),true);
                        break;

                    case 1:
                        ((ActHome)mContext).switchContent(new FragSetupLocations(),FragSetupLocations.class.getSimpleName(),true);
                        break;
                }
            }
        });
    }
}
