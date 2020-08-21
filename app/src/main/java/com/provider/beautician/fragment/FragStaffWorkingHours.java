package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.provider.beautician.CommonBean.JsonArrayResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.StaffListAdapter;
import com.provider.beautician.adapter.WorkingListAdapter;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.AppDialogHelper;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.helpers.NpaLinearLayoutManager;
import com.provider.beautician.listners.CustomDatePickerListener;
import com.provider.beautician.listners.RadioDialogListener;
import com.provider.beautician.model.StaffWorkingMainDataModel;
import com.provider.beautician.model.StaffWorkingMainModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragStaffWorkingHours extends Fragment implements View.OnClickListener {

    private Context mContext;
    private View rootView;
    private boolean mLoaded;
    private TextView txtDate, txtStaff, txtHeader;
    private int selectDate = 0, SelectMonth = 0, selectPosition = 0, year, startDayOfWeek, endDayOfWeek, checkedItem = 0;
    private ImageView imgPrevious, imgNext, imgBack;
    private String TAG = FragStaffWorkingHours.class.getSimpleName();
    private Calendar currentDate = Calendar.getInstance();
    private boolean isFirst = true;
    private RecyclerView mRecyclerStaff, mRecyclerWorking;
    private ArrayList<StaffWorkingMainModel> staffModelArrayList = new ArrayList<>();
    private ArrayList<StaffWorkingMainModel> staffDropList = new ArrayList<>();
    private ArrayList<StaffWorkingMainDataModel> workingModelArrayList = new ArrayList<>();
    private StaffListAdapter mAdapter;
    private WorkingListAdapter mAdapterWorking;
    private TextView txtDay1, txtDay2, txtDay3, txtDay4, txtDay5, txtDay6, txtDay7;
    private LinearLayout layoutStaff, layoutCloseDay1, layoutCloseDay2, layoutCloseDay3, layoutCloseDay4, layoutCloseDay5, layoutCloseDay6, layoutCloseDay7;
    private String startDate = "", endDate = "", staffId = "";

    public FragStaffWorkingHours() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null)
            rootView = inflater.inflate(R.layout.frag_staff_working_hours, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded) {
            mLoaded = true;
            mContext = getActivity();
            initView();
            getCurrentDate();
            setRecyclerStaffAdapter();
            setRecyclerWorkingAdapter();
        }
    }

    private void setRecyclerStaffAdapter() {
        mAdapter = new StaffListAdapter(mContext, staffModelArrayList);
        mRecyclerStaff.setAdapter(mAdapter);
        mRecyclerStaff.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }

    private void setRecyclerWorkingAdapter() {
        mAdapterWorking = new WorkingListAdapter(mContext, workingModelArrayList);
        mRecyclerWorking.setAdapter(mAdapterWorking);
        mRecyclerWorking.setLayoutManager(new GridLayoutManager(mContext, 7));

        mAdapterWorking.setOnRecyclerViewItemClickListener(new WorkingListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Toast.makeText(mContext, CommonUtils.gettingString(R.string.coming_soon,mContext), Toast.LENGTH_SHORT).show();
                /*if (position!=-1) {
                    StaffWorkingMainDataModel model = workingModelArrayList.get(position);
                    FragEditStaffWorkingHours fragEditStaffWorkingHours = new FragEditStaffWorkingHours();
                    fragEditStaffWorkingHours.date = model.getDate();
                    //fragEditStaffWorkingHours.staffId = staffModelArrayList.get(position).getStaffId();
                    ((ActHome)mContext).switchContent(fragEditStaffWorkingHours,FragEditStaffWorkingHours.class.getSimpleName(),true);
                        *//*if (model.isOpen()){
                        }*//*
                }*/
            }
        });
    }

    private void initView() {

        txtHeader = rootView.findViewById(R.id.toolbar2_txtHeaderName);
        imgBack = rootView.findViewById(R.id.toolbar2_imgBack);
        txtDate = rootView.findViewById(R.id.frag_staff_working_hours_txtDate);
        imgPrevious = rootView.findViewById(R.id.frag_staff_working_hours_imgPrevious);
        imgNext = rootView.findViewById(R.id.frag_staff_working_hours_imgNext);
        mRecyclerStaff = rootView.findViewById(R.id.frag_staff_working_hours_recyclerStaffList);
        mRecyclerWorking = rootView.findViewById(R.id.frag_staff_working_hours_recyclerHoursList);
        txtDay1 = rootView.findViewById(R.id.frag_staff_working_hours_txtDay1);
        txtDay2 = rootView.findViewById(R.id.frag_staff_working_hours_txtDay2);
        txtDay3 = rootView.findViewById(R.id.frag_staff_working_hours_txtDay3);
        txtDay4 = rootView.findViewById(R.id.frag_staff_working_hours_txtDay4);
        txtDay5 = rootView.findViewById(R.id.frag_staff_working_hours_txtDay5);
        txtDay6 = rootView.findViewById(R.id.frag_staff_working_hours_txtDay6);
        txtDay7 = rootView.findViewById(R.id.frag_staff_working_hours_txtDay7);
        layoutStaff = rootView.findViewById(R.id.frag_staff_working_hours_layoutStaff);
        txtStaff = rootView.findViewById(R.id.frag_staff_working_hours_txtStaff);
        layoutCloseDay1 = rootView.findViewById(R.id.frag_staff_working_hours_layoutCloseDay1);
        layoutCloseDay2 = rootView.findViewById(R.id.frag_staff_working_hours_layoutCloseDay2);
        layoutCloseDay3 = rootView.findViewById(R.id.frag_staff_working_hours_layoutCloseDay3);
        layoutCloseDay4 = rootView.findViewById(R.id.frag_staff_working_hours_layoutCloseDay4);
        layoutCloseDay5 = rootView.findViewById(R.id.frag_staff_working_hours_layoutCloseDay5);
        layoutCloseDay6 = rootView.findViewById(R.id.frag_staff_working_hours_layoutCloseDay6);
        layoutCloseDay7 = rootView.findViewById(R.id.frag_staff_working_hours_layoutCloseDay7);

        txtHeader.setText(CommonUtils.gettingString(R.string.staff_working_hours, mContext));

        txtDate.setOnClickListener(this);
        imgPrevious.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        layoutStaff.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    private void setDateOnText(String startDate, String endDate, String startMonth, String endMonth, int year) {
        txtDate.setText(String.format(Locale.ENGLISH,"%s%s - %s%s %d", startDate, CommonUtils.getMonthName(Integer.parseInt(startMonth) + 1), endDate, CommonUtils.getMonthName(Integer.parseInt(endMonth) + 1), year));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_staff_working_hours_imgPrevious:
                startDayOfWeek = startDayOfWeek - 7;
                endDayOfWeek = endDayOfWeek - 7;
                if (startDayOfWeek < 1) {
                    startDayOfWeek = 34;
                    endDayOfWeek = 40;
                    SelectMonth = SelectMonth - 1;
                    currentDate.add(Calendar.MONTH, SelectMonth);
                }
                getCurrentDate();
                break;

            case R.id.frag_staff_working_hours_imgNext:
                startDayOfWeek = startDayOfWeek + 7;
                endDayOfWeek = endDayOfWeek + 7;
                if (endDayOfWeek > 40) {
                    startDayOfWeek = 0;
                    endDayOfWeek = 6;
                    SelectMonth = SelectMonth + 1;
                    currentDate.add(Calendar.MONTH, SelectMonth);
                }
                getCurrentDate();
                break;

            case R.id.frag_staff_working_hours_txtDate:
                AppDialogHelper.showDatePicker(mContext, view, selectDate, SelectMonth, selectPosition, new CustomDatePickerListener() {
                    @Override
                    public void onSelectDate(Date date, int selectedPosition, int startDay, ArrayList<Date> cells) {
                        selectDate = date.getDate();
                        SelectMonth = date.getMonth();
                        selectPosition = selectedPosition;
                        startDayOfWeek = startDay;
                        endDayOfWeek = startDay + 6;
                        year = (date.getYear() + 1900);
                        setDayOnTextView(cells, startDayOfWeek);
                        setDateOnText(cells.get(startDay).getDate() + "", cells.get(startDay + 6).getDate() + "", cells.get(startDay).getMonth() + "", cells.get(startDay + 6).getMonth() + "", year);
                    }
                });
                break;

            case R.id.frag_staff_working_hours_layoutStaff:
                openStaffListDialog();
                break;
            case R.id.toolbar2_imgBack:
                ((ActHome) mContext).onBackPressed();
                break;
        }
    }

    private void getCurrentDate() {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, SelectMonth);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < 41) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        if (isFirst) {
            for (int i = 0; i < cells.size(); i++) {
                final Date date = cells.get(i);
                int day = date.getDate();
                final int month = date.getMonth();
                year = date.getYear() + 1900;
                // today
                final Date today = new Date();
                if ((month == today.getMonth()) && (day == today.getDate())) {
                    Log.e(TAG, "Current day ===" + day + " == " + month);
                    getFirstDayOfWeek(i);
                    isFirst = false;
                    Date sDate = cells.get(startDayOfWeek);
                    int sDay = sDate.getDate();
                    Date eDate = cells.get(endDayOfWeek);
                    int eDay = eDate.getDate();
                    setDayOnTextView(cells, startDayOfWeek);
                    setDateOnText(sDay + "", eDay + "", cells.get(startDayOfWeek).getMonth() + "", cells.get(endDayOfWeek).getMonth() + "", year);
                }
            }
        } else {
            Date sDate = cells.get(startDayOfWeek);
            int sDay = sDate.getDate();
            Date eDate = cells.get(endDayOfWeek);
            int eDay = eDate.getDate();
            setDayOnTextView(cells, startDayOfWeek);
            setDateOnText(sDay + "", eDay + "", cells.get(startDayOfWeek).getMonth() + "", cells.get(endDayOfWeek).getMonth() + "", year);
        }
    }

    private void setDayOnTextView(ArrayList<Date> cells, int position) {
        for (int i = 0; i < 7; i++) {
            final Date date = cells.get(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear() + 1900;
            if (i == 0) {
                txtDay1.setText(CommonUtils.getConvertDateTime(day + "-" + (month + 1) + "-" + year, "d-MM-yyyy", "EEE dd MMM"));
                startDate = CommonUtils.getConvertDateTime(day + "-" + (month + 1) + "-" + year, "d-MM-yyyy", "yyyy-MM-dd");
            } else if (i == 1) {
                txtDay2.setText(CommonUtils.getConvertDateTime(day + "-" + (month + 1) + "-" + year, "d-MM-yyyy", "EEE dd MMM"));
            } else if (i == 2) {
                txtDay3.setText(CommonUtils.getConvertDateTime(day + "-" + (month + 1) + "-" + year, "d-MM-yyyy", "EEE dd MMM"));
            } else if (i == 3) {
                txtDay4.setText(CommonUtils.getConvertDateTime(day + "-" + (month + 1) + "-" + year, "d-MM-yyyy", "EEE dd MMM"));
            } else if (i == 4) {
                txtDay5.setText(CommonUtils.getConvertDateTime(day + "-" + (month + 1) + "-" + year, "d-MM-yyyy", "EEE dd MMM"));
            } else if (i == 5) {
                txtDay6.setText(CommonUtils.getConvertDateTime(day + "-" + (month + 1) + "-" + year, "d-MM-yyyy", "EEE dd MMM"));
            } else if (i == 6) {
                txtDay7.setText(CommonUtils.getConvertDateTime(day + "-" + (month + 1) + "-" + year, "d-MM-yyyy", "EEE dd MMM"));
                endDate = CommonUtils.getConvertDateTime(day + "-" + (month + 1) + "-" + year, "d-MM-yyyy", "yyyy-MM-dd");
            }
            position++;
        }
        getStaffWorkingHours();
    }

    private void getFirstDayOfWeek(int position) {
        if (position >= 0 && position <= 6) {
            startDayOfWeek = 0;
            endDayOfWeek = 6;
        } else if (position >= 7 && position <= 13) {
            startDayOfWeek = 7;
            endDayOfWeek = 13;
        } else if (position >= 14 && position <= 20) {
            startDayOfWeek = 14;
            endDayOfWeek = 20;
        } else if (position >= 21 && position <= 27) {
            startDayOfWeek = 21;
            endDayOfWeek = 27;
        } else if (position >= 28 && position <= 34) {
            startDayOfWeek = 28;
            endDayOfWeek = 34;
        } else if (position >= 35 && position <= 41) {
            startDayOfWeek = 35;
            endDayOfWeek = 41;
        }
    }


    private void openStaffListDialog() {
        String[] items = new String[staffDropList.size() + 1];
        items[0] = CommonUtils.gettingString(R.string.all_staff, mContext);
        int position = 1;
        for (StaffWorkingMainModel model : staffDropList) {
            items[position] = model.getStaffName();
            position++;
        }
        CommonUtils.openRadioDialog(mContext, checkedItem, items, new RadioDialogListener() {
            @Override
            public void onClick(int position, String value) {
                if (checkedItem!=position) {
                    checkedItem = position;
                    Log.e(TAG, "Position ===" + position);
                    txtStaff.setText(value);
                    if (position == 0) {
                        staffId = "";
                    } else {
                        staffId = staffDropList.get(position - 1).getStaffId();
                    }
                    getStaffWorkingHours();
                }
            }
        });
    }

    private void getStaffWorkingHours() {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);
            String saloonId = CommonUtils.getSaloonId(mContext);
            String locationId = "1";
            Log.e(TAG,"saloonId         ==="+saloonId);
            Log.e(TAG,"locationId       ==="+locationId);
            Log.e(TAG,"startDate        ==="+startDate);
            Log.e(TAG,"endDate          ==="+endDate);
            Log.e(TAG,"staffId          ==="+staffId);
            ApiClient.getClient().getSaloonStaffWorking(saloonId, locationId, startDate, endDate,staffId).enqueue(new Callback<JsonArrayResponse<StaffWorkingMainModel>>() {
                @Override
                public void onResponse(Call<JsonArrayResponse<StaffWorkingMainModel>> call, Response<JsonArrayResponse<StaffWorkingMainModel>> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            workingModelArrayList.clear();
                            staffModelArrayList.clear();
                            staffModelArrayList.addAll(response.body().getData());
                            if (staffDropList.size()<=0)
                                staffDropList.addAll(response.body().getData());
                            for (int i = 0; i < staffModelArrayList.size(); i++) {
                                workingModelArrayList.addAll(staffModelArrayList.get(i).getData());
                            }
                            mAdapter.notifyDataSetChanged();
                            mAdapterWorking.notifyDataSetChanged();
                        } else {
                            String statusCode = response.body().getMessage();
                            MessageStatusCode.showErrorMessageByStatusCode(statusCode, mContext);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonArrayResponse<StaffWorkingMainModel>> call, Throwable t) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "OnFailure === " + t.getMessage());
                    CommonUtils.showSnackbarWithoutView(getString(R.string.default_error), mContext, 0);
                }
            });
        } else {
            CommonUtils.showSnackbarWithoutView(getString(R.string.no_network_error), mContext, 0);
        }
    }
}
