package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.provider.beautician.R;
import com.provider.beautician.activity.ActLogin;
import com.provider.beautician.helpers.PreferenceConnector;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragSliderFinal extends Fragment {

    private Context             mContext;
    private View                rootView;
    private boolean             mLoaded;
    private TextView            txtGo;

    public FragSliderFinal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView ==null)
        rootView = inflater.inflate(R.layout.frag_slider_final, container, false);
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
    }

    private void initView() {
        txtGo       =   rootView.findViewById(R.id.frag_slider_final_txtGo);

        txtGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceConnector.writeBoolean(mContext,PreferenceConnector.KEY_IS_FIRST_TIME,false);
                ((ActLogin)mContext).switchContent(new FragLogin(),FragLogin.class.getSimpleName(),false);
            }
        });
    }
}
