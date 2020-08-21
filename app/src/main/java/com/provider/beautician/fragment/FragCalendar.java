package com.provider.beautician.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.provider.beautician.R;
import com.provider.beautician.activity.ActHome;
import com.provider.beautician.helpers.CalendarView2;
import com.provider.beautician.helpers.CommonUtils;
import com.provider.beautician.helpers.FragCalendarView;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragCalendar extends Fragment {

    private Context mContext;
    private View rootView;
    private boolean mLoaded;
    private FragCalendarView mCalenderView;
    private ImageView imgBack, imgClose;
    private TextView txtHeader;
    public Date date;
    public int position = 0;

    public FragCalendar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null)
            rootView = inflater.inflate(R.layout.frag_calendar, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLoaded) {
            mLoaded = true;
            mContext = getActivity();
            initView();
        }
    }

    private void initView() {
        mCalenderView = rootView.findViewById(R.id.frag_calender_CalenderView);
        imgBack = rootView.findViewById(R.id.toolbar2_imgBack);
        imgClose = rootView.findViewById(R.id.toolbar2_imgMenu);
        txtHeader = rootView.findViewById(R.id.toolbar2_txtHeaderName);

        imgBack.setVisibility(View.INVISIBLE);
        imgClose.setVisibility(View.VISIBLE);
        imgClose.setImageResource(R.drawable.ic_close);
        txtHeader.setText(CommonUtils.gettingString(R.string.select_date, mContext));
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActHome) mContext).onBackPressed();
            }
        });
        mCalenderView.setUpdateCalendar(position, date);
        mCalenderView.setEventHandler(new FragCalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date selectDate, int selectPosition) {
                if (FragDailySales.getInstance()!=null){
                    FragDailySales.getInstance().setDate(selectDate,selectPosition);
                }
                ((ActHome)mContext).onBackPressed();
            }
        });
    }
}
