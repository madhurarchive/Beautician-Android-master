package com.provider.beautician.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.adapter.BottomSheetAdapter;
import com.provider.beautician.adapter.DailySalesTransactionAdapter;
import com.provider.beautician.app.MyAndroidApp;
import com.provider.beautician.listners.AlertDialogListener;
import com.provider.beautician.listners.AppMaintenanceDialogListener;
import com.provider.beautician.listners.BottomSheetListener;
import com.provider.beautician.listners.CustomDatePickerListener;
import com.provider.beautician.listners.CustomDatePickerListener2;
import com.provider.beautician.listners.NoInternetDialogListener;
import com.provider.beautician.listners.TwoButtonAlertDialogListener;
import com.provider.beautician.model.BottomSheetModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;


/**
 * Created by Archive_PC_1 on 8/11/2018.
 */

public class AppDialogHelper {
    private static Dialog noInternetDialog = null;

    public static void showAlertDialog(Context mContext,String title,String msg, final AlertDialogListener listener) {
        final Dialog mDialog = new Dialog(mContext, R.style.CustomDialogTheme);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.setContentView(R.layout.dialog_alert);

        //Status 0 = Error, 1 = success, 2 = validation error

        TextView lblTitle   =   (TextView) mDialog.findViewById(R.id.dialog_alert_txt_title);
        TextView lblMsg     =   (TextView) mDialog.findViewById(R.id.dialog_alert_txt_msg);
        TextView btnClose   =   (TextView) mDialog.findViewById(R.id.dialog_alert_btn_ok);

        lblTitle.setText(title);
        lblMsg.setText(msg);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                listener.onCloseBtnClick();
            }
        });
        mDialog.show();
    }

    public static void showDialogWithTwoButton(Context mContext, String title, String msg, String cancelBtnText, String okBtnText, final TwoButtonAlertDialogListener twoButtonAlertDialogListener) {
        final Dialog mDialog = new Dialog(mContext, R.style.CustomDialogTheme);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(R.layout.dialog_with_two_button);

        TextView lblTitle  =   (TextView) mDialog.findViewById(R.id.dialog_txt_title);
        TextView lblMsg    =   (TextView) mDialog.findViewById(R.id.dialog_txt_desc);
        Button btnNo       =   (Button) mDialog.findViewById(R.id.dialog_btn_cancel);
        Button btnYes      =   (Button) mDialog.findViewById(R.id.dialog_btn_ok);

        lblTitle.setText(title);
        lblMsg.setText(msg);
        btnNo.setText(cancelBtnText);
        btnYes.setText(okBtnText);

        lblTitle.setTypeface(MyAndroidApp.getInstance().getMediumFont());
        lblMsg.setTypeface(MyAndroidApp.getInstance().getRegularFont());
        btnNo.setTypeface(MyAndroidApp.getInstance().getMediumFont());
        btnYes.setTypeface(MyAndroidApp.getInstance().getMediumFont());

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                twoButtonAlertDialogListener.onNoBtnClick();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                twoButtonAlertDialogListener.onYesBtnClick();
            }
        });

        mDialog.show();
    }

    public static void showAppInMaintenanceDialog(Context mContext, final AppMaintenanceDialogListener listener) {
        final Dialog mDialog = new Dialog(mContext, R.style.CustomDialogTheme);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.setContentView(R.layout.dialog_app_in_maintenance);

        TextView lblTitle  =   (TextView) mDialog.findViewById(R.id.dialog_txt_app_maintenance);
        Button btnClose    =   (Button) mDialog.findViewById(R.id.dialog_btn_close);

        lblTitle.setTypeface(MyAndroidApp.getInstance().getMediumFont());
        btnClose.setTypeface(MyAndroidApp.getInstance().getMediumFont());

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                listener.onCloseBtnClick();
            }
        });

        mDialog.show();
    }

    public static void showNoInternetDialog(Context mContext, final NoInternetDialogListener listener) {
        if (noInternetDialog == null){
            noInternetDialog = new Dialog(mContext, R.style.CustomDialogTheme);
            noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            noInternetDialog.setCanceledOnTouchOutside(false);
            noInternetDialog.setCancelable(false);
            noInternetDialog.setContentView(R.layout.dialog_no_internet);

            TextView txtNoInternet  = (TextView) noInternetDialog.findViewById(R.id.dialog_txt_no_internet);
            Button btnCancel        = (Button) noInternetDialog.findViewById(R.id.dialog_btn_cancel);
            Button btnRetry         = (Button) noInternetDialog.findViewById(R.id.dialog_btn_retry);

            txtNoInternet.setTypeface(MyAndroidApp.getInstance().getRegularFont());
            btnCancel.setTypeface(MyAndroidApp.getInstance().getRegularFont());
            btnRetry.setTypeface(MyAndroidApp.getInstance().getRegularFont());

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCloseBtnClick();
                }
            });

            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRetryBtnClick();
                }
            });

            noInternetDialog.show();
        }
    }

    public static void dismissNoInternetDialog(){
        if (noInternetDialog !=null && noInternetDialog.isShowing()){
            noInternetDialog.dismiss();
            noInternetDialog = null;
        }
    }

    public static void showDatePicker(final Context mContext, View view, int SelectDate, int SelectMonth, int selectedPosition, final CustomDatePickerListener mListener){
        final View mView = LayoutInflater.from(mContext).inflate(R.layout.custom_date_picker, null, false);
        final PopupWindow popUp = new PopupWindow(mView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, false);
        popUp.setTouchable(true);
        popUp.setFocusable(true);
        popUp.setOutsideTouchable(true);
        HashSet<Date> events = new HashSet<>();
        events.add(new Date());
        CalendarView cv = mView.findViewById(R.id.calendar_view);
        cv.selectDay = SelectDate;
        cv.selectMonth = SelectMonth;
        cv.setUpdateCalendar(selectedPosition);
        cv.setEventHandler(new CalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date date, int selectPosition, ArrayList<Date> dates, int startDay) {
                mListener.onSelectDate(date,selectPosition,startDay,dates);
                popUp.dismiss();
            }
        });
        //Solution
        popUp.showAsDropDown(view);
    }

    public static void showDatePickerWithCheck(final Context mContext, View view,Date date,Date selDate,boolean isStartDate,boolean isSelect, int selectedPosition, final CustomDatePickerListener2 mListener){
        final View mView = LayoutInflater.from(mContext).inflate(R.layout.custom_date_picker2, null, false);
        final PopupWindow popUp = new PopupWindow(mView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, false);
        popUp.setTouchable(true);
        popUp.setFocusable(true);
        popUp.setOutsideTouchable(true);
        CalendarView2 cv = mView.findViewById(R.id.calendar_view);
        cv.setUpdateCalendar(selectedPosition,date,selDate,isStartDate,isSelect);
        cv.setEventHandler(new CalendarView2.EventHandler() {
            @Override
            public void onDayLongPress(Date date, int selectPosition) {
                mListener.onSelectDate(date,selectPosition);
                popUp.dismiss();
            }
        });
        //Solution
        popUp.showAsDropDown(view);
    }

    public static void showBottomSheet(Context mContext, ArrayList<BottomSheetModel> arrayList, final BottomSheetListener mListener){
        try {
            View sheetView = ((Activity)mContext).getLayoutInflater().inflate(R.layout.bottom_sheet_item, null);
            final BottomSheetDialog mBottom = new BottomSheetDialog(mContext,R.style.CustomBottomSheetTheme);
            mBottom.setCancelable(true);
            mBottom.setCanceledOnTouchOutside(true);
            mBottom.setContentView(sheetView);
            RecyclerView recyclerView = sheetView.findViewById(R.id.bottom_sheet_item_recyclerView);
            LinearLayout layoutCancel = sheetView.findViewById(R.id.bottom_sheet_item_layoutCancel);
            layoutCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBottom.dismiss();
                }
            });
            BottomSheetAdapter bottomSheetAdapter = new BottomSheetAdapter(mContext, arrayList);
            recyclerView.setAdapter(bottomSheetAdapter);
            recyclerView.setLayoutManager(new NpaLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
            bottomSheetAdapter.setOnRecyclerViewItemClickListener(new BottomSheetAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClicked(int position) {
                        mBottom.dismiss();
                        mListener.onSelect(position);
                }
            });
            mBottom.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


