package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.NotificationListAdapter;
import com.provider.beautician.adapter.NotifyLocationAdapter;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.listners.RadioDialogListener;
import com.provider.beautician.listners.SpannableTextListener;
import com.provider.beautician.model.ListDataModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragNotificationSetting extends Fragment implements View.OnClickListener {

    private Context                             mContext;
    private View                                rootView, viewLocation;
    private boolean                             mLoaded;
    private TextView                            txtLearnMore,txtLocations,txtSaveLocation,txtAppointment,txtSave;
    private RecyclerView                        mRecyclerView, mRecyclerViewSlider;
    private NotificationListAdapter             mAdapter;
    private NotifyLocationAdapter               mAdapterLocation;
    private ArrayList<ListDataModel>            mDataModelArrayList = new ArrayList<ListDataModel>();
    private ArrayList<ListDataModel>            mLocationArrayList = new ArrayList<ListDataModel>();
    private ArrayList<ListDataModel>            mNewLocationArrayList = new ArrayList<ListDataModel>();
    private ImageView                           imgBack, imgClose;
    private int                                 checkedItem = 0;
    private LinearLayout                        layoutAppointment, layoutLocation, layoutAllLocation;
    private String                              TAG = FragNotificationSetting.class.getSimpleName();
    private BottomSheetBehavior                 behavior;
    private RelativeLayout                      slideRoot;
    private CheckBox                            checkBoxAll;

    public FragNotificationSetting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null)
            rootView = inflater.inflate(R.layout.frag_notification_setting, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded) {
            mLoaded = true;
            mContext = getActivity();
            initView();
            setUpRecycleView();
            setUpSliderRecycleView();
        }
        if (mContext != null) {
            ((ActHome) mContext).setUpToolBar(this, "",true);
        }
    }

    private void initView() {
        txtLearnMore            = rootView.findViewById(R.id.frag_notification_setting_txtLearnMore);
        mRecyclerView           = rootView.findViewById(R.id.frag_notification_setting_recyclerView);
        imgBack                 = rootView.findViewById(R.id.toolbar2_imgBack);
        layoutAppointment       = rootView.findViewById(R.id.frag_notification_setting_layoutAppointment);
        layoutLocation          = rootView.findViewById(R.id.frag_notification_setting_layoutLocation);
        viewLocation            = rootView.findViewById(R.id.frag_notification_setting_viewLocation);
        slideRoot               = rootView.findViewById(R.id.containt_slider_layoutRoot);
        imgClose                = rootView.findViewById(R.id.containt_slider_imgClose);
        mRecyclerViewSlider     = rootView.findViewById(R.id.containt_slider_recyclerView);
        layoutAllLocation       = rootView.findViewById(R.id.containt_slider_layoutAll);
        checkBoxAll             = rootView.findViewById(R.id.containt_slider_checkAll);
        txtLocations            = rootView.findViewById(R.id.frag_notification_setting_txtLocation);
        txtSaveLocation         = rootView.findViewById(R.id.containt_slider_txtSelect);
        txtAppointment          = rootView.findViewById(R.id.frag_notification_setting_txt_appointment);
        txtSave                 = rootView.findViewById(R.id.notification_setting_btn_save);

        imgBack.setOnClickListener(this);
        layoutAppointment.setOnClickListener(this);
        layoutLocation.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        layoutAllLocation.setOnClickListener(this);
        checkBoxAll.setOnClickListener(this);
        txtSaveLocation.setOnClickListener(this);
        txtSave.setOnClickListener(this);

        CommonUtils.setSpannableText(mContext, txtLearnMore,
                CommonUtils.gettingString(R.string.choose_which_types_of_notifications_yor, mContext),
                CommonUtils.gettingString(R.string.learn_more_about_notification, mContext),
                ""
                , new SpannableTextListener() {
                    @Override
                    public void clickHere() {
                        Toast.makeText(mContext, "Click here", Toast.LENGTH_SHORT).show();
                    }
                });

        setBottomSheetListener();
    }

    private void setBottomSheetListener() {
        behavior                = BottomSheetBehavior.from(slideRoot);
    }

    private void setUpRecycleView() {

        mDataModelArrayList.add(new ListDataModel("New appointments", "When new appointments are booked", true));
        mDataModelArrayList.add(new ListDataModel("Reschedules", "When appointments time are updated", true));
        mDataModelArrayList.add(new ListDataModel("Cancellations", "When appointments become cancelled", true));
        mDataModelArrayList.add(new ListDataModel("No Shows", "When appointments are marked as no show", true));
        mDataModelArrayList.add(new ListDataModel("Confirmations", "When appointments are marked as confirmed"));
        mDataModelArrayList.add(new ListDataModel("Arrivals", "When appointments are marked as arrived"));
        mDataModelArrayList.add(new ListDataModel("Started", "When appointments are marked as started "));

        mAdapter = new NotificationListAdapter(mContext, mDataModelArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter.setOnRecyclerViewItemClickListener(new NotificationListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                if (mDataModelArrayList.get(position).isChecked()) {
                    mDataModelArrayList.get(position).setChecked(false);
                } else {
                    mDataModelArrayList.get(position).setChecked(true);
                }
                mAdapter.notifyItemChanged(position);
            }
        });
    }

    private void setUpSliderRecycleView() {
        mLocationArrayList.add(new ListDataModel("Archive Infotech", false));
        mNewLocationArrayList.add(new ListDataModel("Archive Infotech", false));
        mAdapterLocation = new NotifyLocationAdapter(mContext, mNewLocationArrayList);
        mRecyclerViewSlider.setAdapter(mAdapterLocation);
        mRecyclerViewSlider.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapterLocation.setOnRecyclerViewItemClickListener(new NotifyLocationAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                if (mNewLocationArrayList.get(position).isChecked()) {
                    mNewLocationArrayList.get(position).setChecked(false);
                }else {
                    mNewLocationArrayList.get(position).setChecked(true);
                }
                mAdapterLocation.notifyItemChanged(position);
                checkedAllItemSelected();
            }
        });
    }

    private void checkedAllItemSelected() {
        boolean isChecked = true;
        for (ListDataModel model: mNewLocationArrayList){
            if (!model.isChecked()){
                isChecked = false;
                break;
            }
        }
        checkBoxAll.setChecked(isChecked);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.toolbar2_imgBack:
                ((ActHome) mContext).onBackPressed();
                break;
            case R.id.containt_slider_imgClose:
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.containt_slider_txtSelect:
                mLocationArrayList.clear();
                mLocationArrayList.addAll(mNewLocationArrayList);
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.containt_slider_checkAll:
                setAllLocation(checkBoxAll.isChecked());
                break;
            case R.id.containt_slider_layoutAll:
                if (checkBoxAll.isChecked()) {
                    checkBoxAll.setChecked(false);
                } else {
                    checkBoxAll.setChecked(true);
                }
                setAllLocation(checkBoxAll.isChecked());
                break;
            case R.id.frag_notification_setting_layoutLocation:
                mNewLocationArrayList.clear();
                mNewLocationArrayList.addAll(mLocationArrayList);
                if (slideRoot.getVisibility() == View.GONE)
                    slideRoot.setVisibility(View.VISIBLE);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.frag_notification_setting_layoutAppointment:
                String[] items = {
                        mContext.getResources().getString(R.string.appointment_notification_option_1),
                        mContext.getResources().getString(R.string.appointment_notification_option_2)
                };

                CommonUtils.openRadioDialog(mContext, checkedItem, items, new RadioDialogListener() {
                    @Override
                    public void onClick(int position, String value) {
                        switch (position) {
                            case 0:
                                layoutLocation.setVisibility(View.VISIBLE);
                                viewLocation.setVisibility(View.VISIBLE);
                                checkedItem = 0;
                                txtAppointment.setText(value);
                                break;
                            case 1:
                                layoutLocation.setVisibility(View.GONE);
                                viewLocation.setVisibility(View.GONE);
                                checkedItem = 1;
                                txtAppointment.setText(value);
                                break;
                        }
                    }
                });
                break;
            case R.id.notification_setting_btn_save:
                Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setAllLocation(boolean checked) {
        for (int i = 0; i < mNewLocationArrayList.size(); i++) {
            mNewLocationArrayList.get(i).setChecked(checked);
        }
        mAdapterLocation.notifyDataSetChanged();
    }
}
