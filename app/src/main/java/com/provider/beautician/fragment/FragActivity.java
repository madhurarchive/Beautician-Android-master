package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.listners.SpannableTextListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragActivity extends Fragment {
    private Context                 mContext;
    private View                    rootView;
    private boolean                 mLoaded;
    private LinearLayout            layoutNoData;
    private TextView                txtClickHere;

    public FragActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView==null)
        rootView = inflater.inflate(R.layout.frag_activity, container, false);
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
            txtClickHere            =   rootView.findViewById(R.id.frag_activity_txtClickHere);

            CommonUtils.setSpannableText(mContext,txtClickHere,
                    CommonUtils.gettingString(R.string.you_do_not_have_any_notification_yet,mContext),
                    CommonUtils.gettingString(R.string.go_here,mContext),
                    CommonUtils.gettingString(R.string.manage_your_notification,mContext)
            , new SpannableTextListener() {
                        @Override
                        public void clickHere() {
                            //((ActHome)mContext).switchContent(new FragNotification(),FragNotification.class.getSimpleName(),false);
                            //FragNotificationSetting fragNotification = new FragNotificationSetting();
                            //((ActHome)mContext).switchContent(fragNotification,FragNotificationSetting.class.getSimpleName(),true);
                        }
                    });
    }



}
