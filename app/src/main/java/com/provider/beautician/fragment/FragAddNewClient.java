package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
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
import com.provider.beautician.listners.RadioDialogListener;
import com.provider.beautician.model.ClientModel;

import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragAddNewClient extends Fragment implements View.OnClickListener {

    private Context mContext;
    private View rootView;
    private boolean mLoaded;
    private ImageView imgBack, imgMenu, imgPersonalInfoClose, imgAddressDetailsClose;
    private LinearLayout layoutPersonalInfo, layoutAddressDetail;
    private BottomSheetBehavior behaviorPersonalInfo, behaviorAddressDetails;
    private RelativeLayout slidePersonalInfoRoot, sliderAddressDetails;
    private EditText edtFName, edtLName, edtMobile, edtTelephone, edtEmail, edtClientNotes;
    private TextView txtTitle,txtNotification, txtLanguage, txtSwitch, txtGender, txtReferral, txtMonth, txtDay, txtYear, txtPersonalSave, txtAddressSave, txtSave;
    private LinearLayout layoutAcceptNotify, layoutNotification, layoutLanguage, layoutGender, layoutReferral, layoutMonth, layoutDay, layoutYear;
    private SwitchCompat switchCompat, switchAcceptNotify;
    private String[] itemsNotification = {"Don't send notification", "Email", "SMS", "Email & SMS"};
    private String[] itemsLanguage = {"", "Hindi", "English"};
    private String[] itemsGender = {"Male", "Female", "Unknown"};
    private String[] itemsReferral = {"", "Walk-in"};
    private String[] itemsMonth = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private String[] itemsDay = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private int itemNotiPosition = 3, itemLangPosition = 0, itemGenderPosition = 2, itemReferralPosition = 0, itemMonthPosition = 0, itemDayPosition = 0, itemYearPosition = -1;
    private EditText edtAddress, edtSuburb, edtCity, edtState, edtZipCode;
    private String TAG = FragAddNewClient.class.getSimpleName();
    private LinearLayout layoutClient;
    public ClientModel model;

    public FragAddNewClient() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null)
            rootView = inflater.inflate(R.layout.frag_add_new_client, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded) {
            mContext = getActivity();
            mLoaded = true;
            initView();
            setData(model);
        }
        if (mContext != null) {
            ((ActHome) mContext).setUpToolBar(this, "", true);
        }
    }

    private void setData(ClientModel model) {
        if (model != null) {
            edtFName.setText(model.getName());
            edtLName.setText(model.getLastName());
            edtMobile.setText(model.getMobileNo());
            edtTelephone.setText(model.getTelephone());
            edtEmail.setText(model.getEmail());
            edtClientNotes.setText(model.getClientNote());
            txtTitle.setText(CommonUtils.gettingString(R.string.edit_client,mContext));
            if (model.getAcceptMarketingNotification().equals("Y")) {
                switchAcceptNotify.setChecked(true);
            } else {
                switchAcceptNotify.setChecked(false);
            }
            if (model.getClientNoteShowAllBooking().equals("Y")) {
                switchCompat.setChecked(true);
                txtSwitch.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            } else {
                switchCompat.setChecked(false);
                txtSwitch.setTextColor(mContext.getResources().getColor(R.color.colorDivider));
            }
            if (model.getGender().equals("M")) {
                txtGender.setText(CommonUtils.gettingString(R.string.male, mContext));
                itemGenderPosition = 0;
            } else if (model.getGender().equals("F")) {
                itemGenderPosition = 1;
                txtGender.setText(CommonUtils.gettingString(R.string.female, mContext));
            } else {
                itemGenderPosition = 2;
                txtGender.setText(CommonUtils.gettingString(R.string.unknown, mContext));
            }
            if (!model.getBirthDay().isEmpty()) {
                String[] dob = model.getBirthDay().split(" ");
                if (dob.length>1) {
                    String day = dob[0];
                    String month = dob[1];
                    for (int i = 0; i < itemsDay.length; i++) {
                        if (itemsDay[i].equals(day)) {
                            itemDayPosition = i;
                            txtDay.setText(itemsDay[i]);
                        }
                    }
                    for (int i = 0; i < itemsMonth.length; i++) {
                        if (itemsMonth[i].contains(month)) {
                            itemMonthPosition = i;
                            txtMonth.setText(itemsMonth[i]);
                        }
                    }
                }
                if (!model.getBirthYear().isEmpty()) {
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    int count = 0;
                    for (int i = 1921; i <= currentYear; i++) {
                        if (i == Integer.parseInt(model.getBirthYear())) {
                            itemYearPosition = count;
                            txtYear.setText(i + "");
                        }
                        count++;
                    }
                }
            }
        }
    }

    private void initView() {

        imgBack = rootView.findViewById(R.id.toolbar2_imgBack);
        imgMenu = rootView.findViewById(R.id.toolbar2_imgMenu);
        txtTitle = rootView.findViewById(R.id.frag_add_new_client_txtTitle);
        layoutPersonalInfo = rootView.findViewById(R.id.frag_add_new_client_layoutPersonalInfo);
        layoutAddressDetail = rootView.findViewById(R.id.frag_add_new_client_layoutAddressDetails);
        slidePersonalInfoRoot = rootView.findViewById(R.id.container_personal_info_slider_layoutRoot);
        sliderAddressDetails = rootView.findViewById(R.id.container_address_detail_slider_layoutRoot);
        imgPersonalInfoClose = rootView.findViewById(R.id.container_personal_info_slider_imgClose);
        imgAddressDetailsClose = rootView.findViewById(R.id.container_address_detail_slider_imgClose);
        layoutAcceptNotify = rootView.findViewById(R.id.frag_add_new_client_layoutAcceptNotification);
        switchAcceptNotify = rootView.findViewById(R.id.frag_add_new_client_switchAcceptNotification);
        layoutClient = rootView.findViewById(R.id.frag_add_new_client_layoutSwitch);

        edtFName = rootView.findViewById(R.id.frag_add_new_client_edtFirstName);
        edtLName = rootView.findViewById(R.id.frag_add_new_client_edtLastName);
        edtMobile = rootView.findViewById(R.id.frag_add_new_client_edtMobile);
        edtTelephone = rootView.findViewById(R.id.frag_add_new_client_edtTelephone);
        edtEmail = rootView.findViewById(R.id.frag_add_new_client_edtEmail);
        edtClientNotes = rootView.findViewById(R.id.frag_add_new_client_edtClientNotes);
        txtNotification = rootView.findViewById(R.id.frag_add_new_client_txtNotification);
        txtLanguage = rootView.findViewById(R.id.frag_add_new_client_txtLanguage);
        txtSwitch = rootView.findViewById(R.id.frag_add_new_client_txtSwitch);
        layoutNotification = rootView.findViewById(R.id.frag_add_new_client_layoutNotification);
        layoutLanguage = rootView.findViewById(R.id.frag_add_new_client_layoutLanguage);
        switchCompat = rootView.findViewById(R.id.frag_add_new_client_switch);
        txtSave = rootView.findViewById(R.id.frag_add_new_client_txtSave);

        layoutGender = rootView.findViewById(R.id.containt_personal_info_layoutGender);
        layoutReferral = rootView.findViewById(R.id.containt_personal_info_layoutReferral);
        layoutMonth = rootView.findViewById(R.id.containt_personal_info_layoutMonth);
        layoutDay = rootView.findViewById(R.id.containt_personal_info_layoutDay);
        layoutYear = rootView.findViewById(R.id.containt_personal_info_layoutYear);
        txtGender = rootView.findViewById(R.id.containt_personal_info_txtGender);
        txtReferral = rootView.findViewById(R.id.containt_personal_info_txtReferral);
        txtMonth = rootView.findViewById(R.id.containt_personal_info_txtMonth);
        txtDay = rootView.findViewById(R.id.containt_personal_info_txtDay);
        txtYear = rootView.findViewById(R.id.containt_personal_info_txtYear);
        txtPersonalSave = rootView.findViewById(R.id.containt_personal_info_txtSave);

        edtAddress = rootView.findViewById(R.id.containt_address_details_edtAddress);
        edtSuburb = rootView.findViewById(R.id.containt_address_details_edtSuburb);
        edtCity = rootView.findViewById(R.id.containt_address_details_edtCity);
        edtState = rootView.findViewById(R.id.containt_address_details_edtState);
        edtZipCode = rootView.findViewById(R.id.containt_address_details_edtZipCode);
        txtAddressSave = rootView.findViewById(R.id.containt_address_details_txtSave);

        behaviorPersonalInfo = BottomSheetBehavior.from(slidePersonalInfoRoot);
        behaviorAddressDetails = BottomSheetBehavior.from(sliderAddressDetails);

        imgBack.setVisibility(View.INVISIBLE);
        imgMenu.setVisibility(View.VISIBLE);
        imgMenu.setImageResource(R.drawable.ic_close);
        imgMenu.setOnClickListener(this);
        imgPersonalInfoClose.setOnClickListener(this);
        imgAddressDetailsClose.setOnClickListener(this);
        layoutPersonalInfo.setOnClickListener(this);
        layoutAddressDetail.setOnClickListener(this);
        layoutNotification.setOnClickListener(this);
        layoutLanguage.setOnClickListener(this);
        layoutGender.setOnClickListener(this);
        layoutReferral.setOnClickListener(this);
        layoutMonth.setOnClickListener(this);
        layoutDay.setOnClickListener(this);
        layoutYear.setOnClickListener(this);
        txtAddressSave.setOnClickListener(this);
        txtPersonalSave.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        layoutAcceptNotify.setOnClickListener(this);
        layoutClient.setOnClickListener(this);
        edtClientNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    txtSwitch.setTextColor(mContext.getResources().getColor(R.color.colorDivider));
                    switchCompat.setChecked(false);
                    switchCompat.setClickable(false);
                    layoutClient.setClickable(false);
                } else {
                    txtSwitch.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                    switchCompat.setChecked(true);
                    switchCompat.setClickable(true);
                    layoutClient.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.container_personal_info_slider_imgClose:
                behaviorPersonalInfo.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.containt_personal_info_txtSave:
                behaviorPersonalInfo.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.frag_add_new_client_layoutSwitch:
                if (switchCompat.isChecked()) {
                    switchCompat.setChecked(false);
                    txtSwitch.setTextColor(mContext.getResources().getColor(R.color.colorDivider));
                } else {
                    switchCompat.setChecked(true);
                    txtSwitch.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                }
                break;
            case R.id.container_address_detail_slider_imgClose:
                behaviorAddressDetails.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.containt_address_details_txtSave:
                behaviorAddressDetails.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.toolbar2_imgMenu:
                ((ActHome) mContext).onBackPressed();
                break;
            case R.id.frag_add_new_client_layoutLanguage:
                CommonUtils.openRadioDialog(mContext, itemLangPosition, itemsLanguage, new RadioDialogListener() {
                    @Override
                    public void onClick(int position, String value) {
                        itemLangPosition = position;
                        txtLanguage.setText(value);
                    }
                });
                break;
            case R.id.frag_add_new_client_layoutNotification:
                CommonUtils.openRadioDialog(mContext, itemNotiPosition, itemsNotification, new RadioDialogListener() {
                    @Override
                    public void onClick(int position, String value) {
                        itemNotiPosition = position;
                        txtNotification.setText(value);
                    }
                });
                break;
            case R.id.containt_personal_info_layoutMonth:
                CommonUtils.openRadioDialog(mContext, itemMonthPosition, itemsMonth, new RadioDialogListener() {
                    @Override
                    public void onClick(int position, String value) {
                        itemMonthPosition = position;
                        itemDayPosition = 0;
                        txtDay.setText("1");
                        txtMonth.setText(value);
                        int daysInMonth = 31;
                        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                        if (txtYear.getText().toString().isEmpty()) {
                            daysInMonth = new GregorianCalendar(currentYear, itemMonthPosition, 1).getActualMaximum(Calendar.DAY_OF_MONTH);
                        } else {
                            daysInMonth = new GregorianCalendar(Integer.parseInt(txtYear.getText().toString()), itemMonthPosition, 1).getActualMaximum(Calendar.DAY_OF_MONTH);
                        }
                        itemsDay = new String[daysInMonth];
                        for (int i = 0; i < daysInMonth; i++) {
                            itemsDay[i] = (i + 1) + "";
                        }
                    }
                });
                break;
            case R.id.containt_personal_info_layoutDay:
                CommonUtils.openRadioDialog(mContext, itemDayPosition, itemsDay, new RadioDialogListener() {
                    @Override
                    public void onClick(int position, String value) {
                        itemDayPosition = position;
                        txtDay.setText(value);
                    }
                });
                break;
            case R.id.containt_personal_info_layoutYear:
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                String[] itemsYear = new String[(currentYear + 1) - 1921];
                int count = 0;
                for (int i = 1921; i <= currentYear; i++) {
                    itemsYear[count] = i + "";
                    count++;
                }
                CommonUtils.openRadioDialog(mContext, itemYearPosition, itemsYear, new RadioDialogListener() {
                    @Override
                    public void onClick(int position, String value) {
                        itemYearPosition = position;
                        txtYear.setText(value);
                        int daysInMonth = new GregorianCalendar(Integer.parseInt(txtYear.getText().toString()), itemMonthPosition, 1).getActualMaximum(Calendar.DAY_OF_MONTH);
                        if (!txtDay.getText().toString().isEmpty() && Integer.parseInt(txtDay.getText().toString()) > daysInMonth) {
                            txtDay.setText("1");
                            itemsDay = new String[daysInMonth];
                            itemDayPosition = 0;
                            for (int i = 0; i < daysInMonth; i++) {
                                itemsDay[i] = (i + 1) + "";
                            }
                        }
                    }
                });
                break;
            case R.id.containt_personal_info_layoutGender:
                CommonUtils.openRadioDialog(mContext, itemGenderPosition, itemsGender, new RadioDialogListener() {
                    @Override
                    public void onClick(int position, String value) {
                        itemGenderPosition = position;
                        txtGender.setText(value);
                    }
                });
                break;
            case R.id.containt_personal_info_layoutReferral:
                CommonUtils.openRadioDialog(mContext, itemReferralPosition, itemsReferral, new RadioDialogListener() {
                    @Override
                    public void onClick(int position, String value) {
                        itemReferralPosition = position;
                        txtReferral.setText(value);
                    }
                });
                break;
            case R.id.frag_add_new_client_layoutPersonalInfo:
                if (layoutPersonalInfo.getVisibility() == View.GONE)
                    layoutPersonalInfo.setVisibility(View.VISIBLE);
                behaviorPersonalInfo.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.frag_add_new_client_layoutAcceptNotification:
                if (switchAcceptNotify.isChecked()) {
                    switchAcceptNotify.setChecked(false);
                } else {
                    switchAcceptNotify.setChecked(true);
                }
                break;
            case R.id.frag_add_new_client_layoutAddressDetails:
                if (layoutAddressDetail.getVisibility() == View.GONE)
                    layoutAddressDetail.setVisibility(View.VISIBLE);
                behaviorAddressDetails.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.frag_add_new_client_txtSave:
                String fName = edtFName.getText().toString().trim();
                String lName = edtLName.getText().toString().trim();
                String mobile = edtMobile.getText().toString().trim();
                String telephone = edtTelephone.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String bDay = txtDay.getText().toString().trim();
                String bMonth = txtMonth.getText().toString().trim();
                String bYear = txtYear.getText().toString().trim();
                String gender = txtGender.getText().toString().trim();
                String clientNotes = edtClientNotes.getText().toString().trim();
                if (gender.equalsIgnoreCase("Male")) {
                    gender = "M";
                } else if (gender.equalsIgnoreCase("Female")) {
                    gender = "F";
                } else {
                    gender = "U";
                }
                String clientNoteShow = "N";
                if (switchCompat.isChecked()) {
                    clientNoteShow = "Y";
                }
                String acceptNotification = "N";
                if (switchAcceptNotify.isChecked()) {
                    acceptNotification = "Y";
                }
                String notification_via = "NONE";
                if (itemNotiPosition == 0) {
                    notification_via = "NONE";
                }
                if (itemNotiPosition == 1) {
                    notification_via = "EMAIL";
                }
                if (itemNotiPosition == 2) {
                    notification_via = "SMS";
                }
                if (itemNotiPosition == 3) {
                    notification_via = "ALL";
                }

                if (fName.isEmpty()) {
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_first_name, mContext), 0, mContext);
                } else if (lName.isEmpty()) {
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_last_name, mContext), 0, mContext);
                } else if (mobile.isEmpty()) {
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_mobile, mContext), 0, mContext);
                } else if (email.isEmpty()) {
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_email, mContext), 0, mContext);
                } else if (!CommonUtils.isEmailValid(email)) {
                    CommonUtils.showAlertDialog(CommonUtils.gettingString(R.string.error_plz_valid_email, mContext), 0, mContext);
                } else if (model!=null){
                    editClient(model.getUserId(),fName, lName, mobile, telephone, email, bDay + " " + bMonth, bYear, gender, clientNoteShow, clientNotes, acceptNotification, notification_via);
                }else {
                    addNewClient(fName, lName, mobile, telephone, email, bDay + " " + bMonth, bYear, gender, clientNoteShow, clientNotes, acceptNotification, notification_via);
                }
                break;
        }
    }

    private void addNewClient(String fName, String lName, String mobile, String telephone, String email, String s, String bYear, String gender, String clientNoteShow, String clientNotes, String acceptNotification, String notification_via) {

        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);
            ApiClient.getClient().addNewClient(CommonUtils.getSaloonId(mContext), fName, lName, mobile, telephone, email, s, bYear, gender, clientNoteShow, clientNotes, acceptNotification, notification_via).enqueue(new Callback<JsonObjectResponse>() {
                @Override
                public void onResponse(Call<JsonObjectResponse> call, Response<JsonObjectResponse> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            AppDialogHelper.showAlertDialog(mContext, CommonUtils.gettingString(R.string.success, mContext),
                                    CommonUtils.gettingString(R.string.new_client_add_successfully, mContext), new AlertDialogListener() {
                                        @Override
                                        public void onCloseBtnClick() {
                                            ((ActHome) mContext).onBackPressed();
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

    private void editClient(String userId,String fName, String lName, String mobile, String telephone, String email, String s, String bYear, String gender, String clientNoteShow, String clientNotes, String acceptNotification, String notification_via) {

        if (CommonUtils.isConnectingToInternet(mContext)) {
            CommonUtils.showLoader(mContext);
            ApiClient.getClient().editClient(userId,CommonUtils.getSaloonId(mContext), fName, lName, mobile, telephone, email, s, bYear, gender, clientNoteShow, clientNotes, acceptNotification, notification_via).enqueue(new Callback<JsonObjectResponse>() {
                @Override
                public void onResponse(Call<JsonObjectResponse> call, Response<JsonObjectResponse> response) {
                    CommonUtils.dismissLoader();
                    Log.e(TAG, "url       ===== " + call.request().url());
                    Log.e(TAG, "response  ===== " + new Gson().toJson(response.body()));
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().getRESULT().equalsIgnoreCase(Constant.RESPONSE_RESULT_YES)) {
                            AppDialogHelper.showAlertDialog(mContext, CommonUtils.gettingString(R.string.success, mContext),
                                    CommonUtils.gettingString(R.string.edit_client_successfully, mContext), new AlertDialogListener() {
                                        @Override
                                        public void onCloseBtnClick() {
                                            ((ActHome) mContext).onBackPressed();
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
