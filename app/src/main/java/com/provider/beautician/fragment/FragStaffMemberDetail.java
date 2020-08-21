package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.provider.beautician.CommonBean.JsonObjectResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.ColorPickerAdapter;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.AppDialogHelper;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.listners.CustomDatePickerListener2;
import com.provider.beautician.listners.TwoButtonAlertDialogListener;
import com.provider.beautician.model.ColorPickerModel;
import com.provider.beautician.model.StaffMemberDetailModel;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragStaffMemberDetail extends Fragment implements View.OnClickListener {
    private Context             mContext;
    private View                rootView;
    private boolean             mLoaded;

    private EditText            edtFirstName, edtLastName,
                                edtMobile, edtEmail, edtStaffTitle,
                                edtNotes;
    private TextView            txtStartDate, txtEndDate;
    private RelativeLayout      rlStartDate, rlEndDate;
    private RecyclerView        rvAppointmentColor;
    private Button              btnSave, btnCancel, btnDelete;
    private ColorPickerAdapter  mAdapter;

    private ArrayList<ColorPickerModel> mColorPickerModelArrayList = new ArrayList<>();
    private String TAG = FragStaffMemberDetail.class.getSimpleName();
    private Date startDate,endDate,selectDate;
    private int startDatePosition=0;
    private boolean isSelected;
    private CountryCodePicker countryCodePicker;
    private String salonId,appointmentColor;
    public StaffMemberDetailModel memberDetailModel;
    public boolean isEdit = false;
    private static FragStaffMemberDetail mInstance;

    public static FragStaffMemberDetail getInstance() {
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.frag_staff_member_detail, container, false);
            mInstance = this;
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded) {
            mLoaded             =   true;
            mContext            =   getActivity();
            salonId             =   CommonUtils.getSaloonId(mContext);
            appointmentColor    =   CommonUtils.getColorCodeInString(mContext,R.color.colorPicker1);
            initView();
            addStaticData();
            setUpRecyclerView();

            if (isEdit){
                setData();
                btnDelete.setVisibility(View.VISIBLE);
            }else {
                btnDelete.setVisibility(View.GONE);
            }

            Log.e("StaffMember","appointmentColor === " + appointmentColor);
        }
    }

    private void addStaticData() {
        ColorPickerModel colorPickerModel = new ColorPickerModel();
        colorPickerModel.setId("1");
        colorPickerModel.setColor(CommonUtils.getColorCodeInString(mContext,R.color.colorPicker1));
        colorPickerModel.setChecked(false);
        colorPickerModel.setView(ContextCompat.getDrawable(mContext, R.drawable.color_picker_view_1));
        mColorPickerModelArrayList.add(colorPickerModel);

        colorPickerModel = new ColorPickerModel();
        colorPickerModel.setId("2");
        colorPickerModel.setColor(CommonUtils.getColorCodeInString(mContext,R.color.colorPicker2));
        colorPickerModel.setChecked(false);
        colorPickerModel.setView(ContextCompat.getDrawable(mContext, R.drawable.color_picker_view_2));
        mColorPickerModelArrayList.add(colorPickerModel);

        colorPickerModel = new ColorPickerModel();
        colorPickerModel.setId("3");
        colorPickerModel.setColor(CommonUtils.getColorCodeInString(mContext,R.color.colorPicker3));
        colorPickerModel.setChecked(false);
        colorPickerModel.setView(ContextCompat.getDrawable(mContext, R.drawable.color_picker_view_3));
        mColorPickerModelArrayList.add(colorPickerModel);

        colorPickerModel = new ColorPickerModel();
        colorPickerModel.setId("4");
        colorPickerModel.setColor(CommonUtils.getColorCodeInString(mContext,R.color.colorPicker4));
        colorPickerModel.setChecked(false);
        colorPickerModel.setView(ContextCompat.getDrawable(mContext, R.drawable.color_picker_view_4));
        mColorPickerModelArrayList.add(colorPickerModel);

        colorPickerModel = new ColorPickerModel();
        colorPickerModel.setId("5");
        colorPickerModel.setColor(CommonUtils.getColorCodeInString(mContext,R.color.colorPicker5));
        colorPickerModel.setChecked(false);
        colorPickerModel.setView(ContextCompat.getDrawable(mContext, R.drawable.color_picker_view_5));
        mColorPickerModelArrayList.add(colorPickerModel);

        colorPickerModel = new ColorPickerModel();
        colorPickerModel.setId("6");
        colorPickerModel.setColor(CommonUtils.getColorCodeInString(mContext,R.color.colorPicker6));
        colorPickerModel.setChecked(false);
        colorPickerModel.setView(ContextCompat.getDrawable(mContext, R.drawable.color_picker_view_6));
        mColorPickerModelArrayList.add(colorPickerModel);

        colorPickerModel = new ColorPickerModel();
        colorPickerModel.setId("7");
        colorPickerModel.setColor(CommonUtils.getColorCodeInString(mContext,R.color.colorPicker7));
        colorPickerModel.setChecked(false);
        colorPickerModel.setView(ContextCompat.getDrawable(mContext, R.drawable.color_picker_view_7));
        mColorPickerModelArrayList.add(colorPickerModel);

        colorPickerModel = new ColorPickerModel();
        colorPickerModel.setId("8");
        colorPickerModel.setColor(CommonUtils.getColorCodeInString(mContext,R.color.colorPicker8));
        colorPickerModel.setChecked(false);
        colorPickerModel.setView(ContextCompat.getDrawable(mContext, R.drawable.color_picker_view_8));
        mColorPickerModelArrayList.add(colorPickerModel);

        colorPickerModel = new ColorPickerModel();
        colorPickerModel.setId("9");
        colorPickerModel.setColor(CommonUtils.getColorCodeInString(mContext,R.color.colorPicker9));
        colorPickerModel.setChecked(false);
        colorPickerModel.setView(ContextCompat.getDrawable(mContext, R.drawable.color_picker_view_9));
        mColorPickerModelArrayList.add(colorPickerModel);

        colorPickerModel = new ColorPickerModel();
        colorPickerModel.setId("10");
        colorPickerModel.setColor(CommonUtils.getColorCodeInString(mContext,R.color.colorPicker10));
        colorPickerModel.setChecked(false);
        colorPickerModel.setView(ContextCompat.getDrawable(mContext, R.drawable.color_picker_view_10));
        mColorPickerModelArrayList.add(colorPickerModel);

    }

    private void setUpRecyclerView() {
        mAdapter = new ColorPickerAdapter(mContext, mColorPickerModelArrayList);
        rvAppointmentColor.setAdapter(mAdapter);
        rvAppointmentColor.setLayoutManager(new GridLayoutManager(mContext, 5));
        mAdapter.setOnRecyclerViewItemClickListener(new ColorPickerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                appointmentColor = mColorPickerModelArrayList.get(position).getColor();
                Log.e("StaffMember","appointmentColor === " + appointmentColor);
            }
        });
    }

    private void initView() {
        edtFirstName        =   rootView.findViewById(R.id.smd_edt_first_name);
        edtLastName         =   rootView.findViewById(R.id.smd_edt_last_name);
        edtMobile           =   rootView.findViewById(R.id.smd_edt_mobile);
        edtEmail            =   rootView.findViewById(R.id.smd_edt_email);
        edtStaffTitle       =   rootView.findViewById(R.id.smd_edt_staff_title);
        edtNotes            =   rootView.findViewById(R.id.smd_edt_notes);
        txtStartDate        =   rootView.findViewById(R.id.smd_txt_emp_start_date);
        txtEndDate          =   rootView.findViewById(R.id.smd_txt_emp_end_date);
        rlStartDate         =   rootView.findViewById(R.id.smd_rl_emp_start_date);
        rlEndDate           =   rootView.findViewById(R.id.smd_rl_emp_end_date);
        rvAppointmentColor  =   rootView.findViewById(R.id.smd_rv_appointment_color);
        btnSave             =   rootView.findViewById(R.id.smd_btn_save);
        btnCancel           =   rootView.findViewById(R.id.smd_btn_cancel);
        btnDelete           =   rootView.findViewById(R.id.smd_btn_delete);
        countryCodePicker   =   rootView.findViewById(R.id.frag_staff_member_details_countryCodePicker);

        //countryCodePicker.registerPhoneNumberTextView(edtMobile);

        rlStartDate.setOnClickListener(this);
        rlEndDate.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        final Date today = new Date();
        startDate = today;
        selectDate = today;
        txtStartDate.setText(CommonUtils.formatDate(today, "EEE,dd MMM yyyy"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.smd_rl_emp_start_date:
                CommonUtils.hideKeyboard(mContext);
                AppDialogHelper.showDatePickerWithCheck(mContext, view, startDate,selectDate, true,isSelected, startDatePosition, new CustomDatePickerListener2() {
                    @Override
                    public void onSelectDate(Date date, int selectedPosition) {
                        startDatePosition = selectedPosition;
                        startDate  = date;
                        selectDate = date;
                        txtStartDate.setText(CommonUtils.formatDate(date, "EEE,dd MMM yyyy"));
                    }
                });
                break;

            case R.id.smd_rl_emp_end_date:
                CommonUtils.hideKeyboard(mContext);
                AppDialogHelper.showDatePickerWithCheck(mContext, view, endDate,selectDate, false,isSelected, startDatePosition, new CustomDatePickerListener2() {
                    @Override
                    public void onSelectDate(Date date, int selectedPosition) {
                        startDatePosition = selectedPosition;
                        endDate = date;
                        selectDate = date;
                        isSelected = true;
                        txtEndDate.setText(CommonUtils.formatDate(date, "EEE,dd MMM yyyy"));
                    }
                });
                break;

            case R.id.smd_btn_save:
                onSaveBtnClick();
                break;

            case R.id.smd_btn_cancel:
                ((ActHome)mContext).onBackPressed();
                break;

            case R.id.smd_btn_delete:
                AppDialogHelper.showDialogWithTwoButton(mContext, CommonUtils.gettingString(R.string.confimation, mContext),
                        CommonUtils.gettingString(R.string.are_you_sure_to_delete, mContext),
                        CommonUtils.gettingString(R.string.cancel, mContext),
                        CommonUtils.gettingString(R.string.ok, mContext),
                        new TwoButtonAlertDialogListener() {
                            @Override
                            public void onYesBtnClick() {
                                callDeleteStaffByIdApi(getStaffId());
                            }

                            @Override
                            public void onNoBtnClick() {

                            }
                        });
                break;
        }
    }

    public void onSaveBtnClick(){
        String firstName    =   edtFirstName.getText().toString().trim();
        String lastName     =   edtLastName.getText().toString().trim();
        String mobile       =   edtMobile.getText().toString().trim();
        String email        =   edtEmail.getText().toString().trim();
        String staffTitle   =   edtStaffTitle.getText().toString().trim();
        String notes        =   edtNotes.getText().toString().trim();
        String strStartDate =   CommonUtils.formatDate(startDate, "yyyy-MM-dd");
        String strEndDate   =   "";
        String commission   =   FragStaffMemberCommission.getInstance().getCommission();
        if (endDate!=null){
            strEndDate  =   CommonUtils.formatDate(endDate, "yyyy-MM-dd");
        }

        if (firstName.isEmpty()){
            CommonUtils.showSnackbarWithoutView(mContext.getResources().getString(R.string.error_plz_first_name),mContext,0);
        } else {
            callAddSaloonStaffApi(
                    getStaffId(),firstName,lastName,mobile,
                    email,staffTitle,notes,
                    strStartDate,strEndDate,appointmentColor,commission);
        }
    }

    private void callAddSaloonStaffApi(String staff_id,String staff_name,String last_name,String mobile,String email,String staff_title,String staff_notes,String staff_start_date,String staff_end_date,String staff_appointment_color,String commission){
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);

            ApiClient.getClient().addSaloonStaff(salonId,staff_id,staff_name,last_name,mobile,email,staff_title,staff_notes,staff_start_date,staff_end_date,staff_appointment_color,commission).enqueue(new Callback<JsonObjectResponse>() {
                @Override
                public void onResponse(Call<JsonObjectResponse> call, Response<JsonObjectResponse> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200 && response.body()!=null) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            FragStaffMemberMain.getInstance().callGetMyStaffApi();
                            ((ActHome)mContext).onBackPressed();
                        } else {
                            String statusCode = response.body().getMessage();
                            MessageStatusCode.showErrorMessageByStatusCode(statusCode, mContext);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObjectResponse> call, Throwable t) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "OnFailure === " + t.getMessage());
                    CommonUtils.showSnackbarWithoutView(getString(R.string.default_error), mContext, 0);
                }
            });
        } else {
            CommonUtils.showSnackbarWithoutView(getString(R.string.no_network_error), mContext, 0);
        }
    }

    public void callDeleteStaffByIdApi(String staff_id){
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);

            ApiClient.getClient().deleteStaffById(staff_id).enqueue(new Callback<JsonObjectResponse>() {
                @Override
                public void onResponse(Call<JsonObjectResponse> call, Response<JsonObjectResponse> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200 && response.body()!=null) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            FragStaffMemberMain.getInstance().callGetMyStaffApi();
                            ((ActHome)mContext).onBackPressed();
                        } else {
                            String statusCode = response.body().getMessage();
                            MessageStatusCode.showErrorMessageByStatusCode(statusCode, mContext);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObjectResponse> call, Throwable t) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "OnFailure === " + t.getMessage());
                    CommonUtils.showSnackbarWithoutView(getString(R.string.default_error), mContext, 0);
                }
            });
        } else {
            CommonUtils.showSnackbarWithoutView(getString(R.string.no_network_error), mContext, 0);
        }
    }

    private void setData(){
        edtFirstName.setText(memberDetailModel.getStaffName());
        edtLastName.setText(memberDetailModel.getLastName());
        edtMobile.setText(memberDetailModel.getMobile());
        edtEmail.setText(memberDetailModel.getEmail());
        edtStaffTitle.setText(memberDetailModel.getStaffTitle());
        edtNotes.setText(memberDetailModel.getStaffNotes());
        setSelectedAppointmentColor();
        setSelectedStartAndEndTime(memberDetailModel.getStaffStartDate(),memberDetailModel.getStaffEndDate());
    }

    private String getStaffId(){
        if (isEdit){
            return memberDetailModel.getStaffId();
        }else {
            return "";
        }
    }

    private void setSelectedAppointmentColor(){
        for (int i = 0; i < mColorPickerModelArrayList.size(); i++) {
            ColorPickerModel model = mColorPickerModelArrayList.get(i);
            if (model.getColor().equalsIgnoreCase(memberDetailModel.getStaffAppointmentColor())){
                appointmentColor = model.getColor();
                model.setChecked(true);
                mAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    private void setSelectedStartAndEndTime(String strStartDate,String strEndDate){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            if (!strStartDate.trim().isEmpty() && !strStartDate.equalsIgnoreCase("0000-00-00")){
                startDate   =   inputFormat.parse(strStartDate);
                txtStartDate.setText(CommonUtils.formatDate(startDate, "EEE,dd MMM yyyy"));
            }

            if (!strEndDate.trim().isEmpty() && !strEndDate.equalsIgnoreCase("0000-00-00")){
                endDate     =   inputFormat.parse(strEndDate);
                txtEndDate.setText(CommonUtils.formatDate(endDate, "EEE,dd MMM yyyy"));
            }
            selectDate  =   startDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
