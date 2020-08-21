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

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.NotifyLocationAdapter;
import com.provider.beautician.adapter.StaffMemberSelectionAdapter;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.listners.RadioDialogListener;
import com.provider.beautician.model.ListDataModel;
import com.provider.beautician.model.StaffModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragEditService extends Fragment implements View.OnClickListener {
    private Context                     mContext;
    private View                        rootView;
    private boolean                     mLoaded;
    private ImageView                   imgBack,imgClose,imgSliderClose;
    private TextView                    txtHeader,txtSliderTitle,txtSliderSubTitle;
    private LinearLayout                layoutDuration,layoutAllLocation,
                                        layoutServiceFor;
    private TextView                    txtDuration,txtPriceType,txtServiceFor;
    private int                         durationPosition=0,priceTypePosition=0,serviceForPosition=0;
    private RelativeLayout              slideRoot;
    private BottomSheetBehavior         behavior;
    private CheckBox                    checkBoxAll;
    private RecyclerView                mRecyclerViewSlider,mRecyclerViewStaff;
    private ArrayList<ListDataModel>    mLocationArrayList = new ArrayList<ListDataModel>();
    private ArrayList<ListDataModel>    mNewLocationArrayList = new ArrayList<ListDataModel>();
    private NotifyLocationAdapter       mAdapterLocation;
    private ArrayList<StaffModel>       mStaffMemberArrayList = new ArrayList<>();
    private StaffMemberSelectionAdapter mStaffSelectionAdapter;


    public FragEditService() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView==null)
        rootView = inflater.inflate(R.layout.frag_edit_service, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded){
            mContext = getActivity();
            mLoaded = true;
            initView();
            setUpSliderRecycleView();
            setUpStaffRecyclerView();
        }
        if (mContext != null) {
            ((ActHome) mContext).setUpToolBar(this, "",true);
        }
    }

    private void initView() {
        imgBack                 =   rootView.findViewById(R.id.toolbar2_imgBack);
        imgClose                =   rootView.findViewById(R.id.toolbar2_imgMenu);
        txtHeader               =   rootView.findViewById(R.id.toolbar2_txtHeaderName);

        layoutDuration          =   rootView.findViewById(R.id.frag_edit_service_layoutDuration);
        txtDuration             =   rootView.findViewById(R.id.frag_edit_service_txtDuration);
        txtPriceType            =   rootView.findViewById(R.id.frag_edit_service_txtPriceType);
        txtServiceFor           =   rootView.findViewById(R.id.frag_edit_service_txtService);

        slideRoot               = rootView.findViewById(R.id.containt_slider_layoutRoot);
        imgSliderClose          = rootView.findViewById(R.id.containt_slider_imgClose);
        mRecyclerViewSlider     = rootView.findViewById(R.id.containt_slider_recyclerView);
        layoutAllLocation       = rootView.findViewById(R.id.containt_slider_layoutAll);
        checkBoxAll             = rootView.findViewById(R.id.containt_slider_checkAll);

        layoutServiceFor        = rootView.findViewById(R.id.frag_edit_service_layoutService);

        txtSliderTitle          = rootView.findViewById(R.id.containt_slider_txtTitle);
        txtSliderSubTitle       = rootView.findViewById(R.id.containt_slider_txtSubTitle);

        mRecyclerViewStaff      = rootView.findViewById(R.id.frag_edit_service_rv_staff);

        behavior                = BottomSheetBehavior.from(slideRoot);

        txtSliderTitle.setText(CommonUtils.gettingString(R.string.staff,mContext));
        txtSliderSubTitle.setText(CommonUtils.gettingString(R.string.select_staff_who_perform,mContext));
        imgClose.setImageResource(R.drawable.ic_close);
        imgClose.setVisibility(View.VISIBLE);
        imgBack.setVisibility(View.INVISIBLE);

        imgClose.setOnClickListener(this);
        imgSliderClose.setOnClickListener(this);
        layoutAllLocation.setOnClickListener(this);
        checkBoxAll.setOnClickListener(this);
        layoutDuration.setOnClickListener(this);
        layoutServiceFor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.toolbar2_imgMenu:
                    ((ActHome)mContext).onBackPressed();
                    break;
                case R.id.frag_edit_service_layoutDuration:
                    final String[] items = {"1h 30min","1h 35min","1h 40min","1h 45min","1h 50min","1h 55min", "2h 05min", "2h 30min"};
                    CommonUtils.openRadioDialog(mContext, durationPosition, items, new RadioDialogListener() {
                        @Override
                        public void onClick(int position, String value) {
                            durationPosition = position;
                            txtDuration.setText(value);
                        }
                    });
                    break;
                case R.id.frag_edit_service_layoutPriceType:
                    final String[] priceType = {"Fixed","Free", "From"};
                    CommonUtils.openRadioDialog(mContext, priceTypePosition, priceType, new RadioDialogListener() {
                        @Override
                        public void onClick(int position, String value) {
                            priceTypePosition = position;
                            txtPriceType.setText(value);
                        }
                    });

                    break;

                case R.id.containt_slider_imgClose:
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    break;

                case R.id.frag_edit_service_layoutStaff:
                    mNewLocationArrayList.clear();
                    mNewLocationArrayList.addAll(mLocationArrayList);
                    if (slideRoot.getVisibility() == View.GONE)
                        slideRoot.setVisibility(View.VISIBLE);
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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

                case R.id.frag_edit_service_layoutService:
                    final String[] serviceForItems = {"Everyone","Females only","Males only"};
                    CommonUtils.openRadioDialog(mContext, serviceForPosition, serviceForItems, new RadioDialogListener() {
                        @Override
                        public void onClick(int position, String value) {
                            serviceForPosition = position;
                            txtServiceFor.setText(value);
                        }
                    });
                    break;
            }
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

    private void setAllLocation(boolean checked) {
        for (int i = 0; i < mNewLocationArrayList.size(); i++) {
            mNewLocationArrayList.get(i).setChecked(checked);
        }
        mAdapterLocation.notifyDataSetChanged();
    }

    private void addStaffStaticData(){
        StaffModel staffModel = new StaffModel();
        staffModel.setStaffId("all");
        staffModel.setStaffName("Select All");
        staffModel.setChecked(false);
        mStaffMemberArrayList.add(staffModel);

        staffModel = new StaffModel();
        staffModel.setStaffId("1");
        staffModel.setStaffName("Archive Info");
        staffModel.setChecked(false);
        mStaffMemberArrayList.add(staffModel);

        staffModel = new StaffModel();
        staffModel.setStaffId("2");
        staffModel.setStaffName("Wendy Smith");
        staffModel.setChecked(false);
        mStaffMemberArrayList.add(staffModel);

    }

    private void setUpStaffRecyclerView() {
        addStaffStaticData();
        mStaffSelectionAdapter = new StaffMemberSelectionAdapter(mContext, mStaffMemberArrayList);
        mRecyclerViewStaff.setAdapter(mStaffSelectionAdapter);
        mRecyclerViewStaff.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mStaffSelectionAdapter.setOnRecyclerViewItemClickListener(new StaffMemberSelectionAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {

            }
        });
    }
}
