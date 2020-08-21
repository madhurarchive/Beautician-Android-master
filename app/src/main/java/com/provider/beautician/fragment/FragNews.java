package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragNews extends Fragment {
    private Context                     mContext;
    private View                        rootView;
    private boolean                     mLoaded;

    public FragNews() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView==null)
        rootView = inflater.inflate(R.layout.frag_news, container, false);
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
        if (mContext!=null){
                        ((ActHome)mContext).setUpToolBar(this,"",false);
        }
    }

    private void initView() {

    }
}
