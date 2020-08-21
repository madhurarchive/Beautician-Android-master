package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.provider.beautician.CommonBean.JsonObjectResponse;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.api.ApiClient;
import com.provider.beautician.constant.Constant;
import com.provider.beautician.helpers.AppDialogHelper;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.MessageStatusCode;
import com.provider.beautician.listners.AlertDialogListener;
import com.provider.beautician.listners.CustomDatePickerListener2;
import com.provider.beautician.listners.RadioDialogListener;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragEditStaffWorkingHours extends Fragment implements View.OnClickListener{

    private Context         mContext;
    private View            rootView;
    private boolean         mLoaded;
    private TextView        txtHeader,txtDelete,txtRepeat,txtEndRepeat,txtSave;
    private ImageView       imgBack,imgClose,imgCloseAnother,imgEndRepeat;
    private LinearLayout    layoutAddShift,layoutAnotherShift,layoutStartShift,layoutEndShit
                            ,layoutAnotherStartShift,layoutAnotherEndShift,layoutRepeat,layoutRootEndRepeat,layoutEndRepeat;
    private TextView        txtStartShift,txtEndShift,txtAnotherStartShift,txtAnotherEndShift;
    private String[] items =   {"9:00pm","10:00pm","11:00pm","12:00pm","1:00am","2:00am","3:00am","4:00am","6:00am"};
    private int             positionRepeat=0,positionEndRepeat=0,positionStartShift=0,positionEndShift=0,positionAnotherStartShift=0,positionAnotherEndShift=0;
    private Date            startDate;
    private Date selectDate;
    private int             startDatePosition=0;
    private String strSelectedDate ="",TAG = FragEditStaffWorkingHours.class.getSimpleName();
    public  String  date ="";
    public  String  staffId ="";
    public FragEditStaffWorkingHours() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView ==null)
        rootView = inflater.inflate(R.layout.frag_edit_staff_working_hours, container, false);
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
        txtHeader               =    rootView.findViewById(R.id.toolbar2_txtHeaderName);
        imgBack                 =    rootView.findViewById(R.id.toolbar2_imgBack);
        imgClose                =    rootView.findViewById(R.id.frag_edit_staff_working_imgClose);
        layoutAddShift          =    rootView.findViewById(R.id.frag_edit_staff_working_layoutAddShift);
        layoutAnotherShift      =    rootView.findViewById(R.id.frag_edit_staff_working_layoutAnotherShift);
        imgCloseAnother         =    rootView.findViewById(R.id.frag_edit_staff_working_imgCloseAnotherShift);
        txtDelete               =    rootView.findViewById(R.id.frag_edit_staff_working_txtDelete);
        layoutStartShift        =    rootView.findViewById(R.id.frag_edit_staff_working_layoutShiftStart);
        txtStartShift           =    rootView.findViewById(R.id.frag_edit_staff_working_txtShiftStartTime);
        layoutEndShit           =    rootView.findViewById(R.id.frag_edit_staff_working_layoutShiftEnd);
        txtEndShift             =    rootView.findViewById(R.id.frag_edit_staff_working_txtShiftEndTime);
        layoutAnotherStartShift =    rootView.findViewById(R.id.frag_edit_staff_working_layoutAnotherShiftStart);
        txtAnotherStartShift    =    rootView.findViewById(R.id.frag_edit_staff_working_txtAnotherShiftStartTime);
        layoutAnotherEndShift   =    rootView.findViewById(R.id.frag_edit_staff_working_layoutAnotherShiftEnd);
        txtAnotherEndShift      =    rootView.findViewById(R.id.frag_edit_staff_working_txtAnotherShiftEndTime);
        layoutRepeat            =    rootView.findViewById(R.id.frag_edit_staff_working_layoutRepeats);
        layoutRootEndRepeat     =    rootView.findViewById(R.id.frag_edit_staff_working_layoutRootEndRepeats);
        layoutEndRepeat         =    rootView.findViewById(R.id.frag_edit_staff_working_layoutEndRepeats);
        txtRepeat               =    rootView.findViewById(R.id.frag_edit_staff_working_txtRepeats);
        txtEndRepeat            =    rootView.findViewById(R.id.frag_edit_staff_working_txtEndRepeats);
        imgEndRepeat            =    rootView.findViewById(R.id.frag_edit_staff_working_imgEndRepeats);
        txtSave                 =    rootView.findViewById(R.id.frag_edit_staff_working_txtSave);

        txtHeader.setText(CommonUtils.gettingString(R.string.staff_working_hours,mContext));
        imgBack.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        layoutAddShift.setOnClickListener(this);
        imgCloseAnother.setOnClickListener(this);
        txtDelete.setOnClickListener(this);
        layoutStartShift.setOnClickListener(this);
        layoutEndShit.setOnClickListener(this);
        layoutAnotherStartShift.setOnClickListener(this);
        layoutAnotherEndShift.setOnClickListener(this);
        layoutRepeat.setOnClickListener(this);
        layoutRootEndRepeat.setOnClickListener(this);
        layoutEndRepeat.setOnClickListener(this);
        imgEndRepeat.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        final Date today = new Date();
        startDate = today;
        selectDate = today;
        /*try {
            selectDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2020-06-30");
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar2_imgBack:
            case R.id.frag_edit_staff_working_imgClose:
                ((ActHome)mContext).onBackPressed();
                break;

            case R.id.frag_edit_staff_working_layoutAddShift:
                layoutAddShift.setVisibility(View.GONE);
                layoutAnotherShift.setVisibility(View.VISIBLE);
                txtDelete.setVisibility(View.GONE);
                break;

            case R.id.frag_edit_staff_working_imgCloseAnotherShift:
                layoutAddShift.setVisibility(View.VISIBLE);
                txtDelete.setVisibility(View.VISIBLE);
                layoutAnotherShift.setVisibility(View.GONE);
                break;

            case R.id.frag_edit_staff_working_txtDelete:

                break;
            case R.id.frag_edit_staff_working_layoutRepeats:
                    openRepeatDialog();
                break;
            case R.id.frag_edit_staff_working_layoutEndRepeats:
                    if (positionEndRepeat==0){
                        openEndRepeatDialog();
                    }else {
                        AppDialogHelper.showDatePickerWithCheck(mContext, view, startDate,selectDate, true,false, startDatePosition, new CustomDatePickerListener2() {
                            @Override
                            public void onSelectDate(Date date, int selectedPosition) {
                                startDatePosition = selectedPosition;
                                startDate  = date;
                                selectDate = date;
                                strSelectedDate = selectDate.getYear()+"-"+selectDate.getMonth()+"-"+selectDate.getDate();
                                txtEndRepeat.setText(CommonUtils.getConvertDateTime(date.getDate() + "-" + (date.getMonth() + 1) + "-" + (date.getYear() + 1900), "d-MM-yyyy", "EEE,dd MMM yyyy"));
                            }
                        });
                    }
                break;
            case R.id.frag_edit_staff_working_imgEndRepeats:
                    if (positionEndRepeat==1){
                        positionEndRepeat= 0;
                        strSelectedDate = "";
                        imgEndRepeat.setImageResource(R.drawable.ic_down_arrow);
                        txtEndRepeat.setText(CommonUtils.gettingString(R.string.ongoing,mContext));
                    }
                break;
            case R.id.frag_edit_staff_working_layoutShiftStart:
                    openShiftStartTime();
                break;
            case R.id.frag_edit_staff_working_layoutShiftEnd:
                    openShiftEndTime();
                break;
            case R.id.frag_edit_staff_working_layoutAnotherShiftStart:
                    openAnotherShiftStartTime();
                break;
            case R.id.frag_edit_staff_working_layoutAnotherShiftEnd:
                    openAnotherShiftEndTime();
                break;
            case R.id.frag_edit_staff_working_txtSave:
                    addStaffWorkingHours();
                break;
        }
    }

    private void openRepeatDialog() {
        String[] repeat = {CommonUtils.gettingString(R.string.dont_repeat,mContext),CommonUtils.gettingString(R.string.weekly,mContext)};
        CommonUtils.openRadioDialog(mContext, positionRepeat, repeat, new RadioDialogListener() {
            @Override
            public void onClick(int position, String value) {
                positionRepeat = position;
                if (position==0){
                    layoutRootEndRepeat.setVisibility(View.GONE);
                }else {
                    layoutRootEndRepeat.setVisibility(View.VISIBLE);
                }
                txtRepeat.setText(value);
            }
        });
    }

    private void openEndRepeatDialog() {
        String[] endRepeat = {CommonUtils.gettingString(R.string.ongoing,mContext),CommonUtils.gettingString(R.string.specific_date,mContext)};
        CommonUtils.openRadioDialog(mContext, positionEndRepeat, endRepeat, new RadioDialogListener() {
            @Override
            public void onClick(int position, String value) {
                positionEndRepeat = position;
                if (position==0){
                    strSelectedDate = "";
                    imgEndRepeat.setImageResource(R.drawable.ic_down_arrow);
                    txtEndRepeat.setText(value);
                }else {
                    strSelectedDate = selectDate.getYear()+"-"+selectDate.getMonth()+"-"+selectDate.getDate();
                    txtEndRepeat.setText(CommonUtils.getConvertDateTime(selectDate.getDate() + "-" + (selectDate.getMonth() + 1) + "-" + (selectDate.getYear() + 1900), "d-MM-yyyy", "EEE,dd MMM yyyy"));
                    imgEndRepeat.setImageResource(R.drawable.ic_close);
                }
            }
        });
    }

    private void openShiftStartTime() {
        CommonUtils.openRadioDialog(mContext, positionStartShift, items, new RadioDialogListener() {
            @Override
            public void onClick(int position, String value) {
                positionStartShift = position;
                txtStartShift.setText(value);
            }
        });
    }

    private void openShiftEndTime() {
        CommonUtils.openRadioDialog(mContext, positionEndShift, items, new RadioDialogListener() {
            @Override
            public void onClick(int position, String value) {
                positionEndShift = position;
                txtEndShift.setText(value);
            }
        });
    }

    private void openAnotherShiftStartTime() {
        CommonUtils.openRadioDialog(mContext, positionAnotherStartShift, items, new RadioDialogListener() {
            @Override
            public void onClick(int position, String value) {
                positionAnotherStartShift = position;
                txtAnotherStartShift.setText(value);
            }
        });
    }

    private void openAnotherShiftEndTime() {
        CommonUtils.openRadioDialog(mContext, positionAnotherEndShift, items, new RadioDialogListener() {
            @Override
            public void onClick(int position, String value) {
                positionAnotherEndShift = position;
                txtAnotherEndShift.setText(value);
            }
        });
    }

    private void addStaffWorkingHours(){
        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);
            String saloonId             = CommonUtils.getSaloonId(mContext);
            String firstStaffStart      = txtStartShift.getText().toString().trim().replace("pm","").replace("am","");
            String firstStaffEnd        = txtEndShift.getText().toString().trim().replace("pm","").replace("am","");
            String secondStaffStart     = txtAnotherStartShift.getText().toString().trim().replace("pm","").replace("am","");
            String secondStaffEnd       = txtAnotherEndShift.getText().toString().trim().replace("pm","").replace("am","");
            String repeatType           = txtRepeat.getText().toString().trim();
            String endRepeat            = "false";
            String endRepeatDate        = "";
            if (positionEndRepeat==1){
                    endRepeat = "true";
                    endRepeatDate = strSelectedDate;
            }
            String locationId           = "1";

            ApiClient.getClient().addStaffWorkingHours(saloonId,staffId,firstStaffStart,firstStaffEnd,secondStaffStart,secondStaffEnd,repeatType,endRepeat,endRepeatDate,locationId,date).enqueue(new Callback<JsonObjectResponse>() {
                @Override
                public void onResponse(Call<JsonObjectResponse> call, Response<JsonObjectResponse> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200 && response.body()!=null) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            AppDialogHelper.showAlertDialog(mContext, CommonUtils.gettingString(R.string.success, mContext),
                                    CommonUtils.gettingString(R.string.add_staff_time_successfully, mContext), new AlertDialogListener() {
                                        @Override
                                        public void onCloseBtnClick() {
                                            ((ActHome)mContext).onBackPressed();
                                        }
                                    });
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

}
